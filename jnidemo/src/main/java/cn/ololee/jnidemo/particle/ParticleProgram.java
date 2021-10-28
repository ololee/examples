package cn.ololee.jnidemo.particle;

import android.content.Context;
import android.opengl.GLES30;
import cn.ololee.jnidemo.R;
import cn.ololee.jnidemo.earth.ShaderHelper;
import cn.ololee.jnidemo.earth.TextResourceReader;

public class ParticleProgram {

  public static final String U_MATRIX = "u_Matrix";
  public static final String U_TIME = "u_Time";

  public static final String A_POSITION = "a_Position";
  public static final String A_COLOR = "a_Color";
  public static final String A_DITRECTION_VECTOR = "a_DirectionVector";
  public static final String A_PARTICLE_START_TIME = "a_ParticleStartTime";

  private int uMatrixLocation, uTimeLocation;
  private int aPositionLocation,
      aColorLocation,
      aDirectionVextorLocation,
      aParticleStartTimeLocation;

  private Context context;

  private int program;

  public ParticleProgram(Context context) {
    this.context = context;
    createProgram();
    initLocationFromGl();
  }

  /**
   * 编译或链接glsl
   */
  private void createProgram() {
    String vertexPracticleStr =
        TextResourceReader.readTextFileFromResource(context, R.raw.vertex_praticle);
    String fragmentPracticleStr =
        TextResourceReader.readTextFileFromResource(context, R.raw.fragment_particle);
    program = ShaderHelper.buildProgram(vertexPracticleStr, fragmentPracticleStr);

    GLES30.glUseProgram(program);
  }

  /**
   * 获得program中的变量的位置，方便后面传参使用
   */
  private void initLocationFromGl() {
    uMatrixLocation = GLES30.glGetUniformLocation(program, U_MATRIX);
    uTimeLocation = GLES30.glGetUniformLocation(program, U_TIME);

    aPositionLocation = GLES30.glGetAttribLocation(program, A_POSITION);
    aColorLocation = GLES30.glGetAttribLocation(program, A_COLOR);
    aDirectionVextorLocation = GLES30.glGetAttribLocation(program, A_DITRECTION_VECTOR);
    aParticleStartTimeLocation = GLES30.glGetAttribLocation(program, A_PARTICLE_START_TIME);
  }

  public void setUniform(float[] projectionMatrix) {
    GLES30.glUniformMatrix4fv(uMatrixLocation, 1, false, projectionMatrix, 0);
  }

  /**
   * 设置当前时间
   */
  public void setCurrentTime(float elapsedTime) {
    GLES30.glUniform1f(uTimeLocation, elapsedTime);
  }

  public int getPositionLocation() {
    return aPositionLocation;
  }

  public int getColorLocation() {
    return aColorLocation;
  }

  public int getDirectionVextorLocation() {
    return aDirectionVextorLocation;
  }

  public int getParticleStartTimeLocation() {
    return aParticleStartTimeLocation;
  }
}
