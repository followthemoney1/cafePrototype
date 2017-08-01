package com.example.leaditteam.cafeprototype.helpers;

/**
 * Created by leaditteam on 20.04.17.
 */

public class ContactsHelper implements java.io.Serializable {
    String Adress;
    String PhoneNumber;

    public void setAdress(String adress) {
        Adress = adress;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getAdress() {

        return Adress;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public ContactsHelper(String adress, String phoneNumber) {

        Adress = adress;
        PhoneNumber = phoneNumber;
    }
}
