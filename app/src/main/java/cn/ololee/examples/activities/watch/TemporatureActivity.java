package cn.ololee.examples.activities.watch;

import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import cn.ololee.examples.R;

public class TemporatureActivity extends AppCompatActivity {
  private WatchThermometerView watchThermometerView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_temporature);
    watchThermometerView = findViewById(R.id.thermometer);
    startThread();
  }

  private float getSpeed(int x) {
    x = Math.abs(x);
    return (100 * x - x * x)/(2500);
  }

  private void startThread() {
    Runnable runnable = () -> {
      int i = 0;
      int direction = 1;
      float speed = 0;
      while (true) {
        i += (direction);
        if (i == 100 || i == 0) {
          direction = -direction;
        }
        speed = getSpeed(i);

        int finalI = i;
        runOnUiThread(() -> {
          watchThermometerView.updateProgress((finalI * 1.0f) / 100);
        });
        try {
          Thread.sleep(20+(50/speed==0?1: (long) speed));
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    };
    Thread thread = new Thread(runnable);
    thread.start();
  }
}