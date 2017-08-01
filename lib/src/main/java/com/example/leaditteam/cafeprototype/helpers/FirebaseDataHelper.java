package com.example.leaditteam.cafeprototype.helpers;

import com.example.leaditteam.cafeprototype.helpers.containers.ContainerForData;
import com.example.leaditteam.cafeprototype.helpers.containers.ContainerForMenu;
import com.example.leaditteam.cafeprototype.jsonhelper.Product;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Created by leaditteam on 12.06.17.
 */

public class FirebaseDataHelper {
    private Boolean is_show_coins;
    private String menuName;
    private String menuUrl;
    private int iterationForMenu;
    
    /**
     * Тело метода getProducts, а именно парсинг полученных данных в mainHashMapForData
     *
     * @param dataSnapshot
     */
    public HashMap<Integer, ContainerForData> creatingMainHashmapForData(DataSnapshot dataSnapshot) {
        int iterationForMainHashmap = 0;
        HashMap<Integer, ContainerForData> mHashmapForData = new HashMap();
        mHashmapForData.clear(); // clear
        
        for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
            //main data
            int iterationForConstructor = 0;
            ArrayList<Product> temp_heshmap = new ArrayList<>();
            
            for (DataSnapshot messageSnapshot2 : messageSnapshot.child("ITEMS").getChildren()) {
                //progress show its news product // main data
                Product product = messageSnapshot2.getValue(Product.class);
                temp_heshmap.add(iterationForConstructor, product);
                iterationForConstructor += 1;
            }
            Collections.reverse(temp_heshmap);
            
            //main data
            String mNumberOfProduct = messageSnapshot.child("NUMBER_OF_PRODUCTS").getValue().toString();
            String nameOfBlock = messageSnapshot.child("TITLE").getValue().toString();
            Boolean mShowCoins = Boolean.parseBoolean(messageSnapshot.child("SHOW_COINS").getValue().toString());
            
            //put to main hashmap
            mHashmapForData.put(iterationForMainHashmap,
                    new ContainerForData(mShowCoins, nameOfBlock, mNumberOfProduct, temp_heshmap));
            iterationForMainHashmap += 1;
            
        }
        return mHashmapForData;
    }
    
    public HashMap createMainHashMapForLocation(DataSnapshot dataSnapshot){
        int i = 0;
        HashMap mainHashmapForLocation = new HashMap();
        for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
            String Description = messageSnapshot.child("DESCRIPTION").getValue().toString();
            double Latitude = Double.parseDouble(messageSnapshot.child("LATITUDE").getValue().toString());
            double Longitude = Double.parseDouble(messageSnapshot.child("LONGITUDE").getValue().toString());
            String Title = messageSnapshot.child("TITLE").getValue().toString();
        
            mainHashmapForLocation.put(i, new LocationHelper(Description, Latitude, Longitude, Title));
            i++;
        }
        return mainHashmapForLocation;
    }
    
    public HashMap createMainHashMapForContacts(DataSnapshot dataSnapshot){
        int i = 0;
        HashMap mHashmapForContacts = new HashMap();
        for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
            String s = messageSnapshot.child("ADDRESS").getValue().toString();
            String s1 = messageSnapshot.child("PHONE_NUMBER").getValue().toString();
            mHashmapForContacts.put(i, new ContactsHelper(s, s1));
            i++;
        }
        return mHashmapForContacts;
    }
    
    public HashMap createMainHashMapForMenu(DataSnapshot dataSnapshot){
        //clear data
        iterationForMenu = 0;
        HashMap mHashmapForMenu = new HashMap();
    
        for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
        
            for (DataSnapshot messageSnapshot2 : messageSnapshot.getChildren()) {
                is_show_coins = Boolean.parseBoolean(messageSnapshot.child("IS_SHOW").getValue().toString());
                menuName = messageSnapshot.child("NAME").getValue().toString();
                menuUrl = messageSnapshot.child("URL").getValue().toString();
            }
        
            //put to main map menu
            mHashmapForMenu.put(iterationForMenu, new ContainerForMenu(is_show_coins, menuName, menuUrl));
            iterationForMenu += 1;
        }
        return mHashmapForMenu;
    }
    
}
