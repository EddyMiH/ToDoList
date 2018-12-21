package com.example.eddy.todolist.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class ToDoItem implements Parcelable {

    public static final int PRIORITY_YELLOW = 0;
    public static final int PRIORITY_ORANGE = 1;
    public static final int PRIORITY_BLUE = 2;
    public static final int PRIORITY_GREEN = 3;
    public static final int PRIORITY_RED = 4;

    private String title;
    private String description;
    private long id;
    private Date date;
    private int priority;
    private String repeat;

    public ToDoItem(){

    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setRepeat(String repeat) {
        this.repeat = repeat;
    }

    public String getTitle() {

        return title;
    }

    public String getDescription() {
        return description;
    }

    public long getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public int getPriority() {
        return priority;
    }

    public String getRepeat() {
        return repeat;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(description);
        dest.writeLong(id);
        dest.writeLong(date.getTime());
        dest.writeInt(priority);
        dest.writeString(repeat);

    }
    public static final Parcelable.Creator<ToDoItem> CREATOR = new Creator<ToDoItem>() {
        @Override
        public ToDoItem createFromParcel(Parcel source) {
            return ToDoItem.createFromParcel(source);
        }

        @Override
        public ToDoItem[] newArray(int size) {
            return new ToDoItem[size];
        }
    };

    private static ToDoItem createFromParcel(Parcel source){
        ToDoItem todo = new ToDoItem();
        todo.title = source.readString();
        todo.description = source.readString();
        todo.id = source.readLong();
        todo.date = new Date(source.readLong());
        todo.priority = source.readInt();
        todo.repeat = source.readString();

        return todo;
    }

    @Override
    public String toString() {
        return title + "\n" + description + "\n" + date + "\n" + repeat + "\n";
    }
}
