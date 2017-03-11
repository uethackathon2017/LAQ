package com.laqa.fastestenglish;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
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
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.laqa.fastestenglish.Question.Question;
import com.laqa.fastestenglish.SQLite.GetData;

import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class PlayActivity extends AppCompatActivity implements View.OnTouchListener,View.OnClickListener{

    RelativeLayout relativeLayoutPlay,playRelaytive1,playRelaytive2,playRelaytive3,playRelaytive4;
    TextView playTextViewScore, playBigText, playTextView1, playTextView2, playTextView3, playTextView4;
    ViewSwitcher playViewSwticher;
    ImageView playImageViewBig,playImageView1,playImageView2,playImageView3,playImageView4;
    Random r1 = new Random();
    int ranNum1 = r1.nextInt((5-1)+ 1)+1;//Random 1 den 5

    Animation shake,leftIn,rightOut;
    AtomicBoolean showPopUp = new AtomicBoolean(false);

    private ProgressBar progressBar;
    private boolean stopProgressBar=false;
    int score = 0;
    boolean winOrLose=false;//Thua
    GetData getData;

    private MediaPlayer song2;
    private MediaPlayer song;
    private MediaPlayer song3;

    Typeface typeface;

    FragmentManager fragmentManager;

    public static final String PACKAGE_NAME="com.laqa.fastestenglish";

    public static final String CHOOSE_1 = "ChooseAnswer1";
    public static final String CHOOSE_2 = "ChooseAnswer2";
    public static final String CHOOSE_3 = "ChooseAnswer3";
    public static final String CHOOSE_4 = "ChooseAnswer4";

    public static final String RESTART_PLAY="restart";//RESTART lai activity

    private int progrssBarStatus = 100;

    int numberQuestionTrue=0;
    int numberQuestion=0;
    int countWrong = 0;

    boolean sound;
    boolean music;

    Handler timerHandler;
    Runnable runnable;

    List<Question> listQuestion;
    Question currentQuestion;

    TextToSpeech textToSpeech;

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
        playImageView1=(ImageView)findViewById(R.id.playImageView1);
        playImageView2=(ImageView)findViewById(R.id.playImageView2);
        playImageView3=(ImageView)findViewById(R.id.playImageView3);
        playImageView4=(ImageView)findViewById(R.id.playImageView4);

        progressBar=(ProgressBar)findViewById(R.id.progressBar);

        playViewSwticher=(ViewSwitcher)findViewById(R.id.playViewSwticher);

        typeface = Typeface.createFromAsset(getAssets(), "fonts/utm_cookies.ttf");

        getData = new GetData(this);
        getData.open();

        if(getData.getSound()==1) {
            sound=true;
        }
        else{
            sound=false;
        }
        if(getData.getMusic()==1) {
            music=true;
        }
        else{
            music=false;
        }

        song2 = MediaPlayer.create(PlayActivity.this, R.raw.lose2);
        song = MediaPlayer.create(PlayActivity.this, R.raw.win);
        song3 = MediaPlayer.create(PlayActivity.this, R.raw.music2);
        if(music==true) {
            song3.start();
        }
        playBigText.setTypeface(typeface);
        playTextView1.setTypeface(typeface);
        playTextView2.setTypeface(typeface);
        playTextView3.setTypeface(typeface);
        playTextView4.setTypeface(typeface);
        playTextViewScore.setTypeface(typeface);

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

        IntentFilter filter = new IntentFilter();
        filter.addAction(CHOOSE_1);
        filter.addAction(CHOOSE_2);
        filter.addAction(CHOOSE_3);
        filter.addAction(CHOOSE_4);
        filter.addAction(RESTART_PLAY);
        registerReceiver(receiver, filter);

        listQuestion = getData.getAll();
        //Toast.makeText(this, String.valueOf(getData.getPosition()), Toast.LENGTH_SHORT).show();
        nextQuestion(listQuestion);

        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
            }
        });


    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if((view.getId()== R.id.playRelaytive1 ||
                view.getId()== R.id.playRelaytive2 ||
                view.getId()== R.id.playRelaytive3 ||
                view.getId()== R.id.playRelaytive4)&&(showPopUp.get()==false)){
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                view.setBackgroundResource(R.drawable.play_button_new2);
            } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                view.setBackgroundResource(R.drawable.play_button_new1);
            }
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        Intent intentCheck=null;
        switch(v.getId()){
            case R.id.playRelaytive1:
                intentCheck = new Intent(this.CHOOSE_1);
                break;
            case R.id.playRelaytive2:
                intentCheck = new Intent(this.CHOOSE_2);
                break;
            case R.id.playRelaytive3:
                intentCheck = new Intent(this.CHOOSE_3);
                break;
            case R.id.playRelaytive4:
                intentCheck = new Intent(this.CHOOSE_4);
                break;
        }
        sendBroadcast(intentCheck);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.leftin, R.anim.rightout);
    }

    @Override
    public void onDestroy() {
        if(song3!=null){
            song3.pause();
        }
        song.reset();
        song2.reset();
        song3.reset();
        if(timerHandler!=null) {
            timerHandler.removeCallbacks(runnable);
        }
        this.unregisterReceiver(receiver);
        super.onDestroy();
    }

    public void showScoreboard(){
        relativeLayoutPlay.startAnimation(shake);

        if(showPopUp.get()==false) {
            if(!isFinishing()) {
                //FragmentManager fragmentManager = getSupportFragmentManager();
                //Hiện logo lên trong tầm 2s rồi chuyển đến menu chính
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.topin, R.anim.bottomout);


                Bundle bundleScore = new Bundle();
                bundleScore.putInt("score", score);
                bundleScore.putInt("time", progrssBarStatus);
                bundleScore.putBoolean("winOrLose",winOrLose);

                Fragment scoreboardFragment = new ScoreboardFragment();

                scoreboardFragment.setArguments(bundleScore);

                if (fragmentManager.findFragmentById(R.id.scoreboardFragment) != null) {
                    fragmentManager.beginTransaction().remove(fragmentManager.findFragmentById(R.id.scoreboardFragment)).commit();
                }
                fragmentTransaction.add(R.id.activity_play, scoreboardFragment);
                fragmentTransaction.commitAllowingStateLoss();
                relativeLayoutPlay.setAlpha(0.2f);// Relative Layout -> alpha
                playRelaytive1.setClickable(false);
                playRelaytive2.setClickable(false);
                playRelaytive3.setClickable(false);
                playRelaytive4.setClickable(false);
            }
        }
        showPopUp.set(true);
        if(song3!=null){
            song3.stop();
        }
        if(song2!=null){
            song2.stop();
            song2.release();
        }
        song2 = MediaPlayer.create(PlayActivity.this, R.raw.lose2);
        if(sound==true) {
            song2.start();
        }
    }

    public void chayTiengTrinh() {
        timerHandler = new Handler();

        runnable = new Runnable() {
            @Override
            public void run() {
                if (progrssBarStatus > 0 && stopProgressBar == false) {
                    progrssBarStatus = progrssBarStatus - 1;// -2
                    progressBar.setProgress(progrssBarStatus);
                    timerHandler.postDelayed(this, 50);
                }

                if (progrssBarStatus == 0) {
                    showScoreboard();
                }
            }
        };
        timerHandler.postDelayed(runnable, 5);
    }


    public void setupQuestion(Question question, Question[] wrongAnswer){
        progrssBarStatus=100;
        Random r = new Random();
        int randomNumber = r.nextInt(3-1)+1;

        //randomNumber = 1;//TEST

        int randomNumber2 =0;
        if(randomNumber==1){
            //Image in question, String in answer
            Random r2 = new Random();
            randomNumber2 = r2.nextInt(5-1)+1;

            //randomNumber2 = 1;//TEST

            int resTrue = getResources().getIdentifier(question.getEnglish(), "drawable", PACKAGE_NAME );
            playImageViewBig.setVisibility(View.VISIBLE);
            playImageViewBig.setImageResource(resTrue);
            playBigText.setVisibility(View.GONE);

            playImageView1.setImageResource(0);
            playImageView2.setImageResource(0);
            playImageView3.setImageResource(0);
            playImageView4.setImageResource(0);


            switch (randomNumber2){
                case 1 :
                    playBigText.setText(question.getEnglish());
                    playTextView1.setText(question.getEnglish());
                    playTextView2.setText(wrongAnswer[0].getEnglish());
                    playTextView3.setText(wrongAnswer[1].getEnglish());
                    playTextView4.setText(wrongAnswer[2].getEnglish());
                    break;
                case 2:
                    playBigText.setText(question.getEnglish());
                    playTextView1.setText(wrongAnswer[0].getEnglish());
                    playTextView2.setText(question.getEnglish());
                    playTextView3.setText(wrongAnswer[1].getEnglish());
                    playTextView4.setText(wrongAnswer[2].getEnglish());
                    break;
                case 3:
                    playBigText.setText(question.getEnglish());
                    playTextView1.setText(wrongAnswer[0].getEnglish());
                    playTextView2.setText(wrongAnswer[1].getEnglish());
                    playTextView3.setText(question.getEnglish());
                    playTextView4.setText(wrongAnswer[2].getEnglish());
                    break;
                case 4:
                    playBigText.setText(question.getEnglish());
                    playTextView1.setText(wrongAnswer[0].getEnglish());
                    playTextView2.setText(wrongAnswer[1].getEnglish());
                    playTextView3.setText(wrongAnswer[2].getEnglish());
                    playTextView4.setText(question.getEnglish());
                    break;
            }
        }
        else{
            //String in answer, Image in answer
            Random r2 = new Random();
            randomNumber2 = r2.nextInt(5-1)+1;
            playImageViewBig.setVisibility(View.GONE);
            playBigText.setVisibility(View.VISIBLE);
            playBigText.setText(question.getEnglish());
            int resTrue = getResources().getIdentifier(question.getEnglish(), "drawable", PACKAGE_NAME );
            int resWrong1 = getResources().getIdentifier(wrongAnswer[0].getEnglish(), "drawable", PACKAGE_NAME );
            int resWrong2 = getResources().getIdentifier(wrongAnswer[1].getEnglish(), "drawable", PACKAGE_NAME );
            int resWrong3 = getResources().getIdentifier(wrongAnswer[2].getEnglish(), "drawable", PACKAGE_NAME );
            playImageView1.setVisibility(View.VISIBLE);
            playImageView2.setVisibility(View.VISIBLE);
            playImageView3.setVisibility(View.VISIBLE);
            playImageView4.setVisibility(View.VISIBLE);

            playTextView1.setText("");
            playTextView2.setText("");
            playTextView3.setText("");
            playTextView4.setText("");
            switch (randomNumber2){
                case 1 :
                    playImageView1.setImageResource(resTrue);
                    playImageView2.setImageResource(resWrong1);
                    playImageView3.setImageResource(resWrong2);
                    playImageView4.setImageResource(resWrong3);
                    break;
                case 2:
                    playImageView1.setImageResource(resWrong1);
                    playImageView2.setImageResource(resTrue);
                    playImageView3.setImageResource(resWrong2);
                    playImageView4.setImageResource(resWrong3);
                    break;
                case 3:
                    playImageView1.setImageResource(resWrong1);
                    playImageView2.setImageResource(resWrong2);
                    playImageView3.setImageResource(resTrue);
                    playImageView4.setImageResource(resWrong3);
                    break;
                case 4:
                    playImageView1.setImageResource(resWrong1);
                    playImageView2.setImageResource(resWrong2);
                    playImageView3.setImageResource(resWrong3);
                    playImageView4.setImageResource(resTrue);
                    break;
            }
        }
        numberQuestionTrue = randomNumber2;
    }

    public void nextQuestion(List<Question> listQuestion){
        numberQuestion++;
        getData.open();
        if(numberQuestion>listQuestion.size()){
            if(listQuestion.size()<=100) {
                //Toast.makeText(this, "Increase position", Toast.LENGTH_SHORT).show();
                if (getData.getPosition() < getData.countData()){
                    getData.updatePosition();
                }
                //Toast.makeText(this, "id of next question: " + (getData.getPosition()), Toast.LENGTH_SHORT).show();
                Question questionAdd = getData.getQuestion(getData.getPosition());
                listQuestion.add(questionAdd);
            }
        }
        if(numberQuestion<=listQuestion.size()) {
            currentQuestion = listQuestion.get(numberQuestion-1);
            setupQuestion(listQuestion.get(numberQuestion-1), getData.getWrongQuestion(listQuestion.get(numberQuestion-1).getId()));//Lỗi hiểm vcc numberQuestion-1 do List chạy từ 0
        }
        else{
            //Toast.makeText(this, "You WIN", Toast.LENGTH_SHORT).show();
        }
    }

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //Lay vao cac dap an
            String action = intent.getAction();
            if(action.equals(CHOOSE_1)){
                if(numberQuestionTrue==1){
                    nextQuestion(listQuestion);
//                    MediaPlayer song = MediaPlayer.create(getActivity(), R.raw.win);
//                    song.start();
                    score++;
                    if(song!=null){
                        song.stop();
                        song.release();
                    }
                    song = MediaPlayer.create(PlayActivity.this, R.raw.win);
                    if(sound==true) {
                        song.start();
                    }
                    playTextViewScore.setText(score+"");
                }
                else{
                    relativeLayoutPlay.startAnimation(shake);
                    if(song2!=null){
                        song2.stop();
                        song2.release();
                    }
                    song2 = MediaPlayer.create(PlayActivity.this, R.raw.lose2);
                    if(countWrong==2){
                        endGame();
                        if(sound==true) {
                            textToSpeech.setLanguage(Locale.UK);
                            textToSpeech.speak(playBigText.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
                        }
                    }
                    else{
                        if(sound==true) {
                            song2.start();
                            textToSpeech.setLanguage(Locale.UK);
                            textToSpeech.speak(playBigText.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
                        }
                        //Toast.makeText(context, "False", Toast.LENGTH_SHORT).show();
//                        MediaPlayer song = MediaPlayer.create(getActivity(), R.raw.lose2);
//                        song.start();
                        countWrong++;
                        score--;
                        listQuestion.add(currentQuestion);
                        nextQuestion(listQuestion);
                    }
                }
            }
            else if(action.equals(CHOOSE_2)){
                if(numberQuestionTrue==2){
                    nextQuestion(listQuestion);
//                    MediaPlayer song = MediaPlayer.create(getActivity(), R.raw.win);
//                    song.start();
                    score++;
                    if(song!=null){
                        song.stop();
                        song.release();
                    }
                    song = MediaPlayer.create(PlayActivity.this, R.raw.win);
                    if(sound==true) {
                        song.start();
                    }
                    playTextViewScore.setText(score+"");
                }
                else{
                    relativeLayoutPlay.startAnimation(shake);
                    if(song2!=null){
                        song2.stop();
                        song2.release();
                    }
                    song2 = MediaPlayer.create(PlayActivity.this, R.raw.lose2);

                    if(countWrong==2){
                        endGame();
                        if(sound==true) {
                            textToSpeech.setLanguage(Locale.UK);
                            textToSpeech.speak(playBigText.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
                        }
                    }
                    else{
                        if(sound==true) {
                            song2.start();
                            textToSpeech.setLanguage(Locale.UK);
                            textToSpeech.speak(playBigText.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
                        }
                        //Toast.makeText(context, "False", Toast.LENGTH_SHORT).show();
//                        MediaPlayer song = MediaPlayer.create(getActivity(), R.raw.lose2);
//                        song.start();
                        countWrong++;
                        score--;
                        listQuestion.add(currentQuestion);
                        nextQuestion(listQuestion);
                    }
                }
            }
            else if(action.equals(CHOOSE_3)){
                if(numberQuestionTrue==3){
                    nextQuestion(listQuestion);
//                    MediaPlayer song = MediaPlayer.create(getActivity(), R.raw.win);
//                    song.start();
                    score++;
                    if(song!=null){
                        song.stop();
                        song.release();
                    }
                    song = MediaPlayer.create(PlayActivity.this, R.raw.win);
                    if(sound==true) {
                        song.start();
                    }
                    playTextViewScore.setText(score+"");
                }
                else{
                    relativeLayoutPlay.startAnimation(shake);
                    if(song2!=null){
                        song2.stop();
                        song2.release();
                    }
                    song2 = MediaPlayer.create(PlayActivity.this, R.raw.lose2);
                    if(countWrong==2){
                        endGame();
                        if(sound==true) {
                            textToSpeech.setLanguage(Locale.UK);
                            textToSpeech.speak(playBigText.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
                        }
                    }
                    else{
                        if(sound==true) {
                            song2.start();
                            textToSpeech.setLanguage(Locale.UK);
                            textToSpeech.speak(playBigText.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
                        }
                        //Toast.makeText(context, "False", Toast.LENGTH_SHORT).show();
//                        MediaPlayer song = MediaPlayer.create(getActivity(), R.raw.lose2);
//                        song.start();
                        countWrong++;
                        score--;
                        listQuestion.add(currentQuestion);
                        nextQuestion(listQuestion);
                    }
                }
            }
            else if(action.equals(CHOOSE_4)){
                if(numberQuestionTrue==4){
                    nextQuestion(listQuestion);
//                    MediaPlayer song = MediaPlayer.create(getActivity(), R.raw.win);
//                    song.start();
                    score++;
                    if(song!=null){
                        song.stop();
                        song.release();
                    }
                    song = MediaPlayer.create(PlayActivity.this, R.raw.win);
                    if(sound==true) {
                        song.start();
                    }
                    playTextViewScore.setText(score+"");
                }
                else{
                    relativeLayoutPlay.startAnimation(shake);
                    if(song2!=null){
                        song2.stop();
                        song2.release();
                    }
                    song2 = MediaPlayer.create(PlayActivity.this, R.raw.lose2);
                    if(sound==true) {
                        song2.start();
                        if(sound==true) {
                            textToSpeech.setLanguage(Locale.UK);
                            textToSpeech.speak(playBigText.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
                        }
                    }
                    if(countWrong==2){
                        endGame();
                        textToSpeech.setLanguage(Locale.UK);
                        textToSpeech.speak(playBigText.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
                    }
                    else{
                        if(sound==true) {
                            song2.start();
                            textToSpeech.setLanguage(Locale.UK);
                            textToSpeech.speak(playBigText.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
                        }
                        //Toast.makeText(context, "False", Toast.LENGTH_SHORT).show();
//                        MediaPlayer song = MediaPlayer.create(getActivity(), R.raw.lose2);
//                        song.start();
                        countWrong++;
                        score--;
                        listQuestion.add(currentQuestion);
                        nextQuestion(listQuestion);
                    }
                }
            }
            else if(action.equals(RESTART_PLAY)==true){
                restart();
            }
            if(score<0){
                score=0;
            }
            if(score==getData.countData()) {
                winOrLose = true;
                showScoreboard();
                stopProgressBar = true;
            }
            if(score==1) {
                chayTiengTrinh();
            }
        }
    };

    public void endGame(){
        showScoreboard();
        stopProgressBar=true;
        if(song2!=null){
            song2.stop();
            song2.release();
        }
        song2 = MediaPlayer.create(PlayActivity.this, R.raw.lose2);
        if(sound==true) {
            song2.start();
        }
        if(song3!=null){
            song3.stop();
        }
    }

    //Restart Activity
    public void restart(){
        finish();
        startActivity(getIntent());
    }

}
