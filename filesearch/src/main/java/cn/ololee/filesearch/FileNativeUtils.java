package cn.ololee.filesearch;

import java.util.List;

class FileNativeUtils {
  static {
    System.loadLibrary("filesearch");
  }

  private static long suffixTirePtr;

  public static native void initSuffixTire();

  public static native void insertSuffixTire(String suffix, int type);

  public static native void insertSuffixTires(List<String> suffixs, int type);

  public static native int getSuffixType(String suffix);

  public static native String getSuffix(String pathOrName);

  /**
   * singleFolder 是否单层文件夹
   */
  public static native List<FileInfo> getFileInfosInPath(String path, boolean singleFolder);

  /**
   * singleFolder 是否单层文件夹
   */
  public static native void getFileInfosWithCallback(String path, boolean singleFolder,
      int pagedSize, FileQueryCallback fileQueryCallback);

}
