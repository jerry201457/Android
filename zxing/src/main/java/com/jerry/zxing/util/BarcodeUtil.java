package com.jerry.zxing.util;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.util.Hashtable;

/**
 * Created by Administrator on 2018/2/7.
 *
 * @author LWH
 */

public class BarcodeUtil {
    public static Bitmap createCode128(String str, int widthPix, int heightPix) throws WriterException {
        Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(str, BarcodeFormat.CODE_128, widthPix, heightPix, hints);
        int[] pixels = new int[widthPix * heightPix];
        for (int y = 0; y < heightPix; y++) {
            for (int x = 0; x < widthPix; x++) {
                if (bitMatrix.get(x, y)) {
                    pixels[y * widthPix + x] = Color.RED;
                }
            }
        }
        Matrix matrix = new Matrix();
        // 缩放原图
        matrix.postScale(1f, 1f);
        // 向左旋转45度，参数为正则向右旋转
        matrix.postRotate(-90);
        Bitmap bitmap = Bitmap.createBitmap(widthPix, heightPix, Bitmap.Config.ARGB_8888);
        Bitmap dstbmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), 500, matrix, true);
        dstbmp.setPixels(pixels, 0, widthPix, 0, 0, widthPix, heightPix);
        return dstbmp;
    }

}
