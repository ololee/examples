package cn.ololee.jnidemo.shadertoy;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

/**
 * 着色器输入
 * uniform vec3      iResolution;           // viewport resolution (in pixels)
 * uniform float     iTime;                 // shader playback time (in seconds)
 * uniform float     iTimeDelta;            // render time (in seconds)
 * uniform int       iFrame;                // shader playback frame
 * uniform float     iChannelTime[4];       // channel playback time (in seconds)
 * uniform vec3      iChannelResolution[4]; // channel resolution (in pixels)
 * uniform vec4      iMouse;                // mouse pixel coords. xy: current (if MLB down), zw: click
 * uniform samplerXX iChannel0..3;          // input channel. XX = 2D/Cube
 * uniform vec4      iDate;                 // (year, month, day, time in seconds)
 * uniform float     iSampleRate;           // sound sample rate (i.e., 44100)
 */

public class ShadertoyGLSurfaceView extends GLSurfaceView {
  private ShadertouRenderer shadertouRenderer;

  public ShadertoyGLSurfaceView(Context context) {
    super(context);
    init();
  }

  public ShadertoyGLSurfaceView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  private void init() {
    if (shadertouRenderer == null) {
      shadertouRenderer = new ShadertouRenderer();
    }
    setEGLContextClientVersion(3);
    setEGLConfigChooser(8, 8, 8, 8, 16, 8);
    setRenderer(shadertouRenderer);
    setRenderMode(RENDERMODE_CONTINUOUSLY);
    initTouch();
  }


  private void initTouch(){
    setOnTouchListener((v,e)->{

      return true;
    });
  }
}
