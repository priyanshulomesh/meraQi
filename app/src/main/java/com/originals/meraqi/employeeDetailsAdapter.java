package com.originals.meraqi;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class employeeDetailsAdapter extends RecyclerView.Adapter<employeeDetailsAdapter.viewHolder> {
    List<employeeDetails> list;

    public employeeDetailsAdapter(List<employeeDetails> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.employee_info_ayout,parent,false);
       viewHolder vh=new viewHolder(view);
       return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.t1.setText(list.get(position).getName());
        holder.t2.setText(list.get(position).getRole());
        holder.t3.setText(list.get(position).getEmail());
        holder.t4.setText(list.get(position).getPhone());
        holder.t5.setText(list.get(position).getSalary());
        holder.t6.setText(list.get(position).getMl());
        holder.t7.setText(list.get(position).getCl());
        holder.t8.setText(list.get(position).getDoj());
        boolean isExpanded=list.get(position).isExpanded();
        holder.expandable.setVisibility(isExpanded?View.VISIBLE:View.GONE);
        holder.i1.setImageResource(isExpanded?R.drawable.ic_baseline_expand_less_24:R.drawable.ic_baseline_expand_more_24);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class viewHolder extends RecyclerView.ViewHolder{
        TextView t1,t2,t3,t4,t5,t6,t7,t8;
        ImageView i1;
        ConstraintLayout title,expandable;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            t1=itemView.findViewById(R.id.employeeName);
            t2=itemView.findViewById(R.id.employeeRole);
            t3=itemView.findViewById(R.id.employeeEmail);
            t4=itemView.findViewById(R.id.employeePhone);
            t5=itemView.findViewById(R.id.employeeSalary);
            t6=itemView.findViewById(R.id.employeeML);
            t7=itemView.findViewById(R.id.employeeCL);
            t8=itemView.findViewById(R.id.employeeDOJ);
            i1=itemView.findViewById(R.id.employeeExpandImage);
            title=itemView.findViewById(R.id.titleLayoutPersonal);
            expandable=itemView.findViewById(R.id.expandableLayoutPersonal);
            title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    employeeDetails ed=list.get(getAdapterPosition());
                    ed.setExpanded(!ed.isExpanded());
                    notifyItemChanged(getAdapterPosition());
                }
            });
        }
    }
}
