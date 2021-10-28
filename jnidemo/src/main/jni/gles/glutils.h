//
// Created by ololee on 2021/10/18.
//

#ifndef EXAMPLES_GLUTILS_H
#define EXAMPLES_GLUTILS_H

#include <GLES3/gl3.h>
#include <string>
#include "../common.h"

GLuint LoadShader(GLenum shaderType, const char *pSource);

void CheckGLError(const char *pGLOperation);

GLuint CreateProgram(const char *pVertexShaderSource, const char *pFragShaderSource,
                     GLuint &vertexShaderHandle, GLuint &fragShaderHandle);

GLuint CreateProgram(const char *pVertexShaderSource, const char *pFragShaderSource,
                     const char *pTessControlShaderSource,
                     const char *pTessEvaluationShaderSource,
                     GLuint &vertexShaderHandle,
                     GLuint &fragShaderHandle,
                     GLuint &tessControlShaderHandle,
                     GLuint &tessEvaluationShaderHandle);


#endif //EXAMPLES_GLUTILS_H
