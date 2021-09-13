package cn.ololee.examples.activities.imagesplice;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;

public class ImageSpliceView extends View {
  private Path path1 = new Path();
  private Path path2 = new Path();
  private Paint paint = new Paint();
  private int scale = 2;
  private Bitmap bmp = null;
  PorterDuffXfermode porterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.XOR);
  private boolean direct = false;

  public ImageSpliceView(Context context) {
    super(context);
    init(context, null, 0);
  }

  public ImageSpliceView(Context context,
      @Nullable AttributeSet attrs) {
    super(context, attrs);
    init(context, attrs, 0);
  }

  public ImageSpliceView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(context, attrs, defStyleAttr);
  }

  private void init(Context context, AttributeSet attrs, int defStyleAttr) {
    initPath();
    initPaint();
  }

  private void initPath() {
    path1.lineTo(330 * scale, 0);
    path1.lineTo(210 * scale, 270 * scale);
    path1.lineTo(0, 270 * scale);
    path1.close();
    path2.moveTo(330 * scale, 0);
    path2.lineTo(540 * scale, 0);
    path2.lineTo(540 * scale, 270 * scale);
    path2.lineTo(210 * scale, 270 * scale);
    path2.close();
  }

  private void initPaint() {
    paint.setColor(Color.RED);
    paint.setAntiAlias(true);
  }

  public void setBmp(Bitmap bmp, boolean left) {
    this.bmp = bmp;
    this.direct = left;
    invalidate();
  }

  @Override protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    //canvas.drawBitmap();
    if (bmp != null) {

      canvas.clipPath(direct ? path1 : path2, Region.Op.INTERSECT);
      canvas.drawBitmap(bmp, 0, 0, paint);
      paint.setXfermode(porterDuffXfermode);
    }
    //paint.setColor(Color.TRANSPARENT);
    //canvas.drawPath(path1, paint);
    //paint.setColor(Color.parseColor("#8800ff00"));
    //canvas.drawPath(path2, paint);
  }
}
