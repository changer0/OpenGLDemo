package com.example.lulu.opengldemo;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.KeyEvent;


public class MainActivity extends AppCompatActivity {

    private AbstractMyRenderer renderer;
    private MyGLSurfaceView myGLSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myGLSurfaceView = new MyGLSurfaceView(this);
        //renderer: 渲染器
        renderer = new MyTriangleConeRenderer();
        myGLSurfaceView.setRenderer(renderer);
        //设置渲染模式:
        //GLSurfaceView.RENDERMODE_CONTINUOUSLY: 持续渲染(默认)
        //GLSurfaceView.RENDERMODE_WHEN_DIRTY: 脏渲染, 命令渲染
        myGLSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        setContentView(myGLSurfaceView);
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
            case KeyEvent.KEYCODE_BACK:
                renderer.xrotate  = renderer.xrotate - step;//沿x轴向上旋转
                break;
            case KeyEvent.KEYCODE_MENU:
                renderer.yrotate = renderer.yrotate + step;//沿y轴向左旋转
                break;
        }
        //请求渲染, 与脏渲染配合使用
        myGLSurfaceView.requestRender();
        return true;
    }
}
