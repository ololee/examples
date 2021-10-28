#ifndef SUFFIXNODE_H
#define SUFFIXNODE_H

/**
 * Why is 16 ?
 * beacuse we find that the tire max children number is 16.
 * now the tire cosisit of :
 * mov,mp4,mp3,mp2,mpeg,mpg,mid,midi,mkv
 * aac,apk,avi
 * ppt,pptx,png,pdf
 * gif
 * jpg,jpeg
 * bmp
 * webm,wav
 * ogg
 * 3gp
 * flv
 * xls,xlsx,xml
 * doc,docx
 * txt
 * log
 * rtf,rar
 * zip
 * u can see it, in our program. we used 16 line to descript the suffixes, so if u want increase the suffixes, u must change this value.
 * if u just want delete some of the support types, just delete it and it's not necessary to change it.
 */
#include "../common_header.h"

class SuffixNode {
public:
    char ch;
    int type;
    bool isLeaf;
    vector<SuffixNode> children;
public:
    SuffixNode() : ch(NULL), type(0), isLeaf(false),children(vector<SuffixNode>()) {
    }

    ~SuffixNode(){

    }
};

#endif
