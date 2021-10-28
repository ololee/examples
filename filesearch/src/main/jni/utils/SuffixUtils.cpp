#include "SuffixUtils.h"
#include "../common_header.h"

const char *getSuffixFromPath(const char *path) {
    const char *ret = strrchr(path, '.');
    if (ret) {
        return ret + 1;
    }
    return "";
}