package com.example.eddy.todolist.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.example.eddy.todolist.adapter.viewholder.ToDoItemViewHolder;
import com.example.eddy.todolist.model.ToDoItem;

import java.util.ArrayList;

public class ToDoItemAdapter extends RecyclerView.Adapter<ToDoItemViewHolder> {

    private ArrayList<ToDoItem> mData = new ArrayList<>();

    @NonNull
    @Override
    public ToDoItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ToDoItemViewHolder toDoItemViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
