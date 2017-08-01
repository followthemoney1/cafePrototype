package com.example.leaditteam.cafeprototype.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.leaditteam.cafeprototype.activities.AddToBalanceFirstActivity;
import com.example.leaditteam.cafeprototype.R;
import com.example.leaditteam.cafeprototype.helpers.Constant;

/**
 * Created by leaditteam on 10.03.17.
 */

public class CardGuestFragment extends Fragment {
    
    private int mUserCoins;
    
    public static CardGuestFragment newInstance() {

        Bundle args = new Bundle();
        CardGuestFragment fragment = new CardGuestFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.card_guesst_fragment, container, false);
        
        mUserCoins = getArguments().getInt(Constant.USERS_COINS.name());
        LinearLayout mLinearLayout = (LinearLayout) root.findViewById(R.id.linearLayout_cardGuesst);
        mLinearLayout.setOnClickListener(linearLayoutOnClick);

        return root;
    }
    public void setmUserCoins(int userCoins){
        this.mUserCoins = userCoins;
    }
    View.OnClickListener linearLayoutOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(v.getContext(), AddToBalanceFirstActivity.class);
            i.putExtra(Constant.USERS_COINS.name(), mUserCoins);
            startActivity(i);
        }
    };
    
}
