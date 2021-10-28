package cn.ololee.examples.activities.pagedload;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.paging.PagingData;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import cn.ololee.examples.R;
import cn.ololee.examples.databinding.ActivityPagedLoadedBinding;
import cn.ololee.filesearch.FileInfo;
import cn.ololee.filesearch.FileQueryCallback;
import cn.ololee.filesearch.FileUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.jetbrains.annotations.NotNull;

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