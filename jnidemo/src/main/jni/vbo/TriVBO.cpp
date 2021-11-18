//
// Created by ololee on 2021/11/18.
//
#include "../common.h"
#include <GLES3/gl3.h>
#include "glutils.h"
#include <GlES3/gl32.h>
#include <GLES3/gl3ext.h>
#include <GLES3/gl3platform.h>
#include <jni.h>

GLuint traingles_program;
GLuint traingles_vertex_handler;
GLuint traingles_frag_handler;
GLuint traingles_tess_control_handler;
GLuint traingles_tess_evaluation_handler;

void init_tri() {
    char vertexShaderText[] = "attribute  vec4 vPosition;  "
                              "void main(){  \n"
                              "gl_Position=vPosition;  \n"
                              "}";
    char fragmentShaderText[] = "precision mediump float;  \n"
                                "uniform vec4 vColor;  \n"
                                "void main(){  \n"
                                "gl_FragColor = vColor;"
                                "   \n"
                                "}";

//    char tessControlShaderText[] = "layout (vertices = 3) out;\n"
//                                   "\n"
//                                   "void main(void){\n"
//                                   "    if(gl_InvocationID == 0){\n"
//                                   "        gl_TessLevelInner[0] = 5.0;\n"
//                                   "        gl_TessLevelOutter[0] = 5.0;\n"
//                                   "        gl_TessLevelOutter[1] = 5.0;\n"
//                                   "        gl_TessLevelOutter[2] = 5.0;\n"
//                                   "    }\n"
//                                   "    gl_out[gl_InvocationID].gl_Position = gl_in[gl_InvocationID].gl_Position;\n"
//                                   "}";
//    char tessEvaluationShaderText[] = "layout (traingles,equal_spacing,cw) in;\n"
//                                      "\n"
//                                      "void main(void){\n"
//                                      "    gl_Position = (\n"
//                                      "    \tgl_TessCoord.x * gl_in[0].gl_Position +\n"
//                                      "        gl_TessCoord.y * gl_in[1].gl_Position +\n"
//                                      "        gl_TessCoord.z * gl_in[2].gl_Position +\n"
//                                      "    );\n"
//                                      "}";
    traingles_program = CreateProgram(vertexShaderText,
                                      fragmentShaderText,
//                                      tessControlShaderText,
//                                      tessEvaluationShaderText,
                                      traingles_vertex_handler,
                                      traingles_frag_handler
//                                      ,traingles_tess_control_handler,
//                                      traingles_tess_evaluation_handler
    );
//    glPatchParameteri(GL_PATCH_VERTICES,3);
}

void draw() {
    if (traingles_program == GL_NONE) return;

    glClear(GL_STENCIL_BUFFER_BIT | GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    glClearColor(1.0, 1.0, 1.0, 1.0);

    GLfloat verticesCoords[] = {
            0.0f, 0.5f, 0.0f, // top
            -0.5f, -0.5f, 0.0f, // bottom left
            0.5f, -0.5f, 0.0f  // bottom right
    };
//    GLushort indices[] = {0, 1, 2};
    GLfloat colors[] = {0.8f, 0.4f, 0.1f, 1.0f};
    glUseProgram(traingles_program);
    GLint vPos = glGetAttribLocation(traingles_program, "vPosition");
    glVertexAttribPointer(vPos, 3, GL_FLOAT,
                          GL_FALSE, 3 * sizeof(GLfloat), verticesCoords);
    glEnableVertexAttribArray(0);

    GLint color_pos = glGetUniformLocation(traingles_program, "vColor");
    glUniform4fv(color_pos, 1, colors);
//    glDrawElements(GL_TRIANGLES, 3, GL_UNSIGNED_SHORT, indices);
//    glPolygonMode(GL_FRONT_AND_BACK,GL_LINES);
    glDrawArrays(GL_TRIANGLES, 0, 3);
//    glUniform4fv(traingles_frag_handler,3,colors);
}

extern "C"
JNIEXPORT void JNICALL
Java_cn_ololee_jnidemo_traingles_TrainglesRenderer_surfaceCreate(JNIEnv *env, jobject thiz) {
init_tri();
}

extern "C"
JNIEXPORT void JNICALL
Java_cn_ololee_jnidemo_traingles_TrainglesRenderer_surfaceChange(JNIEnv *env, jobject thiz,
        jint width, jint height) {
glViewport(0, 0, width, height);
}

extern "C"
JNIEXPORT void JNICALL
Java_cn_ololee_jnidemo_traingles_TrainglesRenderer_drawFrame(JNIEnv *env, jobject thiz) {
draw();
}

