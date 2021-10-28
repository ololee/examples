package cn.ololee.jnidemo.opengl;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

public class TextureMapSurfaceView extends GLSurfaceView {
  private MyGLRenderer renderer;

  public TextureMapSurfaceView(Context context, MyGLRenderer renderer) {
    this(context, null, renderer);
  }

  public TextureMapSurfaceView(Context context, AttributeSet attrs, MyGLRenderer myGLRenderer) {
    super(context, attrs);
    this.renderer = myGLRenderer;
    setEGLContextClientVersion(3);
    setEGLConfigChooser(8, 8, 8, 8, 16, 8);
    setRenderer(renderer);
    setRenderMode(RENDERMODE_WHEN_DIRTY);
  }
}
