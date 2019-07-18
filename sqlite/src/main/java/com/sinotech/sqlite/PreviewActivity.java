package com.sinotech.sqlite;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

public class PreviewActivity extends AppCompatActivity {
    private final String TAG = EditText.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        final AutoEditText editText1 = findViewById(R.id.et1);
        final AutoEditTextView editText2 = findViewById(R.id.et2);
        editText1.setText("123");
        editText1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.i(TAG, "onTextChanged s=" + s.toString());
                editText2.setText("5555");
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.i(TAG, "afterTextChanged s=" + s.toString());
            }
        });
        editText2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editText1.setText("999");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
