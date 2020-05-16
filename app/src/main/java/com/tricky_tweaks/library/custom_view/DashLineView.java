package com.tricky_tweaks.library.custom_view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.tricky_tweaks.library.R;

public class DashLineView extends View {

    static  public int ORIENTATION_HORIZONTAL = 0;
    static public int ORIENTATION_VERTICAL = 1;

    private Paint mPaint;
    private int orientation;

    public DashLineView(Context context, AttributeSet attributeSet) {
        super(context);
        int dashGap, dashLength, dashThickness;
        int color;

        TypedArray a = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.DashLineView, 0, 0);

        try {
            dashGap = a.getDimensionPixelSize(R.styleable.DashLineView_dashGap, 5);
            dashLength = a.getDimensionPixelSize(R.styleable.DashLineView_dashLength, 5);
            dashThickness = a.getDimensionPixelSize(R.styleable.DashLineView_dashThickness, 3);
            color = a.getColor(R.styleable.DashLineView_color, 0xff000000);
            orientation = a.getInt(R.styleable.DashLineView_android_orientation, ORIENTATION_HORIZONTAL);
        } finally {
            a.recycle();
        }

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(color);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(dashThickness);
        mPaint.setPathEffect(new DashPathEffect(new float[] { dashLength, dashGap, }, 0));
    }
    public DashLineView(Context context) {
        this(context, null);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (orientation == ORIENTATION_HORIZONTAL) {
            float center = getHeight() * .5f;
            canvas.drawLine(0, center, getWidth(), center, mPaint);
        } else {
            float center = getWidth() * .5f;
            canvas.drawLine(center, 0, center, getHeight(), mPaint);
        }
    }
}
