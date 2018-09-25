package com.example.lulu.opengldemo;

import android.opengl.GLU;

import com.example.lulu.opengldemo.utils.BufferUtil;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by zhanglulu on 2018/9/20.
 * for 绘制一个球体
 */
public class MySphereRenderer extends AbstractMyRenderer {

    @Override
    public void onDrawFrame(GL10 gl) {
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);//清除颜色缓冲区
        gl.glColor4f(1f, 0f, 0f, 1f);//设置颜色


        //操作模型视图
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
        //设置眼球的位置
        GLU.gluLookAt(gl, 0f, 0f, 5f,
                0f, 0f, 0f, 0f, 1f, 0f);

        //旋转
        gl.glRotatef(xrotate, 1, 0, 0);
        gl.glRotatef(yrotate, 0, 1, 0);

        //计算球体坐标
        float R = 0.5f;//球的半径
        int stack = 8;//水平层数
        float stackStep = ((float) (Math.PI / stack));//单位角度值 180度
        int slice = 12;//竖直
        float sliceStep = (float) ((Math.PI*2) / slice);//水平圆递增角度 360度

        //上下两个圆的坐标 半径
        float r0, r1, x0, x1, y0, y1, z0, z1;
        //两个切片的夹角
        float alpha0 = 0;
        float alpha1 = 0;
        //切片圆的角度
        float beta = 0;

        //顶点坐标
        List<Float> coords = new ArrayList<>();
        //外层循环 水平切片!
        for (int i = 0; i <= stack; i++) {
            alpha0 = (float) (-Math.PI/2 + (i * stackStep));
            alpha1 = (float) (-Math.PI/2 + ((i+1) * stackStep));
            y0 = (float) (R * Math.sin(alpha0));
            r0 = (float) (R * Math.cos(alpha0));

            y1 = (float) (R * Math.sin(alpha1));
            r1 = (float) (R * Math.cos(alpha1));

            //循环每一层的圆
            for (int j = 0; j <= slice * 2; j++) {
                beta = j * sliceStep;
                x0 = (float) (r0 * Math.cos(beta));
                z0 = -(float) (r0 * Math.sin(beta));

                x1 = (float) (r1 * Math.cos(beta));
                z1 = -(float) (r1 * Math.sin(beta));
                coords.add(x0);
                coords.add(y0);
                coords.add(z0);
                coords.add(x1);
                coords.add(y1);
                coords.add(z1);
            }
        }
        FloatBuffer fbb = BufferUtil.list2FloatBuffer(coords);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, fbb);
        gl.glDrawArrays(GL10.GL_LINE_STRIP, 0, coords.size()/3);

    }

}
