package com.laqa.fastestenglish;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class ScoreboardFragment extends Fragment implements View.OnTouchListener,View.OnClickListener{

    ImageButton playFragmentPlayAgain,playFragmentSave;

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

        playFragmentPlayAgain.setOnClickListener(this);
        playFragmentSave.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return false;
    }
}
