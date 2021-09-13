package cn.ololee.jnidemo;

public class JniEntry {
  static {
    System.loadLibrary("jnidemo");
  }
  public static native String sayHello();
}
