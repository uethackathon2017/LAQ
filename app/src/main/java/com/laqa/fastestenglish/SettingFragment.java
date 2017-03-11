package com.laqa.fastestenglish;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.laqa.fastestenglish.SQLite.GetData;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends DialogFragment implements View.OnClickListener{

    RelativeLayout settingFullScreen, settingFragment;
    Typeface typeface;
    TextView menuTextViewEmail;
    Switch switch1,switch2;
    GetData getData;

    public SettingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        settingFullScreen=(RelativeLayout)view.findViewById(R.id.settingFullScreen);
        settingFragment=(RelativeLayout)view.findViewById(R.id.settingFragment);
        menuTextViewEmail=(TextView)view.findViewById(R.id.menuTextViewEmail);
        typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/utm_cookies.ttf");
        menuTextViewEmail.setTypeface(typeface);
        settingFullScreen.setOnClickListener(this);
        settingFragment.setOnClickListener(this);
        switch1=(Switch)view.findViewById(R.id.switch1);
        switch2=(Switch)view.findViewById(R.id.switch2);
        getData = new GetData(getActivity());
        getData.open();

        if(getData.getMusic()==1){
            switch1.setChecked(true);
        }
        else{
            switch1.setChecked(false);
        }

        if(getData.getSound()==1){
            switch2.setChecked(true);
        }
        else{
            switch2.setChecked(false);
        }

        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                getData.changeMusic();
            }
        });
        switch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                getData.changeSound();
            }
        });

        return view;
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.settingFullScreen){
            dismiss();
            getActivity().findViewById(R.id.relativeLayoutMenu).setAlpha(1.0f);
        }
    }
}
