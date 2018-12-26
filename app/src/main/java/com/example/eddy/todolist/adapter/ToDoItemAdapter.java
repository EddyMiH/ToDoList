package com.example.eddy.todolist.adapter;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eddy.todolist.R;
import com.example.eddy.todolist.adapter.viewholder.ToDoItemViewHolder;
import com.example.eddy.todolist.model.ToDoItem;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ToDoItemAdapter extends RecyclerView.Adapter<ToDoItemViewHolder> {

    private ArrayList<ToDoItem> mData = new ArrayList<>();
    private OnItemSelectedListener mOnItemSelectedListener;

    private ToDoItemViewHolder.OnItemClickListener mOnItemClickListener =
            new ToDoItemViewHolder.OnItemClickListener() {
                @Override
                public void onItemClick(int adapterPosition) {
                    if (mOnItemSelectedListener != null) {
                        mOnItemSelectedListener.onItemSelected(mData.get(adapterPosition));
                    }
                }
            };


    @NonNull
    @Override
    public ToDoItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.view_todo_activity_main, viewGroup, false);
        ToDoItemViewHolder viewHolder = new ToDoItemViewHolder(view);
        viewHolder.setOnItemClickListener(mOnItemClickListener);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ToDoItemViewHolder toDoItemViewHolder, int i) {
        ToDoItem todo = mData.get(i);
         toDoItemViewHolder.title.setText(todo.getTitle() + "\n");
         toDoItemViewHolder.description.setText(todo.getDescription() );
         toDoItemViewHolder.date.setText(formatDateToLongStyle(todo.getDate()) );
         toDoItemViewHolder.repeat.setText(todo.getRepeat());
         switch (todo.getPriority()){
             case ToDoItem.PRIORITY_YELLOW:
                 toDoItemViewHolder.priority.setCardBackgroundColor(Color.YELLOW);
                 break;
             case ToDoItem.PRIORITY_BLUE:
                 toDoItemViewHolder.priority.setCardBackgroundColor(Color.BLUE);
                 break;
             case ToDoItem.PRIORITY_GREEN:
                 toDoItemViewHolder.priority.setCardBackgroundColor(Color.GREEN);
                 break;
             case ToDoItem.PRIORITY_RED:
                 toDoItemViewHolder.priority.setCardBackgroundColor(Color.RED);
                 break;
             case ToDoItem.PRIORITY_ORANGE:
                 toDoItemViewHolder.priority.setCardBackgroundColor(Color.rgb(255, 127, 0));
                 break;
         }

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void addItem(ToDoItem toDoItem) {
        mData.add(toDoItem);
        notifyItemInserted(mData.size() - 1);
    }

    public void updateItem(ToDoItem item) {

        for (int i = 0; i < mData.size(); i++) {
            if (item.equals( mData.get(i))) {
                mData.set(i, item);
                notifyItemChanged(i);
            }
        }
    }
    public static String formatDateToLongStyle(Date date) {
        DateFormat format = SimpleDateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.SHORT);
        return format.format(date);
    }

    public void setOnItemSelectedListener(OnItemSelectedListener onItemSelectedListener) {
        mOnItemSelectedListener = onItemSelectedListener;
    }

    public interface OnItemSelectedListener {
        void onItemSelected(ToDoItem todoItem);
    }

}
