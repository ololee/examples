package cn.ololee.examples.activities.watch;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import androidx.annotation.Nullable;
import java.util.Map;

public class WatchThermometerView extends View {
  private Paint paint = new Paint();
  private int centerX = 540;
  private int centerY = 960;
  private float radius = 400.0f;
  private Path circlePath = new Path();
  private float bootomAngle = 90.0f;
  private float startAngle = (float) (90 + bootomAngle / 2);
  private Path progressPath = new Path();
  private float progress = 0.0f;
  private Paint progressPaint = new Paint();
  private Paint progressCursorPaint = new Paint();
  private int bigScaleCount = 10;
  private float[] scalePoints;

  private Path[] scaleTextPaths;
  private Paint scaleTextPaint;
  private float deltaX = 0, deltaY = 0;

  public WatchThermometerView(Context context) {
    super(context);
    init();
  }

  public WatchThermometerView(Context context,
      @Nullable AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public WatchThermometerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  public void init() {
    paint.setAntiAlias(true);
    paint.setColor(Color.GRAY);
    paint.setStrokeWidth(5);
    paint.setStyle(Paint.Style.STROKE);

    progressPaint.setAntiAlias(true);
    progressPaint.setColor(Color.RED);
    progressPaint.setStrokeWidth(8);
    progressPaint.setStyle(Paint.Style.STROKE);

    progressCursorPaint.setAntiAlias(true);
    progressCursorPaint.setColor(Color.GREEN);
    progressCursorPaint.setStrokeWidth(5);

    scalePoints = new float[(bigScaleCount - 1) * 4];

    float currentAngle = startAngle;
    float perAngle = (360 - bootomAngle) / bigScaleCount;
    for (int i = 1; i < bigScaleCount; i++) {
      currentAngle = startAngle + perAngle * i;
      float rad = (float) Math.toRadians(currentAngle);
      float startX = (float) (radius * Math.cos(rad) + centerX);
      float startY = (float) (radius * Math.sin(rad) + centerY);
      float endX = (float) ((radius - 50) * Math.cos(rad) + centerX);
      float endY = (float) ((radius - 50) * Math.sin(rad) + centerY);
      scalePoints[(i - 1) * 4 + 0] = startX;
      scalePoints[(i - 1) * 4 + 1] = startY;
      scalePoints[(i - 1) * 4 + 2] = endX;
      scalePoints[(i - 1) * 4 + 3] = endY;
    }

    deltaX = (float) (radius * Math.cos(Math.toRadians(perAngle)));
    deltaY = (float) (radius * Math.sin(Math.toRadians(perAngle)));
    scaleTextPaint = new Paint();
    scaleTextPaint.setAntiAlias(true);
    scaleTextPaint.setTextSize(50);

    circlePath.moveTo(centerX, centerY);
    circlePath.addArc(centerX - radius,
        centerY - radius,
        centerX + radius,
        centerY + radius,
        startAngle,
        (float) (360 - bootomAngle)
    );
    circlePath.lineTo(centerX, centerY);
    circlePath.close();

    scaleTextPaths = new Path[bigScaleCount + 1];
    for (int i = 0; i <= bigScaleCount; i++) {
      scaleTextPaths[i ] = new Path();
      scaleTextPaths[i].moveTo(centerX, centerY);
      scaleTextPaths[i].addArc(centerX - radius,
          centerY - radius,
          centerX + radius,
          centerY + radius,
          startAngle-3.5f+perAngle*i,
          (float) (perAngle));
    }

    progressPath.addArc(centerX - radius,
        centerY - radius,
        centerX + radius,
        centerY + radius,
        startAngle,
        getProgressAngle()
    );
  }

  @Override protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    canvas.drawPath(circlePath, paint);
    canvas.drawPath(progressPath, progressPaint);
    drawCenterCircle(canvas);

    for (int i = 0; i <= bigScaleCount; i++) {
      canvas.drawTextOnPath(i*10 + "", scaleTextPaths[i], 0, -20, scaleTextPaint);
    }
    //canvas.drawTextOnPath(scaleText, scaleTextPath, 0, 0, scaleTextPaint);
  }

  private void drawCenterCircle(Canvas canvas) {
    float angle = startAngle + getProgressAngle();
    double rad = Math.toRadians(angle);
    float cy = (float) (radius * Math.sin(rad));
    float cx = (float) (radius * Math.cos(rad));
    canvas.drawCircle(centerX + cx, centerY + cy, 15, progressCursorPaint);
    //canvas.drawText
    canvas.drawLines(scalePoints, progressCursorPaint);
  }

  private float getProgressAngle() {
    return (360 - bootomAngle) * progress;
  }

  public void updateProgress(float progress) {
    this.progress = progress;
    if (progress < 0.8) {
      progressPaint.setColor(Color.GREEN);
    } else {
      progressPaint.setColor(Color.RED);
    }
    progressCursorPaint.setColor(progressPaint.getColor());
    progressPath.reset();
    progressPath.addArc(centerX - radius,
        centerY - radius,
        centerX + radius,
        centerY + radius,
        startAngle,
        getProgressAngle()
    );
    invalidate();
  }
}
