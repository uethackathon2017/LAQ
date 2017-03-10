package com.laqa.fastestenglish;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.laqa.fastestenglish.SQLite.GetData;

public class MenuActivity extends AppCompatActivity implements View.OnTouchListener,View.OnClickListener{

    ImageButton menuButtonPlay, menuButtonPacks, menuButtonRecord,menuButtonSetting;
    FragmentManager fragmentManager;
    RelativeLayout relativeLayoutMenu;
    SettingFragment settingFragment;

    GetData getData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        overridePendingTransition(R.anim.topin, R.anim.bottomout);

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

        relativeLayoutMenu=(RelativeLayout)findViewById(R.id.relativeLayoutMenu);

        fragmentManager = getSupportFragmentManager();

        getData = new GetData(this);
        getData.open();
        Toast.makeText(this, "Current Packs: "+getData.getCurrentPacks(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if(view.getId()== R.id.menuButtonPlay ||
            view.getId()== R.id.menuButtonPacks ||
            view.getId()== R.id.menuButtonRecord ||
            view.getId()== R.id.menuButtonSetting){

            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                view.animate().scaleX(1.2f);
                view.animate().scaleY(1.2f);
            } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
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
                Intent intentPacks= new Intent(MenuActivity.this,PacksActivity.class);
                startActivity(intentPacks);
                break;

            case R.id.menuButtonRecord:
                //Chuyen sang trang Record
                Intent intentRecord= new Intent(MenuActivity.this,RecordActivity.class);
                startActivity(intentRecord);
                break;

            case R.id.menuButtonSetting:
                //Goi Fragment Setting
                showSettingFragment();
                break;
        }
    }

    public void showSettingFragment(){
            //FragmentManager fragmentManager = getSupportFragmentManager();
            //Hiện logo lên trong tầm 2s rồi chuyển đến menu chính
            FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.topin, R.anim.bottomout);

            settingFragment = new SettingFragment();

            if(fragmentManager.findFragmentById(R.id.scoreboardFragment)!=null){
                fragmentManager.beginTransaction().remove(fragmentManager.findFragmentById(R.id.scoreboardFragment)).commit();
            }
            fragmentTransaction.add(R.id.activity_main,settingFragment);
            fragmentTransaction.commit();
            relativeLayoutMenu.setAlpha(0.2f);// Relative Layout -> alpha
    }

    @Override
    public void onBackPressed() {
        if(settingFragment!=null){
            settingFragment.dismiss();
            relativeLayoutMenu.setAlpha(1.0f);
        }
        else{
            super.onBackPressed();
        }
    }
}
