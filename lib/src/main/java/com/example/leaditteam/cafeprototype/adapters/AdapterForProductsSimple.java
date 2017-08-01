package com.example.leaditteam.cafeprototype.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.leaditteam.cafeprototype.helpers.ContactsHelper;
import com.example.leaditteam.cafeprototype.R;

import java.util.HashMap;

/**
 * Created by leaditteam on 20.04.17.
 */

public class AdapterForProductsSimple extends BaseAdapter {

    private HashMap<Integer, ContactsHelper> mHashMap;
    private Context mContext;

    public AdapterForProductsSimple(HashMap<Integer, ContactsHelper> hashmap, Context context) {

        this.mHashMap = hashmap;
        this.mContext = context;

    }

    @Override
    public int getCount() {
        return mHashMap.size();
    }

    @Override
    public Object getItem(int position) {
        return mHashMap.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ContactsHelper temp_contact = mHashMap.get(position);
        View temp_call = convertView.inflate(mContext,R.layout.call_number_fragment,null);
        TextView adress = (TextView) temp_call.findViewById(R.id.textView18_);
        TextView phone = (TextView) temp_call.findViewById(R.id.call_number_);
        adress.setText(temp_contact.getAdress());
        phone.setText(temp_contact.getPhoneNumber());

        return temp_call;
    }
}
