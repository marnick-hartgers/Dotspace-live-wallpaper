package com.codeenvolved.wallpapers.dotspace.engine;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class LineConnection {
    private Dot firstDot, secondDot;
    private float strokeWidth = 6;
    private Paint linePaint = new Paint();
    private RenderSettings settings;


    public LineConnection(Dot firstDot, Dot secondDot, RenderSettings settings){
        this. firstDot =firstDot;
        this.secondDot = secondDot;
        this.settings = settings;

        linePaint.setStrokeWidth(strokeWidth);
    }

    public boolean isConnected(Dot d){
        return firstDot == d || secondDot == d;
    }

    public boolean areConnected(Dot a, Dot b){
        return (firstDot == a && secondDot == b )|| (firstDot == b && secondDot == a );
    }

    public void updateFromSettings(){

    }

    public void draw(Canvas canvas){
        linePaint.setColor(firstDot.getDotPaint().getColor());
        linePaint.setAlpha(150);
        canvas.drawLine(firstDot.getPosition().getX(),firstDot.getPosition().getY(),secondDot.getPosition().getX(),secondDot.getPosition().getY(), linePaint);
    }
}
