package com.example.eddy.todolist.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.eddy.todolist.model.ToDoItem;

import java.util.List;

@Dao
public interface ToDoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addToDo(ToDoItem toDoItem);

    @Query("DELETE FROM todo_table")
    void deleteAll();

//    @Query("DELETE FROM todo_table WHERE ")
//    void deleteFromList(List<ToDoItem> toDoList);
    @Delete
    void deleteOneRow(ToDoItem toDoItem);

    @Query("SELECT * FROM todo_table ORDER BY title ASC")
    void orderByTitle();

    @Query("SELECT * FROM todo_table ORDER BY title DESC")
    void orderByTitleDec();

    @Update
    void upDateItem(ToDoItem toDoItem);

    @Query("SELECT * FROM todo_table")
    List<ToDoItem> getAllToDoItems();

}
