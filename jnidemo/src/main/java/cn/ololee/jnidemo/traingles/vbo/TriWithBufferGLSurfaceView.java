package cn.ololee.jnidemo.traingles.vbo;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

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
    setRenderMode(RENDERMODE_CONTINUOUSLY);
    setListener();
  }

  private void setListener(){
    setOnTouchListener((v, event) -> {
      Log.e("move:","x:"+event.getX()+"");
      renderer.move(event.getX(),event.getY(),1);
      return true;
    });
  }
}
