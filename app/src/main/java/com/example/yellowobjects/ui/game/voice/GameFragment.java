package com.example.yellowobjects.ui.game.voice;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yellowobjects.R;

public class GameFragment extends Fragment {
    public static GameFragment lastFragment = null;

    public static final float BASE = 7770f;
    public static final int MAXTIME = 7770;
    public static int currentTime = 0;

    public MediaRecorder myAudioRecorder = null;
    private boolean isRecording = false;

    private ProgressView progressView;
    private VolumeVisualizerView visualizerView;
    private View rootView;
    public static ImageView maria;
    public ImageView bigpic;
    public TextView description;
    public TextView timeDisplay;

    MediaPlayer mpSiachandelier_A;
    MediaPlayer mpSiachandelier_B;

    public static final int REPEAT_INTERVAL = 10;

    private Handler handler = new Handler();

    private void resetTime(){
        timeDisplay.setText(R.string.zero_sec);
        GameFragment.currentTime = 0;
    }
    private void recordPause(){
        visualizerView.clear();
        resetTime();
        description.setText(R.string.description_touch_below);
        isRecording = false;
        visualizerView.invalidate();
        progressView.invalidate();
    }
    private void cleanup(){
        if (isRecording) {
            if (myAudioRecorder != null) {
                try {
                    myAudioRecorder.stop();
                    myAudioRecorder.release();
                } catch (Exception e) {
                }
                myAudioRecorder = null;
            }
            recordPause();
            handler.removeCallbacks(updateVisualizer);
        }
        if (mpSiachandelier_A != null){
            mpSiachandelier_A.release();
            mpSiachandelier_A = null;
        }
        if (mpSiachandelier_B != null){
            mpSiachandelier_B.release();
            mpSiachandelier_B = null;
        }
        bigHeadDisappear();
    }

    private void recordStart(){
        if(!isRecording){
            myAudioRecorder = new MediaRecorder();
            myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.VOICE_RECOGNITION);
            myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
            myAudioRecorder.setOutputFile("/dev/null");
            try {
                myAudioRecorder.prepare();
                myAudioRecorder.start();
                description.setText(R.string.description_scream);
                isRecording = true;
                handler.post(updateVisualizer);
                //handler.postDelayed(updateVisualizer, REPEAT_INTERVAL);
            }
            catch (IOException e) {
                recordPause();
            }
            mpSiachandelier_A = MediaPlayer.create(getContext(), R.raw.siachandelier_a);
            mpSiachandelier_B = MediaPlayer.create(getContext(), R.raw.siachandelier_b);
        }
    }

    public boolean alertShown = false;
    public static boolean bigHeadShown = false;
    private void bigHeadAppear(){
        bigpic.setVisibility(View.VISIBLE);
        alertShown = true;
        GameFragment.bigHeadShown = true;
    }
    private void bigHeadDisappear(){
        bigpic.setVisibility(View.GONE);
        alertShown = false;
        GameFragment.bigHeadShown = false;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(GameFragment.lastFragment!=null)
            GameFragment.lastFragment.cleanup();
        GameFragment.lastFragment = this;

        rootView = inflater.inflate(R.layout.activity_game, container,false);

        progressView = (ProgressView) rootView.findViewById(R.id.progress);
        visualizerView = (VolumeVisualizerView) rootView.findViewById(R.id.visualizer);
        maria = (ImageView) rootView.findViewById(R.id.fat);
        bigpic = (ImageView) rootView.findViewById(R.id.bigpic);
        description = (TextView) rootView.findViewById(R.id.txt_description);
        timeDisplay = (TextView) rootView.findViewById(R.id.txt_time);

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.RECORD_AUDIO}, 10);
        else recordStart();

        visualizerView.setOnTouchListener(
                new OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        recordStart();
                        return false;
                    }
                });
        bigpic.setOnTouchListener(
                new OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        //cleanup();
                        //recordStart();
                        visualizerView.clear();
                        if(mpSiachandelier_A!=null)
                            if(mpSiachandelier_A.isPlaying()){
                                mpSiachandelier_A.seekTo(0);
                                mpSiachandelier_A.pause();
                            }
                        if(mpSiachandelier_B!=null)
                            if(mpSiachandelier_B.isPlaying()){
                                mpSiachandelier_B.seekTo(0);
                                mpSiachandelier_B.pause();
                            }
                        resetTime();
                        bigHeadDisappear();
                        return false;
                    }
                });

        rootView.setFocusableInTouchMode(true);
        rootView.requestFocus();

        return rootView;
    }
    @Override
    public void onPause() {
        super.onPause();
        cleanup();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        cleanup();
    }

    Runnable updateVisualizer = new Runnable(){
        float lastScales[] = {1,1,1,1,1};
        int ptr = 0;
        long startTime = System.currentTimeMillis();
        @Override
        public void run() {
            if (isRecording){
                int x = myAudioRecorder.getMaxAmplitude();
                if(GameFragment.bigHeadShown){
                    float newScale = 1f + ((float)Math.pow((x/15000f),3));
                    float scale = (newScale+lastScales[0]+lastScales[1]+lastScales[2]+lastScales[3]+lastScales[4])/6;
                    bigpic.setScaleX(scale);
                    bigpic.setScaleY(scale);
                    lastScales[ptr] = scale;
                    ptr = (ptr +1) % 5;
                }
                visualizerView.addAmplitude(x);
                if(x>((int)GameFragment.BASE)){
                    float time = MAXTIME / 1000f;
                    GameFragment.currentTime = (int)(System.currentTimeMillis()-startTime);

                    if(GameFragment.currentTime >= 6100){
                        if(!alertShown &&!GameFragment.bigHeadShown){
                            if(mpSiachandelier_A!=null)
                                mpSiachandelier_A.start();
                            alertShown = true;
                        }
                    }
                    else alertShown = false;
                    if(GameFragment.currentTime< GameFragment.MAXTIME)
                        time = GameFragment.currentTime/1000f;
                    else if(GameFragment.currentTime >= GameFragment.MAXTIME){
                        if(!GameFragment.bigHeadShown){
                            bigHeadAppear();
                            if(mpSiachandelier_B!=null)
                                mpSiachandelier_B.start();
                        }
                    }
                    timeDisplay.setText(String.format("%.2f", time)+"s");
                }
                else{
                    startTime = System.currentTimeMillis();
                    resetTime();
                }
                visualizerView.invalidate();
                progressView.invalidate();
            }
            handler.postDelayed(updateVisualizer, REPEAT_INTERVAL);
        }
    };
}
