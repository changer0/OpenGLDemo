package com.example.lulu.opengldemo.utils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.List;

/**
 * Created by zhanglulu on 2018/7/27.
 * for 缓冲区工具类
 */
public class BufferUtil {
    /**
     * 将浮点数组转换成字节缓冲区
     * @param arr 输入待转换的浮点数组
     * @return 字节缓冲区
     */
    public static ByteBuffer arr2ByteBuffer(float[] arr) {
        //分配字节缓存区空间, 存放定点坐标数据
        // 一个字节 8位  浮点数4个字节
        ByteBuffer ibb = ByteBuffer.allocateDirect(arr.length * 4);
        //设置顺序(本地顺序,跟操作系统有关)
        ibb.order(ByteOrder.nativeOrder());
        //放置顶点坐标数据
        FloatBuffer fbb = ibb.asFloatBuffer();
        fbb.put(arr);
        //定位指针的位置, 从该位置开始读取顶点数据
        ibb.position(0);
        return ibb;
    }

    /**
     * 将List集合转换成字节缓冲区
     * @param list 输入待转换的浮点数组
     * @return 字节缓冲区
     */
    public static ByteBuffer list2ByteBuffer(List<Float> list) {
        //分配字节缓存区空间, 存放定点坐标数据
        // 一个字节 8位  浮点数4个字节
        ByteBuffer ibb = ByteBuffer.allocateDirect(list.size() * 4);
        //设置顺序(本地顺序,跟操作系统有关)
        ibb.order(ByteOrder.nativeOrder());
        //放置顶点坐标数据
        FloatBuffer fbb = ibb.asFloatBuffer();
        for (Float aFloat : list) {
            fbb.put(aFloat);
        }
        //定位指针的位置, 从该位置开始读取顶点数据
        ibb.position(0);
        return ibb;
    }
}
