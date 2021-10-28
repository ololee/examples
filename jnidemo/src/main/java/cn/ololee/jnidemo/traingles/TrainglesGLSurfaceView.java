package cn.ololee.jnidemo.traingles;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

public class TrainglesGLSurfaceView extends GLSurfaceView {

  public TrainglesGLSurfaceView(Context context, TrainglesRenderer renderer) {
    this(context,null,renderer);
  }

  public TrainglesGLSurfaceView(Context context, AttributeSet attrs,TrainglesRenderer renderer) {
    super(context, attrs);
    setEGLContextClientVersion(3);
    setEGLConfigChooser(8,8,8,8,16,8);
    setRenderer(renderer);
    setRenderMode(RENDERMODE_WHEN_DIRTY);
  }
}
