package com.jerry.zxing.print;

import android.content.Context;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/2/7.
 *
 * @author LWH
 */

public class PrintManager<T> {
    private InitPrint mInitPrint;
    private Map<String, String> mAddressMap;

    public PrintManager() {
        mAddressMap = BluePrintUtil.getAddressMap();
    }

    /**
     * LWH 2016-10-13
     * 打印运单
     *
     * @throws Exception
     */
    public void printOrder(Context context, List<T> list) throws Exception {
        mInitPrint = new InitPrint(context, list, mAddressMap.get(Config.ADDRESS_ORDER));
        mInitPrint.printOrder();
    }

    /**
     * LWH 2016-10-13
     * 打印标签
     *
     * @throws Exception
     */
    public void printLabel(Context context, List<T> list) throws Exception {
        mInitPrint = new InitPrint(context, list, mAddressMap.get(Config.ADDRESS_ORDER));
        mInitPrint.printLabel();
    }
}
