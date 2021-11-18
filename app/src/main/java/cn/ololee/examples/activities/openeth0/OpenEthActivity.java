package cn.ololee.examples.activities.openeth0;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import cn.ololee.examples.R;

public class OpenEthActivity extends AppCompatActivity {

  @Override
  protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.empty);
  }

  @Override protected void onResume() {
    super.onResume();
    new Thread(() -> {
      ExeCommand exeCommand=new ExeCommand();
      exeCommand.run("ifconfig eth0 172.21.121.23 down ",100*1000);
    }).start();
  }
}
