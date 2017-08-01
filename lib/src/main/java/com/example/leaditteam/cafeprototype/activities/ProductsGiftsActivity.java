package com.example.leaditteam.cafeprototype.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.GridView;

import com.example.leaditteam.cafeprototype.adapters.AdapterForProductsGifts;
import com.example.leaditteam.cafeprototype.R;
import com.example.leaditteam.cafeprototype.helpers.Constant;

import java.util.ArrayList;

/**
 * Created by leaditteam on 17.03.17.
 */

public class ProductsGiftsActivity extends AppCompatActivity {

    protected GridView mGridView;
    private ArrayList mArListName;
    private ArrayList mArListDescription;
    private ArrayList mArListPrice;
    private ArrayList mArListUrlImage;
    private ArrayList mArListInfo;
    private int mNumber;
    private Boolean mShowCoins;
    private String mNameOfBlock;
    private Boolean mIfThisIsMainActivity;

    final private int NUMBER_OF_COLUMNS = 2;
    private int mUserCoins;
    private static Boolean isRunning = false;
    public void init(Bundle savedInstanceState) {
        
        mUserCoins = savedInstanceState.getInt(Constant.USERS_COINS.name());
        mArListName = savedInstanceState.getStringArrayList(Constant.bundleArListName.name());
        mArListDescription = savedInstanceState.getStringArrayList(Constant.bundleArListDescription.name());
        mArListPrice = savedInstanceState.getIntegerArrayList(Constant.bundleArListPrice.name());
        mArListUrlImage = savedInstanceState.getStringArrayList(Constant.bundleArListUrl.name());
        mArListInfo = savedInstanceState.getStringArrayList(Constant.bundleArListInfo.name());
        mNumber = savedInstanceState.getInt(Constant.bundleNumber.name());
        mNameOfBlock = savedInstanceState.getString(Constant.bundleNameB.name());
        mIfThisIsMainActivity = false;
        mShowCoins = savedInstanceState.getBoolean(Constant.bundleShowCoins.name());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_gifts);
        //get arguments
        Bundle extras = getIntent().getExtras();
        init(extras);

        addToolBar();

        //set GridView
        mGridView = (GridView) findViewById(R.id.GridViewGiftsProducts);
        mGridView.setNumColumns(NUMBER_OF_COLUMNS); //number of colums
        mGridView.setColumnWidth(GridView.AUTO_FIT);

        //set adapter
        mGridView.setAdapter(new AdapterForProductsGifts(
                mArListName,
                mArListDescription,
                mArListPrice,
                mArListUrlImage,
                mArListInfo,
                this,
                mIfThisIsMainActivity,
                mShowCoins,
                mNumber,
                mUserCoins
        ));
    }
    public void setmUserCoins(int userCoins){
        this.mUserCoins = userCoins;
        invalidateOptionsMenu();
    }
    public static Boolean isRunning(){
        return isRunning;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_bar, menu);
        menu.findItem(R.id.filter).setTitle(String.valueOf(mUserCoins));
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
    private void addToolBar(){
        //add Action Bar
        Toolbar mToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setTitle(mNameOfBlock);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        invalidateOptionsMenu();
    }
    
    @Override
    protected void onStart() {
        super.onStart();
        isRunning = true;
    }
    
    @Override
    protected void onStop() {
        super.onStop();
        isRunning = false;
    }
}
