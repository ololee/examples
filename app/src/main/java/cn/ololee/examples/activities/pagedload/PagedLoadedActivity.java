package cn.ololee.examples.activities.pagedload;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.paging.PagingData;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.ololee.examples.R;
import cn.ololee.examples.databinding.ActivityPagedLoadedBinding;
import cn.ololee.filesearch.FileInfo;
import cn.ololee.filesearch.FileQueryCallback;
import cn.ololee.filesearch.FileUtils;

import com.google.android.material.drawable.DrawableUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.jetbrains.annotations.NotNull;

import static android.widget.NumberPicker.OnScrollListener.SCROLL_STATE_FLING;
import static android.widget.NumberPicker.OnScrollListener.SCROLL_STATE_IDLE;
import static androidx.viewpager2.widget.ViewPager2.SCROLL_STATE_DRAGGING;

public class PagedLoadedActivity extends AppCompatActivity {
  private ActivityPagedLoadedBinding binding;
  private FileAdapter adapter;
  private List<FileInfo> contentList = new ArrayList<>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityPagedLoadedBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());
    init();
    binding.fileList.setLayoutManager(new LinearLayoutManager(this));
    binding.fileList.setAdapter(adapter);
    binding.fileList.setVerticalScrollBarEnabled(true);
    binding.fileList.setVerticalFadingEdgeEnabled(true);

    Drawable thumb=getResources().getDrawable(R.drawable.scroll_bar);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
      binding.fileList.setVerticalScrollbarThumbDrawable(thumb);
    }
    binding.fileList.addOnScrollListener(new RecyclerView.OnScrollListener() {
      @Override
      public void onScrollStateChanged(@NonNull @NotNull RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        int verticalScrollbarPosition = recyclerView.getVerticalScrollbarPosition();
        int itemCount = recyclerView.getAdapter().getItemCount();
        Log.d("ololeeTAG","verticalScrollbarPosition:"+verticalScrollbarPosition+",itemCount:"+itemCount);
        switch (newState){
          case SCROLL_STATE_IDLE:
            Log.d("ololeeTAG","onScrollStateChanged:idle");
            break;
          case SCROLL_STATE_DRAGGING:
            Log.d("ololeeTAG","onScrollStateChanged:dragging");
            break;
          case SCROLL_STATE_FLING:
            Log.d("ololeeTAG","onScrollStateChanged:fling");
            break;
        }
      }

      @Override
      public void onScrolled(@NonNull @NotNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        Log.d("ololeeTAG","dx:"+dx+",dy:"+dy);
      }
    });
    query();
  }

  private void init() {
    FileUtils.init();
    adapter = new FileAdapter(new DiffUtil.ItemCallback<FileInfo>() {
      @Override public boolean areItemsTheSame(@NonNull @NotNull FileInfo oldItem,
          @NonNull @NotNull FileInfo newItem) {
        return oldItem.equals(newItem);
      }

      @Override public boolean areContentsTheSame(@NonNull @NotNull FileInfo oldItem,
          @NonNull @NotNull FileInfo newItem) {
        return oldItem.equals(newItem);
      }
    });
  }

  private void query() {
    ///storage/F68A-4816/文件管理器测试资源/testphoto1
    new Thread(new Runnable() {
      @Override public void run() {
        long old = System.currentTimeMillis();
        long cost;

        FileUtils.getFilesInDir("/sdcard/testphoto1", 50,
            new FileQueryCallback() {
              @Override public void update(List<FileInfo> fileInfoList) {
                Log.d("ololeeTAG", fileInfoList.size() + "");
                long cost = System.currentTimeMillis() - old;
                Log.d("ololeeTAG", "cost:" + cost);
                contentList.addAll(fileInfoList);
                adapter.submitData(PagedLoadedActivity.this.getLifecycle(),
                    PagingData.from(contentList));
                Log.e("ololeeTAG","size:"+contentList.size());
              }
            });
        cost = System.currentTimeMillis() - old;
        Log.d("ololeeTAG", "cost:" + cost);
      }
    }).start();
  }
}