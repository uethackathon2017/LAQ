package com.laqa.fastestenglish;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;

public class MenuActivity extends AppCompatActivity implements View.OnTouchListener,View.OnClickListener{

    ImageButton menuButtonPlay, menuButtonPacks, menuButtonRecord,menuButtonSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        menuButtonPlay=(ImageButton)findViewById(R.id.menuButtonPlay);
        menuButtonPacks=(ImageButton)findViewById(R.id.menuButtonPacks);
        menuButtonRecord=(ImageButton)findViewById(R.id.menuButtonRecord);
        menuButtonSetting=(ImageButton)findViewById(R.id.menuButtonSetting);

        menuButtonPlay.setOnTouchListener(this);
        menuButtonPacks.setOnTouchListener(this);
        menuButtonRecord.setOnTouchListener(this);
        menuButtonSetting.setOnTouchListener(this);

        menuButtonPlay.setOnClickListener(this);
        menuButtonPacks.setOnClickListener(this);
        menuButtonRecord.setOnClickListener(this);
        menuButtonSetting.setOnClickListener(this);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if(view.getId()== R.id.menuButtonPlay ||
            view.getId()== R.id.menuButtonPacks ||
            view.getId()== R.id.menuButtonRecord ||
            view.getId()== R.id.menuButtonSetting){

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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.menuButtonPlay:
                //Chuyen sang trang Play
                Intent intentPlay= new Intent(MenuActivity.this,PlayActivity.class);
                intentPlay.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intentPlay);
                break;

            case R.id.menuButtonPacks:
                //Chuyen sang trang Packs
                break;

            case R.id.menuButtonRecord:
                //Chuyen sang trang Record
                break;

            case R.id.menuButtonSetting:
                //Goi Fragment Setting
                break;
        }
    }
}
