package com.example.lulu.opengldemo.other;


import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 *  OpenGL 渲染器，用来控制GLSurfaceView中绘制的内容
 */
public class MyGLRenderer implements GLSurfaceView.Renderer {

    private Triangle mTriangle;
    private Square   mSquare;

    // mMVPMatrix is an abbreviation for "Model View Projection Matrix"
    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];
    private float[] mRotationMatrix = new float[16];


    /**
     * 调用一次，用来配置View的OpenGL ES环境。
     * @param unused
     * @param config
     */
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        // Set the background frame color
        //GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        // initialize a triangle 出于内存和执行效率的考量。放置在此处初始化
        mTriangle = new Triangle();
        // initialize a square
        mSquare = new Square();
    }

    /**
     * 每次重新绘制View时被调用
     * @param unused
     */
    public void onDrawFrame(GL10 unused) {
        // Redraw background color
        //GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        // Set the camera position (View matrix)
        Matrix.setLookAtM(mViewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

        // Calculate the projection and view transformation
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);

        // Draw shape
        mTriangle.draw(mMVPMatrix);

//        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        //三角形旋转
        float[] scratch = new float[16];
        // Create a rotation transformation for the triangle
        long time = SystemClock.uptimeMillis() % 4000L;
        float angle = 0.090f * ((int) time);
        Matrix.setRotateM(mRotationMatrix, 0, angle, 0, 0, -1.0f);

        // Combine the rotation matrix with the projection and camera view
        // Note that the mMVPMatrix factor *must be first* in order
        // for the matrix multiplication product to be correct.
        Matrix.multiplyMM(scratch, 0, mMVPMatrix, 0, mRotationMatrix, 0);

        // Draw triangle
        mTriangle.draw(scratch);

    }

    /**
     * 如果View的几何形状发生变化时会被调用，例如当设备的屏幕方向发生改变时。
     * @param unused
     * @param width
     * @param height
     */
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLES20.glViewport(0, 0, width, height);

        float ratio = (float) width / height;

        // this projection matrix is applied to object coordinates
        // in the onDrawFrame() method
        Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
    }

    /**
     * 着色器包含了OpenGL Shading Language（GLSL）代码，它必须先被编译然后才能在OpenGL环境中使用。
     * 要编译这些代码，需要在你的渲染器类中创建一个辅助方法：<br/>
     *
     * 注意: 编译OpenGL ES着色器及链接操作对于CPU周期和处理时间而言，消耗是巨大的，
     * 所以你应该避免重复执行这些事情。如果在执行期间不知道着色器的内容，那么你应该在构建你的应用时，
     * 确保它们只被创建了一次，并且缓存以备后续使用。
     *
     * @param type create a vertex shader type (GLES20.GL_VERTEX_SHADER)
     *            or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
     * @param shaderCode add the source code to the shader and compile it
     * @return
     */
    public static int loadShader(int type, String shaderCode){

        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }

}
