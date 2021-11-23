package cn.ololee.jnidemo.shadertoy;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import cn.ololee.jnidemo.R;
import cn.ololee.jnidemo.databinding.ActivityShadertoyBinding;

public class ShadertoyActivity extends AppCompatActivity {

  private ActivityShadertoyBinding binding;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityShadertoyBinding.inflate(getLayoutInflater());
    binding.getRoot().addView(new ShadertoyGLSurfaceView(this));
    setContentView(binding.getRoot());
  }
}