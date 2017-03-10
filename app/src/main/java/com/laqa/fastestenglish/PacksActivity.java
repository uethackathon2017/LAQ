package com.laqa.fastestenglish;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;

public class PacksActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener{

    ImageButton packsButtonBack;
    GridView packsGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_packs);

        packsButtonBack=(ImageButton)findViewById(R.id.packsButtonBack);
        packsGridView=(GridView)findViewById(R.id.packsGridView);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.packsButtonBack:
                //Quay lại
                break;
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if(view.getId()== R.id.packsButtonBack){
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                view.animate().setDuration(100);
                view.animate().scaleX(1.2f);
                view.animate().scaleY(1.2f);
            } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                view.animate().setDuration(100);
                view.animate().scaleX(1.0f);
                view.animate().scaleY(1.0f);
            }
        }
        return false;
    }
}
