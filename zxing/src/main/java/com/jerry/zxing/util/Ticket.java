package com.jerry.zxing.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextUtils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import static android.graphics.Color.BLACK;

/**
 * the more diligent, the more luckier you are !
 * ---------------------------------------------
 * Created by StudyAbc on 2018/2/6.
 *
 * @des 生成票据打印工具类   建议标准字体行间距为 35  线间距为 5   单个字体宽度为  25；
 */

public class Ticket {
    private Bitmap mBitmap;
    private Paint mPaint;
    private Canvas mCanvas;
    private static Ticket mTicket;

    /**
     * 私有构造方法  禁止外界创建  单例模式
     *
     * @param width  票据的宽度
     * @param height 票据的高度
     * @param rotate 打印时是否旋转
     */
    private Ticket(int width, int height, Rotate rotate) {
        switch (rotate) {
            case Rotate_0:
                this.mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ALPHA_8);
                this.mCanvas = new Canvas(mBitmap);
                break;
            case Rotate_90:
                this.mBitmap = Bitmap.createBitmap(height, width, Bitmap.Config.ALPHA_8);
                this.mCanvas = new Canvas(mBitmap);
                mCanvas.rotate(90, height / 2, height / 2);
                break;
            case Rotate_180:
                this.mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ALPHA_8);
                this.mCanvas = new Canvas(mBitmap);
                mCanvas.rotate(90, width / 2, height / 2);
                break;
            default:
                this.mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ALPHA_8);
                this.mCanvas = new Canvas(mBitmap);
                break;
        }
        this.mPaint = new Paint();
        mPaint.setColor(BLACK);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL));
        mPaint.setTextSize(25f);
    }

    private static Ticket getInstance(int width, int height, Rotate rotate) {
        if (mTicket == null) {
            mTicket = new Ticket(width, height, rotate);
        }
        return mTicket;
    }

    /**
     * 暴露给外界方法获取对象
     *
     * @param width  票据的宽度
     * @param height 票据的高度
     * @param rotate 打印时是否旋转   不需要考虑旋转后的坐标  只需要按照实际的格式来调试
     * @return
     */
    public static Ticket init(int width, int height, Rotate rotate) {
        return getInstance(width, height, rotate);
    }

    /**
     * 设置字体大小  默认字体为 3 号字体
     *
     * @param size
     */
    public void setFontSize(Font size) {
        switch (size) {
            case Size_1:
                mPaint.setTextSize(15f);
                break;
            case Size_2:
                mPaint.setTextSize(20f);
                break;
            case Size_3:
                mPaint.setTextSize(25f);
                break;
            case Size_4:
                mPaint.setTextSize(30f);
                break;
            case Size_5:
                mPaint.setTextSize(35f);
                break;
            default:
                mPaint.setTextSize(25f);
                break;
        }
    }

    /**
     * 设置字体是否加粗
     *
     * @param isBold
     */
    public void setFontBold(boolean isBold) {
        if (isBold) {
            mPaint.setTypeface(Typeface.create(Typeface.DEFAULT_BOLD, Typeface.BOLD));
        } else {
            mPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        }
    }

    /**
     * 打印字体
     *
     * @param x       x 轴坐标
     * @param y       y 轴坐标
     * @param content 打印字体内容
     */
    public void drawText(String content, int x, int y) {
        mCanvas.drawText(content, x, y, mPaint);
    }

    /**
     * 画横线
     */
    public void drawHLine(int startX, int stopX, int y) {
        mCanvas.drawLine(startX, y, stopX, y, mPaint);
    }

    /**
     * 画竖线
     */
    public void drawVLine(int x, int startY, int stopY) {
        mCanvas.drawLine(x, startY, x, stopY, mPaint);
    }

    public void drawQRCode(int x, int y, int size, String content) {
        try {
            Bitmap bitmap = EncodingHandler.createQRCode(content, size);
            mCanvas.drawBitmap(bitmap, x - 50, y - 40, mPaint);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    public void drawCode128(int x, int y, int widthPix, int heightPix, String content) {
        try {
            Bitmap bitmap = EncodingHandler.createCode128(content, 400, heightPix);
            mCanvas.drawBitmap(bitmap, x - 50, y - 40, mPaint);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void drawLineCode128(int startX, int startY, int width, int height, String content) {
        Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        BitMatrix matrix = null;
        try {
            matrix = new MultiFormatWriter().encode(content, BarcodeFormat.CODE_128, width, height, hints);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        width = matrix.getWidth();
        height = matrix.getHeight();
        boolean[] pixels = new boolean[width];
        for (int x = 0; x < width; x++) {
            if (matrix.get(x, 1)) {
                pixels[x] = true;
                mCanvas.drawLine(startX + x, startY, startX + x, startY + height, mPaint);
            } else {
                pixels[x] = false;
            }
        }
//        mCanvas.drawLine();
//        Bitmap bitmap = Bitmap.createBitmap(width, height,Bitmap.Config.ARGB_8888);
//        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
    }

    public void drowBitmap(Bitmap bitmap, int startX, int startY) {
        mCanvas.drawBitmap(bitmap, startX, startY, mPaint);
    }

    /**
     * 绘制条形码
     *
     * @param content   要生成条形码包含的内容
     * @param widthPix  条形码的宽度
     * @param heightPix 条形码的高度
     * @return 返回生成条形的位图
     */
    public void createBarcode128(String content, int startX, int startY, int widthPix, int heightPix) {
        if (TextUtils.isEmpty(content)) {
            return;
        }
        //配置参数
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        // 容错级别 这里选择最高H级别
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        MultiFormatWriter writer = new MultiFormatWriter();

        try {
            // 图像数据转换，使用了矩阵转换 参数顺序分别为：编码内容，编码类型，生成图片宽度，生成图片高度，设置参数
            BitMatrix bitMatrix = writer.encode(content, BarcodeFormat.CODE_128, widthPix, heightPix, hints);
            int[] pixels = new int[widthPix * heightPix];
            // 两个for循环是图片横列扫描的结果
            for (int y = 0; y < heightPix; y++) {
                for (int x = 0; x < widthPix; x++) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * widthPix + x] = 0xff000000; // 黑色
                    } else {
//                        pixels[y * widthPix + x] = 0xffffffff;// 白色
                    }
                }
            }
//            for (int x = 0; x < widthPix; x++) {
//                if (bitMatrix.get(x, 1)) {
//                    mCanvas.drawLine(startX + x, startY, startX + x, startY + 100, mPaint);
//                }
//            }
            Bitmap bitmap = Bitmap.createBitmap(widthPix, heightPix, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, widthPix, 0, 0, widthPix, heightPix);
            mCanvas.drawBitmap(bitmap, startX, startY, mPaint);

//            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
        }
//        return null;
    }

    public Bitmap getTicketBitmap() {
        return mBitmap;
    }

    public void cleanTicket() {
        if (mBitmap != null) {
            mBitmap = null;
        }
        if (mCanvas != null) {
            mCanvas = null;
        }
        if (mPaint != null) {
            mPaint = null;
        }
        if (mTicket != null) {
            mTicket = null;
        }
    }

    public static Ticket getBarcode128() throws Exception {
        Ticket ticket = Ticket.init(520, 300, Rotate.Rotate_0);
        ticket.setFontBold(true);
        ticket.setFontSize(Font.Size_5);
        ticket.drawText("力展物流", 10, 25);
        ticket.setFontBold(false);
        ticket.setFontSize(Font.Size_3);
        ticket.drawText("普通", 10, 50);
        ticket.drowBitmap(EncodingHandler.createCode128("123456789231", 400, 100), 10, 70);
        ticket.drowBitmap(BarcodeUtil.createCode128("1111123456789", 350, 100), 400, 180);
        return ticket;
    }

    public static Ticket getOrderTicket() throws Exception {
        Ticket ticket = Ticket.init(1500, 520, Rotate.Rotate_90);
        ticket.setFontBold(true);
        ticket.setFontSize(Font.Size_5);
        ticket.drawText("力展物流", 500, 25);
        ticket.setFontBold(false);
        ticket.setFontSize(Font.Size_3);
        ticket.drawText("普通", 950, 25);

        ticket.drawText("运单号：", 10, 65);
        ticket.drawText("A04172987", 110, 65);
        ticket.drawText("荷花池--广元", 530, 65);
        ticket.drawText("2017-10-26", 920, 65);

        ticket.drawText("发货人", 10, 105);
        ticket.drawText("测试", 160, 105);
        ticket.drawText("发货电话", 310, 105);
        ticket.drawText("18022221111", 460, 105);
        ticket.drawText("会员号", 620, 105);
        ticket.drawText("8888888", 770, 105);


        ticket.drawText("收货人", 10, 145);
        ticket.drawText("测试", 160, 145);
        ticket.drawText("收货电话", 310, 145);
        ticket.drawText("18022221111", 460, 145);
        ticket.drawText("银行卡号", 620, 145);
        ticket.drawText("6222021702044084895", 770, 145);

        ticket.drawText("货物名称", 10, 185);
        ticket.drawText("件数", 235, 185);
        ticket.drawText("提付", 310, 185);
        ticket.drawText("已付", 460, 185);
        ticket.drawText("回付", 540, 185);
        ticket.drawText("月结", 620, 185);
        ticket.drawText("月结", 695, 185);
        ticket.drawText("保险金", 770, 185);
        ticket.drawText("已付保险费", 930, 185);


        ticket.drawText("测试", 10, 225);
        ticket.drawText("2", 235, 225);
        ticket.drawText("0", 310, 225);
        ticket.drawText("0", 460, 225);
        ticket.drawText("0", 540, 225);
        ticket.drawText("0", 620, 225);
        ticket.drawText("0", 695, 225);
        ticket.drawText("1000", 770, 225);
        ticket.drawText("3", 930, 225);


        ticket.drawText("包装：2袋", 10, 265);
        ticket.drawText("杂费：", 310, 265);
        ticket.drawText("发货合计", 770, 265);
        ticket.drawText("0", 930, 265);

        ticket.drawText("代收款：正常时效", 10, 305);
        ticket.drawText("￥：88", 650, 305);
        ticket.drawText("到站合计", 770, 305);
        ticket.drawText("0", 930, 305);


        ticket.drawText("发货地", 10, 345);
        ticket.drawText("无", 235, 345);
        ticket.drawText("运输要求", 770, 345);
        ticket.drawText("无", 930, 345);

        ticket.drawText("到货地", 10, 385);
        ticket.drawText("无", 235, 385);
        ticket.drawText("回单类型", 770, 385);
        ticket.drawText("无", 930, 385);


        ticket.drawText("特别声明", 10, 445);

        ticket.drawText("回单份数", 770, 425);
        ticket.drawText("0", 930, 425);
        ticket.drawText("目的地", 770, 465);
        ticket.drawText("无", 930, 465);

        ticket.drawText("服务热线：028-66009933   网上查询：www.sclzl.com   攀枝花专线诚招加盟     加盟热线：15102846515", 10, 505);
        ticket.setFontSize(Font.Size_2);
        ticket.drawText("玻璃、陶瓷及制品和无外包装的货品等属易损物品，属免赔范围；可维", 160, 415);
        ticket.drawText("护取得本单视为认可力展物流公司的《托运须知》且没有补充；并确认", 160, 435);
        ticket.drawText("以上内容无误，愿承担相应责任。", 160, 455);

        //横线
        ticket.drawHLine(2, 1070, 70);
        ticket.drawHLine(2, 1070, 110);
        ticket.drawHLine(2, 1070, 150);
        ticket.drawHLine(2, 1070, 190);
        ticket.drawHLine(2, 1070, 230);
        ticket.drawHLine(2, 1070, 270);
        ticket.drawHLine(2, 1070, 310);
        ticket.drawHLine(2, 1070, 350);
        ticket.drawHLine(2, 1070, 390);
        ticket.drawHLine(770, 1070, 430);
        ticket.drawHLine(2, 1070, 470);

        //竖线
        ticket.drawVLine(2, 70, 470);
        ticket.drawVLine(160, 70, 150);
        ticket.drawVLine(160, 310, 470);

        ticket.drawVLine(235, 150, 230);
        ticket.drawVLine(310, 70, 230);

        ticket.drawVLine(460, 70, 230);

        ticket.drawVLine(540, 150, 230);
        ticket.drawVLine(620, 70, 230);

        ticket.drawVLine(690, 150, 230);
        ticket.drawVLine(770, 70, 470);

        ticket.drawVLine(890, 150, 230);
        ticket.drawVLine(890, 310, 470);

        ticket.drawVLine(1070, 70, 470);
        ticket.drawQRCode(1100, 70, 220, "我想你");

//        ticket.drawLineCode128(1100, 200, 300, 100, "12345678");
//        ticket.createBarcode128("123456789123", 1100, 400, 400, 100);
//        Bitmap code128 = EncodingHandler.createCode128("1234567892311", 400, 100);
        ticket.drowBitmap(BarcodeUtil.createCode128("1111123456789", 350, 100), 1100, 300);

        return ticket;
    }
}
