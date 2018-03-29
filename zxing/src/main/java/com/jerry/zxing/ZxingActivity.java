package com.jerry.zxing;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.WriterException;
import com.jerry.zxing.print.PrintManager;
import com.jerry.zxing.util.BarcodeUtil;
import com.jerry.zxing.util.EncodingHandler;
import com.jerry.zxing.util.Ticket;

public class ZxingActivity extends AppCompatActivity {
    private TextView mResultTv;
    private Button mPrintBtn;
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zxing);
        mResultTv = findViewById(R.id.zxing_resultTv);
        mPrintBtn = findViewById(R.id.zxing_printBtn);
        mImageView = findViewById(R.id.zxing_bitmapIv);
        mPrintBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Bitmap bitmap = Ticket.getOrderTicket().getTicketBitmap();
                Bitmap bitmap = null;
                try {
//                    bitmap = EncodingHandler.createCode128("13245678923", 350, 100);
                    bitmap= Ticket.getBarcode128().getTicketBitmap();
                    mImageView.setImageBitmap(bitmap);
                } catch (WriterException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
//                    new PrintManager<Object>().printOrder(getBaseContext(), null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }


}
