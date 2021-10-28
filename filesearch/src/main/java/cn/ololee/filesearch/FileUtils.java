package cn.ololee.filesearch;

import java.util.Arrays;
import java.util.List;

public class FileUtils {

  /**
   * 初始化 后缀的 前缀树
   */
  public static void init() {
    FileNativeUtils.initSuffixTire();
    FileNativeUtils.insertSuffixTires(FileConstants.FileSuffixes.AUDIO_SUFFIXES,
        FileConstants.FileType.TYPE_AUDIO);
    FileNativeUtils.insertSuffixTires(FileConstants.FileSuffixes.COMPRESS_SUFFIXES,
        FileConstants.FileType.TYPE_COMPRESS);
    FileNativeUtils.insertSuffixTires(FileConstants.FileSuffixes.EXCEL_SUFFIXES,
        FileConstants.FileType.TYPE_DOC_EXCEL);
    FileNativeUtils.insertSuffixTires(FileConstants.FileSuffixes.EXECUTE_SUFFIXES,
        FileConstants.FileType.TYPE_EXECUTE);
    FileNativeUtils.insertSuffixTires(FileConstants.FileSuffixes.IMAGE_SUFFIXES,
        FileConstants.FileType.TYPE_IMAGE);
    FileNativeUtils.insertSuffixTires(FileConstants.FileSuffixes.PDF_SUFFIXES,
        FileConstants.FileType.TYPE_DOC_PDF);
    FileNativeUtils.insertSuffixTires(FileConstants.FileSuffixes.PPT_SUFFIXES,
        FileConstants.FileType.TYPE_DOC_PPT);
    FileNativeUtils.insertSuffixTires(FileConstants.FileSuffixes.TXT_SUFFIXES,
        FileConstants.FileType.TYPE_DOC_TXT);
    FileNativeUtils.insertSuffixTires(FileConstants.FileSuffixes.VIDEO_SUFFIXES,
        FileConstants.FileType.TYPE_VIDEO);
    FileNativeUtils.insertSuffixTires(FileConstants.FileSuffixes.WORLD_SUFFIXES,
        FileConstants.FileType.TYPE_DOC_WORD);
  }

  public static List<FileInfo> getFileInfosInPath(String path, boolean singleFolder) {
    return FileNativeUtils.getFileInfosInPath(path, singleFolder);
  }

  public static void getFilesInDir(String path, int pagedSize,
      FileQueryCallback fileQueryCallback) {
    FileNativeUtils.getFileInfosWithCallback(path, true, pagedSize, fileQueryCallback);
  }

  public static void getAllFilesInDir(String path, int pagedSize,
      FileQueryCallback fileQueryCallback) {
    FileNativeUtils.getFileInfosWithCallback(path, false, pagedSize, fileQueryCallback);
  }

  public static void release() {
    //FileNativeUtils.
  }
}
