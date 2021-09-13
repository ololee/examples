package cn.ololee.examples;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import static cn.ololee.examples.utils.StringUtils.getNameFromClassName;

public class ActivityListAdapter extends RecyclerView.Adapter<ActivityListAdapter.VH> {
  private List<String> data;
  private Context context;
  private OnItemClickListener onItemClickListener;

  public ActivityListAdapter(Context context, List<String> data) {
    this.data = data;
    this.context = context;
  }

  @NonNull
  @Override
  public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View itemview =
        LayoutInflater.from(parent.getContext()).inflate(R.layout.single_activity, parent, false);
    VH vh=new VH(itemview);
    return vh;
  }

  @Override public void onBindViewHolder(@NonNull ActivityListAdapter.VH holder, int position) {
    holder.activityName.setText(getNameFromClassName(data.get(position)));
    if(onItemClickListener!=null){
      holder.itemView.setOnClickListener(v -> {
        onItemClickListener.itemclick(data.get(position));
      });
    }
  }

  @Override public int getItemCount() {
    return data.size();
  }

  class VH extends RecyclerView.ViewHolder {
    private TextView activityName;

    public VH(
        @NonNull View itemView) {
      super(itemView);
      activityName=itemView.findViewById(R.id.single_activity_item);
    }
  }

  public interface OnItemClickListener{
    void itemclick(String className);
  }

  public void setOnItemClickListener(
      OnItemClickListener onItemClickListener) {
    this.onItemClickListener = onItemClickListener;
  }
}
