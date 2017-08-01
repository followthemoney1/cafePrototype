package com.example.leaditteam.cafeprototype.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.leaditteam.cafeprototype.activities.CallActivity;
import com.example.leaditteam.cafeprototype.R;
import com.example.leaditteam.cafeprototype.helpers.Constant;
import com.example.leaditteam.cafeprototype.helpers.ContactsHelper;

import java.util.HashMap;

/**
 * Created by leaditteam on 24.03.17.
 */

public class CallFragment extends Fragment implements View.OnClickListener {
    
    private HashMap<Integer, ContactsHelper> mMainHashMapForContacts = new HashMap<Integer, ContactsHelper>();
    
    public static CallFragment newInstance() {

        Bundle args = new Bundle();
        CallFragment fragment = new CallFragment();
        fragment.setArguments(args);
        return fragment;
    }
    
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_call, container, false);

        LinearLayout llContent = (LinearLayout) root.findViewById(R.id.linearLayout_reserved);
        llContent.setOnClickListener(this);

        return root;
    }
    public void setmMainHashMapForContacts(HashMap<Integer, ContactsHelper> mMainHashMapForContacts){
        this.mMainHashMapForContacts = mMainHashMapForContacts;
    }
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.linearLayout_reserved) {
            Intent i = new Intent(v.getContext(), CallActivity.class);
            i.putExtra(Constant.mainHashmapForContacts.name(), mMainHashMapForContacts);
            startActivity(i);
        }
    }
}
