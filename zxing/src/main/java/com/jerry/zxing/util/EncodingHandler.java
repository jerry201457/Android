package com.jerry.zxing.util;

/**
 * Created by Administrator on 2018/2/7.
 *
 * @author LWH
 */

import android.graphics.Bitmap;
import android.graphics.Color;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.util.Hashtable;

/**
 * 生成二维码
 * Created by 江沨 on 2017/1/10.
 */

public class EncodingHandler {
    private static final int BLACK = 0xff000000;

    /**
     * LWH 2017-1-10
     * 把字符串生成二维码
     *
     * @param str            字符串
     * @param widthAndHeight 生成图片的像素  300*300
     * @throws WriterException
     */
    public static Bitmap createQRCode(String str, int widthAndHeight) throws WriterException {
        Hashtable<EncodeHintType, String> hints = new Hashtable<>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        BitMatrix matrix = new MultiFormatWriter().encode(str,
                BarcodeFormat.QR_CODE, widthAndHeight, widthAndHeight, hints);
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        int[] pixels = new int[width * height];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (matrix.get(x, y)) {
                    pixels[y * width + x] = BLACK;
                }
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    public static Bitmap createCode128(String str, int widthPix, int heightPix) throws WriterException {
        Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        BitMatrix matrix = new MultiFormatWriter().encode(str, BarcodeFormat.CODE_128, widthPix, heightPix, hints);
        int[] pixels = new int[widthPix * heightPix];
        for (int y = 0; y < heightPix; y++) {
            for (int x = 0; x < widthPix; x++) {
                if (matrix.get(x, y)) {
                    pixels[y * widthPix + x] = Color.RED;
                }
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(widthPix, heightPix, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, widthPix, 0, 0, widthPix, heightPix);
        return bitmap;
    }

    public static Bitmap createLineCode128(String str, int width, int height) throws WriterException {
        Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        BitMatrix matrix = new MultiFormatWriter().encode(str, BarcodeFormat.CODE_128, width, height, hints);
        width = matrix.getWidth();
        height = matrix.getHeight();
        int[] pixels = new int[width];
        for (int x = 0; x < width; x++) {
            if (matrix.get(x, 1)) {
                pixels[x] = BLACK;
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }
}