package com.example.leaditteam.cafeprototype.helpers.notification;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.NotificationCompat;

import com.example.leaditteam.cafeprototype.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.os.Process.THREAD_PRIORITY_BACKGROUND;
import static com.example.leaditteam.cafeprototype.GetDataFromFirebase.PATH_TO_USER;

/**
 * Created by leaditteam on 03.04.17.
 */

public class ServiceNotification  extends Service {
    private Looper mServiceLooper;
    private ServiceHandler mServiceHandler;

    private final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);
        }
        @Override
        public void handleMessage(Message msg) {
            long endTime = System.currentTimeMillis() + 1*10000;
            while (System.currentTimeMillis() < endTime) {
                synchronized (this) {
                    try {
                        wait(endTime - System.currentTimeMillis());
                    } catch (Exception e) {
                    }
                }
            }
            stopSelf(msg.arg1);
        }
    }

    @Override
    public void onCreate() {

        HandlerThread thread = new HandlerThread("ServiceStartArguments",
                THREAD_PRIORITY_BACKGROUND);
        thread.start();

        mServiceLooper = thread.getLooper();
        mServiceHandler = new ServiceHandler(mServiceLooper);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Message msg = mServiceHandler.obtainMessage();
        msg.arg1 = startId;
        mServiceHandler.sendMessage(msg);

            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("USERS").child(PATH_TO_USER).child("HOW_MUCH_REF");
            final Boolean[] ifNeedRefresh = {true};
            reference.addValueEventListener( new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    try {
                        if (!ifNeedRefresh[0] && !dataSnapshot.getValue().toString().equals("0") ) {
                            NotificationCompat.Builder builder = new NotificationCompat.Builder(ServiceNotification.this);
                            builder.setSmallIcon(R.drawable.app_indicator);
                            builder.setContentTitle("Вам было начислено +15 монет.");
                            builder.setContentText("По вашему промокоду зарегистрироватся пользователь.");

                            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                            builder.setSound(defaultSoundUri);

                            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

                            notificationManager.notify(1, builder.build());

                        } else ifNeedRefresh[0] = false;

                    } catch (Exception e) {
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public boolean stopService(Intent name) {
        stopSelf();
        return super.stopService(name);

    }

    @Override
    public void onDestroy() {
       super.onDestroy();
    }
}
