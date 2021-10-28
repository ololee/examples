package cn.ololee.jnidemo.mmap;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import cn.ololee.jnidemo.databinding.ActivityMmapBinding;

public class MmapActivity extends AppCompatActivity {

  private ActivityMmapBinding binding;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityMmapBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());
  }
}