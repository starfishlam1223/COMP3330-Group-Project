package com.example.yellowobjects.ui.game.voice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class ProgressView extends View {
    private static final int LINE_WIDTH = 1;
    private int width;
    private int height;
    private Paint linePaint;

    public ProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        linePaint = new Paint();
        linePaint.setColor(Color.GREEN);
        linePaint.setStrokeWidth(LINE_WIDTH);
    } 

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        width = w;
        height = h;
    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawColor(Color.GRAY);
        int middle = height / 2;
        float progressPercentage = ((float)GameFragment.currentTime)/((float)GameFragment.MAXTIME);
        for(int i = 0; i<width; i+=LINE_WIDTH){
            float loopPercentage = ((float)i+1f)/((float)width);
            if(loopPercentage<=progressPercentage)
                canvas.drawLine(i, middle + 5000 / 2, i, middle - 5000 / 2, linePaint);
        }
    } 

}