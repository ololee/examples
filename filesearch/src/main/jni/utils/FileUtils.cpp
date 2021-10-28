#include "FileUtils.h"


/*
 * 没有考虑文件特别多的情况
 */
list<FileInfo> getFilesInDir(SuffixTire *suffixTirePtr, const char *path) {
    list<FileInfo> result;
    DIR *dir = opendir(path);
    if (dir == NULL) {
        perror("open dir error");
    } else {
        Dirent direntPtr;
        while ((direntPtr = readdir64(dir)) != NULL) {
            char *cur;
            if (strcmp(direntPtr->d_name, ".") == 0 ||
                strcmp(direntPtr->d_name, "..") == 0)
                continue;
            else if (direntPtr->d_type == 8 || direntPtr->d_type == 4) {//file or folder
                if (strlen(direntPtr->d_name) != 0 &&
                    direntPtr->d_name[0] != '.') {//not empty str or start with '.'
                    int nameLen = strlen(direntPtr->d_name);
                    int strLen = strlen(path) + nameLen + 2;
                    cur = new char[strLen];
                    memset(cur, '\0', sizeof(cur));
                    sprintf(cur, "%s/%s", path, direntPtr->d_name);
                    if (direntPtr->d_type == 4) {//folder
                        result.push_front(FileInfo(cur, direntPtr->d_name, TYPE_FOLDER));
                    } else {//file
                        result.push_back(FileInfo(cur, direntPtr->d_name, suffixTirePtr->search(
                                getSuffixFromPath(direntPtr->d_name))));
                    }
                }
            }
        }
    }
    closedir(dir);
    return result;
}

/**
 * 超级多的文件或文件夹的回调
 * @param suffixTirePtr  前缀树指针
 * @param path 文件夹的路径
 * @param pageSize 分页大小-->文件/文件夹的个数
 * @param callback 回调
 */
void getFilesInDirWithCallback(SuffixTire *suffixTirePtr, const char *path, int pageSize,
                               ParamsPtr params,
                               void(*callback)(list<FileInfo>, ParamsPtr)) {
    auto result = make_shared<list<FileInfo>>();
    int count = 0;
    DIR *dir = opendir(path);
    if (dir == NULL) {
        perror("open dir error");
    } else {
        Dirent direntPtr;
        while ((direntPtr = readdir64(dir)) != NULL) {
            char *cur;
            char *name;
            if (strcmp(direntPtr->d_name, ".") == 0 ||
                strcmp(direntPtr->d_name, "..") == 0)
                continue;
            else if (direntPtr->d_type == 8 || direntPtr->d_type == 4) {//file or folder
                if (strlen(direntPtr->d_name) != 0 &&
                    direntPtr->d_name[0] != '.') {//not empty str or start with '.'
                    int nameLen = strlen(direntPtr->d_name);
                    int strLen = strlen(path) + nameLen + 2;
                    name=new char[nameLen];
                    cur = new char[strLen];
                    memset(name, '\0', sizeof(name));
                    memset(cur, '\0', sizeof(cur));
                    strcpy(name,direntPtr->d_name);
                    sprintf(cur, "%s/%s", path, direntPtr->d_name);
                    LOGE("current path:%s",cur)
                    if (direntPtr->d_type == 4) {//folder
                        result->push_front(FileInfo(cur, name, TYPE_FOLDER));
                    } else {//file
                        result->push_back(FileInfo(cur, name, suffixTirePtr->search(
                                getSuffixFromPath(direntPtr->d_name))));
                    }
                    count++;
                    if (count == pageSize) {
                        count = 0;
                        callback(*result, params);
                        result = make_shared<list<FileInfo>>();
                    }
                }
            }
        }
    }
    if (result->size() != 0)
        callback(*result, params);
    closedir(dir);
}

