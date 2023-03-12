package com.originals.meraqi;

import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class complaintRecyclerAdapter extends RecyclerView.Adapter<complaintRecyclerAdapter.ViewHolder> {
    List<complaintDetails> list;
    //String searched;

    public complaintRecyclerAdapter(List<complaintDetails> list) {
        this.list = list;
        //this.searched=searched;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.complaints_row_layout,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            holder.t1.setText(list.get(position).getName());
            holder.t2.setText(list.get(position).getCategory());
            holder.t3.setText(list.get(position).getDescription());
            boolean isExpanded=list.get(position).isExpanded();
            holder.expandablelayout.setVisibility(isExpanded?View.VISIBLE:View.GONE);
            holder.expandImg.setImageResource(isExpanded?R.drawable.ic_baseline_expand_less_24:R.drawable.ic_baseline_expand_more_24);
//            holder.leave_layout.setVisibility(isExpanded&&list.get(position).getCategory().equalsIgnoreCase("leave")?View.VISIBLE:View.GONE);
//            holder.abuse_layout.setVisibility(isExpanded&&list.get(position).getCategory().equalsIgnoreCase("abuse")?View.VISIBLE:View.GONE);
//              holder.b1.setVisibility(isExpanded&&list.get(position).getCategory().equalsIgnoreCase("leave")?View.VISIBLE:View.GONE);
//              holder.b2.setVisibility(isExpanded&&list.get(position).getCategory().equalsIgnoreCase("leave")?View.VISIBLE:View.GONE);
    }

    @Override
    public int getItemCount() {

       return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView t1,t2,t3;
        ConstraintLayout expandablelayout,main_layout,abuse_layout,leave_layout;
        Button b1,b2;
        ImageView expandImg;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            t1=itemView.findViewById(R.id.complaintName);
            t2=itemView.findViewById(R.id.complaintcategory);
            t3=itemView.findViewById(R.id.complaintDescription);
            expandImg=itemView.findViewById(R.id.expandImage);
            expandablelayout=itemView.findViewById(R.id.expandableLayout);
            main_layout=itemView.findViewById(R.id.titleConsLayout);
//            abuse_layout=itemView.findViewById(R.id.abuseLayout);
//            leave_layout=itemView.findViewById(R.id.leaveLayout);
//          b2=itemView.findViewById(R.id.dontapprove);
//            b1=itemView.findViewById(R.id.approve);
//            b3=itemView.findViewById(R.id.leaveDontApprove);
            main_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    complaintDetails cd=list.get(getAdapterPosition());
                    Log.d("layout",cd.isExpanded()==true?"a":"b");
                    cd.setExpanded(!cd.isExpanded());
                    notifyItemChanged(getAdapterPosition());
                }
            });

        }
    }
}
