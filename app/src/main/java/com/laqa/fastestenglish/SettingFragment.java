package com.laqa.fastestenglish;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends DialogFragment implements View.OnClickListener{

    RelativeLayout settingFullScreen, settingFragment;

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
        settingFullScreen.setOnClickListener(this);
        settingFragment.setOnClickListener(this);
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
