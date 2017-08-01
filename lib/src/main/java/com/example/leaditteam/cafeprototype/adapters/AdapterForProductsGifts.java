package com.example.leaditteam.cafeprototype.adapters;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.util.Pair;

import com.bumptech.glide.Glide;
import com.example.leaditteam.cafeprototype.activities.FullDescriptionActivity;
import com.example.leaditteam.cafeprototype.R;
import com.example.leaditteam.cafeprototype.helpers.Constant;

import java.util.ArrayList;

/**
 * Created by leaditteam on 17.03.17.
 */

public class AdapterForProductsGifts extends BaseAdapter {
    private ArrayList mListName;
    private ArrayList mListDescription;
    private ArrayList mListPrice;
    private ArrayList mListUrlImage;
    private ArrayList mListInfo;
    private Activity mActivity;

    private Boolean mIfThisIsMainActivity;
    private Boolean mShowCoins;
    private int mNumber;
    private int mUserCoins;
    
    public AdapterForProductsGifts(ArrayList arListName, ArrayList arListDescription, ArrayList arListPrice, ArrayList arListUrlImage, ArrayList arListInfo, Activity mActivity, Boolean ifThisIsMainActivity, Boolean mShowCoins, int mNumber, int mUserCoins) {
        this.mListName = arListName;
        this.mListDescription = arListDescription;
        this.mListPrice = arListPrice;
        this.mListUrlImage = arListUrlImage;
        this.mListInfo = arListInfo;
        this.mActivity = mActivity;
        this.mIfThisIsMainActivity = ifThisIsMainActivity;
        this.mShowCoins = mShowCoins;
        this.mNumber = mNumber;
        this.mUserCoins = mUserCoins;
    }

    public int getCount() {
        if (mIfThisIsMainActivity) {
            return mNumber;
        } else
            return mListName.size();
    }

    public Object getItem(int position) {
        return mListName.get(position);
    }

    public long getItemId(int position) {
        return position;
    }
    
    
    @Override
    public View getView(final int id, View view, ViewGroup viewGroup) {
        //display metrics
        DisplayMetrics metrics = mActivity.getResources().getDisplayMetrics();
        int width = metrics.widthPixels / 2;
        int height = metrics.heightPixels / 4;

        ImageView ivCardImage = getImage(); //add image

        GridView.LayoutParams paramsForCard = new GridView.LayoutParams(width, height, Gravity.BOTTOM); //params for grid view

        //layout for cardView
        LinearLayout layoutCardView = getLayoutCardView();

        //helping layout
        LinearLayout line2 = getHelpinLayout();

        //layout for btn cool view
        LinearLayout llRootLineBottom = getRootLineLayout(height);

        //name of object
        final TextView name = getNameOfProduct(id);

        //added view
        final CardView cardView = getCardView();

        llRootLineBottom.addView(name);

        line2.addView(llRootLineBottom);
        layoutCardView.addView(ivCardImage);
        layoutCardView.addView(line2);
        cardView.addView(layoutCardView);

        //llRootLineBottom for coints
        if (mShowCoins) {
            LinearLayout coinsLayout = getCoinsLayout(id, height);
            RelativeLayout rlForShowCoins = getLineCoins(llRootLineBottom,coinsLayout);
            cardView.addView(rlForShowCoins);//add to root view
        }

        LinearLayout layout = new LinearLayout(mActivity);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setLayoutParams(paramsForCard);
        layout.setGravity(Gravity.CENTER);
        layout.setPadding(10, 10, 10, 10);
        layout.addView(cardView);

        Glide.with(mActivity)
                .load(mListUrlImage.get(id).toString())
                .placeholder(R.drawable.ic_crop_original_white_24dp_4x)
                .error(R.drawable.ic_errorl_white_24dp_4x)
                .override(600,600)
                .dontAnimate()
                .into(ivCardImage);
        
        // if we clicked then go to new activity discription
        ivCardImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToDescriptionActivity(
                        (ImageView) v,
                        mListName.get(id).toString(),
                        mListDescription.get(id).toString(),
                        mListPrice.get(id).toString(),
                        mListUrlImage.get(id).toString(),
                        mListInfo.get(id).toString(),
                        mUserCoins);
            }
        });

        return layout;

    }

    private void goToDescriptionActivity(ImageView imageView, String name, String description, String price, String p_url, String info, int USER_COINS) {

        Intent i = new Intent(mActivity, FullDescriptionActivity.class);//from where
        i.setAction(i.ACTION_VIEW);

        Bundle extras = new Bundle();
        extras.putString(Constant.bundleArListName.name(), name);
        extras.putString(Constant.bundleArListDescription.name(), description);
        extras.putString(Constant.bundleArListPrice.name(), price);
        extras.putString(Constant.bundleArListUrl.name(), p_url);
        extras.putString(Constant.bundleArListInfo.name(), info);
        extras.putBoolean(Constant.bundleShowCoins.name(), mShowCoins);
        extras.putInt(Constant.USERS_COINS.name(), USER_COINS);
        i.putExtras(extras);

        Pair[] pairs;
             pairs = new Pair[]{
                    Pair.create(imageView, mActivity.getString(R.string.transition_products))};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(
                    mActivity,
                    pairs
            );

            mActivity.startActivity(i, options.toBundle());
        } else mActivity.startActivity(i);
    }

    private RelativeLayout getLineCoins(final LinearLayout line, LinearLayout coinsLayout){
        final RelativeLayout rlForShowCoins = new RelativeLayout(mActivity);
        RelativeLayout.LayoutParams paramsForShowCoins = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        rlForShowCoins.setLayoutParams(paramsForShowCoins);
        rlForShowCoins.setGravity(Gravity.RIGHT|Gravity.BOTTOM);
        rlForShowCoins.addView(coinsLayout);
        ViewTreeObserver vto = line.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                line.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int height = line.getMeasuredHeight();
                rlForShowCoins.setPadding(0,0,0,height+height/10);
            }
        });

        return rlForShowCoins;
    }
    private LinearLayout getRootLineLayout(int height){
        LinearLayout line =new LinearLayout(mActivity);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, height / 6);
        line.setLayoutParams(layoutParams);
        line.setGravity(Gravity.CENTER);
        line.setBackgroundColor(mActivity.getResources().getColor(R.color.colorAccent));
        return line;
    }
    private LinearLayout getHelpinLayout(){
        LinearLayout line2 = new LinearLayout(mActivity);
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        line2.setLayoutParams(layoutParams2);
        line2.setGravity(Gravity.BOTTOM | Gravity.RIGHT);
        line2.setOrientation(LinearLayout.VERTICAL);
        return line2;
    }
    private LinearLayout getLayoutCardView(){
        LinearLayout layoutCardView = new LinearLayout(mActivity);
        LinearLayout.LayoutParams layoutParamsCardView = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutCardView.setLayoutParams(layoutParamsCardView);
        layoutCardView.setGravity(Gravity.BOTTOM);
        layoutCardView.setOrientation(LinearLayout.VERTICAL);
        return layoutCardView;
    }
    private CardView getCardView() {
        CardView cardView = new CardView(mActivity);
        cardView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        cardView.setForegroundGravity(Gravity.BOTTOM);
        cardView.setElevation(5);

        return cardView;
    }

    private TextView getNameOfProduct(int id) {
        //name of object
        TextView name = new TextView(mActivity);
        name.setText(mListName.get(id).toString());
        name.setEllipsize(TextUtils.TruncateAt.END);
        name.setGravity(Gravity.BOTTOM);
        name.setTextColor(mActivity.getResources().getColor(R.color.backgroundColor));
        name.setPadding(6, 6, 6, 6);
        name.setMaxLines(1);

        return name;
    }

    private ImageView getImage() {
        ImageView ivCardImage = new ImageView(mActivity); //add image
        //photo
        ivCardImage.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, Gravity.BOTTOM));
        ivCardImage.setPadding(0, 0, 0, 0);
        ivCardImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
        ivCardImage.setTransitionName(mActivity.getString(R.string.transition_products));

        return ivCardImage;
    }

    private LinearLayout getCoinsLayout(int id, int height) {
        LinearLayout line_fir_coints = new LinearLayout(mActivity);
        LinearLayout.LayoutParams params_for_line_coints = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params_for_line_coints.setMargins(0, 0, 0, 10);
        line_fir_coints.setLayoutParams(params_for_line_coints);
        line_fir_coints.setGravity(Gravity.RIGHT | Gravity.BOTTOM);
        line_fir_coints.setVerticalGravity(Gravity.CENTER);
        line_fir_coints.setBackgroundColor(mActivity.getResources().getColor(R.color.colorAccent));

        TextView text_for_coints = new TextView(mActivity);
        LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams3.setMargins(0, 0, -3, 0);
        text_for_coints.setLayoutParams(layoutParams3);
        text_for_coints.setText(mListPrice.get(id).toString());
        text_for_coints.setTextSize(16);
        text_for_coints.setTextColor(Color.BLACK);
        text_for_coints.setPadding(0, 4, 10, 4);

        ImageView icon_coints = new ImageView(mActivity);
        LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, height / 9);
        layoutParams1.setMargins(0, 2, 0, 2);
        icon_coints.setLayoutParams(layoutParams1);
        icon_coints.setPadding(4, 2, 0, 2);
        icon_coints.setImageDrawable(mActivity.getResources().getDrawable(R.drawable.ic_stars_black_24dp));

        //add coits to layout

        line_fir_coints.addView(icon_coints);
        line_fir_coints.addView(text_for_coints);
        line_fir_coints.setId(Integer.MAX_VALUE - 12345);

        return line_fir_coints;
    }
    
    public void setmListName(ArrayList mName) {
        this.mListName.clear();
        this.mListName.addAll(mName);
        notifyDataSetChanged();
    }
    
    public void setmListDescription(ArrayList mDescription) {
        this.mListDescription.clear();
        this.mListDescription.addAll(mDescription);
        notifyDataSetChanged();
    }
    
    public void setmPrice(ArrayList mPrice) {
        this.mListPrice.clear();
        this.mListPrice.addAll(mPrice);
        notifyDataSetChanged();
    }
    
    public void setmUrlImage(ArrayList mUrlImage) {
        this.mListUrlImage.clear();
        this.mListUrlImage.addAll(mUrlImage);
        notifyDataSetChanged();
    }
    
    public void setmInfo(ArrayList mInfo) {
        this.mListInfo.clear();
        this.mListInfo.addAll(mInfo);
        notifyDataSetChanged();
    }
    
    public void setmNumber(int mNumber) {
        this.mNumber = mNumber;
        notifyDataSetChanged();
    }
    
    public void setmShowCoins(Boolean mShowCoins) {
        this.mShowCoins = mShowCoins;
        notifyDataSetChanged();
    }
    
    public void setmIfThisIsMainActivity(Boolean mIfThisIsMainActivity) {
        this.mIfThisIsMainActivity = mIfThisIsMainActivity;
        notifyDataSetChanged();
    }
    
    public void setmUserCoins(int mUserCoins) {
        this.mUserCoins = mUserCoins;
        notifyDataSetChanged();
    }
}