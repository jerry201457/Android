package com.jerry.zxing.print;

import android.content.Context;

import java.util.List;

/**
 * Created by Administrator on 2018/2/7.
 *
 * @author LWH
 */

public class InitPrint<T> {
    private IPrint mIPrint;

    InitPrint(Context context, List<T> list, String printName) {
        switch (printName) {
            case "XT4131A":
                mIPrint = new BluePrintXT413(context, list);
                break;
//                case "XT423":
//                    mIPrint = new BluePrintXT423(context, list);
//                    break;
//                case "HDT334":
//                    mIPrint = new BluePrintHDT334(context, list);
//                    break;
            default:
                break;
        }
    }

    public void printOrder() throws Exception {
        if (mIPrint != null) {
            mIPrint.printOrder();
        }
    }

    void printLabel() throws Exception {
        if (mIPrint != null) {
            mIPrint.printLabel();
        }
    }

}
