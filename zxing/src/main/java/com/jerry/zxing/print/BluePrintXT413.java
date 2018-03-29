package com.jerry.zxing.print;

import android.content.Context;
import android.widget.Toast;

import com.jerry.zxing.util.Ticket;

import java.util.List;

import zpSDK.zpSDK.zpSDK;

/**
 * Created by Administrator on 2018/2/7.
 *
 * @author LWH
 */

public class BluePrintXT413<T> implements IPrint {
    private Context mContext;
    private List<T> mList;

    BluePrintXT413(Context context, List<T> list) {
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public void printOrder() throws Exception {
        if (BluePrintUtil.checkPrinterXT413(mContext, Config.ADDRESS_ORDER)) {
            Ticket ticket = Ticket.getBarcode128();
            startPrintOrder(60);
            zpSDK.zp_draw_bitmap(ticket.getTicketBitmap(), 0, 0);
            ticket.cleanTicket();
            endOrderPrint();
        }
        zpSDK.zp_close();
    }

    @Override
    public void printLabel() throws Exception {

    }

    /**
     * 运单开始打印
     */
    private void startPrintOrder(int height) {
        zpSDK.zp_page_create(70, height);
        zpSDK.TextPosWinStyle = false;
        zpSDK.zp_page_clear();
    }

    /**
     * 结束运单信息打印
     */
    private void endOrderPrint() {
        zpSDK.zp_page_print(false);
        zpSDK.zp_printer_status_detect();
        if (zpSDK.zp_printer_status_get(8000) != 0) {
            Toast.makeText(mContext, zpSDK.ErrorMessage, Toast.LENGTH_SHORT).show();
        }
        zpSDK.zp_page_free();
    }

}
