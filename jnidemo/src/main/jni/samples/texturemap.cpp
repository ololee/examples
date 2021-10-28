//
// Created by ololee on 2021/10/14.
//
#include "../common.h"
#include <GLES3/gl3.h>
#include "../gles/glutils.h"
#include <string>
#include "NativeImage.h"

GLuint mTextureId = 0;
GLuint program;
GLuint vertexShader;
GLuint fragmentShader;
GLint sampleLocation;
NativeImagePtr nativeImage;
NativeImage imageStruct;


void setBitmapToJava(jclass clz, JNIEnv *env, NativeImage *ptr) {
    jfieldID fid = env->GetStaticFieldID(clz, "bitmapPtr", "J");
    env->SetStaticLongField(clz, fid, reinterpret_cast<jlong>(ptr));
}

NativeImage *getBitmapArrayFromJava(jclass clz, JNIEnv *env) {
    jfieldID fid = env->GetStaticFieldID(clz, "bitmapPtr", "J");
    return reinterpret_cast<NativeImage *>(env->GetStaticLongField(clz, fid));
}




void init() {
    glGenTextures(1, &mTextureId);
    glBindTexture(GL_TEXTURE_2D, mTextureId);
    glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
    glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_R, GL_CLAMP_TO_EDGE);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
    glBindTexture(GL_TEXTURE_2D, GL_NONE);

    char vertexShaderStr[] =
            "#version 300 es                            \n"
            "layout(location = 0) in vec4 a_position;   \n"
            "layout(location = 1) in vec2 a_texCoord;   \n"
            "out vec2 v_texCoord;                       \n"
            "void main()                                \n"
            "{                                          \n"
            "   gl_Position = a_position;               \n"
            "   v_texCoord = a_texCoord;                \n"
            "}                                          \n";;

    char fragmentShaderStr[] =
            "#version 300 es                                     \n"
            "precision mediump float;                            \n"
            "in vec2 v_texCoord;                                 \n"
            "layout(location = 0) out vec4 outColor;             \n"
            "uniform sampler2D s_TextureMap;                     \n"
            "void main()                                         \n"
            "{                                                   \n"
            "  outColor = texture(s_TextureMap, v_texCoord);     \n"
            "  //outColor = texelFetch(s_TextureMap,  "
            "  //ivec2(int(v_texCoord.x * 404.0),"
            "  // int(v_texCoord.y * 336.0)), 0);\n"
            "}                                                   \n";

    program = CreateProgram(vertexShaderStr, fragmentShaderStr, vertexShader, fragmentShader);
    if (program) {
        sampleLocation = glGetUniformLocation(program, "s_TextureMap");
    } else {
        LOGE("texture map sample, init create program failed.");
    }
}

static int myCount = 0;

void draw(JNIEnv *env, jclass clazz) {
    LOGE("TextureMapSample::Draw(),%d", myCount++);

    if (program == GL_NONE || mTextureId == GL_NONE) return;

    glClear(GL_STENCIL_BUFFER_BIT | GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    glClearColor(1.0, 1.0, 1.0, 1.0);

    GLfloat verticesCoords[] = {
            -1.0f, 1.0f, 0.0f,  // Position 0
            -1.0f, -1.0f, 0.0f,  // Position 1
            1.0f, -1.0f, 0.0f,   // Position 2
            1.0f, 1.0f, 0.0f,   // Position 3
    };

    GLfloat textureCoords[] = {
            0.0f, 0.0f,        // TexCoord 0
            0.0f, 1.0f,        // TexCoord 1
            1.0f, 1.0f,        // TexCoord 2
            1.0f, 0.0f         // TexCoord 3
    };

    GLushort indices[] = {0, 1, 2, 0, 2, 3};

    //upload RGBA image data
    glActiveTexture(GL_TEXTURE0);
    glBindTexture(GL_TEXTURE_2D, mTextureId);
    glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, imageStruct.width, imageStruct.height, 0, GL_RGBA,
                 GL_UNSIGNED_BYTE, imageStruct.data);
    glBindTexture(GL_TEXTURE_2D, GL_NONE);

    // Use the program object
    glUseProgram(program);

    // Load the vertex position
    glVertexAttribPointer(0, 3, GL_FLOAT,
                          GL_FALSE, 3 * sizeof(GLfloat), verticesCoords);
    // Load the texture coordinate
    glVertexAttribPointer(1, 2, GL_FLOAT,
                          GL_FALSE, 2 * sizeof(GLfloat), textureCoords);

    glEnableVertexAttribArray(0);
    glEnableVertexAttribArray(1);

    // Bind the RGBA map
    glActiveTexture(GL_TEXTURE0);
    glBindTexture(GL_TEXTURE_2D, mTextureId);

    // Set the RGBA map sampler to texture unit to 0
    glUniform1i(sampleLocation, 0);

    glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_SHORT, indices);
}

extern "C"
JNIEXPORT void JNICALL
Java_cn_ololee_jnidemo_TextureMapNative_transferBitmap(JNIEnv *env, jclass clazz,
                                                       jint width, jint height, jbyteArray bytes) {
    int len = env->GetArrayLength(bytes);
    uint8_t *buffer = new uint8_t[len];
    env->GetByteArrayRegion(bytes, 0, len, reinterpret_cast<jbyte *>(buffer));
    NativeImage image;
    nativeImage = &image;
    nativeImage->width = width;
    nativeImage->height = height;
    nativeImage->data = buffer;
    imageStruct = image;
    setBitmapToJava(clazz, env, nativeImage);
    env->DeleteLocalRef(bytes);
}



extern "C"
JNIEXPORT void JNICALL
Java_cn_ololee_jnidemo_TextureMapNative_releaseJNIVar(JNIEnv *env, jclass clazz) {
    NativeImage *buffer = getBitmapArrayFromJava(clazz, env);
    if (buffer != NULL && buffer->data != NULL) {
        delete buffer->data;
    }
}


extern "C"
JNIEXPORT void JNICALL
Java_cn_ololee_jnidemo_TextureMapNative_init(JNIEnv *env, jclass clazz) {
    init();
}


extern "C"
JNIEXPORT void JNICALL
Java_cn_ololee_jnidemo_TextureMapNative_draw(JNIEnv *env, jclass clazz) {
    draw(env, clazz);
}

extern "C"
JNIEXPORT void JNICALL
Java_cn_ololee_jnidemo_TextureMapNative_testDraw(JNIEnv *env, jclass clazz) {
    glClearColor(1, 1, 0, 1);
}
