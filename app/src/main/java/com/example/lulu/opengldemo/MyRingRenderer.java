package com.example.lulu.opengldemo;

import android.opengl.GLU;

import com.example.lulu.opengldemo.utils.BufferUtil;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by zhanglulu on 2018/10/13.
 * for 画圆环
 */
public class MyRingRenderer extends AbstractMyRenderer {
    @Override
    public void onDrawFrame(GL10 gl) {
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);//清除颜色缓冲区
        gl.glColor4f(0f, 0f, 1f, 1f);//设置颜色


        //操作模型视图
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
        //设置眼球的位置
        GLU.gluLookAt(gl, 0f, 0f, 5f,
                0f, 0f, 0f, 0f, 1f, 0f);

        //旋转
        gl.glRotatef(xrotate, 1, 0, 0);
        gl.glRotatef(yrotate, 0, 1, 0);

        //开始画
        float Rinner = 0.2f; //内环半径
        float Rring = 0.3f; //环半径

        int count = 20; //环 圆与圆之间的循环次数
        float alphaStep = (float) ((2 * Math.PI) / count);
        float alpha;

        int countRing = 20;//环上圆循环次数
        float betaStep = (float) ((2 * Math.PI) / countRing);
        float beta;

        float x0, y0, z0, x1, y1, z1;

        List<Float> coordsList = new ArrayList<>();

        //外层 圆与圆之间
        for (int i = 0; i < count; i++) {
            alpha = alphaStep * i;

            //环上圆的点 (需要闭合)
            for (int j = 0; j <= countRing; j++) {
                beta = betaStep * j;

                x0 = (float) ((Rinner + Rring * (1 + Math.cos(beta))) * Math.cos(alpha));
                y0 = (float) ((Rinner + Rring * (1 + Math.cos(beta))) * Math.sin(alpha));
                z0 = -(float) (Rring * Math.sin(beta));

                x1 = (float) ((Rinner + Rring * (1 + Math.cos(beta))) * Math.cos(alpha + alphaStep));
                y1 = (float) ((Rinner + Rring * (1 + Math.cos(beta))) * Math.sin(alpha + alphaStep));
                z1 = -(float) (Rring * Math.sin(beta));

                coordsList.add(x0);
                coordsList.add(y0);
                coordsList.add(z0);
                coordsList.add(x1);
                coordsList.add(y1);
                coordsList.add(z1);
            }
        }

        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, BufferUtil.list2ByteBuffer(coordsList));
        gl.glDrawArrays(GL10.GL_LINE_STRIP, 0, coordsList.size()/3);
    }
}
