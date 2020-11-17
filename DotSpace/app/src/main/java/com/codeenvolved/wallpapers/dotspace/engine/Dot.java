package com.codeenvolved.wallpapers.dotspace.engine;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Dot{

    private Vec2 pos;
    private Vec2 circlePoint;
    private Paint dotPaint;
    private float orgSize = 15;
    private float size;
    private float randomOffset;
    private float depth;
    private float orgDepth;
    private boolean depthInverted = false;
    float effectAmount = 0;
    private RenderSettings settings;

    float colorR = 0f,colorG = 0f, colorB = 0f, colorREffect = 0f,colorGEffect = 0f, colorBEffect = 0f;

    public Dot(IReadOnlyVec2 p, float randomOffset, float depth, RenderSettings settings){
        this.settings = settings;
        dotPaint = new Paint();
        circlePoint = new Vec2(p.getX(), p.getY());
        pos = new Vec2();

        this.randomOffset = randomOffset;

        this.orgDepth = depth;
        this.depth= depth;
        size = orgSize;

        loadFromSettings();
    }

    public void updatePos(float time, Vec2 lastTouchPos, float lastTouchTime, Vec2 parallax){
        pos.x = circlePoint.x +
                (float)Math.cos(time + randomOffset) * 50;
        pos.y = circlePoint.y +
                (float)Math.sin(time + randomOffset) * 50;
        if(depthInverted){
            pos.x += parallax.x * depth;
            pos.y += parallax.y * depth;
        }else{
            pos.x -= parallax.x * depth;
            pos.y -= parallax.y * depth;
        }
        effectAmount = 1f - Math.min(1, Math.abs(distance(lastTouchPos) - lastTouchTime) / 500 );

    }
    public Paint getDotPaint(){
        return dotPaint;
    }

    public IReadOnlyVec2 getCirclePoint(){
        return circlePoint;
    }

    public IReadOnlyVec2 getPosition(){
        return pos;
    }

    public void draw(Canvas canvas){
        dotPaint.setColor(Color.rgb(
                (int)fade(colorR,colorREffect , effectAmount),
                (int)fade(colorG,colorGEffect, effectAmount),
                (int)fade(colorB, colorBEffect , effectAmount)));

        size = orgSize * (1f + effectAmount - (depth) / 8f);
        canvas.drawCircle(pos.x, pos.y, size, dotPaint);
    }

    public float distance(Dot d){
        return (float)Math.sqrt(Math.pow(circlePoint.x - d.getCirclePoint().getX(), 2)+ Math.pow(circlePoint.y - d.getCirclePoint().getY(), 2));
    }

    public float distance(IReadOnlyVec2 d){
        return (float)Math.sqrt(Math.pow(circlePoint.x - d.getX(), 2)+ Math.pow(circlePoint.y - d.getY(), 2));
    }

    public void loadFromSettings(){
        depthInverted = settings.getParallaxInvertDepth();
        depth = (this.orgDepth) * settings.getParallaxAmount();
        colorR = (float)Color.red(settings.getDotColor());
        colorG = (float)Color.green(settings.getDotColor());
        colorB = (float)Color.blue(settings.getDotColor());

        colorREffect = (float)Color.red(settings.getDotEffectColor());
        colorGEffect = (float)Color.green(settings.getDotEffectColor());
        colorBEffect = (float)Color.blue(settings.getDotEffectColor());
    }

    private float fade(float a,float b, float p){
        return a * (1 - p ) + b * p;
    }

}
