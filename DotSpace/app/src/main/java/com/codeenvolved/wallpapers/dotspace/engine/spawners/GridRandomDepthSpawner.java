package com.codeenvolved.wallpapers.dotspace.engine.spawners;

import com.codeenvolved.wallpapers.dotspace.engine.Dot;
import com.codeenvolved.wallpapers.dotspace.engine.IDotSpawner;
import com.codeenvolved.wallpapers.dotspace.engine.LineConnection;
import com.codeenvolved.wallpapers.dotspace.engine.RenderSettings;
import com.codeenvolved.wallpapers.dotspace.engine.Vec2;

import java.util.ArrayList;
import java.util.Random;

public class GridRandomDepthSpawner implements IDotSpawner {

    private Random random = new Random();

    @Override
    public void spawn(ArrayList<Dot> dotDestination, ArrayList<LineConnection> lineDestination, RenderSettings settings, boolean isPreview, int width, int height) {
        dotDestination.clear();
        lineDestination.clear();
        if(width < 140 || height < 140){
            return;
        }
        int ndx = width / 140;
        int ndy = height / 140;
        int addX = width / ndx;
        int addY = width / ndx;
        int offsetX = addX / 2;
        int offsetY = addY / 2;


        Dot prevDot = null;
        ArrayList<Dot> prevRow = new ArrayList<>();
        for(int y=0; y< ndy;y++) {
            ArrayList<Dot> newRow = new ArrayList<>();
            for(int x=0; x< ndx;x++){

                Dot newDot = new Dot(new Vec2(offsetX + x * addX, offsetY + y * addY),
                        random.nextFloat() * ((float) Math.PI * 2f),
                        1f + random.nextFloat() * 2f,
                        settings);
                dotDestination.add(newDot);
                newRow.add(newDot);
                if(prevDot != null){
                    lineDestination.add(new LineConnection(prevDot, newDot, settings));
                }
                if(prevRow.size() > x){
                    lineDestination.add(new LineConnection(prevRow.get(x), newDot, settings));
                }

                prevDot = newDot;
            }
            prevRow = newRow;
            prevDot = null;
        }

    }
}
