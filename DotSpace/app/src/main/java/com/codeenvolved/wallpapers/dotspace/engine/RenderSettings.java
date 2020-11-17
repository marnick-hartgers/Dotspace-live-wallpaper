package com.codeenvolved.wallpapers.dotspace.engine;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;

public class RenderSettings implements SharedPreferences.OnSharedPreferenceChangeListener {
    private SharedPreferences mPreferences;
    private boolean mSelfChange = false;
    private boolean handlerSet = false;
    private OnSettingsChangeHandler changeHandler;

    public RenderSettings(Context context){
        mPreferences = context.getSharedPreferences("DotSpaceSettings", Context.MODE_PRIVATE);

        if(!mPreferences.getBoolean("IsSet", false)){
            save();
        }else{
            load();
        }
        mPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    public void registerChangeCallback(OnSettingsChangeHandler handler){
        changeHandler = handler;
        handlerSet = true;
    }
    /*
    Parallax amount setting
     */
    private final String mParallaxAmountName = "ParallaxAmount";
    private final float mParallaxAmountDef = 1f;
    private float mParallaxAmount = mParallaxAmountDef;
    public float getParallaxAmount(){
        return mParallaxAmount;
    }
    public void setParallaxAmount(float parallaxAmount) {
        if(this.mParallaxAmount != parallaxAmount){
            this.mParallaxAmount = parallaxAmount;
            this.save();
        }


    }
    /*
    Parallax inverted setting
    */
    private final String mParallaxInvertDepthName = "ParallaxInvertDepth";
    private final boolean mParallaxInvertDepthDef = true;
    private boolean mParallaxInvertDepth = mParallaxInvertDepthDef;
    public boolean getParallaxInvertDepth() {
        return mParallaxInvertDepth;
    }
    public void setParallaxInvertDepth(boolean parallaxInvertDepth){
        if(this.mParallaxInvertDepth != parallaxInvertDepth) {
            this.mParallaxInvertDepth = parallaxInvertDepth;
            this.save();
        }
    }

    /*
    Num of dots
     */
    public final String mNumDotsName = "NumDots";
    public final int mNumDotsDef = 50;
    public  int mNumDots = mNumDotsDef;
    public int getNumDots(){
        return mNumDots;
    }
    public void setNumDots(int num){
        if(this.mNumDots != Math.min(num, 150)){
            this.mNumDots = Math.min(num, 150);
            this.save();
        }
    }
    /*
    Background color
     */
    private final String mBackgroundColorName = "BackgroundColor";
    private final int mBackgroundColorDef = Color.argb(255,30,30,30);
    private int mBackgroundColor = mBackgroundColorDef;
    public int getBackgroundColor(){
        return mBackgroundColor;
    }
    public void setBackgroundColor(int color){
        if(mBackgroundColor != color) {
            mBackgroundColor = color;
            save();
        }
    }

    /*
    Dot color
     */
    private final String mDotColorName = "DotColor";
    private final int mDotColorDef = Color.argb(255,0,153,153);
    private int mDotColor = mDotColorDef;
    public int getDotColor(){
        return mDotColor;
    }
    public void setDotColor(int color){
        if(mDotColor != color) {
            mDotColor = color;
            save();
        }
    }

    /*
    Dot effect color
     */
    private final String mDotEffectColorName = "DotEffectColor";
    private final int mDotEffectColorDef = Color.argb(255,200,50,200);
    private int mDotEffectColor = mDotEffectColorDef;
    public int getDotEffectColor(){
        return mDotEffectColor;
    }
    public void setDotEffectColor(int color){
        if(mDotEffectColor != color) {
            mDotEffectColor = color;
            save();
        }
    }

    /*
    Dot effect color
     */
    private final String mSpawnModeName = "SpawnMode";
    private final int mSpawnModeDef = 0;
    private int mSpawnMode = mSpawnModeDef;
    public int getSpawnMode(){
        return mSpawnMode;
    }
    public void setSpawnMode(int spawnMode){
        if(mSpawnMode != spawnMode) {
            mSpawnMode = spawnMode;
            save();
        }
    }

    /*
    Dot effect color
     */
    private final String mMotionBlurName = "MotionBlur";
    private final float mMotionBlurDef = 0;
    private float mMotionBlur = mMotionBlurDef;
    public float getMotionBlur(){
        return mMotionBlur;
    }
    public void setMotionBlur(float motionBlur){
        if(mMotionBlur != motionBlur) {
            mMotionBlur = motionBlur;
            save();
        }
    }

    //Save and load functions
    private void save(){
        SharedPreferences.Editor edit = mPreferences.edit();
        edit.putBoolean("IsSet", true);
        edit.putFloat(mParallaxAmountName, mParallaxAmount);
        edit.putBoolean(mParallaxInvertDepthName, mParallaxInvertDepth);
        edit.putInt(mNumDotsName, mNumDots);
        edit.putInt(mBackgroundColorName, mBackgroundColor);
        edit.putInt(mDotColorName, mDotColor);
        edit.putInt(mDotEffectColorName, mDotEffectColor);
        edit.putInt(mSpawnModeName, mSpawnMode);
        edit.putFloat(mMotionBlurName, mMotionBlur);
        mSelfChange = true;
        edit.commit();
    }

    private void load(){
        mParallaxAmount = mPreferences.getFloat(mParallaxAmountName, mParallaxAmountDef);
        mParallaxInvertDepth = mPreferences.getBoolean(mParallaxInvertDepthName, mParallaxInvertDepthDef);
        mNumDots = Math.min(150, mPreferences.getInt(mNumDotsName, mNumDotsDef));
        mBackgroundColor = mPreferences.getInt(mBackgroundColorName, mBackgroundColorDef);
        mDotColor = mPreferences.getInt(mDotColorName, mDotColorDef);
        mDotEffectColor = mPreferences.getInt(mDotEffectColorName, mDotEffectColorDef);
        mSpawnMode = mPreferences.getInt(mSpawnModeName, mSpawnModeDef);
        mMotionBlur = mPreferences.getFloat(mMotionBlurName, mMotionBlurDef);
        if(handlerSet){
            changeHandler.onSettingsChange();
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if(!mSelfChange){
            load();
        }else{
            mSelfChange = false;
        }
    }

    interface OnSettingsChangeHandler{
        void onSettingsChange();
    }
}
