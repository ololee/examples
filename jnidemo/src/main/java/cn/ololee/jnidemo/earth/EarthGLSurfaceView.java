package cn.ololee.jnidemo.earth;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class EarthGLSurfaceView extends GLSurfaceView implements View.OnTouchListener {
  private EarthRenderer earthRenderer;
  private float lastX = 0, lastY = 0;

  public EarthGLSurfaceView(Context context) {
    super(context);
    init(context);
  }

  public EarthGLSurfaceView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(context);
  }

  private void init(Context context) {
    earthRenderer = new EarthRenderer(context);
    this.setEGLContextClientVersion(3);
    setEGLConfigChooser(8, 8, 8, 8, 16, 8);
    this.setRenderer(earthRenderer);
    // 设置渲染模式为主动渲染
    this.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    MatrixState.rotate(90f, 1, 0, 0);//绕y轴旋转

    this.setOnTouchListener(this);
  }

  public boolean onTouch(View v, MotionEvent event) {
    // TODO Auto-generated method stub
    switch (event.getAction()) {
      case MotionEvent.ACTION_DOWN://检测到点击事件时
        lastX = event.getX();
        lastY = event.getY();
      case MotionEvent.ACTION_MOVE:
        float moveX = event.getX() - lastX;
        float moveY = event.getY() - lastY;
        if(Math.abs(moveX)>Math.abs(moveY)){
          MatrixState.rotate((moveX) / 20f, 0, 0, 1);//绕Z轴旋转
        }else {
          MatrixState.rotate((-moveY) / 20f, 1, 0, 0);//绕x轴旋转
        }
    }
    return true;
  }
}
