package cn.ololee.examples.activities.quadheader;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;

public class QuadHeaderView extends View {
  private Path path;
  private Paint paint, textPaint;

  public QuadHeaderView(Context context) {
    super(context);
    init();
  }

  public QuadHeaderView(Context context,
      @Nullable AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public QuadHeaderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  private void init() {
    path = new Path();
    path.moveTo(0, 0);
    path.addRect(0, 0, 1080, 607, Path.Direction.CCW);
    path.moveTo(0, 607);
    path.quadTo(540, 807, 1080, 607);
    path.close();
    paint = new Paint();
    paint.setAntiAlias(true);
    paint.setColor(Color.parseColor("#857857"));
    textPaint = new Paint();
    paint.setAntiAlias(true);
    textPaint.setColor(Color.WHITE);
    textPaint.setTextSize(50);
    LinearGradient linearGradient = new LinearGradient(0, 0, 100, 100,
        new int[] { Color.RED, Color.MAGENTA, Color.GREEN, Color.CYAN },
        new float[] { 0.0f, 0.25f, 0.5f, 0.75f },
        Shader.TileMode.MIRROR);
    paint.setShader(linearGradient);
  }

  @Override protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    canvas.drawPath(path, paint);
    canvas.drawText("镂空效果", 440, 550, textPaint);
  }
}
