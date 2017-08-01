package com.example.leaditteam.cafeprototype.helpers.containers;

import com.example.leaditteam.cafeprototype.jsonhelper.Product;

import java.util.ArrayList;

/**
 * Created by leaditteam on 20.03.17.
 */

public class ContainerForData implements java.io.Serializable {
    public void setNameOfBlock(String nameOfBlock) {
        this.nameOfBlock = nameOfBlock;
    }

    public void setmNumberOfProduct(String mNumberOfProduct) {
        this.mNumberOfProduct = mNumberOfProduct;
    }

    public void setTemp_heshmap(ArrayList<Product> temp_heshmap) {
        this.temp_heshmap = temp_heshmap;
    }

    public String getNameOfBlock() {

        return nameOfBlock;
    }

    public String getmNumberOfProduct() {
        return mNumberOfProduct;
    }

    public ArrayList<Product> getTemp_heshmap() {
        return temp_heshmap;
    }

    public void setmShowCoins(Boolean mShowCoins) {
        this.mShowCoins = mShowCoins;
    }

    public Boolean getmShowCoins() {

        return mShowCoins;
    }

    public ContainerForData(Boolean ShowCoints, String nameOfBlock, String mNumberOfProduct, ArrayList<Product> temp_heshmap) {
        this.nameOfBlock = nameOfBlock;
        this.mNumberOfProduct = mNumberOfProduct;
        this.temp_heshmap = temp_heshmap;
        this.mShowCoins = ShowCoints;


    }

    String nameOfBlock = new String();
     String mNumberOfProduct = new String();
    ArrayList<Product> temp_heshmap =new ArrayList<>();
    Boolean mShowCoins;
}
