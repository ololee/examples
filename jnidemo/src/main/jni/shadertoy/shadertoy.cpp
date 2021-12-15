//
// Created by ololee on 2021/11/24.
//
#include "shadertoy.h"

extern "C"
JNIEXPORT void JNICALL
Java_cn_ololee_jnidemo_shadertoy_ShadertouRenderer_shaderToySurfaceCreated(JNIEnv *env,
                                                                           jobject thiz) {
    glClearColor(1.0, 0.0, 0.0, 0.0);
}



extern "C"
JNIEXPORT void JNICALL
Java_cn_ololee_jnidemo_shadertoy_ShadertouRenderer_shaderToySurfaceChanged(JNIEnv *env,
                                                                           jobject thiz, jint width,
                                                                           jint height) {
    glViewport(0, 0, width, height);
}


extern "C"
JNIEXPORT void JNICALL
Java_cn_ololee_jnidemo_shadertoy_ShadertouRenderer_shaderToyDrawFrame(JNIEnv *env, jobject thiz) {
    jclass clz = env->GetObjectClass(thiz);
    glClear(GL_STENCIL_BUFFER_BIT | GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    jfieldID dataFid = env->GetStaticFieldID(clz, "data", "Lcn/ololee/jnidemo/shadertoy/IData;");
    jobject dataJobj = env->GetStaticObjectField(clz, dataFid);
    jclass IDataClz = env->GetObjectClass(dataJobj);
    jfieldID currentTimeFid;
    GET_FIELD_ID(currentTimeFid, IDataClz, "currentTime", "F")
    jfloat currentTime = env->GetFloatField(dataJobj, currentTimeFid);
    LOGE("currentTime:%f",currentTime);
    glClearColor((sin(currentTime)+1)/2, 1.0, 1.0, 1.0);
}