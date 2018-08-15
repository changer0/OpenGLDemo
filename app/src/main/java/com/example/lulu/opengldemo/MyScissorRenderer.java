package com.example.lulu.opengldemo;

import android.opengl.GLU;

import com.example.lulu.opengldemo.utils.BufferUtil;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by zhanglulu on 2018/7/26.
 * for 剪裁区
 */
public class MyScissorRenderer extends AbstractMyRenderer{

    private int width;
    private int height;
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
        this.width = width;
        this.height = height;
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
    public void onDrawFrame(GL10 gl) {
        //清除颜色缓冲区
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        //设置绘图颜色
        gl.glColor4f(1f, 0f, 0f, 1f);

        //操作模型视图
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
        //设置眼球的位置
        GLU.gluLookAt(gl, 0f, 0f, 5f,
                0f, 0f, 0f, 0f, 1f, 0f);
        //启用剪裁
        gl.glEnable(GL10.GL_SCISSOR_TEST);
        //旋转角度
        //angle: 旋转角度
        //x,y,z: 沿着哪个轴旋转(向量) 迎面轴正方向看去, 顺时针: 负值 逆时针: 正值
        gl.glRotatef(xrotate, 1, 0, 0);//绕x轴旋转
        gl.glRotatef(yrotate, 0, 1, 0);//绕y轴旋转

        //指定4个点
        float[] coords = {
                -ratio, 1f, 2f,//a
                -ratio, -1f, 2f,//b
                ratio, 1f, 2f,//d
                ratio, -1f, 2f//c
        };

        float[][] colors = {
                //r g b a
                {1f, 0f, 0f, 1f},//红色
                {0f, 1f, 0f, 1f},//绿色
                {0f, 0f, 1f, 1f},//蓝色
                {1f, 0f, 1f, 1f}

        };
        int step = 50;
        for (int i = 0; i < colors.length; i++) {
            //剪裁
            gl.glScissor(i*20, i*20, width-(i*20*2),height-(i*20*2));
            //设置颜色
            gl.glColor4f(colors[i][0], colors[i][1], colors[i][2], colors[i][3]);
            //指定顶点缓冲区
            gl.glVertexPointer(3, GL10.GL_FLOAT, 0, BufferUtil.arr2ByteBuffer(coords));
            //画图形
            gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
        }

    }
}
