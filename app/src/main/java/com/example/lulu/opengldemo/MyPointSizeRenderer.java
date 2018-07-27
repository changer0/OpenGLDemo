package com.example.lulu.opengldemo;

import android.opengl.GLU;

import com.example.lulu.opengldemo.utils.BufferUtil;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by zhanglulu on 2018/7/26.
 * for 点渲染器 绘制螺旋线
 */
public class MyPointSizeRenderer extends AbstractMyRenderer{

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
        GLU.gluLookAt(gl, 0f, 0f,5f, 0f, 0f, 0f, 0f, 1f, 0f);

        //旋转角度
        //angle: 旋转角度
        //x,y,z: 沿着哪个轴旋转(向量) 迎面轴正方向看去, 顺时针: 负值 逆时针: 正值
        gl.glRotatef(xrotate, 1, 0, 0);//绕x轴旋转
        gl.glRotatef(yrotate, 0, 1, 0);//绕y轴旋转

        //计算点坐标
        float r = 0.5f;//半径
        //  旋转3圈
        float x = 0f, y = 0f, z = 1f;
        float zstep = 0.01f;//z的步长
        float psize   = 1.0f;//点的大小
        float pstep = 0.5f;//点的步长
        //循环绘制
        for (float alpha = 0f; alpha < Math.PI * 6; alpha = (float) (alpha + Math.PI/32)) {
            x = (float) (r * Math.cos(alpha));
            y = (float) (r * Math.sin(alpha));
            z = z - zstep;
            //设置点的大小
            gl.glPointSize(psize = psize + pstep);
            //指定顶点指针
            gl.glVertexPointer(3, GL10.GL_FLOAT, 0, BufferUtil.arr2ByteBuffer(new float[]{x, y, z}));
            //画数组
            gl.glDrawArrays(GL10.GL_POINTS, 0, 1);
        }

    }
}
