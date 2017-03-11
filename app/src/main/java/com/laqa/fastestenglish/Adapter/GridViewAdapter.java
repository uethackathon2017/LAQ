package com.laqa.fastestenglish.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.laqa.fastestenglish.R;
import com.laqa.fastestenglish.SQLite.GetData;

/**
 * Created by Phong le on 3/11/2017.
 */

public class GridViewAdapter extends BaseAdapter {
    private Context mContext;
    private final String[] web;
    private final int[] Imageid;
    Typeface typeface;

    GetData getData;

    public GridViewAdapter(Context c,String[] web,int[] Imageid ) {
        mContext = c;
        this.Imageid = Imageid;
        this.web = web;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return web.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            grid = inflater.inflate(R.layout.custom_gridview, null);
            ImageView gridViewStar = (ImageView) grid.findViewById(R.id.gridViewStar);
            TextView gridViewTextView = (TextView) grid.findViewById(R.id.gridViewTextView);
            ImageView gridViewImage = (ImageView)grid.findViewById(R.id.packsGridViewImage);
            gridViewTextView.setText(web[position]);
            gridViewImage.setImageResource(Imageid[position]);
            typeface = Typeface.createFromAsset(mContext.getAssets(), "fonts/utm_cookies.ttf");
            gridViewTextView.setTypeface(typeface);
            getData=new GetData(mContext);
            getData.open();
            if((position+1)==getData.getCurrentPacks()){ //2 là số gói mở đu
                gridViewStar.setVisibility(View.VISIBLE);
            }
            else{
                gridViewStar.setVisibility(View.GONE);
            }
        return grid;
    }
}
