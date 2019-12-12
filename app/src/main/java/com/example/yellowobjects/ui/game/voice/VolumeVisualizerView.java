package com.example.yellowobjects.ui.game.voice;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.example.yellowobjects.R;

public class VolumeVisualizerView extends View {
    private static final int LINE_WIDTH = 5;
    private static final int LINE_SCALE = 15;
    private List<Float> amplitudes = null;
    private int width;
    private int height;
    private Paint basePaint;
    private Paint linePaint;

    public VolumeVisualizerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        linePaint = new Paint();
        linePaint.setColor(Color.GREEN);
        linePaint.setStrokeWidth(LINE_WIDTH);
        basePaint = new Paint();
        basePaint.setColor(Color.RED);
        basePaint.setStrokeWidth(LINE_WIDTH);
    } 

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        width = w;
        height = h;
        amplitudes = new ArrayList<Float>(width / LINE_WIDTH);
    } 

    public void clear() {
        amplitudes.clear();
    }

    public void addAmplitude(float amplitude) {
        if(amplitude<50f)
            amplitude=50f;
        amplitudes.add(amplitude);

        if (amplitudes.size() * LINE_WIDTH >= width)
            amplitudes.remove(0);
    } 

    boolean fatMaB = false;
    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        if(amplitudes.size()>0){
            float lastAmplitude = amplitudes.get(amplitudes.size()-1);
            if(lastAmplitude>=GameFragment.BASE){
                canvas.drawColor(Color.YELLOW);
                if(!fatMaB){
                    GameFragment.maria.setImageResource(R.drawable.fat_ma_b);
                    fatMaB = true;
                }
                else{
                    float scale = 1+20*((float)Math.pow((((float)GameFragment.currentTime)/((float)GameFragment.MAXTIME)),5));
                    GameFragment.maria.setScaleX(scale);
                    GameFragment.maria.setScaleY(scale);
                }
            }
            else if(fatMaB){
                GameFragment.maria.setImageResource(R.drawable.fat_ma_a);
                float scale = 1;
                GameFragment.maria.setScaleX(scale);
                GameFragment.maria.setScaleY(scale);
                fatMaB = false;
            }
        }

        int middle = height / 2;
        float scaledBase = GameFragment.BASE / LINE_SCALE;
        float curX = 0;

        for (float power : amplitudes) {
            float scaledHeight = power / LINE_SCALE;
            curX += LINE_WIDTH;

            if(scaledHeight>scaledBase)
                canvas.drawLine(curX, middle + scaledHeight / 2, curX, middle - scaledHeight / 2, linePaint);
            else{
                canvas.drawLine(curX, middle + scaledBase / 2, curX, middle - scaledBase / 2, basePaint);
                canvas.drawLine(curX, middle + scaledHeight / 2, curX, middle - scaledHeight / 2, linePaint);
            }
        } 
    } 

}