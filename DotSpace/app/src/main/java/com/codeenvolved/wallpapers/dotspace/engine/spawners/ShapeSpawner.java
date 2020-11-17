package com.codeenvolved.wallpapers.dotspace.engine.spawners;

import com.codeenvolved.wallpapers.dotspace.engine.Dot;
import com.codeenvolved.wallpapers.dotspace.engine.IDotSpawner;
import com.codeenvolved.wallpapers.dotspace.engine.LineConnection;
import com.codeenvolved.wallpapers.dotspace.engine.RenderSettings;
import com.codeenvolved.wallpapers.dotspace.engine.Vec2;

import java.util.ArrayList;
import java.util.Random;

public abstract class ShapeSpawner implements IDotSpawner {
    protected Random random = new Random();
    protected boolean fitWidth = true;

    class DotDefenition {
        public DotDefenition(float x, float y, float depth){
            this.x = x;
            this.y = y;
            this.depth = depth;
        }
        public float x, y, depth;
    }

    Vec2 scaleToPos(int width, int height, float x, float y, float depth){
        float scale;
        if(fitWidth){
            scale = (float)Math.min(width, height) / 2f;
        }else{
            scale = (float)Math.max(width, height) / 2f;
        }
        int xOffset = width / 2;
        int yOffset = height / 2;
        return new Vec2(xOffset + (scale * x), yOffset + (scale * y));
    }

    void createPath(ArrayList<Dot> dotDestination, ArrayList<LineConnection> lineDestination, RenderSettings settings, int width, int height, ArrayList<DotDefenition> path){
        Dot prevDot = null;
        float randomOffset = random.nextFloat() * ((float) Math.PI * 2f);
        for(int i = 0; i < path.size(); i++){
            Dot newDot = new Dot(scaleToPos(width, height, path.get(i).x, path.get(i).y, path.get(i).depth),
                    randomOffset,
                    path.get(i).depth,
                    settings);
            dotDestination.add(newDot);

            if(prevDot != null){
                lineDestination.add(new LineConnection(prevDot, newDot, settings));
            }
            prevDot = newDot;
        }

    }

    void createClosedPath(ArrayList<Dot> dotDestination, ArrayList<LineConnection> lineDestination, RenderSettings settings, int width, int height, ArrayList<DotDefenition> path){
        Dot prevDot = null;
        Dot firstDot = null;
        float randomOffset = random.nextFloat() * ((float) Math.PI * 2f);
        for(int i = 0; i < path.size(); i++){
            Dot newDot = new Dot(scaleToPos(width, height, path.get(i).x, path.get(i).y, path.get(i).depth),
                    randomOffset,
                    path.get(i).depth,
                    settings);
            dotDestination.add(newDot);

            if(i==0){
                firstDot = newDot;
            }
            if(prevDot != null){
                lineDestination.add(new LineConnection(prevDot, newDot, settings));
            }
            if(i == path.size() - 1){
                lineDestination.add(new LineConnection(firstDot, newDot, settings));
            }
            prevDot = newDot;
        }

    }

}
