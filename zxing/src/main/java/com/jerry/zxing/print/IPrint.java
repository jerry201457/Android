package com.jerry.zxing.print;

/**
 * 打印接口
 * Created by ZYC on 2016/6/25.
 */
public interface IPrint{
    //打印运单
    void printOrder() throws Exception;
    //打印标签
    void printLabel() throws Exception;
}
