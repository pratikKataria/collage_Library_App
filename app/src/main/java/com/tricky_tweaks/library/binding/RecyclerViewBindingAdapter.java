package com.tricky_tweaks.library.binding;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.List;

public class RecyclerViewBindingAdapter {

    @BindingAdapter({"scannedList", "adapter"})
    public static void recyclerView(RecyclerView recyclerView, List<?> list, RecyclerView.Adapter adapter) {
        if (list == null) {
            return;
        }

        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager == null) {
            layoutManager = new StaggeredGridLayoutManager(2, RecyclerView.VERTICAL);
            recyclerView.setLayoutManager(layoutManager);
        }


        RecyclerView.Adapter recyclerViewAdapter = recyclerView.getAdapter();
        if (recyclerViewAdapter == null) {
            recyclerViewAdapter = adapter;
            recyclerView.setAdapter(recyclerViewAdapter);
        }

    }
}
