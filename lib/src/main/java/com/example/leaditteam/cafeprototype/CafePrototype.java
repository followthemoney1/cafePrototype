package com.example.leaditteam.cafeprototype;

import android.app.Application;
import android.graphics.Color;

import com.facebook.FacebookSdk;
import com.google.firebase.database.FirebaseDatabase;
import com.vk.sdk.VKSdk;

/**
 * Created by leaditteam on 31.03.17.
 */

public class CafePrototype extends Application {

    private CafePrototype mainFactory;
    
    private String googleAuthStringApiRes;
    
    @Override
    public void onCreate() {
        super.onCreate();
        //init vk
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        VKSdk.initialize(this);
        mainFactory = createMainFactory();
        FacebookSdk.sdkInitialize(getApplicationContext());
        FacebookSdk.setApplicationId(getFacebookSdkStringApi());
        googleAuthStringApiRes = getGoogleAuthStringApiRes();
    }
    protected CafePrototype createMainFactory() {
        return new CafePrototype();
    }
    
    public String getGoogleAuthStringApiRes() {
        return googleAuthStringApiRes;
    }
    
    public String getFacebookSdkStringApi(){
       return getString(R.string.facebook_app_id);
    }
 
}
