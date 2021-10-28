package cn.ololee.filesearch;

import java.util.List;

public interface FileQueryCallback {
  void update(List<FileInfo> fileInfoList);
}
