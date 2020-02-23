package com.example.myapplication;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Build;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import java.util.Arrays;

public class SelectorTextView extends AppCompatTextView {
    public SelectorTextView(Context context) {
        this(context,null);
    }

    public SelectorTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,android.R.attr.textViewStyle);
    }

    public SelectorTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        sinit();
    }

    private void init() {
        //selector,分两种情况，一种是按压背景为颜色，一种是背景为图片
        // 颜色
        StateListDrawable stateListDrawable = getStateListDrawable(0xff00ff00, 0xffff0000);
        setBackgroundDrawable(stateListDrawable);


        //背景为drawable
//        Drawable drawable = getContext().getResources().getDrawable(R.drawable.ic_launcher_background);
//        StateListDrawable stateListDrawable = getStateListDrawable(drawable);
//        setBackgroundDrawable(stateListDrawable);
    }

    private void sinit() {
        //this is ripple
        ColorStateList colorStateList1 = ColorStateList.valueOf(Color.parseColor("#FF0000"));
        ColorStateList colorStateList = createColorStateList(Color.parseColor("#FF0000"),Color.parseColor("#00000000"));
        int[][] states = new int[][] {
                //new int[] { android.R.attr.state_enabled}, // enabled
                ///new int[] {-android.R.attr.state_enabled}, // disabled
                //new int[] {-android.R.attr.state_checked}, // unchecked
                new int[] { android.R.attr.state_pressed},  // pressed
                new int[]{android.R.attr.state_pressed}
        };

        int[] colors = new int[] {
                //Color.BLACK,
                //Color.RED,
                //Color.GREEN,
                Color.BLUE,
                Color.TRANSPARENT
        };

        ColorStateList myList = new ColorStateList(states, colors);
        //ColorStateList colorStateList = getContext().getResources().getColorStateList(R.color.hcolor);
        RippleDrawable rippleDrawable = new RippleDrawable(colorStateList,null,getRippleMask(0xffff0000));
        //rippleDrawable.setColor(colorStateList1);
        setBackgroundDrawable(rippleDrawable);
//        setBackgroundDrawable(getAdaptiveRippleDrawable(Color.parseColor("#88000000")
//                ,Color.parseColor("#FF0000")));
    }

    private ColorStateList createColorStateList(int pressed, int normal) {
        //状态
        int[][] states = new int[1][];
        //按下
        states[0] = new int[] {android.R.attr.state_pressed};

        //状态对应颜色值（按下，默认）
        int[] colors = new int[] { pressed};
        ColorStateList colorList = new ColorStateList(states, colors);
        return colorList;
    }

    public static Drawable getAdaptiveRippleDrawable(
            int normalColor, int pressedColor) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return new RippleDrawable(ColorStateList.valueOf(pressedColor),
                    null, null);
        } else {
            return getStateListDrawable(normalColor, pressedColor);
        }
    }

    private static Drawable getRippleMask(int color) {
        float[] outerRadii = new float[8];
        // 3 is radius of final ripple,
        // instead of 3 you can give required final radius
        Arrays.fill(outerRadii, 3);
        RoundRectShape r = new RoundRectShape(outerRadii, null, null);
        OvalShape ovalShape = new OvalShape();
        ShapeDrawable shapeDrawable = new ShapeDrawable(ovalShape);
        shapeDrawable.getPaint().setColor(0xFF000000);
        return shapeDrawable;
    }

    public static StateListDrawable getStateListDrawable(
            int normalColor, int pressedColor) {
        StateListDrawable states = new StateListDrawable();
        states.addState(new int[]{android.R.attr.state_pressed},
                new ColorDrawable(pressedColor));
        states.addState(new int[]{android.R.attr.state_focused},
                new ColorDrawable(pressedColor));
        states.addState(new int[]{android.R.attr.state_activated},
                new ColorDrawable(pressedColor));
        states.addState(new int[]{},
                new ColorDrawable(normalColor));
        return states;
    }

    public static StateListDrawable getStateListDrawable(Drawable pressedColor) {
        StateListDrawable states = new StateListDrawable();
        states.addState(new int[]{android.R.attr.state_pressed},
                pressedColor);
        states.addState(new int[]{android.R.attr.state_focused},
                pressedColor);
        states.addState(new int[]{android.R.attr.state_activated},
                pressedColor);
        return states;
    }

}
