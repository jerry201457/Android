package com.jerry.speech;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private EditText mContentEt;
    private Button mConvertBtn;
    TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContentEt = findViewById(R.id.content_et);
        mConvertBtn = findViewById(R.id.convert_btn);
        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                // 判断是否转化成功
                if (status == TextToSpeech.SUCCESS){
                    //默认设定语言为中文，原生的android貌似不支持中文。
                    int result = textToSpeech.setLanguage(Locale.CHINESE);
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
                        Toast.makeText(MainActivity.this, "不支持中文!", Toast.LENGTH_SHORT).show();
                    }else{
                        //不支持中文就将语言设置为英文
                        textToSpeech.setLanguage(Locale.US);
                    }
                }
            }
        });
        mConvertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textToSpeech.speak(mContentEt.getText().toString(),TextToSpeech.QUEUE_FLUSH,null);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
}
