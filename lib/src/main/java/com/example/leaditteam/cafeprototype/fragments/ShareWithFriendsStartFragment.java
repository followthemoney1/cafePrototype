package com.example.leaditteam.cafeprototype.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.leaditteam.cafeprototype.activities.MainProductsActivity;
import com.example.leaditteam.cafeprototype.activities.ShareWithFriendsSecondActivity;
import com.example.leaditteam.cafeprototype.R;
import com.example.leaditteam.cafeprototype.helpers.AnimationHelperKt;
import com.example.leaditteam.cafeprototype.helpers.Constant;

/**
 * Created by leaditteam on 24.03.17.
 */

public class ShareWithFriendsStartFragment extends Fragment {
    protected RelativeLayout goToShareWithFriends;
    private int mPromoCode;
    
    public static ShareWithFriendsStartFragment newInstance(int PROMO_CODE) {
        
        Bundle args = new Bundle();
    
        args.putInt(Constant.PROMO_CODE.name(), PROMO_CODE);
        
        ShareWithFriendsStartFragment fragment = new ShareWithFriendsStartFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_share_with_friends_start, container, false);
        addToolBar();
    
        goToShareWithFriends = (RelativeLayout) root.findViewById(R.id.share_with_friends);
        AnimationHelperKt.fadeIn(goToShareWithFriends,(int)300L);
        
        //animation top images
        AnimationHelperKt.fadeIn(root.findViewById(R.id.imageView_sharewithfriends_fragment),(int)100L);
        
        goToShareWithFriends.setOnClickListener(goToShare);
        mPromoCode = getArguments().getInt(Constant.PROMO_CODE.name());
        
        return root;
    }
    

    View.OnClickListener goToShare = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(getActivity(), ShareWithFriendsSecondActivity.class);
            Bundle b = new Bundle();
            b.putInt(Constant.PROMO_CODE.name(), mPromoCode);
            i.putExtras(b);
            startActivity(i);
        }
    };
    private void addToolBar() {
        //add Action Bar
        ((MainProductsActivity)getActivity()).getSupportActionBar().setTitle(getString(R.string.nav_share));
        ActionBar mActionBar = ((MainProductsActivity)getActivity()).getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
    }
}
