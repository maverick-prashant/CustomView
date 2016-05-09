package com.mavericklabs.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by maverick8 on 6/5/16.
 */
public class CustomImageView extends View {
    Paint circlePaint, bitmapPaint;
    TextPaint textPaint;
    RectF semiCircle;
    Rect textBounds;
    Drawable drawable;
    Bitmap bitmap;
    String text;
    int textColor, circleColor;
    float strokeWidth, textSize;

    public CustomImageView(Context context) {
        super(context);
        init();
    }

    public CustomImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CustomImageView);
        drawable = array.getDrawable(R.styleable.CustomImageView_drawable);
        text = array.getString(R.styleable.CustomImageView_text);
        textColor = array.getColor(R.styleable.CustomImageView_textColor, Color.BLACK);
        circleColor = array.getColor(R.styleable.CustomImageView_circleColor, Color.BLACK);
        strokeWidth = array.getDimension(R.styleable.CustomImageView_strokeWidth, 4);
        textSize = array.getDimension(R.styleable.CustomImageView_textSize, 20);

        if (drawable != null)
            bitmap = ((BitmapDrawable) drawable).getBitmap();
        init();
        array.recycle();
    }

    private void init() {
        circlePaint = new Paint();
        circlePaint.setColor(circleColor);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeWidth(strokeWidth);
        circlePaint.setAntiAlias(true);

        textPaint = new TextPaint();
        textPaint.setStrokeWidth(strokeWidth);
        textPaint.setColor(textColor);
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(textSize);

        bitmapPaint = new Paint();
        bitmapPaint.setColor(Color.RED);
        bitmapPaint.setAntiAlias(true);
        textBounds = new Rect();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        int left = getLeft();
        int top = getTop();
        int right = getRight();
        int bottom = getBottom();

        Log.e("width", String.valueOf(width));
        Log.e("height", String.valueOf(height));
        Log.e("left", String.valueOf(left));
        Log.e("top", String.valueOf(top));
        Log.e("right", String.valueOf(right));
        Log.e("bottom", String.valueOf(bottom));
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) getLayoutParams();

        int lm = params.leftMargin;
        int tm = params.topMargin;
        int rm = params.rightMargin;
        int bm = params.bottomMargin;
        Log.e("lm", String.valueOf(lm));
        Log.e("tm", String.valueOf(tm));
        Log.e("rm", String.valueOf(rm));
        Log.e("bm", String.valueOf(bm));


        if (!TextUtils.isEmpty(text)) {
            textPaint.getTextBounds(text, 0, text.length(), textBounds);
            canvas.drawText(text, width / 2 - textBounds.width() / 2, height / 2 + textBounds.height() / 4, textPaint);
        }
        if (bitmap != null) {
            Bitmap bmp = getCroppedBitmap(bitmap,  width);
            canvas.drawBitmap(bmp, 0, 0 , circlePaint);
            canvas.drawCircle((bmp.getWidth() / 2)+0.7f, (bmp.getHeight() / 2)+0.7f, (bmp.getWidth() / 2), circlePaint);
            //canvas.drawCircle(width / 2, height / 2, width / 2 - (int)circlePaint.getStrokeWidth(), circlePaint);

        }

    }

    public Bitmap getCroppedBitmap(Bitmap bmp, int radius) {
        Bitmap sbmp;
        if (bmp.getWidth() != radius || bmp.getHeight() != radius)
            sbmp = Bitmap.createScaledBitmap(bmp, radius, radius, false);
        else
            sbmp = bmp;
        Bitmap output = Bitmap.createBitmap(sbmp.getWidth(),
                sbmp.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xffa19774;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, sbmp.getWidth(), sbmp.getHeight());

        paint.setAntiAlias(true);
        //foregroundPaint.setFilterBitmap(true);
        //foregroundPaint.setDither(true);
       // canvas.drawARGB(0, 0, 0, 0);
        canvas.drawCircle(sbmp.getWidth() / 2 + 0.7f, sbmp.getHeight() / 2 + 0.7f,
                sbmp.getWidth() / 2 + 0.1f, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(sbmp, rect, rect, paint);

        return output;
    }


}
