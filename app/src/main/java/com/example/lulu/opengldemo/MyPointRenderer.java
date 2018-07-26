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
public class MyPointRenderer implements GLSurfaceView.Renderer{

    private float ratio;

    public float xrotate = 0f; //围绕x轴旋转角度
    public float yrotate = 0f;  //围绕y轴旋转角度

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        //设置清屏色
        gl.glClearColor(0f, 0f, 0f, 1f);
        //启用顶点缓冲区
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
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
        gl.glFrustumf(-1f, 1f, -ratio, ratio, 3, 7);
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
        float x = 0f, y = 0f, z = 1.5f;
        float zstep = 0.005f;//步长
        for (float alpha = 0f; alpha < Math.PI * 6; alpha = (float) (alpha + Math.PI/32)) {
            x = (float) (r * Math.cos(alpha));
            y = (float) (r * Math.sin(alpha));
            z = z - zstep;
            coordsList.add(x);
            coordsList.add(y);
            coordsList.add(z);
        }

        //转换点成为缓冲区
        ByteBuffer ibb = ByteBuffer.allocateDirect(coordsList.size() * 4);
        ibb.order(ByteOrder.nativeOrder());
        FloatBuffer fbb = ibb.asFloatBuffer();
        for (Float f : coordsList) {
            fbb.put(f);
        }
        fbb.position(0);
        //指定顶点指针
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, ibb);
        //画数组
        gl.glDrawArrays(GL10.GL_POINTS, 0, coordsList.size() / 3);
    }
}
