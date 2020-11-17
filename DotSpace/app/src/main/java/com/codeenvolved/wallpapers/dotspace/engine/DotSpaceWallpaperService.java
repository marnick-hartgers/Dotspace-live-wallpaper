package com.codeenvolved.wallpapers.dotspace.engine;

import android.content.Context;
import android.graphics.Canvas;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.service.wallpaper.WallpaperService;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

public class DotSpaceWallpaperService extends WallpaperService {
    @Override
    public Engine onCreateEngine() {

        return new DotSpaceEngine(this);
    }

    public class DotSpaceEngine extends DotSpaceWallpaperService.Engine implements SensorEventListener{

        private SensorManager mSensorManager;
        private DotSpaceRender render;
        private boolean visible = false;
        private int width =0, height = 0;
        private final Handler handler = new Handler();
        private final Runnable drawRunner = new Runnable() {
            @Override
            public void run() {
                draw();
            }

        };
        private boolean mSensorRunning = false;


        public DotSpaceEngine(Context context){
            render = new DotSpaceRender(context, false);
            handler.post(drawRunner);
            mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            super.onVisibilityChanged(visible);
            this.visible = visible;
            if (visible) {
                handler.post(drawRunner);
                activateSensors();
            } else {
                handler.removeCallbacks(drawRunner);
                deactivateSensors();
            }
        }

        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);
        }

        @Override
        public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            visible = true;
            activateSensors();
            this.width = width;
            this.height = height;
            this.render.resizeField(width, height);
            super.onSurfaceChanged(holder, format, width, height);

        }

        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder) {
            this.visible = false;
            handler.removeCallbacks(drawRunner);
            deactivateSensors();
            super.onSurfaceDestroyed(holder);
        }

        @Override
        public void onTouchEvent(MotionEvent event) {
            render.touch(event.getX(), event.getY());
            super.onTouchEvent(event);
        }

        private void draw(){
            SurfaceHolder holder = getSurfaceHolder();
            Canvas canvas = null;
            try {
                canvas = holder.lockCanvas();
                if (canvas != null) {

                    render.updateAndRender(canvas, width, height);
                }
            } finally {
                if (canvas != null)
                    holder.unlockCanvasAndPost(canvas);
            }
            handler.removeCallbacks(drawRunner);
            if (visible) {
                handler.postDelayed(drawRunner, 1000/60);
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

        @Override
        public void onSensorChanged(SensorEvent event) {
            render.motionEvent(event.values[0],event.values[1],event.values[2]);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    }
}
