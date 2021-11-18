package cn.ololee.jnidemo.traingles.vbo;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import cn.ololee.jnidemo.R;
import cn.ololee.jnidemo.databinding.ActivityTriWithBufferBinding;

public class TriWithBufferActivity extends AppCompatActivity {
  private ActivityTriWithBufferBinding binding;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityTriWithBufferBinding.inflate(getLayoutInflater());
    binding.getRoot().addView(new TriWithBufferGLSurfaceView(this));
    setContentView(binding.getRoot());
  }
}