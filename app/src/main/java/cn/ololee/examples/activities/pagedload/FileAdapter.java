package cn.ololee.examples.activities.pagedload;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.paging.PagingDataAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import cn.ololee.examples.databinding.SimpletvBinding;
import cn.ololee.filesearch.FileInfo;
import kotlinx.coroutines.CoroutineDispatcher;
import org.jetbrains.annotations.NotNull;

public class FileAdapter extends PagingDataAdapter<FileInfo, FileAdapter.VH> {

  public FileAdapter(@NotNull DiffUtil.ItemCallback<FileInfo> diffCallback,
      @NotNull CoroutineDispatcher mainDispatcher) {
    super(diffCallback, mainDispatcher);
  }

  public FileAdapter(@NotNull DiffUtil.ItemCallback<FileInfo> diffCallback) {
    super(diffCallback);
  }

  public FileAdapter(@NotNull
      DiffUtil.ItemCallback<FileInfo> diffCallback,
      @NotNull CoroutineDispatcher mainDispatcher,
      @NotNull CoroutineDispatcher workerDispatcher) {
    super(diffCallback, mainDispatcher, workerDispatcher);
  }

  @NonNull
  @NotNull
  @Override
  public VH onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
    SimpletvBinding binding= SimpletvBinding.inflate(LayoutInflater.from(parent.getContext()));
    return new VH(binding);
  }

  @Override public void onBindViewHolder(@NonNull @NotNull VH holder, int position) {
    holder.binding.simpleeTv.setText(getItem(position).getName());
  }

  class VH extends RecyclerView.ViewHolder {
    private SimpletvBinding binding;
    public VH(SimpletvBinding binding) {
      super(binding.getRoot());
      this.binding=binding;
    }
  }
}
