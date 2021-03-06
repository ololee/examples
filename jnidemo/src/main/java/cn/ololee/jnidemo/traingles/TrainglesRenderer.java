package cn.ololee.jnidemo.traingles;


import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class TrainglesRenderer implements GLSurfaceView.Renderer {
  static {
    System.loadLibrary("jnidemo");
  }

  @Override public void onSurfaceCreated(GL10 gl, EGLConfig config) {
    surfaceCreate();
  }
 @Override public void onSurfaceChanged(GL10 gl, int width, int height) {
    surfaceChange(width,height);
    GLES30.glDepthRangef(0.5f,0.6f);
  }

  @Override public void onDrawFrame(GL10 gl) {
    drawFrame();
  }

  public  native void  surfaceCreate();

  public native void surfaceChange(int width,int height);

  public native void drawFrame();
}
