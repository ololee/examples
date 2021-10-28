package cn.ololee.examples.activities.rotate;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import androidx.annotation.ColorRes;
import androidx.annotation.Nullable;
import cn.ololee.examples.R;

public class RotateProgressView extends View {
  private float radius = 320;
  private float maxR = 80;
  private float minR = 40;
  private float cx = radius + maxR, cy = radius + maxR;
  private int circleCount = 8;
  private float rStep = (maxR - minR) / circleCount;
  private int color = Color.GRAY;
  private Paint paint = new Paint();
  private ValueAnimator valueAnimator;

  public RotateProgressView(Context context) {
    super(context);
    init(context, null, 0);
  }

  public RotateProgressView(Context context,
      @Nullable AttributeSet attrs) {
    super(context, attrs);
    init(context, attrs, 0);
  }

  public RotateProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(context, attrs, defStyleAttr);
  }

  private void init(Context context, AttributeSet attrs, int defStyleAttr) {
    if (attrs != null) {
      TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RotateProgressView);
      color = typedArray.getColor(R.styleable.RotateProgressView_color, color);
      circleCount = typedArray.getInteger(R.styleable.RotateProgressView_count, circleCount);
      maxR = typedArray.getDimension(R.styleable.RotateProgressView_maxR, maxR);
      minR = typedArray.getDimension(R.styleable.RotateProgressView_minR, minR);
      radius = typedArray.getDimension(R.styleable.RotateProgressView_r, radius);
      typedArray.recycle();
    }
    valueAnimator = ValueAnimator.ofFloat(1.0f);
    valueAnimator.setDuration(1600);
    valueAnimator.setInterpolator(new LinearInterpolator());
    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
      @Override public void onAnimationUpdate(ValueAnimator animation) {
        Float value = (Float) valueAnimator.getAnimatedValue();
        setRotation((float) (360 * value));
        invalidate();
      }
    });
    valueAnimator.setRepeatCount(-1);
    valueAnimator.addListener(new AnimatorListenerAdapter() {
      @Override public void onAnimationCancel(Animator animation) {
        super.onAnimationCancel(animation);
      }

      @Override public void onAnimationEnd(Animator animation) {
        super.onAnimationEnd(animation);
      }

      @Override public void onAnimationRepeat(Animator animation) {
        super.onAnimationRepeat(animation);
      }

      @Override public void onAnimationStart(Animator animation) {
        super.onAnimationStart(animation);
      }

      @Override public void onAnimationPause(Animator animation) {
        super.onAnimationPause(animation);
      }

      @Override public void onAnimationResume(Animator animation) {
        super.onAnimationResume(animation);
      }
    });
    valueAnimator.start();
    paint.setColor(color);
  }

  public void setRadius(float radius) {
    this.radius = radius;
  }

  public void setMaxR(float maxR) {
    this.maxR = maxR;
  }

  public void setMinR(float minR) {
    this.minR = minR;
  }

  public void setCircleCount(int count) {
    this.circleCount = count;
  }

  public void setColor(@ColorRes int color) {
    this.color = color;
  }

  @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(getMeasure(widthMeasureSpec), getMeasure(heightMeasureSpec));
  }

  public int getMeasure(int spec) {
    int size = MeasureSpec.getSize(spec);
    int mode = MeasureSpec.getMode(spec);
    switch (mode) {
      case MeasureSpec.AT_MOST:
        size = (int) cx*2;
        break;
    }
    return MeasureSpec.makeMeasureSpec(size, mode);
  }

  //public void

  @Override protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    Log.e("ololeeTAG", "onDraw: ");
    canvas.translate(cx, cy);
    double rad = Math.PI / 4;
    for (int i = 0; i < circleCount; i++) {
      canvas.drawCircle((float) Math.cos(i * rad) * radius, (float) Math.sin(i * rad) * radius,
          maxR - rStep * i, paint);
    }
  }
}
