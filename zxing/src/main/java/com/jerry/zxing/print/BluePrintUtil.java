package com.jerry.zxing.print;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import zpSDK.zpSDK.zpSDK;

/**
 * Created by Administrator on 2018/2/7.
 *
 * @author LWH
 */

public class BluePrintUtil {
    public static boolean checkPrinterXT413(Context context, String address) {
        BluetoothAdapter mBluetoothAdapter;
        if (TextUtils.isEmpty(address)) {
            Toast.makeText(context, "没有找到蓝牙打印机地址", Toast.LENGTH_SHORT).show();
            return false;
        }
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            Toast.makeText(context, "没有找到蓝牙适配器", Toast.LENGTH_SHORT).show();
            return false;
        }
        /** 获得已配对的远程蓝牙设备的集合 */
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter
                .getBondedDevices();
        if (pairedDevices.size() <= 0) {
            Toast.makeText(context, "没找到已经配对过的蓝牙设备", Toast.LENGTH_SHORT).show();
            return false;
        }
        BluetoothDevice bluetoothDevice = mBluetoothAdapter.getRemoteDevice(address);
        if (bluetoothDevice == null) {
            Toast.makeText(context, "读取蓝牙设备错误", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!zpSDK.zp_open(mBluetoothAdapter, bluetoothDevice)) {
            if (!zpSDK.zp_open(mBluetoothAdapter, bluetoothDevice)) {
                Toast.makeText(context, "打开打印机失败"+zpSDK.ErrorMessage, Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    /**
     * LWH 2017-1-16
     * 获取手机适配的蓝牙集合
     */
    public static Map<String, String> getAddressMap() {
        Set<BluetoothDevice> set = BluetoothAdapter.getDefaultAdapter().getBondedDevices();
        Map<String, String> map = new HashMap<>();
        if (set != null && set.size() > 0) {
            for (BluetoothDevice device : set) {
                map.put(device.getAddress(), device.getName());
                Log.i("---", "---KEY:" + device.getAddress() + "---VALUE:" + device.getName());
            }
        }else{
            Log.i("","----NONONNNNNNNNNNNNNNNNNNNNN");
        }
        return map;
    }

}
