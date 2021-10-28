package cn.ololee.filesearch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface FileConstants {

  /**
   * 文件类型采用组合类型方式
   * 对于int的低16位都是类型
   * 对于低16位中的高8位，是父类型，共有256种取值
   * 对于低16位中的低8位，是子类型，共有256种取值
   * 从子类型转到父类新子类型&0xff00 == 父类型
   */
  interface FileType {
    /**
     * 未知类型
     */
    int TYPE_UNKNOWN = 0x0000;

    /**
     * 文件类型
     */
    int TYPE_FOLDER = 0x0100;

    /**
     * 图片资源类
     */
    int TYPE_IMAGE = 0x0200;

    /**
     * 视频大类
     */
    int TYPE_VIDEO_OR_AUDIO = 0x0300;//父类型

    //子类型
    int TYPE_VIDEO = 0x0301;
    int TYPE_AUDIO = 0x0302;

    /**
     * 文档类型
     */
    int TYPE_DOC = 0x0400;//父类型

    //子类型
    int TYPE_DOC_PDF = 0x0401;
    int TYPE_DOC_PPT = 0x0402;
    int TYPE_DOC_EXCEL = 0x0403;
    int TYPE_DOC_WORD = 0x0404;
    int TYPE_DOC_TXT = 0x0405;
    int TYPE_DOC_WEB = 0x0406;

    /**
     * 压缩文件类型
     */
    int TYPE_COMPRESS = 0x0500;

    /**
     * 安卓可执行文件
     */
    int TYPE_EXECUTE = 0x0600;

    /**
     * 隐藏文件
     */
    int TYPE_HIDDEN = 0x0700;

    /**
     * 子类转父类的掩码
     */
    int PARENT_MASK = 0xff00;
  }


  interface FileSuffixes {
    List<String> IMAGE_SUFFIXES = Arrays.asList("png", "gif", "jpg", "jpeg", "bmp");
    List<String> AUDIO_SUFFIXES = Arrays.asList("aac", "wav", "ogg", "mid", "midi", "mp2", "mp3");
    List<String> VIDEO_SUFFIXES =
        Arrays.asList("mkv", "mov", "mp4", "mpeg", "mpg", "3gp", "avi", "flv", "webm");
    List<String> PDF_SUFFIXES = Arrays.asList("pdf");
    List<String> PPT_SUFFIXES = Arrays.asList("ppt","pptx");
    List<String> EXCEL_SUFFIXES = Arrays.asList("xls","xlsx");
    List<String> WORLD_SUFFIXES =Arrays.asList("doc","docx");
    List<String> TXT_SUFFIXES = Arrays.asList("txt", "log", "rtf", "xml");
    List<String> COMPRESS_SUFFIXES = Arrays.asList("zip","rar");
    List<String> EXECUTE_SUFFIXES = Arrays.asList("apk");
  }
}
