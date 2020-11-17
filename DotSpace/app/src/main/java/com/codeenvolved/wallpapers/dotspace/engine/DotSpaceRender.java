package com.codeenvolved.wallpapers.dotspace.engine;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;

import com.codeenvolved.wallpapers.dotspace.engine.spawners.GridRandomDepthSpawner;
import com.codeenvolved.wallpapers.dotspace.engine.spawners.GridSpawner;
import com.codeenvolved.wallpapers.dotspace.engine.spawners.RandomSpawner;
import com.codeenvolved.wallpapers.dotspace.engine.spawners.RectangeSpawner;
import com.codeenvolved.wallpapers.dotspace.engine.spawners.TriquetraSpawner;

import java.util.ArrayList;
import java.util.Random;

public class DotSpaceRender implements RenderSettings.OnSettingsChangeHandler {

    private RenderColors colors;
    private float time = 0f;

    private int numDots = 0;
    private int spawnMode = -1;
    private ArrayList<Dot> dots = new ArrayList<>();
    private ArrayList<LineConnection> lines = new ArrayList<>();
    private float lastTouchTimer = 0f;
    private Vec2 lastTouchPos = new Vec2();

    private Vec2 parallaxDirection = new Vec2();
    private boolean isPreview;

    private IDotSpawner spawner;


    private int width=0, height=0;

    private RenderSettings settings;

    public DotSpaceRender(Context context, boolean isPreview){
        this.isPreview = isPreview;
        settings = new RenderSettings(context);

        colors = new RenderColors(settings);
        loadFromSettings();
        settings.registerChangeCallback(this);
        //blockPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    public void resizeField(int width, int height){
        this.width = width;
        this.height = height;
        respawnDots(width, height);
    }

    public void touch(float x, float y){
        lastTouchPos.x = x;
        lastTouchPos.y = y;
        lastTouchTimer = 0f;
    }

    public void resetMotion(){
        parallaxDirection.x = 0f;
        parallaxDirection.y = 0f;
        respawnDots(width, height);
    }

    public void motionEvent(float x, float y, float z){
        parallaxDirection.x += y;
        parallaxDirection.y += x;
    }

    public void updateAndRender(Canvas canvas, int width, int height){
        parallaxDirection.x -= parallaxDirection.x / 20f;
        parallaxDirection.y -= parallaxDirection.y / 20f;
        time += 0.015f;
        if(time > Math.PI * 2){
            time = 0f;
        }
        canvas.drawRect(new RectF(0,0,width, height), colors.getBackgroundColor());
        for(Dot d : dots){
            d.updatePos(time, lastTouchPos, lastTouchTimer, parallaxDirection);
        }
        if(lastTouchTimer < 4000){
            lastTouchTimer += 50;
        }
        for(LineConnection l : lines){
            l.draw(canvas);
        }
        for(Dot d : dots){
            d.draw(canvas);
        }

    }

    private void respawnDots(int width, int height){

        spawner.spawn(dots, lines, settings,isPreview, width, height);

    }




    private void loadFromSettings(){
        //spawner = new RandomSpawner();
        colors.updateFromSettings();
        if(numDots != settings.getNumDots() || spawnMode != settings.getSpawnMode()){
            numDots = settings.getNumDots();
            spawnMode = settings.getSpawnMode();
            switch (spawnMode){
                case 0:
                    spawner = new RandomSpawner();
                    break;
                case 1:
                    spawner = new GridSpawner();
                    break;
                case 2:
                    spawner = new GridRandomDepthSpawner();
                    break;
                case 3:
                    spawner = new RectangeSpawner();
                    break;
                case 4:
                    spawner = new TriquetraSpawner();
                    break;
                default:
                    spawner = new RandomSpawner();
                    break;
            }
            respawnDots(width, height);
        }else{
            for(Dot d : dots){
                d.loadFromSettings();
            }
            for(LineConnection lc: lines){
                lc.updateFromSettings();
            }
        }
    }

    @Override
    public void onSettingsChange() {
        loadFromSettings();
    }
}
