package com.android.languagetranslator;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.common.model.RemoteModelManager;
import com.google.mlkit.nl.translate.TranslateLanguage;
import com.google.mlkit.nl.translate.TranslateRemoteModel;

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
        return new viewHolder(LayoutInflater.from(context).inflate(R.layout.model_item,parent,false));
    }
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        String name=arrayList.get(position).getName();
        if (name.equals("ar")){
            holder.Name.setText("Arabic");
        }else if (name.equals("hi")){
            holder.Name.setText("Hindi");
        }
        holder.Delete.setOnClickListener(view -> {
            String name1=holder.Name.getText().toString();
            if (name1.equals("Arabic")){
                deleteModel(TranslateLanguage.ARABIC,position);
            }
            else if (name1.equals("Hindi")){
                deleteModel(TranslateLanguage.HINDI,position);
            }
        });
    }
    @SuppressLint("NotifyDataSetChanged")
    private void deleteModel(String l1,int pos) {
        RemoteModelManager modelManager = RemoteModelManager.getInstance();
        TranslateRemoteModel germanModel =
                new TranslateRemoteModel.Builder(l1).build();
        modelManager.deleteDownloadedModel(germanModel)
                .addOnSuccessListener(unused -> {
                    Toast.makeText(context, "Deleted successfully!", Toast.LENGTH_SHORT).show();
                    arrayList.remove(pos);
                    notifyDataSetChanged();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(context, "Unable to delete due to : -"+e.getMessage(), Toast.LENGTH_SHORT).show());
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
