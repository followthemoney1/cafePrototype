package com.example.leaditteam.cafeprototype.fragments;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.leaditteam.cafeprototype.activities.MainProductsActivity;
import com.example.leaditteam.cafeprototype.activities.MapActivity;
import com.example.leaditteam.cafeprototype.R;
import com.example.leaditteam.cafeprototype.helpers.AnimationHelperKt;
import com.example.leaditteam.cafeprototype.helpers.Constant;

import java.util.HashMap;

public class AboutFragment extends Fragment {
    private HashMap mMainHashmapForLocation;
    
    public static AboutFragment newInstance(HashMap mainHashmapForLocation) {

        Bundle args = new Bundle();
        args.putSerializable(Constant.mainHashmapForLocation.name(), mainHashmapForLocation);
        AboutFragment fragment = new AboutFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_about, container, false);
        //set action
        ImageView ivGoToMap = (ImageView) root.findViewById(R.id.image_map_aboutactivity);
        ivGoToMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMap();
            }
        });
        AnimationHelperKt.fadeIn(ivGoToMap,(int)300L);

        TextView tvGoToFacebook = (TextView) root.findViewById(R.id.facebook_abotactivity_textview);
        tvGoToFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToFacebook();
            }
        });
        AnimationHelperKt.fadeIn(tvGoToFacebook,(int)300L);

        LinearLayout llGoToSite = (LinearLayout) root.findViewById(R.id.go_to_site_layout);
        llGoToSite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSite();
            }
        });
        AnimationHelperKt.fadeIn(llGoToSite,(int)300L);
        
        //image animation top
        AnimationHelperKt.fadeIn(root.findViewById(R.id.imageView_about_activity_top), (int)200L);
    
        mMainHashmapForLocation = (HashMap) getArguments().getSerializable(Constant.mainHashmapForLocation.name());
        
        changeToolBar();
        

        return root;
    }

    private void changeToolBar() {
        //toolbar
        //add Action Bar
        ((MainProductsActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.nav_about));
        ((MainProductsActivity) getActivity()).getSupportActionBar().setElevation(0.8f);
        ActionBar ab = ((MainProductsActivity) getActivity()).getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ////
    }

    private void openMap() {
        Intent i = new Intent(getActivity(), MapActivity.class);
        i.putExtra(Constant.mainHashmapForLocation.name(), mMainHashmapForLocation);
        startActivity(i);
    }

    private void goToFacebook() {
        String url = "https://www.facebook.com/facebook/?fref=ts";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    private void goToSite() {
        String url = "https://www.google.com";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
}
