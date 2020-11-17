package com.codeenvolved.wallpapers.dotspace.engine;

import java.util.ArrayList;

public interface IDotSpawner {
    void spawn(ArrayList<Dot> dotDestination, ArrayList<LineConnection> lineDestination, RenderSettings settings, boolean isPreview, int width, int height);
}
