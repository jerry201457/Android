package com.sinotech.tms.main.print.sdk;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.UUID;

/**
 * 使用CPCL指令打印
 *
 * @author LiuWeiHao
 * 2019/7/10 14:59
 */
public class CPCLPrint implements IPrint {
    private final String TAG = CPCLPrint.class.getName();
    private final int RATE = 8;
    private final String CHARSET = "gb2312";
    private final UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private BluetoothSocket bluetoothSocket;
    private InputStream inputStream;
    private OutputStream outputStream;

    public CPCLPrint() {
    }

    @Override
    public boolean connect(String address) {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        BluetoothDevice remoteDevice = bluetoothAdapter.getRemoteDevice(address);
        try {
            Method m = remoteDevice.getClass().getMethod("createRfcommSocket", Integer.TYPE);
            bluetoothSocket = (BluetoothSocket)m.invoke(remoteDevice, 1);
//            bluetoothSocket = remoteDevice.createRfcommSocketToServiceRecord(uuid);
            inputStream = bluetoothSocket.getInputStream();
            outputStream = bluetoothSocket.getOutputStream();
            if(outputStream==null){
                Log.i(TAG, "打印机连接失败! outputStream is null");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.i(TAG, "打印机连接失败!");
            return false;
        }
        return true;
    }

    @Override
    public boolean writeData(byte[] data) {
        if(outputStream==null){
            Log.i(TAG, "writeData 失败 ! outputStream is null");
            return false;
        }
        if(data==null){
            Log.i(TAG, "writeData 失败 ! data is null");
            return false;
        }
        int length = data.length;
        byte[] bytes = new byte[10000];
        try {
            outputStream.write(data);
        } catch (Exception e) {
            e.printStackTrace();
            Log.i(TAG, "传递数据出错!");
            return false;
        }
        return true;
    }

    @Override
    public boolean createPrintPage(int width, int height) {
        try {
            byte[] bytes = String.format("! 0 %s %s %s 1", width * RATE, height * RATE, height * RATE).getBytes(CHARSET);
            return writeData(bytes);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Log.i(TAG, "创建打印页出错!");
            return false;
        }
    }

    @Override
    public boolean printText(int angle, int font, int size, int x, int y, String data) {
        try {
            String direction="TEXT";
            switch (angle){
                case 0:
                    direction="TEXT";
                    break;
                case 90:
                    direction="TEXT90";
                    break;
                case 180:
                    direction="TEXT180";
                    break;
                case 270:
                    direction="TEXT270";
                    break;
            }
            byte[] bytes = String.format("%s %s %s %s %s %s", direction, font, size, x * RATE, y * RATE, data).getBytes(CHARSET);
            return writeData(bytes);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Log.i(TAG, "打印文本出错!");
            return false;
        }
    }

    @Override
    public boolean print() {
        try {
            byte[] bytes = "PRINT\r\n".getBytes(CHARSET);
            return writeData(bytes);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Log.i(TAG, "结束打印出错!");
            return false;
        }
    }

    @Override
    public boolean close() {
        try {
            if (bluetoothSocket != null) {
                bluetoothSocket.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
            if(outputStream!=null){
                outputStream.close();
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            Log.i(TAG, "关闭socket出错!");
            return false;
        }
    }

}
