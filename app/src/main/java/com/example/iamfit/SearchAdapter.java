package com.example.iamfit;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {
    int cnt=1;
    Context context;
    ArrayList<String> names;
    ArrayList<String> Uid;
    private OnResultListener monResultListener;
    class SearchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView userName;
        OnResultListener onResultListener1;
        public SearchViewHolder(View itemView,OnResultListener onResultListener1){
            super (itemView);
            userName=(TextView) itemView.findViewById(R.id.ItemName);
            itemView.setOnClickListener(this);
            this.onResultListener1=onResultListener1;

        }
        @Override
        public void onClick(View view) {
            onResultListener1.onResultClick(getAdapterPosition());
            //return names.size();
        }

    }

    SearchAdapter(Context context, ArrayList<String> names, ArrayList<String> uid,OnResultListener onResultListener1) {
        this.context = context;
        this.names = names;
        Uid = uid;
        this.monResultListener=onResultListener1;
    }

    @NonNull
    @Override
    public SearchAdapter.SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.search_list_item,parent,false);
        return new SearchAdapter.SearchViewHolder(view,monResultListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final SearchViewHolder holder, int position) {
        holder.userName.setText(cnt+"."+names.get(position));
        cnt++;
    }



    @Override
    public int getItemCount() {
        return names.size();
    }
    public  interface OnResultListener{
        void onResultClick(int position);
    }
}
