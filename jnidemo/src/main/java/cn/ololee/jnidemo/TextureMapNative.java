package cn.ololee.jnidemo;

public class TextureMapNative {
  static {
    System.loadLibrary("jnidemo");
  }

  public static long bitmapPtr;

  public static native void transferBitmap(int width,int height,byte[] bytes);

  public static native void releaseJNIVar();

  public static native void init();

  public static native void draw();

  public static native void testDraw();
}
