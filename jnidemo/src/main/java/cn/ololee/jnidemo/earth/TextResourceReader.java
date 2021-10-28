package cn.ololee.jnidemo.earth;

import android.content.Context;
import android.content.res.AssetManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TextResourceReader {
  public static String readTextFileFromResource(Context context, int id) {
    InputStream inputStream = context.getResources().openRawResource(id);
    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
    String line;
    StringBuilder result = new StringBuilder();
    try {
      while ((line = bufferedReader.readLine()) != null) {
        result.append(line);
        result.append("\n");
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        bufferedReader.close();
        inputStreamReader.close();
        inputStream.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return result.toString();
  }
}
