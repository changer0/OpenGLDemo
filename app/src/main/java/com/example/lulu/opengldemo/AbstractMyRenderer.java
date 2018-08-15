package com.example.lulu.opengldemo;

import android.opengl.GLSurfaceView;
import android.opengl.GLU;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by zhanglulu on 2018/7/26.
 * for 点渲染器 绘制螺旋线
 */
public abstract class AbstractMyRenderer implements GLSurfaceView.Renderer{

    protected float ratio;

    public float xrotate = 0f; //围绕x轴旋转角度
    public float yrotate = 0f;  //围绕y轴旋转角度

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        //设置清屏色
        gl.glClearColor(0f, 0f, 0f, 1f);
        //启用顶点缓冲区
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        //启动颜色缓冲区
        //gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        //设置视口
        gl.glViewport(0, 0, width, height);

        //设置矩阵模式为投影矩阵, 为设置平截头体做准备
        gl.glMatrixMode(GL10.GL_PROJECTION);
        //加载单位矩阵
        gl.glLoadIdentity();
        ratio = ((float) width) / ((float) height);
        //设置平截头体
        gl.glFrustumf( -ratio, ratio, -1f, 1f, 3, 7);
    }

    @Override
    public abstract void onDrawFrame(GL10 gl);
}
