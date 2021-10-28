package cn.ololee.jnidemo.earth;

import android.opengl.GLES30;
import android.util.Log;

public class ShaderHelper {
  public static final String TAG = "ololeeDetail";

  public static int createShader(String shader, int type) {
    int shaderObject = 0;
    shaderObject = GLES30.glCreateShader(type);
    GLES30.glShaderSource(shaderObject, shader);
    GLES30.glCompileShader(shaderObject);
    final int[] compileStatus = new int[1];
    GLES30.glGetShaderiv(shaderObject, GLES30.GL_COMPILE_STATUS, compileStatus, 0);
    String log = GLES30.glGetShaderInfoLog(shaderObject);
    Log.d(TAG, "createShader: " + shader + "\nresult:" + log);
    if (compileStatus[0] == 0) {//编译失败
      GLES30.glDeleteShader(shaderObject);
      Log.e(TAG, "createShader:  Compile Shader Failed!!!");
      return 0;
    }
    return shaderObject;
  }

  public static int buildProgram(String vertexShader, String fragmentShader) {
    int program = 0;
    int vertexShaderHandle = createShader(vertexShader, GLES30.GL_VERTEX_SHADER);
    int fragmentShaderHandle = createShader(fragmentShader, GLES30.GL_FRAGMENT_SHADER);
    program = GLES30.glCreateProgram();

    GLES30.glAttachShader(program, vertexShaderHandle);
    GLES30.glAttachShader(program, fragmentShaderHandle);

    GLES30.glLinkProgram(program);

    //检查错误
    final int[] statues = new int[1];
    GLES30.glGetProgramiv(program, GLES30.GL_LINK_STATUS, statues, 0);

    String log = GLES30.glGetProgramInfoLog(program);
    Log.d(TAG, "buildProgram: \n" + log);

    if (statues[0] == 0) {
      GLES30.glDeleteProgram(program);
      Log.e(TAG, "buildProgram: program 链接失败");
      return 0;
    }
    return program;
  }
}
