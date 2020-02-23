package com.example.myapplication.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.RoundRectShape;
import android.graphics.drawable.shapes.Shape;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.ColorInt;

import com.example.myapplication.R;

public class SelectorUtils {
    private static final int RECTANGLE = 1;
    private static final int OVAL = 2;
    private static final int mDefaultShape = OVAL;
    private static final int NO_RADIUS = 0;
    private static final int mDefaultColor = 0x00000000;
    private static final int mDefaultRippleColor = 0x55000000;

    private int mNormalShape = mDefaultShape;
    private int mPressedShape = NO_RADIUS;
    private int mSelectedShape = NO_RADIUS;

    private int mNormalColor = mDefaultColor;
    private int mPressedColor = mNormalColor;
    private int mSelectedColor = mNormalColor;
    private int mRippleColor = mDefaultRippleColor;

    private Drawable mNormalDrawable;
    private Drawable mPressedDrawable;
    private Drawable mSelectedDrawable;

    private int mRadius = NO_RADIUS;
    private int mLeftTopRadius = NO_RADIUS;
    private int mLeftBottomRadius = NO_RADIUS;
    private int mRightTopRadius = NO_RADIUS;
    private int mRightBottomRadius = NO_RADIUS;

    private boolean mRipple = true;
    private boolean mIsBounded = true;

    public void inject(View view, Context context, AttributeSet attrs, int defStyleAttr) {
        final TypedArray a = context.obtainStyledAttributes(
                attrs, R.styleable.Selector_Attr, defStyleAttr, 0);
        int count = a.getIndexCount();
        for (int i = 0; i < count; ++i) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.Selector_Attr_normalColor:
                    mNormalColor = a.getColor(attr, mNormalColor);
                    break;
                case R.styleable.Selector_Attr_pressedColor:
                    mPressedColor = a.getColor(attr, mPressedColor);
                    break;
                case R.styleable.Selector_Attr_selectedColor:
                    mSelectedColor = a.getColor(attr, mSelectedColor);
                    break;
                case R.styleable.Selector_Attr_rippleColor:
                    mRippleColor = a.getColor(attr, mRippleColor);
                    break;
                case R.styleable.Selector_Attr_normalShape:
                    mNormalShape = a.getInteger(attr, mDefaultShape);
                    break;
                case R.styleable.Selector_Attr_pressedShape:
                    mPressedShape = a.getInteger(attr, NO_RADIUS);
                    break;
                case R.styleable.Selector_Attr_selectedShape:
                    mSelectedShape = a.getInteger(attr, NO_RADIUS);
                    break;
                case R.styleable.Selector_Attr_normalDrawable:
                    mNormalDrawable = a.getDrawable(attr);
                    break;
                case R.styleable.Selector_Attr_pressedDrawable:
                    mPressedDrawable = a.getDrawable(attr);
                    break;
                case R.styleable.Selector_Attr_selectedDrawable:
                    mSelectedDrawable = a.getDrawable(attr);
                    break;
                case R.styleable.Selector_Attr_radius:
                    mRadius = a.getDimensionPixelSize(attr, mRadius);
                    break;
                case R.styleable.Selector_Attr_leftTopCornerRadius:
                    mLeftTopRadius = a.getDimensionPixelSize(attr, mLeftTopRadius);
                    break;
                case R.styleable.Selector_Attr_leftBottomCornerRadius:
                    mLeftBottomRadius = a.getDimensionPixelSize(attr, mLeftBottomRadius);
                    break;
                case R.styleable.Selector_Attr_rightTopCornerRadius:
                    mRightTopRadius = a.getDimensionPixelSize(attr, mRightTopRadius);
                    break;
                case R.styleable.Selector_Attr_rightBottomCornerRadius:
                    mRightBottomRadius = a.getDimensionPixelSize(attr, mRightBottomRadius);
                    break;
                case R.styleable.Selector_Attr_ripple:
                    mRipple = a.getBoolean(attr, mRipple);
                    break;
                case R.styleable.Selector_Attr_isBounded:
                    mIsBounded = a.getBoolean(attr, mIsBounded);
                    break;
                default:
                    break;
            }
        }
        if (mPressedShape == NO_RADIUS) {
            mPressedShape = mNormalShape;
        }
        if (mSelectedShape == NO_RADIUS) {
            mSelectedShape = mNormalShape;
        }
        if (mPressedColor == mDefaultColor) {
            mPressedColor = mNormalColor;
        }
        if (mSelectedColor == mDefaultColor) {
            mSelectedColor = mNormalColor;
        }
        a.recycle();
        //如果为ripple，那么pressed状态只能为color drawable无效，normal和select可以为drawable
        if (mRipple) {
            ShapeDrawable shapeDrawable;
            if (mPressedShape == OVAL) {
                shapeDrawable = new ShapeDrawable(new OvalShape());
            } else {
                float[] outerRadii = {mLeftTopRadius, mLeftTopRadius, mRightTopRadius, mRightTopRadius,
                        mRightBottomRadius, mRightBottomRadius, mLeftBottomRadius, mLeftBottomRadius};
                shapeDrawable = new ShapeDrawable(new RoundRectShape(outerRadii, null, null));
            }
            shapeDrawable.getPaint().setColor(0xFF000000);
            boolean isNormalBackgroundTransparent = false;
            if (mNormalDrawable == null && mNormalColor == 0) {
                isNormalBackgroundTransparent = true;
            }
            mNormalDrawable = getDrawable(mNormalDrawable, mNormalColor, mNormalShape);
            mPressedDrawable = getDrawable(null, mPressedColor, mPressedShape);
            mSelectedDrawable = getDrawable(mSelectedDrawable, mSelectedColor, mSelectedShape);
            //如果mNormalDrawable为透明，那么此时通过maskDrawable去限制边界
            RippleDrawable rippleDrawable = null;
            if (isNormalBackgroundTransparent) {
                //判断是否有界
                Drawable maskDrawable = null;
                if (mIsBounded) {
                    if (mNormalDrawable instanceof ShapeDrawable) {
                        Shape shape = ((ShapeDrawable) mNormalDrawable).getShape();
                        ShapeDrawable drawable = new ShapeDrawable(shape);
                        drawable.getPaint().setColor(0xff000000);
                        maskDrawable = drawable;
                    }
                } else {
                    maskDrawable = null;
                }
                rippleDrawable = new RippleDrawable(ColorStateList.valueOf(mRippleColor),
                        null, maskDrawable);
            } else {
                rippleDrawable = new RippleDrawable(ColorStateList.valueOf(mRippleColor),
                        mNormalDrawable, null);
            }

            view.setBackgroundDrawable(rippleDrawable);
        } else {
            //如果不是ripple，那么就根据radius和color获取三种情况的drawable
            mNormalDrawable = getDrawable(mNormalDrawable, mNormalColor, mNormalShape);
            mPressedDrawable = getDrawable(mPressedDrawable, mPressedColor, mPressedShape);
            mSelectedDrawable = getDrawable(mSelectedDrawable, mSelectedColor, mSelectedShape);
            StateListDrawable stateListDrawable = getStateListDrawable(mNormalDrawable, mPressedDrawable, mSelectedDrawable);
            view.setBackgroundDrawable(stateListDrawable);
        }
    }

    /**
     * getStateDrawable according to color
     *
     * @param
     * @param
     * @return
     */
    public static StateListDrawable getStateListDrawable(
            Drawable normalDrawable, Drawable pressedDrawable, Drawable selectedDrawable) {
        StateListDrawable states = new StateListDrawable();
        states.addState(new int[]{android.R.attr.state_pressed},
                pressedDrawable);
        states.addState(new int[]{android.R.attr.state_selected},
                selectedDrawable);
        states.addState(new int[]{},
                normalDrawable);
        return states;
    }

    public static StateListDrawable getStateListDrawable(
            Drawable normalDrawable, Drawable selectedDrawable) {
        StateListDrawable states = new StateListDrawable();
        states.addState(new int[]{android.R.attr.state_selected},
                selectedDrawable);
        states.addState(new int[]{},
                normalDrawable);
        return states;
    }

    /**
     * 根据drawable color 和 shape选择最终获取到的drawable
     * 如果drawable不为null，那么只返回drawable
     * 如果drawable为null，那么根据shape和color去确定返回的drawable
     *
     * @param drawable
     * @param color
     * @param shape
     * @return
     */
    public Drawable getDrawable(Drawable drawable, @ColorInt int color, int shape) {
        if (drawable == null) {
            //判断是圆形
            ShapeDrawable shapeDrawable;
            if (shape == OVAL) {
                shapeDrawable = new ShapeDrawable(new OvalShape());
                shapeDrawable.getPaint().setColor(color);
            } else {
                float[] outerRadii;
                if (mRadius != NO_RADIUS) {
                    outerRadii = new float[]{mRadius, mRadius, mRadius, mRadius
                            , mRadius, mRadius, mRadius, mRadius};
                } else {
                    outerRadii = new float[]{mLeftTopRadius, mLeftTopRadius, mRightTopRadius, mRightTopRadius,
                            mRightBottomRadius, mRightBottomRadius, mLeftBottomRadius, mLeftBottomRadius};
                }
                shapeDrawable = new ShapeDrawable(new RoundRectShape(outerRadii, null, null));
                shapeDrawable.getPaint().setColor(color);
            }
            drawable = shapeDrawable;
        }
        return drawable;
    }
}
