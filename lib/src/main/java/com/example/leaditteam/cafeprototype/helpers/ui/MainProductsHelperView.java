package com.example.leaditteam.cafeprototype.helpers.ui;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.leaditteam.cafeprototype.fragments.CallFragment;
import com.example.leaditteam.cafeprototype.fragments.ProductFragment;
import com.example.leaditteam.cafeprototype.helpers.Constant;
import com.example.leaditteam.cafeprototype.helpers.containers.ContainerForData;
import com.example.leaditteam.cafeprototype.jsonhelper.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by leaditteam on 12.05.17.
 */

public class MainProductsHelperView {
    //arrays
    private ArrayList<Product> mTempHashmapProduct = new ArrayList<>();
    private ArrayList<String> mArListName = new ArrayList();
    private ArrayList<String> mArListDescription = new ArrayList();
    private ArrayList<Integer> mArListPrice = new ArrayList();
    private ArrayList<String> mArListUrlImage = new ArrayList();
    private ArrayList<String> mArListInfo = new ArrayList();
    
    private List<ProductFragment> mProdctFragment = new ArrayList<>();
    
    /**
     * Creating fragment which data don't be change
     *
     * @param llRoot
     */
    public void createStaticFragment(LinearLayout llRoot, FragmentManager mFragmentManager, Fragment fragment, Context context, String tag) {
        if (fragment != null){
            mFragmentManager.popBackStackImmediate(tag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        mFragmentManager.beginTransaction()
                .replace(createContainerForFragmentFirebase(llRoot, context), fragment, tag)
                .addToBackStack(tag)
                .commit();
    }
    
    /**
     * Parsing data which we get from GetDataFromFirebase class and parsing it before we creating
     * fragment. Then we create fragment and put it this data. Cool :)
     *
     * @param root
     */
    public void createFragmentFromDataFirebase(LinearLayout root, FragmentManager mFragmentManager, Context context, HashMap<Integer, ContainerForData> mainHashmapForData, int USER_COINS) {
            for (int i = 0; i < mainHashmapForData.size(); i++) {
                //clear data
                ContainerForData conteier = mainHashmapForData.get(i); // get data
                
                parseMainHashMapToArrays(mainHashmapForData,i);
                
                String nameOfBlock = conteier.getNameOfBlock();
                int mNumberOfProduct = Integer.parseInt(conteier.getmNumberOfProduct());
                Boolean mShowCoins = conteier.getmShowCoins();
                //create class
                mProdctFragment.add(i, ProductFragment.newInstance(
                        new ArrayList<String>(mArListName),
                        new ArrayList<String>(mArListDescription),
                        new ArrayList<Integer>(mArListPrice),
                        new ArrayList<String>(mArListUrlImage),
                        new ArrayList<String>(mArListInfo),
                        mNumberOfProduct,
                        nameOfBlock,
                        true,
                        mShowCoins,
                        USER_COINS));
                String tag = "prod" + String.valueOf(i);
                mFragmentManager.beginTransaction()
                        .replace(createContainerForFragmentFirebase(root, context), mProdctFragment.get(i), tag)
                        .addToBackStack(tag)
                        .commit();
            }
    }
    
    public Boolean updateViews(HashMap<Integer, ContainerForData> mainHashmapForData) {
        if ((mainHashmapForData.size() == mProdctFragment.size())&&(mainHashmapForData.size()!=0)) {
            for (int i = 0; i < mProdctFragment.size(); i++) {
                ProductFragment fragmentManager = (ProductFragment) mProdctFragment.get(i);
                parseMainHashMapToArrays(mainHashmapForData,i);
                fragmentManager.notifiAdapter(mArListName,
                        mArListDescription,
                        mArListPrice,
                        mArListUrlImage,
                        mArListInfo,
                        Integer.parseInt(mainHashmapForData.get(i).getmNumberOfProduct()));
            }
            return true;
        }else
            return false;
    }
    
    public void updateCoinsInFragment(int coins) {
        for (int i = 0; i < mProdctFragment.size(); i++) {
            mProdctFragment.get(i).setmUserCoins(coins);
        }
    }
    private void parseMainHashMapToArrays(HashMap<Integer, ContainerForData> mainHashmapForData, int i){
        mArListName.clear();
        mArListDescription.clear();
        mArListInfo.clear();
        mArListPrice.clear();
        mArListUrlImage.clear();
        
        mTempHashmapProduct.clear();
        mTempHashmapProduct.addAll(mainHashmapForData.get(i).getTemp_heshmap());
    
        for (int j = 0; j != mTempHashmapProduct.size(); j++) {
        
            Product temp_product = mTempHashmapProduct.get(j);//parsing
            mArListName.add(temp_product.getTITLE());
            mArListDescription.add(temp_product.getDESCRIPTION());
            mArListInfo.add(temp_product.getINFO());
            mArListPrice.add(temp_product.getCOINS());
            mArListUrlImage.add(temp_product.getIMG());
        
        }
    }
    /**
     * Creating conteiner for CardGuestFragment and CallFragment
     *
     * @param llRoot
     * @return
     */
    private int createContainerForFragmentFirebase(LinearLayout llRoot, Context context) {
        FrameLayout frameLayout = new FrameLayout(context);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        frameLayout.setLayoutParams(layoutParams);
        int randomValue = 1000 + (int) (Math.random() * 10000);
        frameLayout.setId(Integer.MAX_VALUE - randomValue);
        
        llRoot.addView(frameLayout);
        llRoot.requestLayout();
        
        return frameLayout.getId();
    }
}
