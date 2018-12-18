package com.example.lulu.opengldemo;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private AbstractMyRenderer renderer;
    private MyGLSurfaceView myGLSurfaceView;
    private Button left, right, top, bottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myGLSurfaceView = new MyGLSurfaceView(this);
        //renderer: 渲染器
        renderer = new MyTextureRenderer(this);
//        renderer = new MyTriangleConeRenderer();
        myGLSurfaceView.setEGLConfigChooser(5, 6, 5, 0, 16, 8);
        myGLSurfaceView.setRenderer(renderer);
        //设置渲染模式:
        //GLSurfaceView.RENDERMODE_CONTINUOUSLY: 持续渲染(默认)
        //GLSurfaceView.RENDERMODE_WHEN_DIRTY: 脏渲染, 命令渲染
        myGLSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        ((ViewGroup) findViewById(R.id.gl_surface_view)).addView(myGLSurfaceView);

        left = ((Button) findViewById(R.id.left));
        right = ((Button) findViewById(R.id.right));
        top = ((Button) findViewById(R.id.top));
        bottom = ((Button) findViewById(R.id.bottom));

        left.setOnClickListener(this);
        right.setOnClickListener(this);
        top.setOnClickListener(this);
        bottom.setOnClickListener(this);
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
    public void onClick(View v) {
        float step = 5f;
        switch (v.getId()) {
            case R.id.left:
                renderer.yrotate = renderer.yrotate + step;//沿y轴向左旋转
                break;
            case R.id.right:
                renderer.yrotate = renderer.yrotate - step;//沿y轴向右旋转
                break;
            case R.id.top:
                renderer.xrotate = renderer.xrotate - step;//沿x轴向上旋转
                break;
            case R.id.bottom:
                renderer.xrotate = renderer.xrotate + step;//沿x轴向下旋转
                break;
        }
        //请求渲染, 与脏渲染配合使用
        myGLSurfaceView.requestRender();
    }
}
