package com.sinotech.tms.main.print.sdk;

public interface IPrint {
    boolean connect(String address);

    boolean writeData(byte[] data);

    /**
     * 创建打印页
     *
     * @param width  宽 mm
     * @param height 高 mm
     * @return
     */
    boolean createPrintPage(int width, int height);

    boolean printText(int angle,int font,int size,int x,int y,String data);

    boolean print();

    boolean close();
}
