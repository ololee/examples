package cn.ololee.jnidemo.opengl;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import cn.ololee.jnidemo.R;
import cn.ololee.jnidemo.TextureMapNative;
import cn.ololee.jnidemo.databinding.ActivityTextureMapBinding;
import java.nio.ByteBuffer;

public class TextureMapActivity extends AppCompatActivity {

  private ActivityTextureMapBinding binding;
  private MyGLRenderer myGLRenderer = new MyGLRenderer();
  private TextureMapSurfaceView surfaceView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityTextureMapBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());
    surfaceView = new TextureMapSurfaceView(this, myGLRenderer);
    binding.getRoot().addView(surfaceView);
    setBitmap();
  }

  private void setBitmap() {
    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.background);
    int bytesLen = bitmap.getByteCount();
    ByteBuffer buffer = ByteBuffer.allocate(bytesLen);
    bitmap.copyPixelsToBuffer(buffer);
    byte[] array = buffer.array();
    TextureMapNative.transferBitmap(bitmap.getWidth(), bitmap.getHeight(), array);
    //TextureMapNative.init();
    //TextureMapNative.draw();
    //TextureMapNative.releaseJNIVar();
  }
}