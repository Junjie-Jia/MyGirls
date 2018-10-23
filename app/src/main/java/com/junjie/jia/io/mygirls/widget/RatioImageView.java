package com.junjie.jia.io.mygirls.widget;

import android.content.Context;
import androidx.appcompat.widget.AppCompatImageView;
import android.util.AttributeSet;

/**
 * 宽度一定，高度自适应的ImageView
 */
public class RatioImageView extends AppCompatImageView {

    private int originalWidth;
    private int originalHeight;

    public RatioImageView(Context context) {
        super(context);
    }

    public RatioImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RatioImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setOriginalSize(int originalWidth, int originalHeight) {
        this.originalWidth = originalWidth;
        this.originalHeight = originalHeight;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (originalWidth > 0 && originalHeight > 0) {
            int width = MeasureSpec.getSize(widthMeasureSpec);

            float ratio = originalHeight / (float) originalWidth;

            int height = Math.round(width * ratio);

            setMeasuredDimension(width, height);
        }
    }
}
