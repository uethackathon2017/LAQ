package com.laqa.fastestenglish.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.laqa.fastestenglish.Question.Record;
import com.laqa.fastestenglish.R;

import java.util.ArrayList;

/**
 * Created by Phong le on 3/11/2017.
 */

public class ListViewAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Record> listView;
    Typeface typeface;


    public ListViewAdapter(Context mContext, ArrayList<Record> listView) {
        this.mContext = mContext;
        this.listView = listView;
    }

    @Override
    public int getCount() {
        return listView.size();
    }

    @Override
    public Object getItem(int position) {
        return listView.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        if(position==0){
            v = inflater.inflate(R.layout.custom_listview2, null);
        }
        else {
            v = inflater.inflate(R.layout.custom_listview, null);
        }
        TextView textViewSTT =(TextView)v.findViewById(R.id.recordTextViewSTT);
        TextView textViewRecordPlayer =(TextView)v.findViewById(R.id.recordTextViewName);
        TextView textViewRecordNumber =(TextView)v.findViewById(R.id.recordScore);
        textViewSTT.setText(String.valueOf(position+1));
        textViewRecordPlayer.setText(String.valueOf(listView.get(position).getTen()));
        textViewRecordNumber.setText(String.valueOf(listView.get(position).getDiem()));
        if(position==0){
            textViewSTT.setVisibility(View.INVISIBLE);
        }
        typeface = Typeface.createFromAsset(mContext.getAssets(), "fonts/utm_cookies.ttf");
        textViewSTT.setTypeface(typeface);
        textViewRecordPlayer.setTypeface(typeface);
        textViewRecordNumber.setTypeface(typeface);
//        if(position==0){
//            typeface = Typeface.createFromAsset(mContext.getAssets(), "fonts/utm_helvebold.ttf");
//        }
//        else {
//            typeface = Typeface.createFromAsset(mContext.getAssets(), "fonts/utm_helve.ttf");
//        }
//        textViewSTT.setTypeface(typeface);
//        textViewRecordPlayer.setTypeface(typeface);
//        textViewRecordNumber.setTypeface(typeface);

        return v;
    }
}

