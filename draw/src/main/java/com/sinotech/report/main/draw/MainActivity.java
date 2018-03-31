package com.sinotech.report.main.draw;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class MainActivity extends AppCompatActivity {
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.main_nameBtn);
        mImageView = findViewById(R.id.main_iv1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), DrawActivity.class);
                startActivityForResult(intent, 10);
            }
        });
        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            Bitmap bitmap = extras.getParcelable("bitmap");
            mImageView.setImageBitmap(bitmap);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 10:
                if (resultCode == -1) {
                    Glide.with(this).load(DrawActivity.path1 + ".sign").
                            skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(mImageView);
                }
                break;
        }
    }

}
