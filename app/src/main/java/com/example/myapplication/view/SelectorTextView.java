package com.example.myapplication.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

/**
 * 总纲：
 * 1、默认为开启ripple，而且当设置了normaldrawable或normalcolor的时候
 * 那么ripple的背景就为normal背景，如果只有normalcolor那么形状就需要看有没有
 * 设定，默认是oval，目前没有找到既支持ripple又支持selector的方法
 *
 * 2、如果不是ripple，那么就是selector模式，默认为oval形状。color
 */

public class SelectorTextView extends AppCompatTextView {
    public SelectorTextView(Context context) {
        this(context, null);
    }

    public SelectorTextView(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.textViewStyle);
    }

    public SelectorTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setClickable(true);
        new SelectorUtils().inject(this, context, attrs, defStyleAttr);
    }
}
