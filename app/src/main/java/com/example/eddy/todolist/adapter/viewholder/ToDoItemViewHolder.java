package com.example.eddy.todolist.adapter.viewholder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.eddy.todolist.R;

public class ToDoItemViewHolder extends RecyclerView.ViewHolder {

    public TextView title;
    public TextView description;
    public TextView date;

    public ToDoItemViewHolder(@NonNull View itemView) {
        super(itemView);

        title = itemView.findViewById(R.id.title_view_todo_activity_main);
        description = itemView.findViewById(R.id.description_view_todo_activity_main);
        date = itemView.findViewById(R.id.date_view_todo_activity_main);
    }
}
