package com.laqa.fastestenglish;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.laqa.fastestenglish.Question.Record;
import com.laqa.fastestenglish.SQLite.GetData;

import java.util.ArrayList;

public class InputActivity extends AppCompatActivity implements View.OnTouchListener,View.OnClickListener{

    ArrayList<Record> DuLieu= new ArrayList<Record>();
    EditText inputEditText;
    ImageButton inputSaveButton;
    TextView inputTextViewScore,inputScore;
    GetData getData;
    Typeface typeface;
    int score;
    Intent intentPlayRestart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        inputEditText=(EditText)findViewById(R.id.inputEditText);
        inputSaveButton=(ImageButton)findViewById(R.id.inputSaveButton);
        inputTextViewScore=(TextView)findViewById(R.id.inputTextViewScore);
        inputScore=(TextView)findViewById(R.id.inputScore);

        typeface = Typeface.createFromAsset(getAssets(), "fonts/utm_cookies.ttf");
        inputEditText.setTypeface(typeface);
        inputTextViewScore.setTypeface(typeface);
        inputScore.setTypeface(typeface);

        inputSaveButton.setOnClickListener(this);
        inputSaveButton.setOnTouchListener(this);

        intentPlayRestart = new Intent(PlayActivity.RESTART_PLAY);

        getData = new GetData(InputActivity.this);
        getData.open();

        Bundle bd=getIntent().getExtras();
        score = bd.getInt("score");
        inputScore.setText(String.valueOf(score));

    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.inputSaveButton) {
            Record rc = new Record();

            String ten = inputEditText.getText().toString();
            if (ten.trim().length() > 0) {
                rc.setTen(ten);
                rc.setDiem(score);

                if (getData.themDuLieu(rc) == true) {
                    //Toast.makeText(InputActivity.this, "THEM THANH CONG", Toast.LENGTH_SHORT).show();
                    DuLieu = getData.layRecord();
                    if (DuLieu.size() > 10) {
                        getData.xoaDuLieu(DuLieu.get(10).getId());
                    }
                } else {
                    Toast.makeText(InputActivity.this, "FAILED!!!!", Toast.LENGTH_SHORT).show();
                }
                Intent intentRate = new Intent(InputActivity.this, RecordActivity.class);// tac dung static =))))
                intentRate.putExtra("score", score);
                startActivity(intentRate);
            } else {
                Toast.makeText(InputActivity.this, "Please type your name", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if(view.getId()== R.id.inputSaveButton){
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                view.animate().setDuration(100);
                view.animate().scaleX(1.1f);
                view.animate().scaleY(1.1f);
            } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                view.animate().setDuration(100);
                view.animate().scaleX(1.0f);
                view.animate().scaleY(1.0f);
            }
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        getData.close();
        sendBroadcast(intentPlayRestart);
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.leftin, R.anim.rightout);
    }
}
