package com.originals.meraqi;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class personalnfoAdapter extends RecyclerView.Adapter<personalnfoAdapter.viewholder> {
    List<personalDetails> list;

    public personalnfoAdapter(List<personalDetails> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.personal_info_layout,parent,false);
        viewholder vh=new viewholder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        holder.t1.setText(list.get(position).getName());
        holder.t2.setText(list.get(position).getEmail());
        holder.t3.setText(list.get(position).getPhone());
        holder.t4.setText(list.get(position).getSalary());
        holder.t5.setText(list.get(position).getMl());
        holder.t6.setText(list.get(position).getCl());
        holder.t7.setText(list.get(position).getDoj());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewholder extends RecyclerView.ViewHolder{
        TextView t1,t2,t3,t4,t5,t6,t7;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            t1=itemView.findViewById(R.id.yourName);
            t2=itemView.findViewById(R.id.yourEmail);
            t3=itemView.findViewById(R.id.yourPhone);
            t4=itemView.findViewById(R.id.yourSalary);
            t5=itemView.findViewById(R.id.yourML);
            t6=itemView.findViewById(R.id.yourCL);
            t7=itemView.findViewById(R.id.yourDOJ);
        }
    }
}
