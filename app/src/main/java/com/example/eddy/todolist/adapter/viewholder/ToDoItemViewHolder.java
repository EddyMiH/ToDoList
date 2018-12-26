package com.example.eddy.todolist.adapter.viewholder;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.example.eddy.todolist.R;

public class ToDoItemViewHolder extends RecyclerView.ViewHolder {

    public TextView title;
    public TextView description;
    public TextView date;
    public TextView repeat;
    public CardView priority;
    private OnItemClickListener mOnItemClickListener;

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(getAdapterPosition());
                }
            }
    };

    public ToDoItemViewHolder(@NonNull View itemView) {
        super(itemView);

        title = itemView.findViewById(R.id.title_view_todo_activity_main);
        description = itemView.findViewById(R.id.description_view_todo_activity_main);
        date = itemView.findViewById(R.id.date_view_todo_activity_main);
        repeat = itemView.findViewById(R.id.repeat_view_todo_activity_main);
        priority = itemView.findViewById(R.id.cardView_activity_main);

        itemView.setOnClickListener(mOnClickListener);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }
    public interface OnItemClickListener {
        void onItemClick(int adapterPosition);
    }
}
