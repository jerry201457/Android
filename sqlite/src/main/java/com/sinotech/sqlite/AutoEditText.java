package com.sinotech.sqlite;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.EditText;

import java.util.Objects;

public class AutoEditText extends android.support.v7.widget.AppCompatEditText {
    public AutoEditText(Context context) {
        super(context);
    }

    public AutoEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setText(String text){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if(!Objects.requireNonNull(getText()).toString().equals(text)){
                super.setText(text);
            }
        }
    }
}
