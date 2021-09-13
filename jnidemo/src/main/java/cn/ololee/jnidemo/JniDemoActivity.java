package cn.ololee.jnidemo;

import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class JniDemoActivity extends AppCompatActivity {
  private TextView jniTv;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_jni_demo);
    jniTv = findViewById(R.id.jnitv);

    jniTv.setText(JniEntry.sayHello());
  }
}