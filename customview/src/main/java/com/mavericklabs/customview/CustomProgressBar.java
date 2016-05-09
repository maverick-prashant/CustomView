package com.mavericklabs.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by maverick8 on 7/5/16.
 */
public class CustomProgressBar extends View {

    Paint foregroundPaint, backgroundPaint;
    TextPaint textPaint;
    RectF oval;
    int progress = 25;
    int max = 100;
    Rect textBounds;
    private String text = "100%";

    public CustomProgressBar(Context context) {
        super(context);
        init();
    }

    public CustomProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        backgroundPaint.setColor(Color.GRAY);
        backgroundPaint.setStyle(Paint.Style.STROKE);
        backgroundPaint.setStrokeWidth(4);

        foregroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        foregroundPaint.setColor(Color.RED);
        foregroundPaint.setStyle(Paint.Style.STROKE);
        foregroundPaint.setStrokeWidth(6);

        textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.GREEN);

        textBounds = new Rect();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int desiredWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,100,getResources().getDisplayMetrics());;
        int desiredHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,100,getResources().getDisplayMetrics());

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;

        //Measure Width
        if (widthMode == MeasureSpec.EXACTLY) {
            //Must be this size
            width = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            width = Math.min(desiredWidth, widthSize);
        } else {
            //Be whatever you want
            width = desiredWidth;
        }

        //Measure Height
        if (heightMode == MeasureSpec.EXACTLY) {
            //Must be this size
            height = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            height = Math.min(desiredHeight, heightSize);
        } else {
            //Be whatever you want
            height = desiredHeight;
        }

        //MUST CALL THIS
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();

        width = Math.min(width, height);
        height = Math.min(width, height);
        oval = new RectF(0 + foregroundPaint.getStrokeWidth(), 0 + foregroundPaint.getStrokeWidth(), width - foregroundPaint.getStrokeWidth(), height - foregroundPaint.getStrokeWidth());
        canvas.drawArc(oval, 0, 360, false, backgroundPaint);
        int angle = 360 * progress / max;
        canvas.drawArc(oval, 270, angle, false, foregroundPaint);

        float desiredTextSize = width*10/100;
        textPaint.setTextSize(desiredTextSize);
        textPaint.getTextBounds(text, 0, text.length(), textBounds);
        canvas.drawText(text, width / 2 - textBounds.width() / 2, height / 2 + textBounds.height() / 4, textPaint);
    }
}
