package com.sinotech.tms.main.print.sdk;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.text.TextUtils;

import com.sinotech.tms.main.print.ToastUtil;

import java.util.Set;

public class BlueUtil {
    /**
     * 校验打印机状态
     */
    public static boolean checkPrinter(String address) {
        if (TextUtils.isEmpty(address)) {
            ToastUtil.showToast("未设置蓝牙打印机地址");
            return false;
        }
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            ToastUtil.showToast("没有找到蓝牙适配器!");
            return false;
        }
        if (!bluetoothAdapter.isEnabled()) {
            ToastUtil.showToast("请打开蓝牙");
            return false;
        }
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() <= 0) {
            ToastUtil.showToast("没找到已经配对过的蓝牙设备!");
            return false;
        }
        BluetoothDevice bluetoothDevice = bluetoothAdapter.getRemoteDevice(address);
        if (bluetoothDevice == null) {
            ToastUtil.showToast("读取蓝牙设备错误");
            return false;
        }
        int bondState = bluetoothDevice.getBondState();
        if (bondState == BluetoothDevice.BOND_NONE) {
            ToastUtil.showToast("蓝牙未绑定");
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                boolean bond = bluetoothDevice.createBond();
                if (!bond) {
                    ToastUtil.showToast("蓝牙设备未配对，请重新绑定蓝牙设备");
                    return false;
                }
            }
        }
        return true;
    }

}
