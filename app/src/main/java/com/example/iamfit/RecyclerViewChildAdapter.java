package com.example.iamfit;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewChildAdapter extends RecyclerView.Adapter<RecyclerViewChildAdapter.ViewHolder1> {

    public RecyclerViewChildAdapter(List<RecyclerViewChildModelClass> modelClassList) {
        this.modelClassList = modelClassList;
    }

    private List<RecyclerViewChildModelClass>modelClassList;

    @NonNull
    @Override
    public ViewHolder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_child,parent,false);
        return new ViewHolder1(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder1 holder, int position) {
        int resource=modelClassList.get(position).getImageResource();
        String name=modelClassList.get(position).getName();
        String age=modelClassList.get(position).getAge();
        holder.setData1(resource,name,age);

    }

    @Override
    public int getItemCount() {
        return modelClassList.size();
    }

    class ViewHolder1 extends RecyclerView.ViewHolder{
        de.hdodenhof.circleimageview.CircleImageView imageView;
        TextView textView1,textView2;

        public ViewHolder1(@NonNull View itemView) {
            super(itemView);
            textView1=itemView.findViewById(R.id.textView37);
            textView2=itemView.findViewById(R.id.textView38);
            imageView=itemView.findViewById(R.id.profile_image_recyclerview_child);
        }
        public void setData1(int resource,String a,String b){
            imageView.setImageResource(resource);
            textView1.setText(a);
            textView2.setText(b);
        }
    }
}
