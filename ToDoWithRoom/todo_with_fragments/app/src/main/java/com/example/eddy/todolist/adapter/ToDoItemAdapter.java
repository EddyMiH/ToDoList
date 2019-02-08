package com.example.eddy.todolist.adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.example.eddy.todolist.R;
import com.example.eddy.todolist.adapter.viewholder.ToDoItemViewHolder;
import com.example.eddy.todolist.persistence.AppDatabase;
import com.example.eddy.todolist.model.ToDoItem;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import static com.example.eddy.todolist.MainActivity.isInActionMode;

public class ToDoItemAdapter extends RecyclerView.Adapter<ToDoItemViewHolder> {

    private List<ToDoItem> mData = new ArrayList<>();

    public CheckBox selectedItems;
    public ArrayList<ToDoItem> selectedItemsArray = new ArrayList<>();

    private OnItemSelectedListener mOnItemSelectedListener;
    private  OnItemLongClickedListener mOnItemLongClickedListener;

    private AppDatabase database;

    public void setDatabase(AppDatabase database) {
        this.database = database;
    }

    private ToDoItemViewHolder.OnItemClickListener mOnItemClickListener =
            new ToDoItemViewHolder.OnItemClickListener() {
                @Override
                public void onItemClick(int adapterPosition) {
                    if (mOnItemSelectedListener != null) {
                        mOnItemSelectedListener.onItemSelected(mData.get(adapterPosition));
                    }
                }
            };

    private ToDoItemViewHolder.OnItemLongClickListener mOnItemLongClickListener =
            new ToDoItemViewHolder.OnItemLongClickListener() {
                @Override
                public void onItemLongClick() {
                    if(mOnItemLongClickedListener != null){
                        mOnItemLongClickedListener.onItemLongClicked();
                    }
                }
            };

    @NonNull
    @Override
    public ToDoItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.view_todo_activity_main, viewGroup, false);
        ToDoItemViewHolder viewHolder = new ToDoItemViewHolder(view);

        selectedItems = viewGroup.findViewById(R.id.check_box_multiSelect_remove);

        viewHolder.setOnItemClickListener(mOnItemClickListener);
        viewHolder.setOnLongItemClickListener(mOnItemLongClickListener);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ToDoItemViewHolder toDoItemViewHolder, int i) {
        final ToDoItem todo = mData.get(i);
         toDoItemViewHolder.title.setText(todo.getTitle() + "\n");
         toDoItemViewHolder.description.setText(todo.getDescription() );
         toDoItemViewHolder.date.setText(formatDateToLongStyle(todo.getDate()) );
         toDoItemViewHolder.repeat.setText(todo.getRepeat());
         switch (todo.getPriority()){
             case ToDoItem.PRIORITY_WHITE:
                 toDoItemViewHolder.priority.setCardBackgroundColor(Color.WHITE);
                 break;
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
         if(isInActionMode){
             toDoItemViewHolder.selectingForRemove.setVisibility(View.VISIBLE);
             toDoItemViewHolder.selectingForRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(selectedItemsArray.contains(todo)){
                        selectedItemsArray.remove(todo);
                    }else{
                        selectedItemsArray.add(todo);
                    }

                }
             });
         }else{
             toDoItemViewHolder.selectingForRemove.setVisibility(View.GONE);
             toDoItemViewHolder.selectingForRemove.setChecked(false);
         }

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public int getSelectedItemsQuantity(){
        return selectedItemsArray.size();
    }

    public void setMData(List<ToDoItem> toDoItemList){
        mData = toDoItemList;
    }

    public void removeItems(){
        Log.d(ToDoItemAdapter.class.getSimpleName(),"selectedItemsArray size in removeItems method: " + selectedItemsArray.size());
        for (int i = 0; i < selectedItemsArray.size(); ++i){
            final int index = i;
            final ToDoItem tempItem = selectedItemsArray.get(i);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    database.toDoDao().deleteOneRow(tempItem);
                }
            }).start();
            //database.toDoDao().deleteOneRow(selectedItemsArray.get(i));
            mData.remove(selectedItemsArray.get(i));

        }
        selectedItemsArray.clear();
    }

    public void addItem(final ToDoItem toDoItem) {
        mData.add(toDoItem);
        new Thread(new Runnable() {
            @Override
            public void run() {
                database.toDoDao().addToDo(toDoItem);            }
        }).start();
        //database.toDoDao().addToDo(toDoItem);
        notifyItemInserted(mData.size() - 1);
    }

    public void updateItem(ToDoItem item) {

        for (int i = 0; i < mData.size(); i++) {
            if (item.equals( mData.get(i))) {
                final int index = i;
                mData.set(i, item);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        database.toDoDao().upDateItem(mData.get(index));           }
                }).start();
                //database.toDoDao().upDateItem(mData.get(i));
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

    public void setOnItemLongClickedListener(OnItemLongClickedListener onItemLongClickedListener) {
        mOnItemLongClickedListener = onItemLongClickedListener;
    }

    public interface OnItemSelectedListener {
        void onItemSelected(ToDoItem todoItem);
    }

    public  interface  OnItemLongClickedListener{
        void onItemLongClicked();
    }

    public boolean sortDataByTitle(boolean isIncrease){
        if(isIncrease){
            Collections.sort(mData, new Comparator<ToDoItem>() {
                @Override
                public int compare(ToDoItem o1, ToDoItem o2) {
                    return o1.getTitle().compareTo(o2.getTitle());
                }
            });
            isIncrease = false;
        }else{
            Collections.sort(mData, new Comparator<ToDoItem>() {
                @Override
                public int compare(ToDoItem o1, ToDoItem o2) {
                    return o2.getTitle().compareTo(o1.getTitle());
                }
            });
            isIncrease = true;
        }
        notifyDataSetChanged();
        return isIncrease;
    }

    public boolean sortDataByDate(boolean isIncrease){
        if(isIncrease){
            Collections.sort(mData, new Comparator<ToDoItem>() {
                @Override
                public int compare(ToDoItem o1, ToDoItem o2) {
                    return o1.getDate().compareTo(o2.getDate());
                }
            });
            isIncrease = false;
        }else{
            Collections.sort(mData, new Comparator<ToDoItem>() {
                @Override
                public int compare(ToDoItem o1, ToDoItem o2) {
                    return o2.getDate().compareTo(o1.getDate());
                }
            });
            isIncrease = true;
        }
        notifyDataSetChanged();
        return isIncrease;
    }

}
