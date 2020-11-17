package com.codeenvolved.wallpapers.dotspace.engine;

public class Vec2 implements IReadOnlyVec2{
    public float x=0f, y=0f;

    public Vec2(){}

    public Vec2(float x, float y){
        this.x=x;
        this.y=y;
    }


    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }
}
