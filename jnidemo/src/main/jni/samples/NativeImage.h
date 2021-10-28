//
// Created by ololee on 2021/10/14.
//

#ifndef EXAMPLES_NATIVEIMAGE_H
#define EXAMPLES_NATIVEIMAGE_H

#include <jni.h>

typedef struct {
    int width;
    int height;
    uint8_t *data;
} NativeImage ,*NativeImagePtr;

#endif //EXAMPLES_NATIVEIMAGE_H
