package com.example.lulu.opengldemo;

import android.opengl.GLU;

import com.example.lulu.opengldemo.utils.BufferUtil;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by zhanglulu on 2018/7/26.
 * for 棱锥
 */
public class MyTriangleConeRenderer extends AbstractMyRenderer{

    @Override
    public void onDrawFrame(GL10 gl) {
        //启动颜色缓冲区
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
        //清除颜色缓冲区 和 深度缓冲区
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        //设置绘图颜色
        gl.glColor4f(1f, 0f, 0f, 1f);
        //启用深度测试
        gl.glEnable(GL10.GL_DEPTH_TEST);
        //启用表面剔除
        gl.glEnable(GL10.GL_CULL_FACE);
        //指定正面
        //ccw: counter clock wise -> 逆时针
        //cw: clock wise -> 顺时针
        gl.glFrontFace(GL10.GL_CCW);

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

        //GL10.GL_SMOOTH: 平滑模式
        //GL10.GL_FLAT: 单调模式
        gl.glShadeModel(GL10.GL_FLAT);

        //计算点坐标
        float r = 0.5f;//半径
        float x = 0f, y = 0f, z = -0.5f;

        /************************锥面***************************/
        //顶点坐标点集合
        List<Float> coordsList = new ArrayList<>();
        //添加锥顶点
        coordsList.add(0f);
        coordsList.add(0f);
        coordsList.add(0.5f);

        //顶点颜色值
        List<Float> colorList = new ArrayList<>();
        colorList.add(1f);//r
        colorList.add(0f);//g
        colorList.add(0f);//b
        colorList.add(1f);//a

        /************************锥底***************************/
        //锥底坐标
        List<Float> coordsConeBottomList = new ArrayList<>();
        coordsConeBottomList.add(0f);
        coordsConeBottomList.add(0f);
        coordsConeBottomList.add(-0.5f);

        boolean flag = false;
        //底面
        for (float alpha = 0f; alpha < Math.PI * 2;
             alpha = (float) (alpha + Math.PI / 6)) {
            //点坐标值
            x = (float) (r * Math.cos(alpha));
            y = (float) (r * Math.sin(alpha));
            //锥面
            coordsList.add(x);
            coordsList.add(y);
            coordsList.add(z);
            //锥底坐标
            coordsConeBottomList.add(x);
            coordsConeBottomList.add(y);
            coordsConeBottomList.add(z);

            //点颜色值
            if (flag = !flag) {
                //黄色
                colorList.add(1f);
                colorList.add(1f);
                colorList.add(0f);
                colorList.add(1f);
            } else {
                //红色
                colorList.add(1f);
                colorList.add(0f);
                colorList.add(0f);
                colorList.add(1f);
            }
        }

        //点颜色值
        if (flag = !flag) {
            //黄色
            colorList.add(1f);
            colorList.add(1f);
            colorList.add(0f);
            colorList.add(1f);
        } else {
            //红色
            colorList.add(1f);
            colorList.add(0f);
            colorList.add(0f);
            colorList.add(1f);
        }

        //指定颜色缓冲区
        ByteBuffer colorBuffer = BufferUtil.list2ByteBuffer(colorList);
        gl.glColorPointer(4, GL10.GL_FLOAT, 0,colorBuffer);

        //画锥面之前剔除背面
        gl.glCullFace(GL10.GL_BACK);
        //绘制锥面
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0,
                BufferUtil.list2ByteBuffer(coordsList));
        gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, coordsList.size()/ 3);

        //画锥底时不能剔除背面(由于指定了逆时针为正面)
        gl.glCullFace(GL10.GL_FRONT);
        //绘制锥底
        colorBuffer.position(4*4);//移动4*4个字节,也就是挪动1个颜 色值
        gl.glColorPointer(4, GL10.GL_FLOAT, 0, colorBuffer);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0,
                BufferUtil.list2ByteBuffer(coordsConeBottomList));
        gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, coordsConeBottomList.size()/ 3);
    }
}
