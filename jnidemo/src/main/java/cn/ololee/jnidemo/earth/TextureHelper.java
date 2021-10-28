package cn.ololee.jnidemo.earth;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES30;
import android.opengl.GLUtils;
import android.util.Log;

public class TextureHelper {
  public static final String TAG = "ololeeDetail";

  public static int loadTexture(Context context,int resourceId,boolean isRepeat){
    /*
     * 第一步 : 创建纹理对象
     */
    final int[] textureObjectId = new int[1];//用于存储返回的纹理对象ID
    GLES30.glGenTextures(1,textureObjectId, 0);
    if(textureObjectId[0] == 0){//若返回为0,,则创建失败
      Log.e(TAG, "Could not generate a new Opengl texture object");
      return 0;
    }
    /*
     * 第二步: 加载位图数据并与纹理绑定
     */
    final BitmapFactory.Options options = new BitmapFactory.Options();
    options.inScaled = false;//Opengl需要非压缩形式的原始数据
    final Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),resourceId, options);
    if(bitmap == null){
      Log.w(TAG,"ResourceId:"+resourceId+"could not be decoded");
      GLES30.glDeleteTextures(1, textureObjectId, 0);
      return 0;
    }
    GLES30.glBindTexture(GLES30.GL_TEXTURE_2D,textureObjectId[0]);//通过纹理ID进行绑定

    if(isRepeat){
      GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_WRAP_S, GLES30.GL_REPEAT);
      GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_WRAP_T, GLES30.GL_CLAMP_TO_EDGE);
    }

    /*
     * 第三步: 设置纹理过滤
     */
    //设置缩小时为三线性过滤
    GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MIN_FILTER,GLES30.GL_LINEAR_MIPMAP_LINEAR);
    //设置放大时为双线性过滤
    GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MAG_FILTER, GLES30.GL_LINEAR);
    /*
     * 第四步: 加载纹理到Opengl并返回ID
     */
    GLUtils.texImage2D(GLES30.GL_TEXTURE_2D, 0, bitmap, 0);
    bitmap.recycle();
    GLES30.glGenerateMipmap(GLES30.GL_TEXTURE_2D);
    return textureObjectId[0];
  }
}
