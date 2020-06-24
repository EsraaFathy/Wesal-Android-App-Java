package com.example.wesal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class TimeAdabter extends RecyclerView.Adapter<TimeAdabter.ViewHolder> {
    private Context context;
    private List<TimeModel> timeModelList;

    TimeAdabter(Context context, List<TimeModel> timeModelList) {
        this.context = context;
        this.timeModelList = timeModelList;
    }

    @NonNull
    @Override
    public TimeAdabter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_desgin_time, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeAdabter.ViewHolder holder, int position) {
       TimeModel timeModel=timeModelList.get(position);
       holder.circleImageView.setImageResource(timeModel.getImage());
       holder.content.setText(timeModel.getContent());
       holder.title.setText(timeModel.getTitle());
       holder.time.setText(timeModel.getTime());

    }

    @Override
    public int getItemCount() {
        return timeModelList.size();
    }



      static class ViewHolder extends RecyclerView.ViewHolder {

          CircleImageView circleImageView;
          TextView title, content, time;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            circleImageView=itemView.findViewById(R.id.imageViewItemTime);
            title=itemView.findViewById(R.id.titleOfItemTime);
            content=itemView.findViewById(R.id.contentItemDesign);
            time=itemView.findViewById(R.id.timeItemTime);
        }
    }
}
