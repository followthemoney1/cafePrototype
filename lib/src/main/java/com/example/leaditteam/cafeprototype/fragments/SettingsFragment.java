package com.example.leaditteam.cafeprototype.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.leaditteam.cafeprototype.activities.ChangePersonalDataActivity;
import com.example.leaditteam.cafeprototype.activities.MainProductsActivity;
import com.example.leaditteam.cafeprototype.adapters.AdapterForSettings;
import com.example.leaditteam.cafeprototype.R;
import com.example.leaditteam.cafeprototype.helpers.AnimationHelperKt;
import com.example.leaditteam.cafeprototype.helpers.Constant;
import com.google.firebase.crash.FirebaseCrash;

public class SettingsFragment extends Fragment {
    
    
    private String[] itemName = {
            "Редактировать профиль"
    };
    private Integer[] itemImg = {
            R.drawable.ic_create_black_24dp
    };
    private int PROMO_CODE;
    
    public static SettingsFragment newInstance(int PROMO_CODE) {
        
        Bundle args = new Bundle();
        args.putInt(Constant.PROMO_CODE.name(), PROMO_CODE);
        SettingsFragment fragment = new SettingsFragment();
        fragment.setArguments(args);
        return fragment;
    }
    
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_settings, container, false);
        
        PROMO_CODE = getArguments().getInt(Constant.PROMO_CODE.name());
        
        addToolBar();
        
        init(root);
        
        return root;
    }
    
    private void init(View root) {
        TextView tvPromoCode = (TextView) root.findViewById(R.id.textView17);
        try {
            tvPromoCode.setText(String.valueOf(PROMO_CODE));
        } catch (Exception e) {
            FirebaseCrash.report(new Exception(e.getMessage()));
        }
        
        ListView lvSettings = (ListView) root.findViewById(R.id.lv_settings);
        lvSettings.setAdapter(new AdapterForSettings(itemName, itemImg, getActivity()));
        lvSettings.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    startActivity(new Intent(getActivity(), ChangePersonalDataActivity.class));
                }
            }
        });
        AnimationHelperKt.fadeIn(tvPromoCode, (int) 300L);
    }
    
    private void addToolBar() {
        ((MainProductsActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.nav_settings));
        ActionBar mActionBar = ((MainProductsActivity) getActivity()).getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
    }
    
}
