package cn.ololee.examples.activities.controller;

import android.util.Log;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import cn.ololee.examples.R;

public class ControllerActivity extends AppCompatActivity {
  private ControllerView controllerView;
  private TextView resultTv;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_controller);
    controllerView = findViewById(R.id.controller);
    resultTv = findViewById(R.id.result);
    controllerView.setMoveListener(new ControllerView.MoveListener() {
      @Override public void move( float dx, float dy) {
        Log.e("ololeeTAG", "dx:" + dx + ",dy:" + dy);
        StringBuilder builder = new StringBuilder();
        if (dy < -0.5) {
          builder.append("前进");
        } else if (dy > 0.5) {
          builder.append("后退");
        }
        if (dx < -0.5) {
          builder.append("左转");
        } else if (dx > 0.5) {
          builder.append("右转");
        }
        if(dx==0&&dy==0){
          builder.append("停止");
        }
        resultTv.setText(builder.toString());
        Log.e("ololeeTAG__", builder.toString());
      }
    });
  }
}