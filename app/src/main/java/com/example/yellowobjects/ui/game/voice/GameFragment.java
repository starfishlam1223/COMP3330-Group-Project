package com.example.yellowobjects.ui.game.voice;

import java.io.IOException;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
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

    public static final float BASE = 8000f;
    public static final int MAXTIME = 7770;
    public static int currentTime = 0;

    private MediaRecorder myAudioRecorder;

    private boolean isRecording = false;
    private ProgressView progressView;
    private VolumeVisualizerView visualizerView;
    private View rootView;
    public static ImageView maria;
    public TextView description;
    public TextView timeDisplay;

    private boolean doubleBackToExitPressedOnce;
    private Handler mHandler = new Handler();

    private final Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            doubleBackToExitPressedOnce = false;
        }
    };

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
    private void recordStart(){
        if(!isRecording){
            myAudioRecorder = new MediaRecorder();
            myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.VOICE_RECOGNITION);
            myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
            myAudioRecorder.setOutputFile("/dev/null");
            handler.post(updateVisualizer);
            try {
                myAudioRecorder.prepare();
                myAudioRecorder.start();
                description.setText(R.string.description_scream);
                isRecording = true;
                handler.post(updateVisualizer);
            }
            catch (IOException e) {
                recordPause();
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.activity_game, container,false);

        progressView = (ProgressView) rootView.findViewById(R.id.progress);
        visualizerView = (VolumeVisualizerView) rootView.findViewById(R.id.visualizer);
        maria = (ImageView) rootView.findViewById(R.id.fat);
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

        rootView.setFocusableInTouchMode(true);
        rootView.requestFocus();
        return rootView;

    }

    @Override
    public void onPause() {
        super.onPause();
        if (isRecording) {
            try {
                if (null != myAudioRecorder) {
                    myAudioRecorder.stop();
                    myAudioRecorder.release();
                    myAudioRecorder = null;
                    recordPause();
                    handler.removeCallbacks(updateVisualizer);
                }
            } catch (Exception e) {
                recordPause();
                handler.removeCallbacks(updateVisualizer);
            }
        }
    }

    Runnable updateVisualizer = new Runnable() {
        @Override
        public void run() {
            if (isRecording){
                int x = myAudioRecorder.getMaxAmplitude();
                visualizerView.addAmplitude(x);
                if(x>((int)GameFragment.BASE)){
                    GameFragment.currentTime += REPEAT_INTERVAL;
                    float time = MAXTIME / 1000f;
                    if(GameFragment.currentTime< GameFragment.MAXTIME)
                        time = GameFragment.currentTime/1000f;
                    timeDisplay.setText(String.format("%.2f", time)+"s");
                }
                else resetTime();
                visualizerView.invalidate();
                progressView.invalidate();
                handler.postDelayed(this, REPEAT_INTERVAL);
            }
        }
    };
}
