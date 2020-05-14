package com.tricky_tweaks.library.scanner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tricky_tweaks.library.databinding.CardviewScannerBinding;
import com.tricky_tweaks.library.model.Student;

import java.util.List;

public class ScannedRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Student> studentList;
    private Context context;

    public ScannedRecyclerViewAdapter(List<Student> studentList, Context context) {
        this.studentList = studentList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardviewScannerBinding cardviewScannerBinding = CardviewScannerBinding.inflate(LayoutInflater.from(parent.getContext()), parent,false);
        return new ScannedRecyclerViewHolder(cardviewScannerBinding.getRoot()) ;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    static class ScannedRecyclerViewHolder extends  RecyclerView.ViewHolder {

        public ScannedRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
