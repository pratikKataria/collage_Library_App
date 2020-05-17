package com.tricky_tweaks.library.scanner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.tricky_tweaks.library.databinding.CardviewDateHeaderBinding;
import com.tricky_tweaks.library.databinding.CardviewScannerBinding;
import com.tricky_tweaks.library.interfaces.ListHeader;
import com.tricky_tweaks.library.model.DateHeader;
import com.tricky_tweaks.library.model.LibraryEntryModel;
import com.tricky_tweaks.library.utils.LogMessage;

import java.util.List;

public class ScannedRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ListHeader> studentList;
    private Context context;
    private static final int HEADER = 1;
    private static final int CHILD = 2;

    public ScannedRecyclerViewAdapter(List<ListHeader> studentList, Context context) {
        this.studentList = studentList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflaterLayout = LayoutInflater.from(parent.getContext());
        RecyclerView.ViewHolder viewHolder;
        if (viewType == HEADER) {
            LogMessage.eMess("header ");
            CardviewDateHeaderBinding cardviewDateHeaderBinding = CardviewDateHeaderBinding.inflate(inflaterLayout, parent, false);
            StaggeredGridLayoutManager.LayoutParams layoutParams = new StaggeredGridLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setFullSpan(true);
            viewHolder = new DateViewViewHolder(cardviewDateHeaderBinding);
            viewHolder.itemView.setLayoutParams(layoutParams);
        } else {
            CardviewScannerBinding cardviewScannerBinding = CardviewScannerBinding.inflate(inflaterLayout, parent, false);
            viewHolder = new ScannedRecyclerViewHolder(cardviewScannerBinding);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ScannedRecyclerViewHolder) {

            ScannedRecyclerViewHolder scannedRecyclerViewHolder = (ScannedRecyclerViewHolder) holder;
            LibraryEntryModel libraryEntryModel = (LibraryEntryModel) studentList.get(position);
            scannedRecyclerViewHolder.setBinding(libraryEntryModel);

        } else {

            DateViewViewHolder dateViewViewHolder = (DateViewViewHolder) holder;
            DateHeader dateHeader = (DateHeader) studentList.get(position);
            dateViewViewHolder.setBinding(dateHeader);
        }
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    @Override
    public int getItemViewType(int position) {
        ListHeader listHeader = studentList.get(position);
        if (listHeader.isHeader()) {
            LogMessage.eMess("HEADER");
            return HEADER;
        } else {
            LogMessage.eMess("CHILD");
            return CHILD;
        }
    }

    static class DateViewViewHolder extends RecyclerView.ViewHolder {
        CardviewDateHeaderBinding cardviewDateHeaderBinding;
        public DateViewViewHolder(CardviewDateHeaderBinding itemView) {
            super(itemView.getRoot());
            cardviewDateHeaderBinding = itemView;
        }

        void setBinding(DateHeader dateHeader) {
            cardviewDateHeaderBinding.setDate(dateHeader);
            cardviewDateHeaderBinding.executePendingBindings();
        }
    }

    static class ScannedRecyclerViewHolder extends  RecyclerView.ViewHolder {
        CardviewScannerBinding cardviewScannerBinding;
        public ScannedRecyclerViewHolder(CardviewScannerBinding cardviewScannerBinding) {
            super(cardviewScannerBinding.getRoot());
            this.cardviewScannerBinding = cardviewScannerBinding;
        }

        void setBinding(LibraryEntryModel libraryEntryModel) {
            cardviewScannerBinding.setLibraryEntry(libraryEntryModel);
            cardviewScannerBinding.executePendingBindings();
        }

    }
}
