package com.example.iamfit;
//The adapter class to use to populate the recycler view in medicine list
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Medicine_List_Adapter extends RecyclerView.Adapter<Medicine_List_Adapter.ViewHolder> {

    private List<medicinePageModelClass>medicinePageModelClassList;
    private ResultListener monResultListener;
    Context context;

    public Medicine_List_Adapter(Context context,List<medicinePageModelClass> medicinePageModelClassList,ResultListener onResultListener1) {
        this.medicinePageModelClassList = medicinePageModelClassList;
        this.context = context;
        this.monResultListener=onResultListener1;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.medicine_item_layout,parent,false);
        //return new ViewHolder(view);
       return new Medicine_List_Adapter.ViewHolder(view,monResultListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String medicine=medicinePageModelClassList.get(position).getMedicineName();
        String time=medicinePageModelClassList.get(position).getTime();
        int checkboxInt=medicinePageModelClassList.get(position).getCheckBoxInt();
        holder.setData(medicine,time,checkboxInt);
    }

    @Override
    public int getItemCount() {
        return medicinePageModelClassList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private CheckBox checkBox;
        private TextView medicine,time;
        ResultListener onResultListener1;

        public ViewHolder(@NonNull View itemView, ResultListener onResultListener1) {
            super(itemView);
            checkBox=itemView.findViewById(R.id.checkBoxMedicine);
            medicine = itemView.findViewById(R.id.medicineItemLayoutMedicine);
            time=  itemView.findViewById(R.id.medicineItemLayoutTime);
            itemView.setOnClickListener(this);
            this.onResultListener1=onResultListener1;
        }

        public void setData(String a,String b,int c){
            if(c==1)checkBox.setChecked(true);
            else checkBox.setChecked(false);
            medicine.setText(a);
            time.setText(b);
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
