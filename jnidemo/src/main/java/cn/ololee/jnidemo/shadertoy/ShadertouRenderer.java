package cn.ololee.jnidemo.shadertoy;

import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import java.util.Timer;
import java.util.TimerTask;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class ShadertouRenderer implements GLSurfaceView.Renderer {
  static {
    System.loadLibrary("jnidemo");
  }

  public static IData data = new IData();
  public static long startTime = 0;
  private Timer timer = new Timer();
  private TimerTask timerTask = new TimerTask() {
    @Override public void run() {
      data.currentTime = System.currentTimeMillis() * 1.0f;
      data.deltaTime = (System.currentTimeMillis() - startTime) * 1.0f;
    }
  };

  @Override public void onSurfaceCreated(GL10 gl, EGLConfig config) {
    shaderToySurfaceCreated();
    startTime = System.currentTimeMillis();
    timer.schedule(timerTask, 0, 1);
  }

  @Override public void onSurfaceChanged(GL10 gl, int width, int height) {
    data.width = width * 1.0f;
    data.height = height * 1.0f;
    shaderToySurfaceChanged(width, height);
  }

  @Override public void onDrawFrame(GL10 gl) {
    shaderToyDrawFrame();
  }

  public void move(float x, float y, float z) {
    data.touchX = x;
    data.touchY = y;
    data.touchPress = z;
  }

  public native void shaderToySurfaceCreated();

  public native void shaderToySurfaceChanged(int width, int height);

  public native void shaderToyDrawFrame();
}
