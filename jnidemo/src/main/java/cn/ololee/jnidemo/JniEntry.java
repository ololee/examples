package cn.ololee.jnidemo;

import android.graphics.Bitmap;

public class JniEntry {
  static {
    System.loadLibrary("jnidemo");
  }
  public static native String sayHello();

  public static native int open();

  static void dosomething(){
    System.out.println("_______________________");
  }

  public static native void guassBlur(Bitmap bitmap);


}
