package com.example.iamfit;

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

    public Medicine_List_Adapter(List<medicinePageModelClass> medicinePageModelClassList) {
        this.medicinePageModelClassList = medicinePageModelClassList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.medicine_item_layout,parent,false);
        return new ViewHolder(view);
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


    class ViewHolder extends RecyclerView.ViewHolder{
        private CheckBox checkBox;
        private TextView medicine,time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox=itemView.findViewById(R.id.checkBoxMedicine);
            medicine = itemView.findViewById(R.id.medicineItemLayoutMedicine);
            time=  itemView.findViewById(R.id.medicineItemLayoutTime);
        }

        public void setData(String a,String b,int c){
            if(c==1)checkBox.setChecked(true);
            else checkBox.setChecked(false);
            medicine.setText(a);
            time.setText(b);
        }
    }
}
