package com.example.lulu.opengldemo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.opengl.GLU;
import android.opengl.GLUtils;
import android.os.Build;

import com.example.lulu.opengldemo.utils.BufferUtil;

import java.nio.IntBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * @author zhanglulu on 2018/12/18.
 * for 纹理贴图
 */
public class MyTextureRenderer extends AbstractMyRenderer {

    private Bitmap textureBitmap;

    public MyTextureRenderer(Activity activity) {
        textureBitmap = getBitmap(activity, R.mipmap.ic_launcher);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        gl.glClearColor(0f, 0f, 0f, 1f);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        //启动纹理缓冲区
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

        //设置纹理
        setTexture(gl);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        gl.glViewport(0, 0, width, height);

        //投影矩阵
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        ratio = ((float) width) / ((float) height);
        //平截头体
        gl.glFrustumf(-ratio, ratio, -1f, 1f, 3, 7);
    }

    float r = 0.5f;
    //坐标顶点
    float[] coordsVertex = {
            -r, r, 0,
            -r, -r, 0,
            r, r, 0,
            r, -r, 0,
    };

    //纹理顶点
    float[] textureVertex = {
            0, -1,
            0, 0,
            -1, -1,
            -1, 0
    };

    int[] textureId = new int[1];

    @Override
    public void onDrawFrame(GL10 gl) {
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

        //gl.glColor4f(1f, 1f, 0f, 1f);
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
        GLU.gluLookAt(gl, 0f, 0f, 5f, 0f, 0f, 0f, 0f, 1f, 0f);

        //旋转角度
        //angle: 旋转角度
        //x,y,z: 沿着哪个轴旋转(向量) 迎面轴正方向看去, 顺时针: 负值 逆时针: 正值
        gl.glRotatef(xrotate, 1, 0, 0);//绕x轴旋转
        gl.glRotatef(yrotate, 0, 1, 0);//绕y轴旋转

        //指定纹理坐标
        gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, BufferUtil.arr2ByteBuffer(textureVertex));

        //设置顶点坐标
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, BufferUtil.arr2ByteBuffer(coordsVertex));

        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, coordsVertex.length / 3);

    }


    /**
     * 设置贴图
     *
     * @param gl
     */
    private void setTexture(GL10 gl) {
        //启动纹理
        gl.glEnable(GL10.GL_TEXTURE_2D);
        IntBuffer intBuffer = IntBuffer.allocate(1);
        //创建纹理
        gl.glGenTextures(1, intBuffer);
        textureId[0] = intBuffer.get();

        //绑定纹理
        gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId[0]);
        //生成纹理
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, textureBitmap, 0);

        //当纹理需要被放大和缩小是都使用线性方法调整图像
        gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
        gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);

    }


    private static Bitmap getBitmap(Context context, int vectorDrawableId) {
        Bitmap bitmap = null;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            Drawable vectorDrawable = context.getDrawable(vectorDrawableId);
            bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),
                    vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            vectorDrawable.draw(canvas);
        } else {
            bitmap = BitmapFactory.decodeResource(context.getResources(), vectorDrawableId);
        }
        return bitmap;
    }
}
