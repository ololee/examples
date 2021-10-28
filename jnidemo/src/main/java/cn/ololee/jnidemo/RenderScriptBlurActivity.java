package cn.ololee.jnidemo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Environment;
import android.util.Log;
import androidx.annotation.ColorRes;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.renderscript.Allocation;
import cn.ololee.jnidemo.databinding.ActivityRenderScriptBlurBinding;
import androidx.renderscript.RenderScript;
import cn.ololee.jnidemo.rs.ScriptC_Gray;
import com.hht.pinyin.Pinyin;
import com.hht.pinyin.PinyinData;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class RenderScriptBlurActivity extends AppCompatActivity {
  private ActivityRenderScriptBlurBinding binding;
  private Allocation pinyinAllocation;
  private RenderScript renderScript;
  private ScriptC_Gray scriptCGray;
  //ScriptC

  private void calcInit() {
    renderScript = RenderScript.create(this);
    Bitmap pinyin = BitmapFactory.decodeResource(getResources(), R.drawable.pinyinmap_square);
    pinyinAllocation = Allocation.createFromBitmap(renderScript, pinyin);
    scriptCGray = new ScriptC_Gray(renderScript);
    scriptCGray.set_pinyinData(pinyinAllocation);
  }

  private void calc(String text) {
    Log.e("ololeeTAG", "calc->text:" + text);
    Bitmap textBmp = Bitmap.createBitmap(text.length(), 1, Bitmap.Config.ARGB_8888);
    StringBuilder inputBuilder = new StringBuilder();
    StringBuilder inputVerifierBuilder = new StringBuilder();
    Canvas canvas = new Canvas();
    canvas.setBitmap(textBmp);
    Paint paint = new Paint();
    paint.setAntiAlias(true);
    paint.setStyle(Paint.Style.FILL);
    for (int i = 0; i < text.length(); i++) {
      char ch = text.charAt(i);
      inputBuilder.append(ch);
      int ch_high = (ch & 0xff00) >> 8;
      int ch_low = (ch & 0xff);
      inputBuilder.append("  high:" + ch_high);
      inputBuilder.append(",  low:" + ch_low + " ");
      paint.setColor(Color.argb(0, 0, ch_high, ch_low));
      canvas.drawPoint(i, 0, paint);
    }
    Log.e("ololeeTAG", "inputStr:" + inputBuilder.toString());
    Log.e("ololeeTAG", "width:" + textBmp.getWidth());
    int textLength = textBmp.getWidth();
    int[] pixels = new int[textLength];
    textBmp.getPixels(pixels, 0, textBmp.getWidth(), 0, 0, textLength, 0);
    for (int i = 0; i < textLength; i++) {
      int color = pixels[i];
      int ch_high = Color.green(color);
      int ch_low = Color.blue(color);
      int ch = ch_high * 256 + ch_low;//(ch_high<<8)|ch_low;
      inputVerifierBuilder.append(ch).append(",");
      Log.d("ololeeTAG", "input verify: color:" + color + ",ch:" + ch + "++++++++++++");
    }
    Log.e("ololeeTAG", "input verify " + inputVerifierBuilder.toString());
    Bitmap output =
        Bitmap.createBitmap(textBmp.getWidth(), textBmp.getHeight(), Bitmap.Config.ARGB_8888);
    Allocation allIn = Allocation.createFromBitmap(renderScript, textBmp);
    Allocation allOut = Allocation.createFromBitmap(renderScript, output);
    //scriptCGray.forEach_pinyin(allIn, allOut);
    allOut.copyTo(output);
    for (int i = 0; i < output.getWidth(); i++) {
      int pixel = output.getPixel(i, 0);
      Log.e("ololeeTAG", "output pix:" + pixel);
    }
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    //scriptCGray.destroy();
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityRenderScriptBlurBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());
    Bitmap bmp= BitmapFactory.decodeResource(getResources(),R.drawable.background);
    binding.originImage.setImageBitmap(BlurBitmap.iDontKnow(this,bmp));
    init();
    //Bitmap different = BitmapFactory.decodeResource(getResources(), R.drawable.different);
    //Bitmap blurBmp = BlurBitmap.blackGold(this, bmp);
    //binding.blurImage.setImageBitmap(blurBmp);
    //Bitmap grayBmp = BlurBitmap.pinyin(this,bmp,different);
    //binding.originImage.setImageBitmap(grayBmp);
    //binding.originImage.setOnClickListener(v->{
    //  new Thread(() -> {
    //    int min = 19968;
    //    int count = 20901;
    //    int arr[] = new int[count + 1];
    //    int width =150;
    //    int height =150;
    //
    //    Bitmap bitmap=Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);
    //    bitmap.eraseColor(Color.BLACK);
    //    Paint paint=new Paint();
    //    paint.setAntiAlias(true);
    //    paint.setStyle(Paint.Style.FILL);
    //    Canvas canvas=new Canvas(bitmap);
    //    for (int i = 0; i < count; i++) {
    //      char ch = (char) (i + min);
    //      int pinyin = Pinyin.getCode(ch);
    //      if (pinyin > PinyinData.PINYIN_TABLE.length) {
    //        arr[i] = -1;
    //        paint.setColor(Color.BLACK);
    //      } else {
    //        arr[i] = pinyin;
    //        int pinyin_high = (pinyin &0xff00)>>8;
    //        int pinyin_low = pinyin&0xff;
    //        paint.setColor(Color.argb(0xff,0x00,pinyin_high,pinyin_low));
    //      }
    //      canvas.drawPoint(i%width,i/width,paint);
    //    }
    //    Log.e("ololeeTAG", Arrays.toString(arr));
    //    saveBitmap(bitmap,"/sdcard/pinyinmap.png");
    //
    //
    //  }).start();
    //});
  }

  private void init() {
    calcInit();
    binding.originImage.setOnClickListener(v -> {
      calc("中文转拼音");
    });
  }

  public static void saveBitmap(Bitmap bitmap, String path) {
    String savePath;
    File filePic;
    if (Environment.getExternalStorageState().equals(
        Environment.MEDIA_MOUNTED)) {
      savePath = path;
    } else {
      Log.e("tag", "saveBitmap failure : sdcard not mounted");
      return;
    }
    try {
      filePic = new File(savePath);
      Log.e("ololeeTAG", filePic.getAbsoluteFile().getPath());
      if (!filePic.exists()) {
        filePic.createNewFile();
      }
      FileOutputStream fos = new FileOutputStream(filePic);
      bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
      fos.flush();
      fos.close();
    } catch (IOException e) {
      Log.e("tag", "saveBitmap: " + e.getMessage());
      return;
    }
    Log.i("tag", "saveBitmap success: " + filePic.getAbsolutePath());
  }
}