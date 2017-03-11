package com.laqa.fastestenglish;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.laqa.fastestenglish.SQLite.GetData;


/**
 * A simple {@link Fragment} subclass.
 */
public class ScoreboardFragment extends Fragment implements View.OnTouchListener,View.OnClickListener{

    ImageButton playFragmentPlayAgain2,playFragmentPlayAgain,playFragmentSave;
    TextView playFragmentTextViewScore;
    Typeface typeface;
    int score,progressBarStatus;
    TextView wrongOrSlow;
    Intent intentInput;
    boolean winOrLose;
    GetData getData;

    public ScoreboardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_scoreboard, container, false);
        playFragmentPlayAgain2=(ImageButton)view.findViewById(R.id.playFragmentPlayAgain2);
        playFragmentPlayAgain=(ImageButton)view.findViewById(R.id.playFragmentPlayAgain);
        playFragmentSave=(ImageButton)view.findViewById(R.id.playFragmentSave);
        playFragmentTextViewScore=(TextView)view.findViewById(R.id.playFragmentTextViewScore);

        wrongOrSlow=(TextView)view.findViewById(R.id.wrongOrSlow);

        intentInput = new Intent(getActivity(), InputActivity.class);

        playFragmentPlayAgain2.setOnClickListener(this);
        playFragmentPlayAgain.setOnClickListener(this);
        playFragmentSave.setOnClickListener(this);

        playFragmentPlayAgain2.setOnTouchListener(this);
        playFragmentPlayAgain.setOnTouchListener(this);
        playFragmentSave.setOnTouchListener(this);

        typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/utm_cookies.ttf");
        playFragmentTextViewScore.setTypeface(typeface);

        wrongOrSlow.setTypeface(typeface);
        score = this.getArguments().getInt("score");
        progressBarStatus = this.getArguments().getInt("time");
        winOrLose = this.getArguments().getBoolean("winOrLose");
        playFragmentTextViewScore.setText(score+"");

        getData=new GetData(getActivity());
        getData.open();

        if(score<=getData.minScore()){
//            buttonSave.setImageResource(R.drawable.save2);
//            buttonSave.setClickable(false);
//            buttonSave.setEnabled(false);
            playFragmentSave.setVisibility(View.INVISIBLE);
            playFragmentPlayAgain.setVisibility(View.INVISIBLE);
            playFragmentPlayAgain2.setVisibility(View.VISIBLE);

        }

        if(winOrLose==true){
            wrongOrSlow.setText("GOODJOB!!!!");
        }
        else {
            if (progressBarStatus == 0) {
                wrongOrSlow.setText("TOO SLOW");
            } else {
                wrongOrSlow.setText("WRONG");
            }
        }

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.playFragmentPlayAgain2:
                getActivity().finish();
                startActivity(getActivity().getIntent());
                break;
            case R.id.playFragmentPlayAgain:
                getActivity().finish();
                startActivity(getActivity().getIntent());
                break;
            case R.id.playFragmentSave:
                intentInput.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                intentInput.putExtra("score",score);
                startActivity(intentInput);
                break;
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if(view.getId()== R.id.playFragmentPlayAgain2 ||
                view.getId()== R.id.playFragmentPlayAgain ||
                view.getId()== R.id.playFragmentSave){
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
}
