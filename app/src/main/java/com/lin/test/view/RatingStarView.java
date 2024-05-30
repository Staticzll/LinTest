package com.lin.test.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.lin.test.R;

public class RatingStarView extends View {
    private Drawable starDrawable;
    private int numStars;
    private float rating;
    private Paint paint;

    public RatingStarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public RatingStarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RatingStarView);
        starDrawable = typedArray.getDrawable(R.styleable.RatingStarView_starDrawable);
        numStars = typedArray.getInt(R.styleable.RatingStarView_numStars, 5); // 默认5颗星
        rating = typedArray.getFloat(R.styleable.RatingStarView_rating, 0); // 默认评分为0
        typedArray.recycle();

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (starDrawable != null) {
            int starWidth = starDrawable.getIntrinsicWidth();
            int starHeight = starDrawable.getIntrinsicHeight();
            int xPos = getPaddingLeft();
            for (int i = 0; i < numStars; i++) {
                if (i < rating) {
                    // 画完整的星星
                    starDrawable.setBounds(xPos, getPaddingTop(), xPos + starWidth, getPaddingTop() + starHeight);
                    starDrawable.draw(canvas);
                } else {
                    // 根据评分画部分星星（可选实现）
                    // 这里可以添加逻辑来绘制部分填充的星星
                }
                xPos += starWidth + getPaddingRight(); // 根据需要调整星星之间的间距
            }
        }
    }

    // 可以在这里添加设置评分的方法
    public void setRating(float rating) {
        this.rating = rating;
        invalidate(); // 请求重绘View
    }
}