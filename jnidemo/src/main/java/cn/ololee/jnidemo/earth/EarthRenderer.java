package cn.ololee.jnidemo.earth;

import android.content.Context;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.util.Log;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class EarthRenderer implements GLSurfaceView.Renderer {
  private Context context;
  private Earth earth;

  public EarthRenderer(Context context) {
    this.context = context;
  }

  @Override public void onSurfaceCreated(GL10 gl, EGLConfig config) {
    GLES30.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
    //打开深度检测
    GLES30.glEnable(GLES30.GL_DEPTH_TEST);
    //打开背面剪裁
    GLES30.glEnable(GLES30.GL_CULL_FACE);
    earth = new Earth(context);
  }

  @Override public void onSurfaceChanged(GL10 gl, int width, int height) {
    GLES30.glViewport(0, 0, width, height);
    float ratio = (float) width / height;
    // 调用此方法计算产生透视投影矩阵
    MatrixState.setProjectFrustum(-ratio, ratio, -1, 1, 20, 100);
    // 调用此方法产生摄像机9参数位置矩阵
    MatrixState.setCamera(0, 0, 20, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
  }

  public void onDrawFrame(GL10 gl) {
    //清除深度缓冲与颜色缓冲
    GLES30.glClear(GLES30.GL_DEPTH_BUFFER_BIT | GLES30.GL_COLOR_BUFFER_BIT);
    earth.draw();
  }
}
