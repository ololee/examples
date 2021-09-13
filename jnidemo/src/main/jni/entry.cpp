//
// Created by ololee on 2021/9/1.
//
#include "common.h"


extern "C"
JNIEXPORT jstring JNICALL
Java_cn_ololee_jnidemo_JniEntry_sayHello(JNIEnv *env, jclass clazz) {

    return env->NewStringUTF("hello");
}
