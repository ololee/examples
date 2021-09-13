package cn.ololee.examples.utils;

public class StringUtils {

  public static String getNameFromClassName(String className){
    if(className==null||className.isEmpty()||!className.contains("."))
     throw new IllegalArgumentException("className must not be null");
    int lastIndexOfDot = className.lastIndexOf(".");
    return className.substring(lastIndexOfDot+1);
  }
}
