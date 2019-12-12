package hk.hkucs.yellowobjects.ui.game;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import hk.hkucs.yellowobjects.R;

public class VolumeVisualizerView extends View {
    private static final int LINE_WIDTH = 5;
    private static final int LINE_SCALE = 15;
    private List<Float> amplitudes = null;
    private int width = -1;
    private int height = -1;
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
        if(w!=width)
            amplitudes = new ArrayList<Float>(w / LINE_WIDTH);
        width = w;
        height = h;
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
    int chance = 10;
    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        if(amplitudes.size()>0){
            float lastAmplitude = amplitudes.get(amplitudes.size()-1);
            if(fatMaB){
                canvas.drawColor(Color.YELLOW);
                if(lastAmplitude>=GameFragment.BASE){
                    float scale = 1+20*((float)Math.pow((((float)GameFragment.currentTime)/((float)GameFragment.MAXTIME)),5));
                    GameFragment.maria.setScaleX(scale);
                    GameFragment.maria.setScaleY(scale);
                    chance = 10;
                }
                else{
                    if(chance>0)
                        chance--;
                    else{
                        GameFragment.maria.setImageResource(R.drawable.fat_ma_a);
                        float scale = 1;
                        GameFragment.maria.setScaleX(scale);
                        GameFragment.maria.setScaleY(scale);
                        fatMaB = false;
                    }
                }
            }
            else{
                if(lastAmplitude>=GameFragment.BASE){
                    canvas.drawColor(Color.YELLOW);
                    GameFragment.maria.setImageResource(R.drawable.fat_ma_b);
                    fatMaB = true;
                    chance = 10;
                }
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