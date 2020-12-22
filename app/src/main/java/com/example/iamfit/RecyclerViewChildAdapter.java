package com.example.iamfit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewChildAdapter extends RecyclerView.Adapter<RecyclerViewChildAdapter.ViewHolder1> {

    private List<RecyclerViewChildModelClass>modelClassList;
    private Context context;
    private RecyclerViewChildAdapter.ResultListener monResultListener;
    public RecyclerViewChildAdapter(Context context,List<RecyclerViewChildModelClass> modelClassList,RecyclerViewChildAdapter.ResultListener monResultListener) {
        this.context=context;
        this.modelClassList = modelClassList;
        this.monResultListener=monResultListener;
    }



    @NonNull
    @Override
    public ViewHolder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_child,parent,false);
        return new RecyclerViewChildAdapter.ViewHolder1(view,monResultListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder1 holder, int position) {
        String resource=modelClassList.get(position).getImageResource();
        String name=modelClassList.get(position).getName();
        String age=modelClassList.get(position).getAge();
        holder.setData1(resource,name,age);

    }

    @Override
    public int getItemCount() {
        return modelClassList.size();
    }

    class ViewHolder1 extends RecyclerView.ViewHolder implements View.OnClickListener{
        de.hdodenhof.circleimageview.CircleImageView imageView;
        TextView textView1,textView2;
        RecyclerViewChildAdapter.ResultListener onResultListener1;

        public ViewHolder1(@NonNull View itemView,RecyclerViewChildAdapter.ResultListener  onResultListener1) {
            super(itemView);
            textView1=itemView.findViewById(R.id.textView37);
            textView2=itemView.findViewById(R.id.textView38);
            imageView=itemView.findViewById(R.id.profile_image_recyclerview_child);
            itemView.setOnClickListener(this);
            this.onResultListener1=onResultListener1;
        }
        public void setData1(String resource,String a,String b){
            //imageView.setImageResource(resource);

            textView1.setText(a);
            textView2.setText(b);
        }
        @Override
        public void onClick(View v) {
            onResultListener1.onResultClick(getAdapterPosition());
        }
    }
    public  interface ResultListener{
        void onResultClick(int position);
    }
}
