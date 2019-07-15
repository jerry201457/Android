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
            bluetoothAdapter.cancelDiscovery();
            bluetoothSocket = remoteDevice.createRfcommSocketToServiceRecord(uuid);
            Log.i(TAG, "创建连接createRfcommSocketToServiceRecord");
            bluetoothSocket.connect();
//            Thread.sleep(2000L);
            if (!bluetoothSocket.isConnected()) {
                bluetoothSocket = remoteDevice.createInsecureRfcommSocketToServiceRecord(uuid);
                Log.i(TAG, "创建连接createInsecureRfcommSocketToServiceRecord");
                bluetoothSocket.connect();
            }
//            Thread.sleep(2000L);
            if (!bluetoothSocket.isConnected()) {
                Method m = remoteDevice.getClass().getMethod("createRfcommSocket", Integer.TYPE);
                bluetoothSocket = (BluetoothSocket) m.invoke(remoteDevice, 1);
                bluetoothSocket.connect();
                Log.i(TAG, "创建连接getMethod");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "打印机连接失败!");
//            try {
//                Method m = remoteDevice.getClass().getMethod("createRfcommSocket", Integer.TYPE);
//                bluetoothSocket = (BluetoothSocket) m.invoke(remoteDevice, 1);
//                Thread.sleep(1000);
//                bluetoothSocket.connect();
//                Log.i(TAG, "创建连接getMethod");
//            } catch (Exception e1) {
//                e1.printStackTrace();
//                Log.e(TAG, "createRfcommSocket打印机连接失败!");
//            }
        }
        try {
            inputStream = bluetoothSocket.getInputStream();
            outputStream = bluetoothSocket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (outputStream == null) {
            Log.e(TAG, "打印机连接失败! outputStream is null");
            return false;
        }
        if (bluetoothSocket.isConnected()) {
            Log.e(TAG, "打印机连接成功!");
        }
        return bluetoothSocket.isConnected();
    }

    @Override
    public boolean writeData(byte[] data) {
        if (!bluetoothSocket.isConnected()) {
            Log.e(TAG, "bluetoothSocket未连接!");
            return false;
        }
        if (outputStream == null) {
            Log.i(TAG, "writeData 失败 ! outputStream is null");
            return false;
        }
        if (data == null) {
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
        boolean success;
        try {
            byte[] bytes = String.format("! 0 %s %s %s 1", width * RATE, height * RATE, height * RATE).getBytes(CHARSET);
            success = writeData(bytes);
            Log.i(TAG, "创建打印页结果:" + success);
            return success;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Log.i(TAG, "创建打印页出错!");
            return false;
        }
    }

    @Override
    public boolean printText(int angle, int font, int size, int x, int y, String data) {
        try {
            String direction = "TEXT";
            switch (angle) {
                case 0:
                    direction = "TEXT";
                    break;
                case 90:
                    direction = "TEXT90";
                    break;
                case 180:
                    direction = "TEXT180";
                    break;
                case 270:
                    direction = "TEXT270";
                    break;
            }
            byte[] bytes = String.format("%s %s %s %s %s %s", direction, font, size, x * RATE, y * RATE, data).getBytes(CHARSET);
            boolean writeData = writeData(bytes);
            Log.i(TAG, "打印文本=" + writeData + ",data=" + data);
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
            Log.i(TAG, "print()执行打印出错!");
            return false;
        }
    }

    @Override
    public boolean form() {
        try {
            byte[] bytes = "FORM\r\n".getBytes(CHARSET);
            return writeData(bytes);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Log.i(TAG, "form()执行打印出错!");
            return false;
        }
    }

    @Override
    public boolean close() {
        try {
            if (bluetoothSocket != null) {
                bluetoothSocket.close();
                bluetoothSocket = null;
            }
            if (inputStream != null) {
                inputStream.close();
                inputStream = null;
            }
            if (outputStream != null) {
                outputStream.close();
                outputStream = null;
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "关闭打印出错!");
            return false;
        }
    }
}
