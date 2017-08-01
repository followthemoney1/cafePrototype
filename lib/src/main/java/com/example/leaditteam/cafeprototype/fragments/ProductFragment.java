package com.example.leaditteam.cafeprototype.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.example.leaditteam.cafeprototype.helpers.AnimationHelperKt;
import com.example.leaditteam.cafeprototype.helpers.Constant;
import com.example.leaditteam.cafeprototype.helpers.ui.MyGridView;
import com.example.leaditteam.cafeprototype.activities.ProductsGiftsActivity;
import com.example.leaditteam.cafeprototype.adapters.AdapterForProductsGifts;
import com.example.leaditteam.cafeprototype.R;

import java.util.ArrayList;

/**
 * Created by leaditteam on 17.03.17.
 */

public class ProductFragment extends Fragment implements View.OnClickListener {
    
    private ArrayList mName;
    private ArrayList mDescription;
    private ArrayList mPrice;
    private ArrayList mUrlImage;
    private ArrayList mInfo;
    
    private int mNumber;
    private Boolean mShowCoins;
    private String mNameOfBlock;
    
    private Boolean mIfThisIsMainActivity;
    private int mUserCoins;
    
    private AdapterForProductsGifts adapterForProductGifts;
    private MyGridView gridView;
    
    public static ProductFragment newInstance(ArrayList Name, ArrayList Description, ArrayList Price, ArrayList UrlImage, ArrayList Info,
                                              int Number, String mnameOfBlock, Boolean if_main_activity, Boolean ShowCoins, int userCoins) {
        
        ProductFragment myFragment = new ProductFragment();
        
        Bundle args = new Bundle();
        args.putStringArrayList(Constant.bundleArListName.name(), (Name));
        args.putStringArrayList(Constant.bundleArListDescription.name(), (Description));
        args.putIntegerArrayList(Constant.bundleArListPrice.name(), (Price));
        args.putStringArrayList(Constant.bundleArListUrl.name(), (UrlImage));
        args.putStringArrayList(Constant.bundleArListInfo.name(), (Info));
        args.putInt(Constant.bundleNumber.name(), Number);
        args.putString(Constant.bundleNameB.name(), mnameOfBlock);
        args.putBoolean(Constant.bundleIfMainActivity.name(), if_main_activity);
        args.putBoolean(Constant.bundleShowCoins.name(), ShowCoins);
        args.putInt(Constant.USERS_COINS.name(), userCoins);
        myFragment.setArguments(args);
        
        return myFragment;
    }
    
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mName = getArguments().getStringArrayList(Constant.bundleArListName.name());
        mDescription = getArguments().getStringArrayList(Constant.bundleArListDescription.name());
        mPrice = getArguments().getIntegerArrayList(Constant.bundleArListPrice.name());
        mUrlImage = getArguments().getStringArrayList(Constant.bundleArListUrl.name());
        mInfo = getArguments().getStringArrayList(Constant.bundleArListInfo.name());
        mNumber = getArguments().getInt(Constant.bundleNumber.name());
        mNameOfBlock = getArguments().getString(Constant.bundleNameB.name());
        mIfThisIsMainActivity = getArguments().getBoolean(Constant.bundleIfMainActivity.name());
        mShowCoins = getArguments().getBoolean(Constant.bundleShowCoins.name());
        if (mUserCoins == 0)
            mUserCoins = getArguments().getInt(Constant.USERS_COINS.name());
        
        super.onCreate(savedInstanceState);
    }
    
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_product_gift, container, false);
        
        ///set up name of top in fragment
        TextView name_on_block = (TextView) root.findViewById(R.id.TextViewGiftProductsFragment);
        name_on_block.setText(mNameOfBlock);
        
        gridView = (MyGridView) root.findViewById(R.id.GridViewGiftFragment);
        gridView.setNumColumns(2); //number of colums
        gridView.setColumnWidth(GridView.AUTO_FIT);
        
        //create adapter
        adapterForProductGifts = createAdapter();
        //set adapter
        gridView.setAdapter(adapterForProductGifts);
        
        CardView mCardView = (CardView) root.findViewById(R.id.cardView_productGift_fragment);
        mCardView.setOnClickListener(this);
        AnimationHelperKt.fadeIn(mCardView, (int) 500L);
        
        return root;
    }
    
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.cardView_productGift_fragment) {
            Intent intentProductsGifts = new Intent(getActivity().getApplicationContext(), ProductsGiftsActivity.class);
            Bundle args = new Bundle();
            args.putStringArrayList(Constant.bundleArListName.name(), (mName)); //important to push new array
            args.putStringArrayList(Constant.bundleArListDescription.name(), (mDescription));
            args.putIntegerArrayList(Constant.bundleArListPrice.name(), (mPrice));
            args.putStringArrayList(Constant.bundleArListUrl.name(), (mUrlImage));
            args.putStringArrayList(Constant.bundleArListInfo.name(), (mInfo));
            args.putInt(Constant.bundleNumber.name(), mNumber);
            args.putString(Constant.bundleNameB.name(), mNameOfBlock);
            args.putBoolean(Constant.bundleIfMainActivity.name(), mIfThisIsMainActivity);
            args.putBoolean(Constant.bundleShowCoins.name(), mShowCoins);
            args.putInt(Constant.USERS_COINS.name(), mUserCoins);
            intentProductsGifts.putExtras(args);
            startActivity(intentProductsGifts);
            return;
        } else {
            return;
        }
    }
    
    private AdapterForProductsGifts createAdapter() {
        return new AdapterForProductsGifts(
                mName,
                mDescription,
                mPrice,
                mUrlImage,
                mInfo,
                getActivity(),
                mIfThisIsMainActivity,
                mShowCoins,
                mNumber,
                mUserCoins
        );
    }
    
    public void notifiAdapter(ArrayList<String> mArListName,
                              ArrayList<String> mArListDescription, ArrayList<Integer> mArListPrice,
                              ArrayList<String> mArListUrlImage, ArrayList<String> mArListInfo, int mNumber) {
        if (adapterForProductGifts != null) {
            adapterForProductGifts.setmListName(mArListName);
            adapterForProductGifts.setmInfo(mArListInfo);
            adapterForProductGifts.setmUrlImage(mArListUrlImage);
            adapterForProductGifts.setmListDescription(mArListDescription);
            adapterForProductGifts.setmPrice(mArListPrice);
            adapterForProductGifts.setmNumber(mNumber);
        }
    }
    
    public void setmUserCoins(int mUserCoins) {
        this.mUserCoins = mUserCoins;
        
        if (adapterForProductGifts != null)
            adapterForProductGifts.setmUserCoins(mUserCoins);
        
        if (ProductsGiftsActivity.isRunning()) ((ProductsGiftsActivity)getContext()).setmUserCoins(mUserCoins);
        
    }
}
