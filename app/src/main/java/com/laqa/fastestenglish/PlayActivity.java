package com.laqa.fastestenglish;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import java.util.Random;

public class PlayActivity extends AppCompatActivity implements View.OnTouchListener,View.OnClickListener{

    RelativeLayout relativeLayoutPlay,playRelaytive1,playRelaytive2,playRelaytive3,playRelaytive4;
    TextView playTextViewScore, playBigText, playTextView1, playTextView2, playTextView3, playTextView4;
    ViewSwitcher viewSwitcher;
    ImageView playImageViewBig;
    Random r1 = new Random();
    int ranNum1 = r1.nextInt((5-1)+ 1)+1;//Random 1 den 5

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        overridePendingTransition(R.anim.rightin, R.anim.leftout);

        relativeLayoutPlay=(RelativeLayout)findViewById(R.id.relativeLayoutPlay);
        playRelaytive1=(RelativeLayout)findViewById(R.id.playRelaytive1);
        playRelaytive2=(RelativeLayout)findViewById(R.id.playRelaytive2);
        playRelaytive3=(RelativeLayout)findViewById(R.id.playRelaytive3);
        playRelaytive4=(RelativeLayout)findViewById(R.id.playRelaytive4);
        relativeLayoutPlay=(RelativeLayout)findViewById(R.id.relativeLayoutPlay);

        playTextViewScore=(TextView)findViewById(R.id.playTextViewScore);
        playBigText=(TextView)findViewById(R.id.playBigText);
        playTextView1=(TextView)findViewById(R.id.playTextView1);
        playTextView2=(TextView)findViewById(R.id.playTextView2);
        playTextView3=(TextView)findViewById(R.id.playTextView3);
        playTextView4=(TextView)findViewById(R.id.playTextView4);

        playImageViewBig=(ImageView)findViewById(R.id.playImageViewBig);

        switch(ranNum1){
            case 1:
                relativeLayoutPlay.setBackgroundResource(R.drawable.play_bg1);
                break;
            case 2:
                relativeLayoutPlay.setBackgroundResource(R.drawable.play_bg2);
                break;
            case 3:
                relativeLayoutPlay.setBackgroundResource(R.drawable.play_bg3);
                break;
            case 4:
                relativeLayoutPlay.setBackgroundResource(R.drawable.play_bg4);
                break;
            default:
                relativeLayoutPlay.setBackgroundResource(R.drawable.play_bg5);
                break;
        }

        playRelaytive1.setOnTouchListener(this);
        playRelaytive2.setOnTouchListener(this);
        playRelaytive3.setOnTouchListener(this);
        playRelaytive4.setOnTouchListener(this);

        playRelaytive1.setOnClickListener(this);
        playRelaytive2.setOnClickListener(this);
        playRelaytive3.setOnClickListener(this);
        playRelaytive4.setOnClickListener(this);

        //Random số thứ nhất
        //Dựa vào số random để chọn bg cho màn hình
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if(view.getId()== R.id.playRelaytive1 ||
                view.getId()== R.id.playRelaytive2 ||
                view.getId()== R.id.playRelaytive3 ||
                view.getId()== R.id.playRelaytive4){
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                view.setBackgroundResource(R.drawable.play_button2);
            } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                view.setBackgroundResource(R.drawable.play_button1);
            }
        }
        return false;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.playRelaytive1:
                break;
            case R.id.playRelaytive2:
                break;
            case R.id.playRelaytive3:
                break;
            case R.id.playRelaytive4:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.leftin, R.anim.rightout);
    }
}
