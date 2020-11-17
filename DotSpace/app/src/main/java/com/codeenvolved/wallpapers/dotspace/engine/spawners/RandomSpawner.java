package com.codeenvolved.wallpapers.dotspace.engine.spawners;

import com.codeenvolved.wallpapers.dotspace.engine.Dot;
import com.codeenvolved.wallpapers.dotspace.engine.IDotSpawner;
import com.codeenvolved.wallpapers.dotspace.engine.LineConnection;
import com.codeenvolved.wallpapers.dotspace.engine.RenderSettings;
import com.codeenvolved.wallpapers.dotspace.engine.Vec2;

import java.util.ArrayList;
import java.util.Random;

public class RandomSpawner implements IDotSpawner {

    private Random random = new Random();

    @Override
    public void spawn(ArrayList<Dot> dotDestination, ArrayList<LineConnection> lineDestination, RenderSettings settings, boolean isPreview, int width, int height) {

        dotDestination.clear();
        int nd = isPreview ? settings.getNumDots() / 3 : settings.getNumDots();
        for(int i=0; i< nd;i++){
            dotDestination.add(new Dot(new Vec2(random.nextFloat() * (float)width,random.nextFloat() * (float)height),
                    random.nextFloat() * ((float)Math.PI * 2f),
                    1f + random.nextFloat() * 2f,
                    settings));
        }

        lineDestination.clear();
        for(Dot d1 : dotDestination){
            for(Dot d2 : dotDestination) {
                if(d1 != d2 && d1.distance(d2) < 200 && !areDotsConencted(d1,d2, lineDestination)){
                    lineDestination.add(new LineConnection(d1,d2, settings));
                }
            }
        }
    }

    private boolean areDotsConencted(Dot a, Dot b, ArrayList<LineConnection> lines){
        for(LineConnection l : lines){
            if(l.areConnected(a, b)){
                return true;
            }
        }
        return false;
    }
}
