package com.example.leaditteam.cafeprototype.helpers;

import android.content.Context;
import android.support.design.widget.NavigationView;

import com.example.leaditteam.cafeprototype.helpers.containers.ContainerForData;
import com.example.leaditteam.cafeprototype.helpers.containers.ContainerForMenu;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by leaditteam on 15.06.17.
 */

public class FirebaseDataConstructor {
    private HashMap<Integer, ContainerForData> mHashMapForData = new HashMap<Integer, ContainerForData>();
    private HashMap<Integer, ContainerForMenu> mHashMapForMenu = new HashMap<Integer, ContainerForMenu>();
    private HashMap<Integer, ContactsHelper> mHashMapForContacts = new HashMap<Integer, ContactsHelper>();
    private HashMap<Integer, LocationHelper> mHashMapForLocation = new HashMap<Integer, LocationHelper>();
    
    private ArrayList<String> mArListIconDrawer = new ArrayList();
    private ArrayList<String> mArListNameDrawer = new ArrayList();
    private ArrayList<Boolean> mArListShowDrawer = new ArrayList();
    
    public int usersCoins;
    public int promoCode;
    
    public FirebaseDataHelper firebaseDataHelper = new FirebaseDataHelper();
    
    private Context context;
    private NavigationView mNavigationView;
    
    public void parsingDataToNavigation() {
        mArListIconDrawer.clear();
        mArListNameDrawer.clear();
        mArListShowDrawer.clear();
        if (getmHashMapForMenu().size() != 0) {
            for (int i = 0; i != getmHashMapForMenu().size(); i++) {
                ContainerForMenu containerForMenu = getmHashMapForMenu().get(i);
                mArListIconDrawer.add(containerForMenu.getMenuUrl());
                mArListNameDrawer.add(containerForMenu.getMenuName());
                mArListShowDrawer.add(containerForMenu.getIsNeedShowCoins());
            }
        }
    }
    
    public FirebaseDataConstructor setContext(Context context) {
        this.context = context;
        return this;
    }
    
    public FirebaseDataConstructor setmNavigationView(NavigationView mNavigationView) {
        this.mNavigationView = mNavigationView;
        return this;
    }
    
    public HashMap<Integer, ContainerForMenu> getmHashMapForMenu() {
        return mHashMapForMenu;
    }
    
    public void setmHashMapForMenu(HashMap<Integer, ContainerForMenu> mHashMapForMenu) {
        this.mHashMapForMenu = mHashMapForMenu;
    }
    
    public HashMap<Integer, ContactsHelper> getmHashMapForContacts() {
        return mHashMapForContacts;
    }
    
    public void setmHashMapForContacts(HashMap<Integer, ContactsHelper> mHashMapForContacts) {
        this.mHashMapForContacts = mHashMapForContacts;
    }
    
    public HashMap<Integer, LocationHelper> getmHashMapForLocation() {
        return mHashMapForLocation;
    }
    
    public void setmHashMapForLocation(HashMap<Integer, LocationHelper> mHashMapForLocation) {
        this.mHashMapForLocation = mHashMapForLocation;
    }
    
    public ArrayList<String> getmArListIconDrawer() {
        return mArListIconDrawer;
    }
    
    public void setmArListIconDrawer(ArrayList<String> mArListIconDrawer) {
        this.mArListIconDrawer = mArListIconDrawer;
    }
    
    public ArrayList<String> getmArListNameDrawer() {
        return mArListNameDrawer;
    }
    
    public void setmArListNameDrawer(ArrayList<String> mArListNameDrawer) {
        this.mArListNameDrawer = mArListNameDrawer;
    }
    
    public ArrayList<Boolean> getmArListShowDrawer() {
        return mArListShowDrawer;
    }
    
    public void setmArListShowDrawer(ArrayList<Boolean> mArListShowDrawer) {
        this.mArListShowDrawer = mArListShowDrawer;
    }
    
    public int getUsersCoins() {
        return usersCoins;
    }
    
    public void setUsersCoins(int usersCoins) {
        this.usersCoins = usersCoins;
    }
    
    public int getPromoCode() {
        return promoCode;
    }
    
    public void setPromoCode(int promoCode) {
        this.promoCode = promoCode;
    }
    
    public HashMap<Integer, ContainerForData> getmHashMapForData() {
        return mHashMapForData;
    }
    
    public void setmHashMapForData(HashMap<Integer, ContainerForData> mHashMapForData) {
        this.mHashMapForData = mHashMapForData;
    }
}
