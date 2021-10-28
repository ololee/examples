package cn.ololee.jnidemo.opengl;

import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import cn.ololee.jnidemo.TextureMapNative;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.GL_DEPTH_BUFFER_BIT;

public class MyGLRenderer implements GLSurfaceView.Renderer {
  @Override public void onSurfaceCreated(GL10 gl, EGLConfig config) {
    GLES30.glClearColor(0, 0, 1, 1);
    TextureMapNative.init();
  }

  @Override public void onSurfaceChanged(GL10 gl, int width, int height) {
    GLES30.glViewport(0, 0, width, height);
  }

  @Override public void onDrawFrame(GL10 gl) {
    //GLES30.glClearColor(1, 0, 0, 1);

    TextureMapNative.draw();
    //TextureMapNative.testDraw();
    //GLES30.glClear(GL_DEPTH_BUFFER_BIT | GL_COLOR_BUFFER_BIT);
  }
}
