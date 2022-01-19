package com.student.lab3.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.student.lab3.R;
import com.student.lab3.model.SavesModel;

import java.util.ArrayList;

public class SavesAdapter extends RecyclerView.Adapter<SavesAdapter.SavesViewHolder> {

    private ArrayList<SavesModel> list = new ArrayList<SavesModel>();

    @NonNull
    @Override
    public SavesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_saves, parent, false);
        return new SavesViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull SavesViewHolder holder, int position) {
        //Заполняем элемент в списке
        SavesModel model = list.get(position);
        holder.date.setText("Saving Date: " + model.getDate());
        holder.password.setText("Password: " + model.getPassword());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setList(ArrayList<SavesModel> list) {
        if (!this.list.isEmpty()) this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public class SavesViewHolder extends RecyclerView.ViewHolder {

        public TextView date;
        public TextView password;

        public SavesViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.text_item_date);
            password = itemView.findViewById(R.id.text_item_password);
        }
    }
}
