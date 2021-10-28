#ifndef EXAMPLES_FILEUTILS_H
#define EXAMPLES_FILEUTILS_H

#include "../common_header.h"
#include "../file/FileInfo.h"
#include "../file/SuffixTire.h"
#include "../file/SuffixNode.h"
#include "SuffixUtils.h"

typedef struct dirent64* Dirent;

list<FileInfo> getFilesInDir(SuffixTire* suffixTirePtr,const char *path);


void getFilesInDirWithCallback(SuffixTire* suffixTirePtr,const char *path,int pageSize,ParamsPtr params,void(*callback)(list<FileInfo>,ParamsPtr));
#endif
