package cn.ololee.jnidemo.earth;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import cn.ololee.jnidemo.R;
import cn.ololee.jnidemo.databinding.ActivityEarthBinding;

public class EarthActivity extends AppCompatActivity {
  private ActivityEarthBinding binding;
  private EarthGLSurfaceView earthGLSurfaceView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityEarthBinding.inflate(getLayoutInflater());
    earthGLSurfaceView = new EarthGLSurfaceView(this);
    setContentView(earthGLSurfaceView);
  }

  @Override protected void onPause() {
    super.onPause();
    earthGLSurfaceView.onPause();
  }

  @Override protected void onResume() {
    super.onResume();
    earthGLSurfaceView.onResume();
  }
}