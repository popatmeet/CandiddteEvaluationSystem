/*package com.example.candidateevaluationsystemandroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class recycleradapter extends RecyclerView.Adapter<MyHolder> {

    Context context;
    ArrayList<ModelData> models;

    public recycleradapter(Context context, ArrayList<ModelData> models) {
        this.context = context;
        this.models = models;

    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_design,null);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

       // holder.mhname.setText(models.get(position).getMname());
       // holder.mhemail.setText(models.get(position).getMemail());
        holder.mhaddress.setText(models.get(position).getMadderess());
        holder.mhcontact.setText(models.get(position).getMcontact());
        holder.mhscore.setText(models.get(position).getMscore());

    }

    @Override
    public int getItemCount() {
        return models.size();
    }
}
*/
