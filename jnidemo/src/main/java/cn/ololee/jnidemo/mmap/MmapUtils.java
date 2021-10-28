package cn.ololee.jnidemo.mmap;

public class MmapUtils {

  static {
    System.loadLibrary("jnidemo");
  }

  public static native void useMmap();
}
