package com.laqa.fastestenglish;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ScoreboardFragment extends Fragment implements View.OnTouchListener,View.OnClickListener{

    ImageButton playFragmentPlayAgain,playFragmentSave;
    TextView playFragmentTextViewScore;

    public ScoreboardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_scoreboard, container, false);
        playFragmentPlayAgain=(ImageButton)view.findViewById(R.id.playFragmentPlayAgain);
        playFragmentSave=(ImageButton)view.findViewById(R.id.playFragmentSave);
        playFragmentTextViewScore=(TextView)view.findViewById(R.id.playFragmentTextViewScore);

        playFragmentPlayAgain.setOnClickListener(this);
        playFragmentSave.setOnClickListener(this);

        playFragmentPlayAgain.setOnTouchListener(this);
        playFragmentSave.setOnTouchListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.playFragmentPlayAgain:
                getActivity().finish();
                startActivity(getActivity().getIntent());
                break;
            case R.id.playFragmentSave:

                break;
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if(view.getId()== R.id.playFragmentPlayAgain ||
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
