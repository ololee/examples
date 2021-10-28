package cn.ololee.jnidemo.sphere;

import android.content.Context;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class SphereRender implements GLSurfaceView.Renderer {

  // 视图矩阵
  private float[] mViewMatrix = new float[16];
  // 模型矩阵
  private float[] mModelMatrix = new float[16];
  // 投影矩阵
  private float[] mProjectionMatrix = new float[16];
  // 总变换矩阵
  private float[] mMVPMatrix = new float[16];

  private SphereFilter mSphereFilter;

  private Context mContext;

  public SphereRender(Context context) {
    mContext = context;
  }

  @Override
  public void onSurfaceCreated(GL10 gl, EGLConfig config) {
    //设置屏幕背景色RGBA
    GLES30.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
    //打开深度检测
    GLES30.glEnable(GLES30.GL_DEPTH_TEST);
    //打开背面剪裁
    GLES30.glEnable(GLES30.GL_CULL_FACE);
    // 创建球面绘制实体
    mSphereFilter = new SphereFilter(mContext);
    // 初始化矩阵
    initMatrix();
  }

  @Override
  public void onSurfaceChanged(GL10 gl, int width, int height) {
    GLES30.glViewport(0, 0, width, height);
    float ratio = (float) width / height;
    // 调用此方法计算产生透视投影矩阵
    Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 20, 100);
    // 调用此方法产生摄像机9参数位置矩阵
    Matrix.setLookAtM(mViewMatrix, 0,
        0f, 0f, 30f,    // 相机位置
        0f, 0f, 0f,     // 目标位置
        0f, 1.0f, 0.0f  // 相机朝向
    );
  }

  @Override
  public void onDrawFrame(GL10 gl) {
    //清除深度缓冲与颜色缓冲
    GLES30.glClear( GLES30.GL_DEPTH_BUFFER_BIT | GLES30.GL_COLOR_BUFFER_BIT);
    calculateMatrix();
    mSphereFilter.setMVPMatrix(mMVPMatrix);
    mSphereFilter.drawSphere();
  }

  /**
   * 初始化矩阵
   */
  private void initMatrix() {
    Matrix.setIdentityM(mViewMatrix, 0);
    Matrix.setIdentityM(mModelMatrix, 0);
    Matrix.setIdentityM(mProjectionMatrix, 0);
    Matrix.setIdentityM(mMVPMatrix, 0);
  }

  /**
   * 计算总变换矩阵
   */
  private void calculateMatrix() {
    Matrix.multiplyMM(mMVPMatrix, 0, mViewMatrix, 0, mModelMatrix, 0);
    Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mMVPMatrix, 0);
  }

  /**
   * 物体旋转
   * @param angle
   * @param x
   * @param y
   * @param z
   */
  public void rotate(float angle, float x, float y, float z) {
    Matrix.rotateM(mModelMatrix, 0, angle, x, y, z);
  }
}
