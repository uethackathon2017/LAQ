package com.laqa.fastestenglish;

import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class PlayActivity extends AppCompatActivity implements View.OnTouchListener,View.OnClickListener{

    RelativeLayout relativeLayoutPlay,playRelaytive1,playRelaytive2,playRelaytive3,playRelaytive4;
    TextView playTextViewScore, playBigText, playTextView1, playTextView2, playTextView3, playTextView4;
    ViewSwitcher playViewSwticher;
    ImageView playImageViewBig;
    Random r1 = new Random();
    int ranNum1 = r1.nextInt((5-1)+ 1)+1;//Random 1 den 5

    Animation shake,leftIn,rightOut;
    AtomicBoolean showPopUp = new AtomicBoolean(false);

    private ProgressBar progressBar;
    private int progrssBarStatus = 100;
    private boolean stopProgressBar=false;
    int score = 0;

    FragmentManager fragmentManager;

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

        progressBar=(ProgressBar)findViewById(R.id.progressBar);

        playViewSwticher=(ViewSwitcher)findViewById(R.id.playViewSwticher);

        fragmentManager = getSupportFragmentManager();

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

        shake = AnimationUtils.loadAnimation(this, R.anim.shakeanim);
        leftIn = AnimationUtils.loadAnimation(this, R.anim.leftin);
        rightOut = AnimationUtils.loadAnimation(this, R.anim.rightout);
        playViewSwticher.setInAnimation(leftIn);
        playViewSwticher.setOutAnimation(rightOut);

        chayTiengTrinh();
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

    public void showScoreboard(){
        relativeLayoutPlay.startAnimation(shake);

        if(showPopUp.get()==false) {
            //FragmentManager fragmentManager = getSupportFragmentManager();
            //Hiện logo lên trong tầm 2s rồi chuyển đến menu chính
            FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.topin, R.anim.bottomout);


            Bundle bundleScore = new Bundle();
            bundleScore.putInt("score",score);
            bundleScore.putInt("time",progrssBarStatus);

            Fragment scoreboardFragment = new ScoreboardFragment();

            scoreboardFragment.setArguments(bundleScore);

            if(fragmentManager.findFragmentById(R.id.scoreboardFragment)!=null){
                fragmentManager.beginTransaction().remove(fragmentManager.findFragmentById(R.id.scoreboardFragment)).commit();
            }
            fragmentTransaction.add(R.id.activity_play,scoreboardFragment);
            fragmentTransaction.commit();
            relativeLayoutPlay.setAlpha(0.2f);// Relative Layout -> alpha
            playRelaytive1.setClickable(false);
            playRelaytive2.setClickable(false);
            playRelaytive3.setClickable(false);
            playRelaytive4.setClickable(false);

        }
        showPopUp.set(true);
    }

    public void chayTiengTrinh() {
        final Handler timerHandler = new Handler();

        timerHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (progrssBarStatus > 0 && stopProgressBar == false) {
                    progrssBarStatus = progrssBarStatus - 2;// -2
                    progressBar.setProgress(progrssBarStatus);
                    timerHandler.postDelayed(this, 20);
                }

                if (progrssBarStatus == 0) {
                    showScoreboard();
                }
            }
        }, 5);
    }

    public void closeFragment(){
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.topin, R.anim.bottomout);
        fragmentTransaction.remove(fragmentManager.findFragmentById(R.id.scoreboardFragment)).commit();
    }
}
