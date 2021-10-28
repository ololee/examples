package cn.ololee.jnidemo;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.Nullable;
import cn.ololee.jnidemo.databinding.ActivityOpenglGuassBlurBinding;

public class OpenGLGaussBlurActivity extends Activity {
  private ActivityOpenglGuassBlurBinding binding;
  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding=ActivityOpenglGuassBlurBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());

  }
}
