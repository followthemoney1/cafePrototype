package com.example.leaditteam.cafeprototype.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.leaditteam.cafeprototype.fragments.AboutFragment;
import com.example.leaditteam.cafeprototype.fragments.SettingsFragment;
import com.example.leaditteam.cafeprototype.fragments.ShareWithFriendsStartFragment;
import com.example.leaditteam.cafeprototype.fragments.TechnicalSupportFragment;
import com.example.leaditteam.cafeprototype.GetDataFromFirebase;
import com.example.leaditteam.cafeprototype.fragments.CallFragment;
import com.example.leaditteam.cafeprototype.fragments.CardGuestFragment;
import com.example.leaditteam.cafeprototype.helpers.Constant;
import com.example.leaditteam.cafeprototype.helpers.FirebaseDataConstructor;
import com.example.leaditteam.cafeprototype.helpers.ui.MainProductsHelperView;
import com.example.leaditteam.cafeprototype.R;
import com.example.leaditteam.cafeprototype.helpers.notification.ServiceNotification;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

/**
 * Created by leaditteam on 10.03.17.
 */

public class MainProductsActivity extends AppCompatActivity implements  DrawerLayout.DrawerListener {
    public static ProgressBar mainActivityProgressBar;
    
    private LinearLayout mRootView;
    private NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;
    
    private FragmentManager mFragmentManager = getSupportFragmentManager();
    private MainProductsHelperView mProductsHelper = new MainProductsHelperView();
    private Boolean mOnBackPress = false;
    
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mAuth;
    
    private CallFragment callFragment = CallFragment.newInstance();
    private CardGuestFragment cardGuestFragment = CardGuestFragment.newInstance();
    
    private GetDataFromFirebase dataFromFirebase;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_products_activity);
    
        //add Action Bar
        addToolBar();
        
        initView();
        
        //initialize firebase
        setUpAuthentication();
        
        //notification coins
        Intent intent = new Intent(getBaseContext(), ServiceNotification.class);
        startService(intent);
        
    }
    
    private void initView() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mRootView = (LinearLayout) findViewById(R.id.root_linear_layout);
        mainActivityProgressBar = (ProgressBar) findViewById(R.id.main_products_progressBar);
        mDrawerLayout.setDrawerListener(this);
    
        dataFromFirebase = new GetDataFromFirebase(this);
    }
    
    public void createViews(FirebaseDataConstructor firebaseDataConstructor) {
        MainProductsActivity.mainActivityProgressBar.setVisibility(View.INVISIBLE);
        mRootView.removeAllViews();
        getSupportActionBar().setTitle(R.string.app_name);
        
        cardGuestFragment.setmUserCoins(firebaseDataConstructor.getUsersCoins());
        mProductsHelper.createStaticFragment(mRootView,
                mFragmentManager,
                cardGuestFragment,
                this,
                Constant.TAG_CARD_FRAGMENT.name());
        
        mProductsHelper.createFragmentFromDataFirebase(mRootView,
                mFragmentManager,
                this,
                firebaseDataConstructor.getmHashMapForData(),
                firebaseDataConstructor.getUsersCoins());
    
        callFragment.setmMainHashMapForContacts(firebaseDataConstructor.getmHashMapForContacts());
        mProductsHelper.createStaticFragment(mRootView,
                mFragmentManager,
                callFragment,
                this,
                Constant.TAG_CALL_FRAGMENT.name());
    }
    
    public void updateViews(FirebaseDataConstructor firebaseDataConstructor){
        if(!mProductsHelper.updateViews(firebaseDataConstructor.getmHashMapForData())) createViews(firebaseDataConstructor);
    }
    
    public void updateCallFragmentHashMap(FirebaseDataConstructor firebaseDataConstructor){
        callFragment.setmMainHashMapForContacts(firebaseDataConstructor.getmHashMapForContacts());
    }
    
    public void updateCoins(int usersCoins){
        invalidateOptionsMenu();
        mProductsHelper.updateCoinsInFragment(usersCoins);
    }
    /**
     * This method parsing data which we get from GetDataFromFirebase class and creating the menu
     * after that. We using glide cause we need load the icon to navigation.
     *
     * @param arListIcon
     * @param arListName
     * @param arListShow
     */
    public void createMenu(ArrayList<String> arListIcon, final ArrayList<String> arListName, final ArrayList<Boolean> arListShow) {
        final Menu menu = mNavigationView.getMenu();
        menu.clear();
        for (int i = 0; i != arListIcon.size(); i++) {
            
            final int finalI = i;
            
            Glide.with(this)
                    .load(arListIcon.get(i))
                    .asBitmap()
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            //visability
                            if (arListShow.get(finalI)) {
                                //add item
                                Drawable icon = new BitmapDrawable(getResources(), resource);
                                menu.add(R.id.icon_group1, finalI, finalI, arListName.get(finalI)).setIcon(icon);
                                //add action
                                mNavigationView.setNavigationItemSelectedListener(
                                        new NavigationView.OnNavigationItemSelectedListener() {
                                            @Override
                                            public boolean onNavigationItemSelected(MenuItem menuItem) {
                                                selectDrawerItem(menuItem);
                                                return true;
                                            }
                                        });
                            }
                        }
                    });
        }
        
    }
    
    private void selectDrawerItem(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case 0:
                goToMainAct();
                break;
            case 1:
                goToAbout(mRootView);
                break;
            case 2:
                goToShare(mRootView);
                break;
            case 3:
                goToSettings(mRootView);
                break;
            case 4:
                goToSupport(mRootView);
                break;
            case 5:
                goToExit();
                break;
            default:
                goToMainAct();
                break;
        }
        mDrawerLayout.closeDrawer(Gravity.START);
        menuItem.setChecked(true);
        
    }
    
    private void goToMainAct() {
       createViews(dataFromFirebase.getFirebaseDataConstructor());
    }
    
    private void goToAbout(LinearLayout root) {
        removeViewsAndCloseDrawer(root, AboutFragment.newInstance(dataFromFirebase.getFirebaseDataConstructor().getmHashMapForLocation()));
    }
    
    private void goToSettings(LinearLayout root) {
        removeViewsAndCloseDrawer(root, SettingsFragment.newInstance(dataFromFirebase.getFirebaseDataConstructor().getPromoCode()));
    }
    
    private void goToSupport(LinearLayout root) {
        removeViewsAndCloseDrawer(root, TechnicalSupportFragment.newInstance());
    }
    
    private void goToShare(LinearLayout root) {
        removeViewsAndCloseDrawer(root, ShareWithFriendsStartFragment.newInstance(dataFromFirebase.getFirebaseDataConstructor().getPromoCode()));
    }
    
    private void removeViewsAndCloseDrawer(LinearLayout root, Fragment fragment) {
        root.removeAllViews();
        mFragmentManager.beginTransaction()
                .replace(root.getId(), fragment)
                .commit();
    }
    
    private void goToExit() {
        
        AlertDialog.Builder builder =
                new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle);
        builder.setTitle("Выйти из аккаунта?");
        
        builder.setPositiveButton("Продолжить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setPositiveButtonClick();
            }
        });
        
        builder.setNegativeButton("Отмена", null);
        builder.show();
    }
    
    private void setPositiveButtonClick() {
        FirebaseAuth.getInstance().signOut();
        stopService(new Intent(MainProductsActivity.this, ServiceNotification.class));
        finish();
    }
    
    /**
     * Check if we login before.
     */
    private void setUpAuthentication() {
        //mAuth
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("LOGIN USER--", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    startActivity(new Intent(MainProductsActivity.this, LoginActivity.class));
                    Log.d("LOGIN USER--", "onAuthStateChanged:signed_out");
                    MainProductsActivity.this.finish();
                }
            }
        };
    }
    
    private void addToolBar() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(mToolbar);
        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setHomeAsUpIndicator(R.drawable.animated_vector_drawable);
        mActionBar.setDisplayHomeAsUpEnabled(true);
    }
    
    /**
     * If we selected then open navigation
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (mDrawerLayout.isDrawerOpen(Gravity.START)) {
                mDrawerLayout.closeDrawer(Gravity.START);
            } else {
                mDrawerLayout.openDrawer(Gravity.START);
            }
        }
        return super.onOptionsItemSelected(item);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_bar, menu);
        menu.findItem(R.id.filter).setTitle(String.valueOf(dataFromFirebase.getFirebaseDataConstructor().getUsersCoins()));
        
        return super.onCreateOptionsMenu(menu);
    }
    
    @Override
    public void onBackPressed() {
        if (mOnBackPress) {
            setPositiveButtonClick();
        } else {
            Toast.makeText(this, "Пожалуйста нажмите еще раз что бы выйти из приложения.", Toast.LENGTH_SHORT).show();
            mOnBackPress = true;
        }
    }
    
    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
    
    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
    
    @Override
    protected void onPostResume() {
        super.onPostResume();
        invalidateOptionsMenu();
    }
    
    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {
        
    }
    
    @Override
    public void onDrawerOpened(View drawerView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AnimatedVectorDrawable mBackDrawable = (AnimatedVectorDrawable) getDrawable(R.drawable.animated_vector_drawable);
            getSupportActionBar().setHomeAsUpIndicator(mBackDrawable);
            mBackDrawable.start();
            
        }
    }
    
    @Override
    public void onDrawerClosed(View drawerView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AnimatedVectorDrawable mBackDrawable = (AnimatedVectorDrawable) getDrawable(R.drawable.animate_to_back);
            getSupportActionBar().setHomeAsUpIndicator(mBackDrawable);
            mBackDrawable.start();
        }
    }
    
    @Override
    public void onDrawerStateChanged(int newState) {
        
    }
}