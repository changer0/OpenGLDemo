package com.example.lulu.opengldemo;

import android.opengl.EGLConfig;
import android.opengl.GLU;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by zhanglulu on 2018/8/12.
 * for 测试模板缓冲区
 */
public class MyStencilRenderer extends AbstractMyRenderer {

    List<Float> vertexList;
    float ratio = 0;
    float left = -ratio, top = 1f, width = 0.3f;
    boolean xadd = false;
    boolean yadd = false;

    public void onSurfaceCreated(GL10 gl, EGLConfig arg1) {
        gl.glClearColor(0f, 0f, 0f, 1.0f);//clearColor
        gl.glClearStencil(0);//模板清除值

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);//vertex array
        gl.glEnable(GL10.GL_DEPTH_TEST);//depth test
        gl.glEnable(GL10.GL_STENCIL_TEST);//启用模板测试
    }

    //设置视口
    public void onSurfaceChanged(GL10 gl, int w, int h) {
        gl.glViewport(0, 0, w, h);
        ratio = (float) w / h;
        left = -ratio;
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glFrustumf(-ratio, ratio, -1, 1, 3, 7);
    }

    public void onDrawFrame(GL10 gl) {
        //清除颜色,深度,模板缓冲区
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT
                | GL10.GL_DEPTH_BUFFER_BIT
                | GL10.GL_STENCIL_BUFFER_BIT);
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
        GLU.gluLookAt(gl, 0, 0, 5, 0, 0, 0, 0, 1, 0);

        /****************************** 绘制白色螺旋线 **********************************/
        gl.glPushMatrix();
        gl.glRotatef(xrotate, 1, 0, 0);//绕x旋转
        gl.glRotatef(yrotate, 0, 1, 0);//绕y旋转

        //单调着色
        gl.glShadeModel(GL10.GL_FLAT);

        vertexList = new ArrayList<Float>();
        float x = 0, y = .8f, z = 0, ystep = 0.005f, r = 0.7f;
        for (float angle = 0f; angle < (Math.PI * 2 * 3); angle += (Math.PI / 40)) {
            x = (float) (r * Math.cos(angle));
            z = -(float) (r * Math.sin(angle));
            y = y - ystep;
            vertexList.add(x);
            vertexList.add(y);
            vertexList.add(z);
        }
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertexList.size() * 4);
        vbb.order(ByteOrder.nativeOrder());
        FloatBuffer fvb = vbb.asFloatBuffer();
        for (Float f : vertexList) {
            fvb.put(f);
        }
        fvb.position(0);

        //绘制白线直线,设置模板函数,所有操作都不能通过测试,但是对模板缓冲区的值进行增加
        gl.glStencilFunc(GL10.GL_NEVER, 1, 0);
        gl.glStencilOp(GL10.GL_INCR, GL10.GL_INCR, GL10.GL_INCR);

        //绘制白色螺旋线
        gl.glColor4f(1f, 1f, 1f, 1f);//白线
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, fvb);
        gl.glDrawArrays(GL10.GL_LINE_STRIP, 0, vertexList.size() / 3);
        gl.glPopMatrix();

        /****************************** 绘制红色方块 **********************************/
        if (xadd) {
            left = left + 0.01f;
        } else {
            left = left - 0.01f;
        }
        if (left <= (-ratio)) {
            xadd = true;
        }
        if (left >= (ratio - width)) {
            xadd = false;
        }

        if (yadd) {
            top = top + 0.01f;
        } else {
            top = top - 0.01f;
        }
        if (top >= 1) {
            yadd = false;
        }
        if (top <= (-1 + width)) {
            yadd = true;
        }

        float[] rectVertex = {
                left, top - width, 2f,
                left, top, 2f,
                left + width, top - width, 2f,
                left + width, top, 2f
        };
        vbb = ByteBuffer.allocateDirect(3 * 4 * 4);
        vbb.order(ByteOrder.nativeOrder());
        FloatBuffer rectfb = vbb.asFloatBuffer();
        rectfb.put(rectVertex);
        rectfb.position(0);


        //设置模板函数,所有操作都不能通过测试,但是对模板缓冲区的值进行增加
        gl.glStencilFunc(GL10.GL_EQUAL, 1, 1);
        gl.glStencilOp(GL10.GL_KEEP, GL10.GL_KEEP, GL10.GL_KEEP);

        gl.glColor4f(1f, 0f, 0f, 1f);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, rectfb);
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
    }


}
