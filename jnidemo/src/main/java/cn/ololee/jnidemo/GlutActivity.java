package cn.ololee.jnidemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.renderscript.Allocation;
import androidx.renderscript.Element;
import androidx.renderscript.RenderScript;
import androidx.renderscript.ScriptIntrinsic3DLUT;
import androidx.renderscript.Type;

public class GlutActivity extends AppCompatActivity implements View.OnClickListener{
  ImageView mImageView;
  RenderScript mRs;
  Bitmap mBitmap;
  Bitmap mLutBitmap;
  ScriptIntrinsic3DLUT mScriptlut;
  Bitmap mOutputBitmap;
  Allocation mAllocIn;
  Allocation mAllocOut;
  Allocation mAllocCube;
  int mFilter = 0;
  int[] mLut3D = {
      R.drawable.lut_vintage,
      R.drawable.lut_bleach,
      R.drawable.lut_blue_crush,
      R.drawable.lut_bw_contrast,
      R.drawable.lut_instant,
      R.drawable.lut_punch,
      R.drawable.lut_washout,
      R.drawable.lut_washout_color,
      R.drawable.lut_x_process
  };

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_glut);
    mImageView = (ImageView) findViewById(R.id.imageView);
    mImageView.setOnClickListener(this);
    mRs = RenderScript.create(this);
    Background background = new Background();
    background.execute();
  }

  @Override
  public void onClick(View v) {
    mFilter = (1 + mFilter) % (mLut3D.length + 1);
    Background background = new Background();
    background.execute();
  }

  class Background extends AsyncTask<Void, Void, Void> {

    @Override
    protected Void doInBackground(Void... params) {
      int redDim, greenDim, blueDim;
      int w, h;
      int[] lut;

      if (mScriptlut == null) {
        mScriptlut = ScriptIntrinsic3DLUT.create(mRs, Element.U8_4(mRs));
      }
      if (mBitmap == null) {
        mBitmap = BitmapFactory.decodeResource(getResources(),
            R.drawable.bugs);

        mOutputBitmap = Bitmap.createBitmap(mBitmap.getWidth(), mBitmap.getHeight(), mBitmap.getConfig());

        mAllocIn = Allocation.createFromBitmap(mRs, mBitmap);
        mAllocOut = Allocation.createFromBitmap(mRs, mOutputBitmap);
      }
      if (mFilter != 0) {
        mLutBitmap = BitmapFactory.decodeResource(getResources(), mLut3D[mFilter - 1]);
        w = mLutBitmap.getWidth();
        h = mLutBitmap.getHeight();
        redDim = w / h;
        greenDim = redDim;
        blueDim = redDim;
        int[] pixels = new int[w * h];
        lut = new int[w * h];
        mLutBitmap.getPixels(pixels, 0, w, 0, 0, w, h);
        int i = 0;

        for (int r = 0; r < redDim; r++) {
          for (int g = 0; g < greenDim; g++) {
            int p = r + g * w;
            for (int b = 0; b < blueDim; b++) {
              lut[i++] = pixels[p + b * h];
            }
          }
        }

      } else {
        // identity filter provided for refrence
        redDim = greenDim = blueDim = 16;
        lut = new int[redDim * greenDim * blueDim];
        int i = 0;
        for (int r = 0; r < redDim; r++) {
          for (int g = 0; g < greenDim; g++) {
            for (int b = 0; b < blueDim; b++) {
              int bcol = (b * 255) / blueDim;
              int gcol = (g * 255) / greenDim;
              int rcol = (r * 255) / redDim;
              lut[i++] = bcol | (gcol << 8) | (rcol << 16);
            }
          }
        }
      }
      Type.Builder tb = new Type.Builder(mRs, Element.U8_4(mRs));
      tb.setX(redDim).setY(greenDim).setZ(blueDim);
      Type t = tb.create();
      mAllocCube = Allocation.createTyped(mRs, t);
      mAllocCube.copyFromUnchecked(lut);

      mScriptlut.setLUT(mAllocCube);
      mScriptlut.forEach(mAllocIn, mAllocOut);

      mAllocOut.copyTo(mOutputBitmap);
      return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
      mImageView.setImageBitmap(mOutputBitmap);
    }
  }
}