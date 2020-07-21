package com.example.iamfit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {
    int cnt=1;
    Context context;
    ArrayList<String> names;
    ArrayList<String> Uid;
    class SearchViewHolder extends RecyclerView.ViewHolder{
        TextView userName;
        public SearchViewHolder(View itemView){
            super (itemView);
            userName=(TextView) itemView.findViewById(R.id.ItemName);

        }

    }

    SearchAdapter(Context context, ArrayList<String> names, ArrayList<String> uid) {
        this.context = context;
        this.names = names;
        Uid = uid;
    }

    @NonNull
    @Override
    public SearchAdapter.SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.search_list_item,parent,false);
        return new SearchAdapter.SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        holder.userName.setText(cnt+"."+names.get(position));
        cnt++;
    }


    @Override
    public int getItemCount() {
        return names.size();
    }
}
