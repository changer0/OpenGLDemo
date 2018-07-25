package com.example.lulu.opengldemo;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;


import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyGLSurfaceView view = new MyGLSurfaceView(this);
        //renderer: 渲染器
        view.setRenderer(new MyRenderer());
        setContentView(view);
    }
    class MyGLSurfaceView extends GLSurfaceView {

        public MyGLSurfaceView(Context context) {
            super(context);
        }

        public MyGLSurfaceView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }
    }

    //自定义渲染器(OpenGL ES 开发中最为重要的部分)
    class MyRenderer implements GLSurfaceView.Renderer {

        private float ratio;

        //表层创建时
        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            //设置清屏色
            gl.glClearColor(0, 0, 0, 1);
            //启用顶点缓冲区
            gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        }

        //表层size变化时
        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            //设置视口 输出画面的区域 (从左下角开始)
            gl.glViewport(0, 0, width, height);

            ratio = (float)width / (float)height;
            //设置矩阵模式  投影矩阵 (openGL基于状态机)
            gl.glMatrixMode(GL10.GL_PROJECTION);
            //加载单位矩阵
            gl.glLoadIdentity();
            //设置平截头体 zNear:近平面 zFar:远平面
            gl.glFrustumf(-1f, 1f, -ratio, ratio, 3, 7);
        }

        //绘图
        @Override
        public void onDrawFrame(GL10 gl) {
            //清除颜色缓冲区
            gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
            //模型视图矩阵
            gl.glMatrixMode(GL10.GL_MODELVIEW);
            //加载单位矩阵
            gl.glLoadIdentity();
            //eyex, eyey, eyez,:放置眼球的坐标
            //centerx, centery, centerz : 眼球的观察点
            //upx, upy, upz : 指定眼球向上的向量
            GLU.gluLookAt(gl, 0, 0, 5, 0, 0, 0, 0, 1, 0);

            //画三角形
            //绘制数组
            //三角形坐标
            float[] coords = {
                    0f, ratio, 2f,
                    -1f, -ratio, 2f,
                    1f, -ratio, 2f,
            };
            //分配字节缓存区空间, 存放定点坐标数据
            // 一个字节 8位  浮点数4个字节
            ByteBuffer ibb = ByteBuffer.allocateDirect(coords.length * 4);
            //设置顺序(本地顺序,跟操作系统有关)
            ibb.order(ByteOrder.nativeOrder());
            //放置顶点坐标数据
            FloatBuffer fbb = ibb.asFloatBuffer();
            fbb.put(coords);
            //定位指针的位置, 从该位置开始读取顶点数据
            fbb.position(0);
            //设置绘图的颜色 (红色)
            gl.glColor4f(1f, 0f, 0f, 1f);

            //加载顶点指针
            // size: 使用三个坐标值表示一个点
            // type: 每个点的数据类型
            // stride: 0, 跨度
            // pointer: 指定顶点缓存区
            gl.glVertexPointer(3, GL10.GL_FLOAT, 0, ibb);
            //绘制三角形
            gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3);

        }
    }
}
