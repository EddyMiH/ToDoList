package com.example.eddy.todolist.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.example.eddy.todolist.model.ToDoItem;

@Database(entities = {ToDoItem.class}, version = 1)
@TypeConverters({DateTypeConverter.class})
public abstract class AppDatabase  extends RoomDatabase {

    public abstract  ToDoDao toDoDao();
}
