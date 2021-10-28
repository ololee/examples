#ifndef EXAMPLES_FILESEARCH_H
#define EXAMPLES_FILESEARCH_H

#include "common_header.h"

#define NDK_ENTRY(result_type, header)                               \
extern "C"                                                          \
  result_type JNICALL header {

#define NDK_END  }

#define FILE_INFO_CLAZZ "cn/ololee/filesearch/FileInfo"


#endif
