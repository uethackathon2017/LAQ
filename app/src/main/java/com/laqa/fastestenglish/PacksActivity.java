package com.laqa.fastestenglish;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.laqa.fastestenglish.Adapter.GridViewAdapter;
import com.laqa.fastestenglish.SQLite.GetData;

public class PacksActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener{

    ImageButton packsButtonBack;
    GridView packsGridView;
    GetData getData;

    String[] web = {
            "Shapes",
            "Animals",
            "Lock",
            "Lock",
            "Lock",
            "Lock",
            "Lock",
            "Lock"
    } ;
    int[] imageId = {
            R.drawable.shapes,
            R.drawable.animals,
            R.drawable.packs_lock,
            R.drawable.packs_lock,
            R.drawable.packs_lock,
            R.drawable.packs_lock,
            R.drawable.packs_lock,
            R.drawable.packs_lock
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_packs);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        overridePendingTransition(R.anim.rightin, R.anim.leftout);

        packsButtonBack=(ImageButton)findViewById(R.id.packsButtonBack);
        packsGridView=(GridView)findViewById(R.id.packsGridView);

        packsButtonBack.setOnClickListener(this);
        packsButtonBack.setOnTouchListener(this);

        final GridViewAdapter adapter = new GridViewAdapter(this, web, imageId);
        packsGridView=(GridView)findViewById(R.id.packsGridView);
        packsGridView.setAdapter(adapter);

        getData= new GetData(this);
        getData.open();

        packsGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                packsGridView.findViewById(R.id.gridViewStar).setVisibility(View.INVISIBLE);
                //Toast.makeText(PacksActivity.this, "You Clicked at " +web[+ position], Toast.LENGTH_SHORT).show();
                if(position<2) { //2 là số lượng gói mở ra
                    view.findViewById(R.id.gridViewStar).setVisibility(View.VISIBLE);
                    //getData.setCurrentPacks(position + 1);
                    adapter.notifyDataSetChanged();
                }
            }

        });

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.packsButtonBack:
                //Quay lại
                onBackPressed();
                break;
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if(view.getId()== R.id.packsButtonBack){
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
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.leftin, R.anim.rightout);
    }
}
