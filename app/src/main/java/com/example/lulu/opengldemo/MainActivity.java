package com.example.lulu.opengldemo;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.KeyEvent;


import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


public class MainActivity extends AppCompatActivity {

    private MyPointRenderer renderer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyGLSurfaceView view = new MyGLSurfaceView(this);
        //renderer: 渲染器
        //view.setRenderer(new MyTriangleRenderer());
        renderer = new MyPointRenderer();
        view.setRenderer(renderer);
        setContentView(view);
    }
    class MyGLSurfaceView extends GLSurfaceView {

        public MyGLSurfaceView(Context context) {
            super(context);
        }

        public MyGLSurfaceView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        float step = 5f;
        switch (keyCode) {
            case KeyEvent.KEYCODE_MENU:
                renderer.xrotate  = renderer.xrotate - step;//沿x轴向上旋转
                return true;
            case KeyEvent.KEYCODE_BACK:
                renderer.yrotate = renderer.yrotate + step;//沿y轴向左旋转
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
