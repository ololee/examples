package cn.ololee.jnidemo.traingles.vbo;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

public class TriWithBufferGLSurfaceView extends GLSurfaceView {

  private TriWithBufferRenderer renderer = null;

  public TriWithBufferGLSurfaceView(Context context) {
    super(context);
    if (renderer == null) {
      renderer = new TriWithBufferRenderer();
    }
    setEGLContextClientVersion(3);
    setEGLConfigChooser(8, 8, 8, 8, 16, 8);
    setRenderer(renderer);
    setRenderMode(RENDERMODE_WHEN_DIRTY);
  }
}
