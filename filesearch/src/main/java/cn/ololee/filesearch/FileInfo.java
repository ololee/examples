package cn.ololee.filesearch;

import java.util.Objects;

public class FileInfo {
  private String path;
  private String name;
  private int type;

  public FileInfo() {
  }

  public FileInfo(String path, String name, int type) {
    this.path = path;
    this.name = name;
    this.type = type;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  @Override public String toString() {
    return "FileInfo{" +
        "path='" + path + '\'' +
        ", name='" + name + '\'' +
        ", type=" + type +
        '}';
  }

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof FileInfo)) return false;
    FileInfo fileInfo = (FileInfo) o;
    return getType() == fileInfo.getType() &&
        getPath().equals(fileInfo.getPath()) &&
        getName().equals(fileInfo.getName());
  }

  @Override public int hashCode() {
    return Objects.hash(getPath(), getName(), getType());
  }
}
