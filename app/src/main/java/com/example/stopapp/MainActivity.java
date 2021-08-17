package com.example.stopapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.view.View;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private long millisecond=0;
    private boolean running=false;
    private boolean wasrunning=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState!=null){
            millisecond=savedInstanceState.getLong("millisecond");
            running=savedInstanceState.getBoolean("running");
            wasrunning=savedInstanceState.getBoolean("wasrunning");
        }
        runtime();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putLong("millisecond",millisecond);
        savedInstanceState.putBoolean("running",running);
        savedInstanceState.putBoolean("wasrunning",wasrunning);

    }

    protected void onPause(){
        super.onPause();
        wasrunning=running;
        running=false;
    }

    protected void onResume(){
        super.onResume();
        if(wasrunning){
            running=true;
        }
    }

    public void toStartWatch(View view){
        running=true;
    }

    public void toStopWatch(View view){
        running=false;
    }

    public void toResetWatch(View view){
        running=false;
        millisecond=0;
    }
    public void runtime(){
        TextView timeView=(TextView) findViewById(R.id.time);
        final Handler handle=new Handler();
        handle.post(new Runnable() {
            @Override
            public void run() {
                long hours=millisecond/360000;
                int min=(int) (millisecond%360000)/6000;
                int sec=(int) (millisecond%6000)/100;
                int mili = (int) millisecond%100;

                StringBuilder h = new StringBuilder();
                StringBuilder m = new StringBuilder();
                StringBuilder s = new StringBuilder();
                StringBuilder mi = new StringBuilder();

                if(hours==0){
                    h.append("00");
                }
                else if(hours<10){
                    h.append("0");
                    h.append(hours);
                }
                else{
                    h.append(hours);
                }

                if(min==0){
                    m.append("00");
                }
                else if(min<10){
                    m.append("0");
                    m.append(min);
                }
                else{
                    m.append(min);
                }

                if(sec==0){
                    s.append("00");
                }
                else if(sec<10){
                    s.append("0");
                    s.append(sec);
                }
                else{
                    s.append(sec);
                }

                if(mili==0){
                    mi.append("00");
                }
                else if(mili<10){
                    mi.append("0");
                    mi.append(mili);
                }
                else{
                    mi.append(mili);
                }

                String timeText = String.format("%s:%s:%s:%s",h,m,s,mi);
                timeView.setText(timeText);
                if(running){
                    millisecond++;
                }
                handle.postDelayed(this, 1);
            }
        });
    }
}