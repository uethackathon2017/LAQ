package com.laqa.fastestenglish;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.ListView;

import com.laqa.fastestenglish.Adapter.ListViewAdapter;
import com.laqa.fastestenglish.Question.Record;

import java.util.ArrayList;

public class RecordActivity extends AppCompatActivity implements View.OnClickListener,View.OnTouchListener{

    ListView recordListView;
    ImageButton recordButtonBack;

    float oldPosition,newPosition;
    float distance;
    boolean listViewTop=true;
    boolean listViewBottom=true;

    Animation animation;
    DecelerateInterpolator decelerateInterpolator;
    ListViewAdapter adapter;

    Record record1 = new Record("LEANHQUAN",23);
    Record record2 = new Record("VUMINHTUAN",5);
    Record record3 = new Record("NGUYENCONGHAI",18);

    ArrayList<Record> arrayList= new ArrayList<Record>();
    Record[] listRecord = {
            record1,
            record2,
            record3
    } ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        overridePendingTransition(R.anim.rightin, R.anim.leftout);

        recordListView=(ListView)findViewById(R.id.recordListView);
        recordButtonBack=(ImageButton)findViewById(R.id.recordButtonBack);

        recordButtonBack.setOnTouchListener(this);
        recordButtonBack.setOnClickListener(this);



        decelerateInterpolator = new DecelerateInterpolator(1.0f);

        arrayList.add(record1);arrayList.add(record2);arrayList.add(record3);

        adapter= new ListViewAdapter(RecordActivity.this,arrayList);
        recordListView.setAdapter(adapter);

        recordListView.setOnTouchListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.recordButtonBack){
            //Nhấn vào nút quay lại
            onBackPressed();
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (view.getId()){
            case R.id.recordButtonBack:
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    recordButtonBack.animate().scaleX(1.2f);
                    recordButtonBack.animate().scaleY(1.2f);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    recordButtonBack.animate().scaleX(1.0f);
                    recordButtonBack.animate().scaleY(1.0f);
                }
                break;
            case R.id.recordListView:
                if(recordListView.getChildCount()>1) {
                    if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                        oldPosition = motionEvent.getY();
                    } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {

                        newPosition = motionEvent.getY();
                        distance = newPosition - oldPosition;
                        if (distance > 0 && listViewTop == true) {//Keo xuong
                            //Toast.makeText(this, "Keo xuong "+khoangCach, Toast.LENGTH_SHORT).show();
                            animation = AnimationUtils.loadAnimation(this, R.anim.topin);
                            //listView.startAnimation(animation);
                            for (int i = 0; i < recordListView.getChildCount(); i++) {
                                //listView.getChildAt(i).startAnimation(animation);

                                recordListView.getChildAt(i).setTranslationY(i * 50);
                                recordListView.getChildAt(i).animate()
                                        .setInterpolator(decelerateInterpolator)
                                        .translationY(0f)
                                        .setDuration(500l)
                                        .setListener(null);

                            }
                        } else if (distance < 0 && listViewBottom == true) {
                            //Toast.makeText(this, "Keo len "+khoangCach, Toast.LENGTH_SHORT).show();
                            //animation = AnimationUtils.loadAnimation(this, R.anim.topin);
                            //listView.startAnimation(animation);
                            for (int i = 0; i < recordListView.getChildCount(); i++) {
                                //listView.getChildAt(i).startAnimation(animation);

                                recordListView.getChildAt(i).setTranslationY((-1) * i * 15);
                                recordListView.getChildAt(i).animate()
                                        .setInterpolator(decelerateInterpolator)
                                        .translationY(0f)
                                        .setDuration(500l)
                                        .setListener(null);

                            }
                        }

                    }

                    break;
                }
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.leftin, R.anim.rightout);
    }
}
