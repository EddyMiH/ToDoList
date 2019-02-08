package com.example.eddy.todolist.persistence;

import android.arch.persistence.room.Room;
import android.content.Context;

public class DatabaseWrapper {
    private static AppDatabase DbInstance = null;

    public static void create(Context context){
        if(DbInstance == null)
            DbInstance = Room.databaseBuilder(context, AppDatabase.class, "todo_table").build();

    }
    public static AppDatabase getAppDatabace(){
        if (DbInstance == null){
            throw new IllegalStateException("Database is not created");
        }
        return DbInstance;
    }
}
