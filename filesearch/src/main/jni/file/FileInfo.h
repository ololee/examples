#ifndef EXAMPLES_FILEINFO_H
#define EXAMPLES_FILEINFO_H
#include "../common_header.h"

class FileInfo {
private:
    char* path;
    char* name;
    int type;
public:

    FileInfo();

    FileInfo(char *path, char *name, int type);

    char *getPath() const;

    void setPath(char *path);

    char *getName() const;

    void setName(char *name);

    int getType() const;

    void setType(int type);

    friend std::ostream &operator<<(std::ostream &os, const FileInfo &info);

    string toString();
};


#endif
