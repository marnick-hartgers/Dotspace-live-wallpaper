package com.codeenvolved.wallpapers.dotspace.activity;

import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TableLayout;

import com.codeenvolved.wallpapers.dotspace.R;
import com.codeenvolved.wallpapers.dotspace.engine.DotSpaceRender;
import com.codeenvolved.wallpapers.dotspace.engine.DotSpaceWallpaperService;
import com.codeenvolved.wallpapers.dotspace.engine.RenderSettings;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    //Fields for the little preview
    private SensorManager mSensorManager;
    private Handler handler = new Handler();
    private boolean running = false;
    private boolean mSensorRunning = false;
    private DotSpaceRender render;
    private final Runnable drawRunnable = new Runnable() {
        @Override
        public void run() {
            drawPreview();
        }
    };
    private SurfaceView surfaceView;
    private RenderSettings settings;
    private boolean previewSizeSet = false;

    private ViewPager settingsPager;
    private TabLayout settingTabs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        render = new DotSpaceRender(this, true);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        surfaceView = (SurfaceView) findViewById( R.id.previewSurfaceView);
        settings = new RenderSettings(this);
        settingsPager = findViewById(R.id.settings_pager);
        settingsPager.setAdapter(new SettingsFragmentAdapter(getSupportFragmentManager(), this));
        settingTabs = findViewById(R.id.settings_tabs);
        settingTabs.setupWithViewPager(settingsPager);



    }

    @Override
    protected void onResume() {
        super.onResume();
        //render.resizeField(surfaceView.getWidth(), surfaceView.getHeight());
        startHandler();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopHandler();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopHandler();
    }

    private void stopHandler(){
        if(running){
            running = false;
            handler.removeCallbacks(drawRunnable);
            deactivateSensors();
        }
    }

    private void startHandler(){
        if(!running){
            running = true;
            handler.post(drawRunnable);
            render.resetMotion();
            render.resizeField(surfaceView.getWidth(), surfaceView.getHeight());
            activateSensors();
        }
    }

    private void activateSensors(){
        if(!mSensorRunning){
            render.resetMotion();
            Sensor s = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
            mSensorManager.registerListener(this, s,SensorManager.SENSOR_DELAY_FASTEST);
            mSensorRunning = true;
        }
    }

    private void deactivateSensors(){
        if(mSensorRunning){
            mSensorManager.unregisterListener(this);
            mSensorRunning = false;
        }
    }

    private void drawPreview(){
        if(!previewSizeSet && surfaceView.getWidth() > 0){
            previewSizeSet = true;
            render.resizeField(surfaceView.getWidth(), surfaceView.getHeight());
        }
        SurfaceHolder holder = surfaceView.getHolder();
        Canvas canvas = null;
        try {
            canvas = holder.lockCanvas();
            if (canvas != null) {
                render.updateAndRender(canvas, surfaceView.getWidth(), surfaceView.getHeight());
            }
        } finally {
            if (canvas != null)
                holder.unlockCanvasAndPost(canvas);
        }

        handler.removeCallbacks(drawRunnable);
        if (running) {
            handler.postDelayed(drawRunnable, 1000/60);
        }
    }

    //Setting elements







    @Override
    public void onSensorChanged(SensorEvent event) {
        render.motionEvent(event.values[0],event.values[1],event.values[2]);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void setWallpaperButtonClick(View v){
        Intent intent = new Intent(
                WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);
        intent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,
                new ComponentName(this, DotSpaceWallpaperService.class));
        startActivity(intent);
    }

    public void simulateTouchEvent(View v){
        render.touch(0,0);
    }

}
