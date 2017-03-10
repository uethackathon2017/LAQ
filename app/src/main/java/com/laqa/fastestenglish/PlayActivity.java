package com.laqa.fastestenglish;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewSwitcher;

public class PlayActivity extends AppCompatActivity implements View.OnTouchListener,View.OnClickListener{

    RelativeLayout relativeLayoutPlay,playRelaytive1,playRelaytive2,playRelaytive3,playRelaytive4;
    TextView playTextViewScore, playBigText, playTextView1, playTextView2, playTextView3, playTextView4;
    ViewSwitcher viewSwitcher;
    ImageView playImageViewBig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

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
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if(view.getId()== R.id.playRelaytive1 ||
                view.getId()== R.id.playRelaytive2 ||
                view.getId()== R.id.playRelaytive3 ||
                view.getId()== R.id.playRelaytive4){

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

    }
}
