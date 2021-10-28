package cn.ololee.jnidemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import androidx.annotation.NonNull;
import androidx.renderscript.Allocation;
import androidx.renderscript.Element;
import androidx.renderscript.RenderScript;
import androidx.renderscript.ScriptIntrinsicBlur;
import cn.ololee.jnidemo.rs.ScriptC_Gray;

public class BlurBitmap {

  //图片缩放比例
  private static final float BITMAP_SCAL = 1f;
  //最大模糊度(在0.0到25.0之间)
  private static final float BLUR_RADIUS = 25f;


  /**
   * 模糊图片的具体操作方法
   *
   * @param context 上下文对象
   * @param image 需要模糊的图片
   * @return 模糊处理后的图片
   */
  public static Bitmap blur(Context context, Bitmap image) {
    //计算图片缩小后的长宽
    int width = Math.round(image.getWidth() * BITMAP_SCAL);
    int height = Math.round(image.getHeight() * BITMAP_SCAL);

    //将缩小后的图片做为预渲染的图片
    Bitmap inputBitmap = Bitmap.createScaledBitmap(image, width, height, false);
    //创建一张渲染后的输出图片
    Bitmap outputBitmap = Bitmap.createBitmap(inputBitmap);

    //创建RenderScript内核对象
    RenderScript rs = RenderScript.create(context);
    //创建一个模糊效果的RenderScript的工具对象
    ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));

    //由于RenderScript并没有使用VM来分配内存，所以需要使用Allocation类来创建和分配内存空间
    //创建Allocation对象的时候其实内存是空的，需要使用copyTo()将数据填充进去。
    Allocation tmpIn = Allocation.createFromBitmap(rs, inputBitmap);
    Allocation tmpOut = Allocation.createFromBitmap(rs, outputBitmap);

    //设置渲染的模糊程度，25f是最大模糊程度
    blurScript.setRadius(BLUR_RADIUS);
    //设置blurScript对象的输入内存
    blurScript.setInput(tmpIn);
    //将输出数据保存到输出内存中
    blurScript.forEach(tmpOut);

    //将数据填充到Allocation中
    tmpOut.copyTo(outputBitmap);

    return outputBitmap;
  }

  /**
   * 将 bitmap 去色后返回一张新的 Bitmap。
   */
  public static Bitmap gray(@NonNull Context context, @NonNull Bitmap bitmap) {

    // 创建输出 bitamp
    int width = bitmap.getWidth();
    int height = bitmap.getHeight();
    Bitmap outBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

    // 创建 RenderScript 对象
    RenderScript rs = RenderScript.create(context);

    // 创建输入、输出 Allocation
    Allocation allIn = Allocation.createFromBitmap(rs, bitmap);
    Allocation allOut = Allocation.createFromBitmap(rs, outBitmap);

    // 创建我们在上面定义的 script
    ScriptC_Gray script = new ScriptC_Gray(rs);

    // 对每一个像素执行 root 方法
    script.forEach_root(allIn, allOut);

    // 将执行结果复制到输出 bitmap 上
    allOut.copyTo(outBitmap);

    // 释放资源
    rs.destroy();
    return outBitmap;
  }

  /**
   * 将 bitmap 去色后返回一张新的 Bitmap。
   */
  public static Bitmap blackGold(@NonNull Context context, @NonNull Bitmap bitmap) {

    // 创建输出 bitamp
    int width = bitmap.getWidth();
    int height = bitmap.getHeight();
    Bitmap outBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

    // 创建 RenderScript 对象
    RenderScript rs = RenderScript.create(context);

    // 创建输入、输出 Allocation
    Allocation allIn = Allocation.createFromBitmap(rs, bitmap);
    Allocation allOut = Allocation.createFromBitmap(rs, outBitmap);

    // 创建我们在上面定义的 script
    ScriptC_Gray script = new ScriptC_Gray(rs);

    // 对每一个像素执行 root 方法
    script.forEach_blackGold(allIn, allOut);

    // 将执行结果复制到输出 bitmap 上
    allOut.copyTo(outBitmap);

    // 释放资源
    rs.destroy();
    return outBitmap;
  }

  //public static Bitmap pinyin(@NonNull Context context, @NonNull Bitmap input, Bitmap pinyin) {
  //  // 创建输出 bitamp
  //  int width = input.getWidth();
  //  int height = input.getHeight();
  //  Bitmap outBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
  //
  //  // 创建 RenderScript 对象
  //  RenderScript rs = RenderScript.create(context);
  //
  //  // 创建输入、输出 Allocation
  //  Allocation allIn = Allocation.createFromBitmap(rs, input);
  //  Allocation allDifferent = Allocation.createFromBitmap(rs, pinyin);
  //  Allocation allOut = Allocation.createFromBitmap(rs, outBitmap);
  //
  //  // 创建我们在上面定义的 script
  //  ScriptC_Gray script = new ScriptC_Gray(rs);
  //
  //  // 对每一个像素执行 root 方法
  //  script.forEach_pinyin(allIn, allDifferent, allOut);
  //
  //  // 将执行结果复制到输出 bitmap 上
  //  allOut.copyTo(outBitmap);
  //
  //  // 释放资源
  //  rs.destroy();
  //  return outBitmap;
  //}



  public static String realPinyin(Context context,Bitmap pinyinMap,String chinese){
    int width= pinyinMap.getWidth();
    int height= pinyinMap.getHeight();
   // Bitmap output=Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);
    return "";
  }

  public static Bitmap iDontKnow(@NonNull Context context, @NonNull Bitmap bitmap) {

    // 创建输出 bitamp
    int width = bitmap.getWidth();
    int height = bitmap.getHeight();
    Bitmap outBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

    // 创建 RenderScript 对象
    RenderScript rs = RenderScript.create(context);

    // 创建输入、输出 Allocation
    Allocation allIn = Allocation.createFromBitmap(rs, bitmap);
    Allocation allOut = Allocation.createFromBitmap(rs, outBitmap);

    // 创建我们在上面定义的 script
    ScriptC_Gray script = new ScriptC_Gray(rs);

    // 对每一个像素执行 root 方法
    script.forEach_idontknow(allIn, allOut);

    // 将执行结果复制到输出 bitmap 上
    allOut.copyTo(outBitmap);

    // 释放资源
    rs.destroy();
    return outBitmap;
  }
}
