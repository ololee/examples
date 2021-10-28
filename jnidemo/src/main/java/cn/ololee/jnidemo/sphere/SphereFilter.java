package cn.ololee.jnidemo.sphere;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.opengl.GLES20;
import android.opengl.GLES30;
import android.opengl.Matrix;
import android.util.Log;
import cn.ololee.jnidemo.R;
import cn.ololee.jnidemo.earth.TextureHelper;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

public class SphereFilter {

  private static final String VERTEX_SHADER =
      "uniform mat4 u_Matrix;//最终的变换矩阵\n" +
          "attribute vec4 a_Position;//顶点位置\n" +
          "varying vec4 vPosition;//用于传递给片元着色器的顶点位置\n" +
          "void main() {\n" +
          "    gl_Position = u_Matrix * a_Position;\n" +
          "    vPosition = a_Position;\n" +
          "}";

  private static final String FRAGMENT_SHADER =
      "precision mediump float;\n" +
          "varying vec4 vPosition;\n"
          + "uniform sampler2D s_TextureMap;\n" +
          "void main() {\n" +
          "    float uR = 0.6;//球的半径\n" +
          "    vec4 color;\n" +
          "    float n = 8.0;//分为n层n列n行\n" +
          "    float span = 2.0*uR/n;//正方形长度\n" +
          "    //计算行列层数\n" +
          "    int i = int((vPosition.x + uR)/span);//行数\n" +
          "    int j = int((vPosition.y + uR)/span);//层数\n" +
          "    int k = int((vPosition.z + uR)/span);//列数\n" +
          "    int colorType = int(mod(float(i+j+k),2.0));\n" +
          "    if(colorType == 1) {//奇数时为绿色\n" +
          "        color = vec4(0.2,1.0,0.129,0);\n" +
          "    } else {//偶数时为白色\n" +
          "        color = vec4(1.0,1.0,1.0,0);//白色\n" +
          "    }\n" +
          "    //将计算出的颜色给此片元\n" +
          "    gl_FragColor = color;\n" +
          "}";

  private float radius = 1.0f; // 球的半径
  final double angleSpan = Math.PI / 90f; // 将球进行单位切分的角度
  private FloatBuffer mVertexBuffer;// 顶点坐标
  int mVertexCount = 0;// 顶点个数，先初始化为0

  // float类型的字节数
  private static final int BYTES_PER_FLOAT = 4;

  // 数组中每个顶点的坐标数
  private static final int COORDS_PER_VERTEX = 3;

  private int mProgramHandle;
  private int muMatrixHandle;
  private int maPositionHandle;
  private int mSampleLocation;
  private Context context;
  private int texture;

  private float[] mMVPMatrix = new float[16];

  public SphereFilter(Context context) {
    initSphereVertex();
    createProgram();
    Matrix.setIdentityM(mMVPMatrix, 0);
    this.context = context;
    texture = TextureHelper.loadTexture(context, R.drawable.earth, false);
  }

  /**
   * 计算球面顶点
   */
  public void initSphereVertex() {
    ArrayList<Float> vertex = new ArrayList<Float>();

    for (double vAngle = 0; vAngle < Math.PI; vAngle = vAngle + angleSpan) { // vertical
      for (double hAngle = 0; hAngle < 2 * Math.PI; hAngle = hAngle + angleSpan) { // horizontal

        float x0 = (float) (radius * Math.sin(vAngle) * Math.cos(hAngle));
        float y0 = (float) (radius * Math.sin(vAngle) * Math.sin(hAngle));
        float z0 = (float) (radius * Math.cos((vAngle)));

        float x1 = (float) (radius * Math.sin(vAngle) * Math.cos(hAngle + angleSpan));
        float y1 = (float) (radius * Math.sin(vAngle) * Math.sin(hAngle + angleSpan));
        float z1 = (float) (radius * Math.cos(vAngle));

        float x2 = (float) (radius * Math.sin(vAngle + angleSpan) * Math.cos(hAngle + angleSpan));
        float y2 = (float) (radius * Math.sin(vAngle + angleSpan) * Math.sin(hAngle + angleSpan));
        float z2 = (float) (radius * Math.cos(vAngle + angleSpan));

        float x3 = (float) (radius * Math.sin(vAngle + angleSpan) * Math.cos(hAngle));
        float y3 = (float) (radius * Math.sin(vAngle + angleSpan) * Math.sin(hAngle));
        float z3 = (float) (radius * Math.cos(vAngle + angleSpan));

        vertex.add(x1);
        vertex.add(y1);
        vertex.add(z1);

        vertex.add(x3);
        vertex.add(y3);
        vertex.add(z3);

        vertex.add(x0);
        vertex.add(y0);
        vertex.add(z0);

        vertex.add(x1);
        vertex.add(y1);
        vertex.add(z1);

        vertex.add(x2);
        vertex.add(y2);
        vertex.add(z2);

        vertex.add(x3);
        vertex.add(y3);
        vertex.add(z3);
      }
    }

    mVertexCount = vertex.size() / COORDS_PER_VERTEX;
    float vertices[] = new float[vertex.size()];
    for (int i = 0; i < vertex.size(); i++) {
      vertices[i] = vertex.get(i);
    }
    mVertexBuffer = GlUtil.createFloatBuffer(vertices);
  }

  /**
   * 创建Program
   */
  private void createProgram() {

    mProgramHandle = GlUtil.createProgram(VERTEX_SHADER, FRAGMENT_SHADER);
    maPositionHandle = GLES30.glGetAttribLocation(mProgramHandle, "a_Position");
    muMatrixHandle = GLES30.glGetUniformLocation(mProgramHandle, "u_Matrix");
    if (mProgramHandle != 0) {
      mSampleLocation = GLES30.glGetUniformLocation(mProgramHandle, "s_TextureMap");
    } else {
      Log.e("ololeeTAG", "can't create texture");
    }
  }

  /**
   * 绘制球面
   */
  public void drawSphere() {
    GLES30.glActiveTexture(GLES30.GL_TEXTURE0);
    GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, texture);
    GLES30.glUseProgram(mProgramHandle);
    GLES30.glVertexAttribPointer(maPositionHandle, COORDS_PER_VERTEX,
        GLES30.GL_FLOAT, false, 0, mVertexBuffer);
    GLES30.glEnableVertexAttribArray(maPositionHandle);
    GLES30.glUniformMatrix4fv(muMatrixHandle, 1, false, mMVPMatrix, 0);
    GLES30.glUniform1i(mSampleLocation, 0);
    GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, mVertexCount);
    GLES30.glDisableVertexAttribArray(maPositionHandle);
    GLES30.glUseProgram(0);
  }

  /**
   * 设置最终的变换矩阵
   */
  public void setMVPMatrix(float[] matrix) {
    mMVPMatrix = matrix;
  }
}
