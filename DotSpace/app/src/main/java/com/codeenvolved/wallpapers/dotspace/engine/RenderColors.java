package com.codeenvolved.wallpapers.dotspace.engine;

import android.graphics.Color;
import android.graphics.Paint;

public class RenderColors{



    private Paint backgroundPaint = new Paint();
    private Paint dotPaint = new Paint();
    private Paint dotEffectColor = new Paint();
    private RenderSettings settings;

    public RenderColors(RenderSettings settings){
        this.settings = settings;
    }

    public void updateFromSettings(){
        backgroundPaint.setColor(settings.getBackgroundColor());
        backgroundPaint.setAlpha(255 - (int)(settings.getMotionBlur() * 128));
        dotPaint.setColor(settings.getDotColor());
        dotEffectColor.setColor(settings.getDotEffectColor());
    }

    public Paint dotColor(){
        return dotPaint;
    }

    public Paint getBackgroundColor(){
        return backgroundPaint;
    }

}
