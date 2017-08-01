package com.example.leaditteam.cafeprototype;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.leaditteam.cafeprototype.activities.MainProductsActivity;
import com.example.leaditteam.cafeprototype.helpers.FirebaseDataConstructor;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by leaditteam on 14.03.17.
 */

public class GetDataFromFirebase {
    protected FirebaseDatabase database = FirebaseDatabase.getInstance();
    protected DatabaseReference reference;
    
    @NonNull
    public static String PATH_TO_USER = FirebaseAuth.getInstance().getCurrentUser().getUid();
    
    private int usersCoins;
    private int promoCode;
    
    private FirebaseDataConstructor firebaseDataConstructor;
    private MainProductsActivity mainProductsActivity;
    
    public GetDataFromFirebase(MainProductsActivity mainProductsActivity) {
        this.mainProductsActivity = mainProductsActivity;
    
        firebaseDataConstructor = new FirebaseDataConstructor();
        
        createMainHashMapForData();
        createMainHashMapForMenu();
        createMainHashMapForLocation();
        createCoinsAndPromoCodeData();
        createMainHashMapForContacts();
    }
    
    /**
     * Метод предназначен для получения данных из Firebase а именно получения данных в руте "OBJ"
     */
    private void createMainHashMapForData() {
        reference = database.getReference().child("MAIN_SCREEN");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    firebaseDataConstructor.setmHashMapForData(firebaseDataConstructor.firebaseDataHelper.creatingMainHashmapForData(dataSnapshot));
                    mainProductsActivity.updateViews(firebaseDataConstructor);
                } catch (Exception e) {
                    Log.d("Exception getProducts", e.getMessage());
                    FirebaseCrash.report(new Exception(e.getMessage()));
                }
                ;
            }
            
            @Override
            public void onCancelled(DatabaseError databaseError) {
                FirebaseCrash.report(new Exception(databaseError.getMessage()));
            }
        });
    }
    
    /**
     * Метод предназначен для получения данных из Firebase а именно получения данных в руте "MENU"
     */
    private void createMainHashMapForMenu() {
        reference = database.getReference().child("MENU");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    firebaseDataConstructor.setmHashMapForMenu(firebaseDataConstructor.firebaseDataHelper.createMainHashMapForMenu(dataSnapshot));
                    firebaseDataConstructor.parsingDataToNavigation();
                    
                    mainProductsActivity.createMenu(firebaseDataConstructor.getmArListIconDrawer(),
                            firebaseDataConstructor.getmArListNameDrawer(),
                            firebaseDataConstructor.getmArListShowDrawer());
                } catch (Exception e) {
                    FirebaseCrash.report(new Exception(e.getMessage()));
                }
            }
            
            @Override
            public void onCancelled(DatabaseError databaseError) {
                FirebaseCrash.report(new Exception(databaseError.getMessage()));
            }
        });
    }
    
    /**
     * Метод предназначен для получения данных из Firebase а именно получения данных в руте "USERS"
     */
    private void createCoinsAndPromoCodeData() {
        reference = database.getReference().child("USERS").child(PATH_TO_USER);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    usersCoins = Integer.parseInt(dataSnapshot.child("COINS").getValue().toString());
                    promoCode = Integer.parseInt(dataSnapshot.child("PROMO").getValue().toString());
                    
                    firebaseDataConstructor.setUsersCoins(usersCoins);
                    firebaseDataConstructor.setPromoCode(promoCode);
                    
                    mainProductsActivity.updateCoins(usersCoins);
                } catch (Exception e) {
                    FirebaseCrash.report(new Exception(e.getMessage()));
                }
            }
            
            @Override
            public void onCancelled(DatabaseError databaseError) {
                FirebaseCrash.report(new Exception(databaseError.getMessage()));
            }
        });
    }
    
    /**
     * Метод предназначен для получения данных из Firebase а именно получения данных в руте "CONTACTS"
     */
    private void createMainHashMapForContacts() {
        reference = database.getReference().child("RESTAURANTS").child("CONTACTS");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                firebaseDataConstructor.setmHashMapForContacts(firebaseDataConstructor.firebaseDataHelper.createMainHashMapForContacts(dataSnapshot));
                mainProductsActivity.updateCallFragmentHashMap(firebaseDataConstructor);
            }
            
            @Override
            public void onCancelled(DatabaseError databaseError) {
                
            }
        });
    }
    
    /**
     * Метод предназначен для получения данных из Firebase а именно получения данных в руте "LOCATION"
     */
    public void createMainHashMapForLocation() {
        reference = database.getReference().child("RESTAURANTS").child("LOCATION");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                firebaseDataConstructor.setmHashMapForLocation(firebaseDataConstructor.firebaseDataHelper.createMainHashMapForLocation(dataSnapshot));
            }
            
            @Override
            public void onCancelled(DatabaseError databaseError) {
                
            }
        });
    }
    
    public FirebaseDataConstructor getFirebaseDataConstructor() {
        return firebaseDataConstructor;
    }
}
