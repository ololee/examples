#include "SuffixTire.h"
#include "../common_header.h"


void SuffixTire::insert(const char *suffix, int type) {
    assert(suffix != NULL);
    int suffixLen = strlen(suffix);
    assert(suffixLen != 0);
    SuffixNode *node = root;
    for (int i = 0; i < suffixLen; ++i) {
        int index = 0;
        for (; index < node->children.size(); ++index) {
            if (node->children[index].ch == suffix[i])
                break;
        }

        if (index < node->children.size()) {//找到了
            node = &(node->children[index]);
        } else if (index == node->children.size()) {//未找到节点
            SuffixNode temp;
            temp.ch = suffix[i];
            node->children.push_back(temp);
            node = &(node->children.back());
        }
    }
    node->isLeaf = true;
    node->type = type;
}

int SuffixTire::search(const char *suffix) {
    assert(suffix != nullptr);
    int suffixLen = strlen(suffix);
    if (suffixLen == 0) {
        return 0;
    }
    SuffixNode *node = root;
    for (int i = 0; i < suffixLen; ++i) {
        int index = 0;
        for (; index < node->children.size(); ++index) {
            if (node->children[index].ch == suffix[i])
                break;
        }
        if (index == node->children.size()) {//未找到
            return 0;
        }
        node = &(node->children[index]);
    }
    if (node->isLeaf)
        return node->type;
    else
        return 0;
}
