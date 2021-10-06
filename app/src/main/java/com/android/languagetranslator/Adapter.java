package com.android.languagetranslator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.viewHolder> {
    public Context context;
    public ArrayList<Holder> arrayList;
    public Adapter(Context context, ArrayList<Holder> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }
    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new viewHolder(LayoutInflater.from(context).inflate(R.layout.model_item,parent));
    }
    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.Name.setText(arrayList.get(position).getName());
        holder.Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Deleted successfully!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    public static class viewHolder extends RecyclerView.ViewHolder {
        public TextView Name;
        public ImageView Delete;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            Name=itemView.findViewById(R.id.model_name);
            Delete=itemView.findViewById(R.id.delete_model);
        }
    }
}
