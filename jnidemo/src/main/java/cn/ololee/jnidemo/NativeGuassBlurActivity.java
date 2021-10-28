package cn.ololee.jnidemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import cn.ololee.jnidemo.databinding.ActivityNativeGuassBlurBinding;

public class NativeGuassBlurActivity extends AppCompatActivity {
  private ActivityNativeGuassBlurBinding binding;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding=ActivityNativeGuassBlurBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());
    binding.originImage.setImageResource(R.drawable.background);
    Bitmap bg = BitmapFactory.decodeResource(getResources(), R.drawable.background);
    JniEntry.guassBlur(bg);
    binding.blurImage.setImageBitmap(bg);
    //binding.originImage
  }
}