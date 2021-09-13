package cn.ololee.examples.activities.imagesplice;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import cn.ololee.examples.R;

public class ImageSpliceActivity extends AppCompatActivity {
  private ImageSpliceView imageSpliceView;
  private ImageSpliceView imageSpliceViewRight;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_image_splice);
    imageSpliceView = findViewById(R.id.image_splice_view);
    imageSpliceViewRight=findViewById(R.id.image_splice_view_right);
    setBmp();
  }

  private void setBmp() {
    Bitmap bitmap = BitmapFactory.decodeFile("/sdcard/1.jpg");
    Bitmap scaledBitmapLeft = Bitmap.createScaledBitmap(bitmap, 1080, 540, false);
    imageSpliceView.setBmp(scaledBitmapLeft,true);
    Bitmap bmp2 = BitmapFactory.decodeFile("/sdcard/2.jpg");
    Bitmap scaledBitmapRight =Bitmap.createScaledBitmap(bmp2,1080,540,false);
    imageSpliceViewRight.setBmp(scaledBitmapRight,false);
  }
}