package com.example.lulu.opengldemo;

import android.opengl.GLU;

import com.example.lulu.opengldemo.utils.BufferUtil;
import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by zhanglulu on 2018/7/26.
 * for 线带渲染器 依次相连 不闭合
 */
public class MyLineStripRenderer extends AbstractMyRenderer{

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
        List<Float> coordsList = new ArrayList<>();//坐标点集合
        //  旋转3圈
        float x = 0f, y = 0f, z = 1f;
        float zstep = 0.01f;//步长
        for (float alpha = 0f; alpha < Math.PI * 6; alpha = (float) (alpha + Math.PI/32)) {
            x = (float) (r * Math.cos(alpha));
            y = (float) (r * Math.sin(alpha));
            z = z - zstep;
            coordsList.add(x);
            coordsList.add(y);
            coordsList.add(z);
        }

        //指定顶点指针
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, BufferUtil.list2ByteBuffer(coordsList));
        //画数组
        gl.glDrawArrays(GL10.GL_LINE_STRIP, 0, coordsList.size() / 3);
    }
}
