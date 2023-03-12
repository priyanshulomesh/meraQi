package com.originals.meraqi;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class scheduleRecyclerAdapter extends RecyclerView.Adapter<scheduleRecyclerAdapter.ViewHolder> {
    List<scheduleDetails> list;

    public scheduleRecyclerAdapter(List<scheduleDetails> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.schedule_layout,parent,false);
        ViewHolder vh=new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(list.get(position).getTitle());
        holder.description.setText(list.get(position).getDescription());
        holder.time.setText(list.get(position).getDate()+"/"+list.get(position).getMonth()+"/"+list.get(position).getYear()+" "+
                list.get(position).getHour()+":"+list.get(position).getMinute());
        holder.duration.setText(list.get(position).getDuration());
        holder.location.setText(list.get(position).getLocation());
        holder.exp_Layout.setVisibility(list.get(position).isExpanded()?View.VISIBLE:View.GONE);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView title,duration,location,time,description;
        ConstraintLayout title_Layout,exp_Layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.scheduleTitle);
            duration=itemView.findViewById(R.id.scheduleDuration);
            location=itemView.findViewById(R.id.scheduleLocation);
            time=itemView.findViewById(R.id.scheduleTime);
            description=itemView.findViewById(R.id.scheduleDescription);
            title_Layout=itemView.findViewById(R.id.scheduleTitleLayout);
            exp_Layout=itemView.findViewById(R.id.scheduleExpandableLayout);
            title_Layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    scheduleDetails ed=list.get(getAdapterPosition());
                    ed.setExpanded(!ed.isExpanded());
                    notifyItemChanged(getAdapterPosition());
                }
            });
        }
    }
}
