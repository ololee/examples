package cn.ololee.jnidemo.traingles;

import android.app.Activity;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import cn.ololee.jnidemo.R;
import cn.ololee.jnidemo.databinding.ActivityTrainglesBinding;

public class TrainglesActivity extends AppCompatActivity {
  private ActivityTrainglesBinding binding;
  private TrainglesGLSurfaceView surfaceView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityTrainglesBinding.inflate(getLayoutInflater());
    TrainglesRenderer trainglesRenderer = new TrainglesRenderer();
    surfaceView = new TrainglesGLSurfaceView(this,trainglesRenderer);
    binding.getRoot().addView(surfaceView);
    setContentView(binding.getRoot());
  }
}