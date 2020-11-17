package com.codeenvolved.wallpapers.dotspace.engine.spawners;

import com.codeenvolved.wallpapers.dotspace.engine.Dot;
import com.codeenvolved.wallpapers.dotspace.engine.LineConnection;
import com.codeenvolved.wallpapers.dotspace.engine.RenderSettings;

import java.util.ArrayList;

public class TriquetraSpawner extends ShapeSpawner {



    @Override
    public void spawn(ArrayList<Dot> dotDestination, ArrayList<LineConnection> lineDestination, RenderSettings settings, boolean isPreview, int width, int height) {
        dotDestination.clear();
        lineDestination.clear();
        fitWidth = false;
        int numTriangles = settings.getNumDots() / 3;

        for(int i = 0; i < numTriangles; i++){
            float   x= random.nextFloat() * 2f - 1f,
                    y = random.nextFloat()* 2f - 1f,
                    r = random.nextFloat() * ((float)Math.PI * 2f),
                    s= 0.15f,
                    d = random.nextFloat();

            ArrayList<DotDefenition> triangles = new ArrayList<>();
            for(int corner = 0; corner < 3; corner++){
                triangles.add(new DotDefenition(x + (float)Math.cos(r) * s,y + (float)Math.sin(r) * s, 1f + d * 2f));
                r += (Math.PI * 2f) / 3f;
            }
            this.createClosedPath(dotDestination, lineDestination, settings, width, height ,triangles);
        }
    }
}
