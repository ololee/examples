#ifndef EXAMPLES_COMMON_HEADER_H
#define EXAMPLES_COMMON_HEADER_H

#include <jni.h>
#include<android/log.h>
#include <unistd.h>
#include <string.h>
#include <stdint.h>
#include <stdio.h>
#include <iostream>
#include <dirent.h>
#include <queue>
#include <list>
#include <memory>

#define TAG "filesearch"
#define ENABLE_LOG true

#define LOGD(...) if(ENABLE_LOG){\
     __android_log_print(ANDROID_LOG_DEBUG,TAG ,__VA_ARGS__);\
 } // 定义LOGD类型
#define LOGI(...) if(ENABLE_LOG){\
     __android_log_print(ANDROID_LOG_INFO,TAG ,__VA_ARGS__); \
 }// 定义LOGI类型
#define LOGW(...) if(ENABLE_LOG){\
    __android_log_print(ANDROID_LOG_WARN,TAG ,__VA_ARGS__); \
}// 定义LOGW类型
#define LOGE(...) if(ENABLE_LOG){\
__android_log_print(ANDROID_LOG_ERROR,TAG ,__VA_ARGS__); \
}// 定义LOGE类型
#define LOGF(...)  if(ENABLE_LOG){\
__android_log_print(ANDROID_LOG_FATAL,TAG ,__VA_ARGS__); \
}// 定义LOGF类型
#define NELEM(x) ((int) (sizeof(x) / sizeof((x)[0])))


#define FIND_CLASS(var, className) \
var = env->FindClass(className); \
if(!var){ \
 LOGE("could not find class %s",className); \
}

#define GET_METHOD_ID(var, clazz, methodName, descriptor) \
var = env->GetMethodID(clazz, methodName, descriptor); \
if(!var){ \
 LOGE("unabele to find field %s",methodName); \
}

#define GET_FIELD_ID(var, clazz, fieldName, SIG) \
var =  env->GetFieldID(clzzz,fieldName,SIG); \
if(!var){ \
 LOGE("unabele to find field %s",fieldName); \
}

using namespace std;

#define TYPE_FOLDER 0x0100

typedef struct {
    JNIEnv *env;
    jobject fileQueryCallback;
    jclass arrayListClazz;
    jclass fileInfoClazz;
    jmethodID arrayListConstructorMid;
    jmethodID fileInfoConstructorMid;
    jmethodID arrayListAddMid;
    jmethodID updateMid;

} Params, *ParamsPtr;
#endif