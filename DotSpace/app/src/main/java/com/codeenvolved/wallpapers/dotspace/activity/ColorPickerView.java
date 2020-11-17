package com.codeenvolved.wallpapers.dotspace.activity;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import com.codeenvolved.wallpapers.dotspace.R;

/**
 * TODO: document your custom view class.
 */
public class ColorPickerView extends LinearLayout {

    private OnColorPickChangedListner listner = null;

    public ColorPickerView(Context context) {
        super(context);
        View v = inflate(context, R.layout.sample_color_picker_view, this);//(R.layout.sample_color_picker_view);
        init(v);
    }

    public ColorPickerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View v = inflate(context, R.layout.sample_color_picker_view, this);
        init(v);
    }
    public ColorPickerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View v = inflate(context, R.layout.sample_color_picker_view, this);
        init(v);
    }
    public ColorPickerView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        View v = inflate(context, R.layout.sample_color_picker_view, this);
        init(v);
    }

    private void init(View v){
        redSeekbar = v.findViewById(R.id.seekBarRed);
        greenSeekbar = v.findViewById(R.id.seekBarGreen);
        blueSeekbar = v.findViewById(R.id.seekBarBlue);

        redSeekbar.setId(View.generateViewId());
        greenSeekbar.setId(View.generateViewId());
        blueSeekbar.setId(View.generateViewId());

        redSeekbar.setOnSeekBarChangeListener(redSeekbarChangesListner);
        greenSeekbar.setOnSeekBarChangeListener(greenSeekbarChangesListner);
        blueSeekbar.setOnSeekBarChangeListener(blueSeekbarChangesListner);
    }

    private SeekBar redSeekbar, greenSeekbar, blueSeekbar;


    public void registerOnColorPickChangeListner(OnColorPickChangedListner l){
        listner = l;
    }

    public int getColorPick(){
        return Color.rgb(redSeekbar.getProgress(),greenSeekbar.getProgress(),blueSeekbar.getProgress());
    }

    public void setColorPick(int color){
        Log.d("ColorPicker", "Setting color: " + color);
        int r = Color.red(color);
        redSeekbar.setProgress(r);
        int g = Color.green(color);
        greenSeekbar.setProgress(g);
        int b = Color.blue(color);
        blueSeekbar.setProgress(b);
    }

    private SeekBar.OnSeekBarChangeListener redSeekbarChangesListner = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if(listner != null && fromUser){
                listner.onColorPickChanged();
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {}

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {}
    };
    private SeekBar.OnSeekBarChangeListener greenSeekbarChangesListner = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if(listner != null&& fromUser){
                listner.onColorPickChanged();
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {}

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {}
    };
    private SeekBar.OnSeekBarChangeListener blueSeekbarChangesListner = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if(listner != null&& fromUser){
                listner.onColorPickChanged();
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {}

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {}
    };

    public interface OnColorPickChangedListner{
        void onColorPickChanged();
    }
}
