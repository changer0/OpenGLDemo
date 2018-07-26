package com.example.lulu.opengldemo;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;


import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyGLSurfaceView view = new MyGLSurfaceView(this);
        //renderer: 渲染器
        //view.setRenderer(new MyTriangleRenderer());
        view.setRenderer(new MyPointRenderer());
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

}
