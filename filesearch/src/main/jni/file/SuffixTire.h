#ifndef SUFFIXTIRE_H
#define SUFFIXTIRE_H
#include "SuffixNode.h"


class SuffixTire {
private:
    SuffixNode *root;
public:
    SuffixTire() {
        root = new SuffixNode();
    }

    /**
     * 构造字典树
     * @param suffix 后缀
     * @param type 后缀类型
     */
    void insert(const char *suffix, int type);

    /**
     * 查询字典
     * @param suffix 后缀
     * @return  后缀类型
     */
    int search(const char* suffix);

    ~SuffixTire(){
        delete root;
    }
};

#endif
