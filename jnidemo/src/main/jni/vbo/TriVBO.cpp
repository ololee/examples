//
// Created by ololee on 2021/11/18.
//
#include "../common.h"
#include <GLES3/gl3.h>
#include "../gles/glutils.h"
#include <GlES3/gl32.h>
#include <GLES3/gl3ext.h>
#include <GLES3/gl3platform.h>
#include <jni.h>

GLuint vbo_traingles_program;
GLuint vbo_traingles_vertex_handler;
GLuint vbo_traingles_frag_handler;

typedef struct TouchPoint {
    float x;
    float y;
    float z;
    float width;
    float height;
} TouchPoint;


static TouchPoint gTouchPoint;
GLfloat touchPoint[] = {gTouchPoint.x, gTouchPoint.y, gTouchPoint.z, 0};

GLfloat pointMatrix[] = {0, 0, 0, 0,
                         0, 0, 0, 0,
                         0, 0, 0, 0,
                         0, 0, 0, 0
};

void init_tri_vbo() {
    char vertexShaderText[] = "attribute  vec4 vPosition; \n"
                              "uniform vec4 touchPoint;\n"
                              "uniform mat4 touchPoints; \n"
                              "varying vec4 pos;\n"
                              "varying vec4 iMouse;\n"
                              "void main(){  \n"
                              "gl_Position=vPosition;  \n"
                              "pos = vPosition;\n"
                              "iMouse = vec4(touchPoints[0][0],touchPoints[0][1],touchPoints[0][2],0);\n"
                              "}";
    char fragmentShaderText[] = "precision mediump float;  \n"
                                "uniform vec4 vColor;\n"
                                "varying vec4 pos;  \n"
                                "varying vec4 iMouse;\n"
                                "\n"
                                "float sdCircle(in vec2 p,in float r){\n"
                                "   return length(p)-r;\n"
                                "}"
                                "\n"
                                "void main(){  \n"
                                "float d=sdCircle(pos.xy,0.25);\n"
                                "vec2 m = iMouse.xy;\n"
                                "vec3 col = vec3(1.0) - sign(d)*vec3(0.1,0.4,0.7);\n"
                                "col *= 1.0 - exp(-3.0*abs(d));\n"
                                "col *= 0.8 + 0.2*cos(150.0*d);\n"
                                "col = mix( col, iMouse.xyz, 1.0-smoothstep(0.0,0.01,abs(d)) );\n"
                                "if( iMouse.z>0.001 )\n"
                                "    {\n"
                                "    d = sdCircle(m,0.25);\n"
                                "    col = mix(col, vec3(1.0,1.0,0.0), 1.0-smoothstep(0.0, 0.005, abs(length(pos.xy-m)-abs(d))-0.0025));\n"
                                "    col = mix(col, vec3(1.0,1.0,0.0), 1.0-smoothstep(0.0, 0.005, length(pos.xy-m)-0.015));\n"
                                "    }\n"
                                "gl_FragColor = vec4(col,1.0f);"
                                "   \n"
                                "}";

    vbo_traingles_program = CreateProgram(vertexShaderText,
                                          fragmentShaderText,
//                                      tessControlShaderText,
//                                      tessEvaluationShaderText,
                                          vbo_traingles_vertex_handler,
                                          vbo_traingles_frag_handler
//                                      ,traingles_tess_control_handler,
//                                      traingles_tess_evaluation_handler
    );
//    glPatchParameteri(GL_PATCH_VERTICES,3);
}

void draw_vbo() {
    if (vbo_traingles_program == GL_NONE) return;

    glClear(GL_STENCIL_BUFFER_BIT | GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    glClearColor(1.0, 1.0, 1.0, 1.0);

    GLfloat verticesCoords[] = {
            -1.f, 1.f, 0.0f, // top
            -1.f, -1.f, 0.0f, // bottom left
            1.f, -1.f, 0.0f,  // bottom right
            1.f, 1.f, 0.0f,
            -1.f, 1.f, 0.0f, // top
            1.f, -1.f, 0.0f  // bottom right
    };


    GLuint buffer[2];
    glGenBuffers(2, buffer);
    glBindBuffer(GL_ARRAY_BUFFER, buffer[0]);
    glBufferData(GL_ARRAY_BUFFER, sizeof(verticesCoords), verticesCoords, GL_STATIC_DRAW);

    glBindBuffer(GL_ARRAY_BUFFER, buffer[1]);
    glBufferData(GL_ARRAY_BUFFER, sizeof(touchPoint), touchPoint, GL_STATIC_DRAW);

    glBindBuffer(GL_ARRAY_BUFFER, buffer[0]);
    glEnableVertexAttribArray(0);
    glVertexAttribPointer(0, 3, GL_FLOAT, GL_FALSE, 3 * sizeof(GLfloat), (const void *) 0);
    glBindBuffer(GL_ARRAY_BUFFER, GL_NONE);

    glBindBuffer(GL_ARRAY_BUFFER, buffer[1]);
    glEnableVertexAttribArray(1);
    glVertexAttribPointer(1, 1, GL_FLOAT, GL_FALSE, 3 * sizeof(GLfloat), (const void *) 0);
    glBindBuffer(GL_ARRAY_BUFFER, GL_NONE);

    glUseProgram(vbo_traingles_program);
    glBindBuffer(GL_ARRAY_BUFFER, GL_NONE);
    GLushort indices[] = {0, 1, 2};
    GLfloat colors[] = {0.8f, 0.4f, 0.1f, 1.0f};
//    GLint vPos = glGetAttribLocation(vbo_traingles_program, "vPosition");
//    glVertexAttribPointer(vPos, 3, GL_FLOAT,
//                          GL_FALSE, 3 * sizeof(GLfloat), verticesCoords);
//    glEnableVertexAttribArray(0);

    GLint mat_loc = glGetUniformLocation(vbo_traingles_program, "touchPoints");
    glUniformMatrix4fv(mat_loc, 1, GL_FALSE, pointMatrix);

    GLint color_pos = glGetUniformLocation(vbo_traingles_program, "vColor");
    glUniform4fv(color_pos, 1, colors);
//    GLint mouse_pos = glGetUniformLocation(vbo_traingles_program, "touchPoint");
//    glUniform4fv(mouse_pos, 1, touchPoint);
    glDrawElements(GL_TRIANGLES, 3, GL_UNSIGNED_SHORT, indices);


//    glPolygonMode(GL_FRONT_AND_BACK,GL_LINES);
    glDrawArrays(GL_TRIANGLES, 0, 3);
    glDrawArrays(GL_TRIANGLES, 3, 3);
//    glUniform4fv(traingles_frag_handler,3,colors);
}

extern "C"
JNIEXPORT void JNICALL
Java_cn_ololee_jnidemo_traingles_vbo_TriWithBufferRenderer_surfaceCreate(JNIEnv *env,
                                                                         jobject thiz) {
    init_tri_vbo();
}

extern "C"
JNIEXPORT void JNICALL
Java_cn_ololee_jnidemo_traingles_vbo_TriWithBufferRenderer_surfaceChange(JNIEnv *env, jobject thiz,
                                                                         jint width, jint height) {
    glViewport(0, width/2, width, width);
    gTouchPoint.width=width;
    gTouchPoint.height=height;
}

extern "C"
JNIEXPORT void JNICALL
Java_cn_ololee_jnidemo_traingles_vbo_TriWithBufferRenderer_drawFrame(JNIEnv *env, jobject thiz) {
    draw_vbo();
}



extern "C"
JNIEXPORT void JNICALL
Java_cn_ololee_jnidemo_traingles_vbo_TriWithBufferRenderer_move(JNIEnv *env, jobject thiz, jfloat x,
                                                                jfloat y, jfloat z) {
    pointMatrix[0] = ((x-gTouchPoint.width/2)/gTouchPoint.width);
    pointMatrix[1] = (-(y-gTouchPoint.height/2)/gTouchPoint.height);
    pointMatrix[2] = z;


    LOGE("ololeeTAG___move:x:%f,y:%f,z:%f", x, y, z);
}