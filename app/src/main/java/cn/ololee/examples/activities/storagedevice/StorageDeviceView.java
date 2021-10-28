package cn.ololee.examples.activities.storagedevice;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;

public class StorageDeviceView extends View {

  public StorageDeviceView(Context context) {
    super(context);
  }

  public StorageDeviceView(Context context,
      @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  public StorageDeviceView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
  }
}
