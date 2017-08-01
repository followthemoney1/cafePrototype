package com.example.leaditteam.cafeprototype.fragments;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.leaditteam.cafeprototype.activities.MainProductsActivity;
import com.example.leaditteam.cafeprototype.adapters.HintSpinnerAdapter;
import com.example.leaditteam.cafeprototype.GetDataFromFirebase;
import com.example.leaditteam.cafeprototype.R;
import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class TechnicalSupportFragment extends Fragment {

    protected TextView mName;
    
    public static TechnicalSupportFragment newInstance() {
        
        Bundle args = new Bundle();
        
        TechnicalSupportFragment fragment = new TechnicalSupportFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_tehnical_support, container, false);
        addToolBar();
        setUpSettings(root);
        mName = (TextView) root.findViewById(R.id.name_sup_tv);
       
        return root;
    }
    
    private void setUpSettings(View root){
        Spinner mSpinner = (Spinner) root.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> mAdapterForSpinner = ArrayAdapter.createFromResource(getContext(),
                R.array.tex_name, R.layout.spinner_text_clicked);
        mAdapterForSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.getBackground().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
        mSpinner.setAdapter(new HintSpinnerAdapter(
                mAdapterForSpinner, R.layout.hint_row_item, getContext()));

        //firebase datad user get name
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("USERS").child(GetDataFromFirebase.PATH_TO_USER);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    mName.setText(dataSnapshot.child("FIRST_NAME").getValue().toString());
                } catch (Exception e) {
                    Log.d("E data change Tech Sup", e.getMessage());
                    FirebaseCrash.report(new Exception(e.getMessage()));
                }
                ;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                FirebaseCrash.report(new Exception(databaseError.getMessage()));
            }
        });
    }
    private void addToolBar(){
        //add Action Bar
        ((MainProductsActivity)getActivity()).getSupportActionBar().setTitle(getString(R.string.nav_support));
        ActionBar mActionBar = ((MainProductsActivity)getActivity()).getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        ////
    }
}