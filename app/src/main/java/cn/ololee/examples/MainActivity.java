package cn.ololee.examples;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
    implements ActivityListAdapter.OnItemClickListener {

  private RecyclerView recyclerView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    recyclerView = findViewById(R.id.activities);
    requestPermission();
    recyclerView.setLayoutManager(
        new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    try {
      List<String> currentPkgList = getPackageInfo(getPackageName());
      ActivityListAdapter activityListAdapter = new ActivityListAdapter(this,currentPkgList );
      activityListAdapter.setOnItemClickListener(this::itemclick);
      recyclerView.setAdapter(activityListAdapter);
    } catch (PackageManager.NameNotFoundException e) {
      e.printStackTrace();
    }
  }

  public List<String> getPackageInfo(String pkgName) throws PackageManager.NameNotFoundException {
    List<String> activityNames = new ArrayList<>();
    PackageManager packageManager = getPackageManager();
    PackageInfo packageInfo =
        packageManager.getPackageInfo(pkgName, PackageManager.GET_ACTIVITIES);
    ActivityInfo[] activityInfos = packageInfo.activities;
    for (ActivityInfo activityInfo : activityInfos) {
      if (!activityInfo.name.equalsIgnoreCase(getClass().getName())) {
        activityNames.add(activityInfo.name);
        Log.e("ololeeTAG", activityInfo.name);
      }
    }
    return activityNames;
  }

  @Override
  public void itemclick(String className) {
    Toast.makeText(this, className, Toast.LENGTH_SHORT).show();
    try {
      startActivity(className);
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  public void startActivity(String className) throws ClassNotFoundException {
    Intent intent = new Intent(this, Class.forName(className));
    startActivity(intent);
  }

  private void requestPermission() {
    ActivityCompat.requestPermissions(this,new String[]{
        Manifest.permission.READ_EXTERNAL_STORAGE
    },10);
  }
}