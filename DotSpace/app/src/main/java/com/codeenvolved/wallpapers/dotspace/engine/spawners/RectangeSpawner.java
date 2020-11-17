package com.codeenvolved.wallpapers.dotspace.engine.spawners;

import com.codeenvolved.wallpapers.dotspace.engine.Dot;
import com.codeenvolved.wallpapers.dotspace.engine.LineConnection;
import com.codeenvolved.wallpapers.dotspace.engine.RenderSettings;

import java.util.ArrayList;

public class RectangeSpawner extends ShapeSpawner {



    @Override
    public void spawn(ArrayList<Dot> dotDestination, ArrayList<LineConnection> lineDestination, RenderSettings settings, boolean isPreview, int width, int height) {
        dotDestination.clear();
        lineDestination.clear();
        int numRects = settings.getNumDots() / 4;
        fitWidth = false;
        for(int i =0; i < numRects; i++){
            float   x= random.nextFloat() * 2f - 1f,
                    y = random.nextFloat()* 2f - 1f,
                    r = random.nextFloat() * ((float)Math.PI * 2f),
                    s= 0.15f,
                    d = random.nextFloat();

            ArrayList<DotDefenition> rects = new ArrayList<>();
            for(int corner = 0; corner < 4; corner++){
                rects.add(new DotDefenition(x + (float)Math.cos(r) * s,y + (float)Math.sin(r) * s, 1f + d * 2f));
                r += Math.PI * 0.5;
            }
            this.createClosedPath(dotDestination, lineDestination, settings, width, height ,rects);
        }
    }


}
