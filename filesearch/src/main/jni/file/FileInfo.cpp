#include "FileInfo.h"

FileInfo::FileInfo() {}

FileInfo::FileInfo(char *path, char *name, int type) : path(path), name(name), type(type) {}

char *FileInfo::getPath() const {
    return path;
}

void FileInfo::setPath(char *path) {
    FileInfo::path = path;
}

char *FileInfo::getName() const {
    return name;
}

void FileInfo::setName(char *name) {
    FileInfo::name = name;
}

int FileInfo::getType() const {
    return type;
}

void FileInfo::setType(int type) {
    FileInfo::type = type;
}

std::ostream &operator<<(std::ostream &os, const FileInfo &info) {
    os << "path: " << info.path << " name: " << info.name << " type: " << info.type;
    return os;
}

string FileInfo::toString() {
    string str;
    str += "path:";
    str += path;
    str += ",name:";
    str += name;
    str += ",type:";
    str += to_string(type);
    return str;
}
