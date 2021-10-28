package cn.ololee.jnidemo.sphere;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class SphereSurfaceView extends GLSurfaceView {

  private SphereRender mSphereRender;

  public SphereSurfaceView(Context context) {
    super(context);
    init(context);
  }

  public SphereSurfaceView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(context);
  }

  private void init(Context context) {
    mSphereRender = new SphereRender(context);
    setEGLContextClientVersion(3);
    setRenderer(mSphereRender);
    setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    setOnTouchListener(new OnTouchListener() {

      public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
          case MotionEvent.ACTION_DOWN://检测到点击事件时
          case MotionEvent.ACTION_MOVE:
            mSphereRender.rotate(20f, 0, 1, 0); //绕y轴旋转
        }
        return true;
      }
    });
  }
}
