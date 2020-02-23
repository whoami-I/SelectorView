package com.example.myapplication;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

public class MImageView extends AppCompatImageView {
    public MImageView(Context context) {
        this(context, null);
    }

    public MImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //setScaleType(ScaleType.MATRIX);
    }
}
