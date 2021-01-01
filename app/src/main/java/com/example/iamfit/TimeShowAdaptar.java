package com.example.iamfit;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TimeShowAdaptar extends RecyclerView.Adapter<TimeShowAdaptar.ViewHolderTimeShow> {
    List<TimeShowModelClass> timeShowModelClassList;
    public TimeShowAdaptar(List<TimeShowModelClass> timeShowModelClassList){
        this.timeShowModelClassList=timeShowModelClassList;
    }
    @NonNull
    @Override
    public ViewHolderTimeShow onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.timeshow,parent,false);
        return new ViewHolderTimeShow(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderTimeShow holder, int position) {
        String a=timeShowModelClassList.get(position).getTime();
        holder.setDataTimeShow(a);
    }

    @Override
    public int getItemCount() {
        return timeShowModelClassList.size();
    }

    public class ViewHolderTimeShow extends RecyclerView.ViewHolder {
        private TextView textViewTimeSet;
        public ViewHolderTimeShow(@NonNull View itemView) {
            super(itemView);
            textViewTimeSet=(TextView)itemView.findViewById(R.id.textViewTimeShow);
        }
        public void setDataTimeShow(String a){
            textViewTimeSet.setText(a);
        }
    }
}
