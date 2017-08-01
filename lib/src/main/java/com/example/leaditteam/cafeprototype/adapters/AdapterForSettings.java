package com.example.leaditteam.cafeprototype.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.leaditteam.cafeprototype.R;

/**
 * Created by leaditteam on 29.03.17.
 */

public class AdapterForSettings extends BaseAdapter {
    private String[] mItemName;
    private Integer[] mItemImg;
    private Context mContext;

    public AdapterForSettings(String[] itemname, Integer[] itemimg, Context con) {
        this.mItemName = itemname;
        this.mItemImg = itemimg;
        this.mContext = con;
    }

    @Override
    public int getCount() {
        return mItemImg.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //root view
        RelativeLayout relativeLayout = new RelativeLayout(mContext);
        RelativeLayout.LayoutParams layoutParamsMain = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        relativeLayout.setLayoutParams(layoutParamsMain);

        // add image view
        ImageView ivContent = new ImageView(mContext);
        ivContent.setImageResource(mItemImg[position]);
        ivContent.setColorFilter(mContext.getResources().getColor(R.color.colorAccent));
        ivContent.setPadding(32, 52, 32, 52);
        ivContent.setId(Integer.MAX_VALUE - 235235);


        RelativeLayout.LayoutParams layoutParamsText = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        layoutParamsText.addRule(RelativeLayout.RIGHT_OF, ivContent.getId());
        TextView tvContent = new TextView(mContext);
        tvContent.setPadding(32, 60, 32, 32);
        tvContent.setText(mItemName[position]);
        tvContent.setTextColor(mContext.getResources().getColor(R.color.textColorLabel));
        tvContent.setLayoutParams(layoutParamsText);

        relativeLayout.addView(ivContent);
        relativeLayout.addView(tvContent);

        return relativeLayout;
    }
}
