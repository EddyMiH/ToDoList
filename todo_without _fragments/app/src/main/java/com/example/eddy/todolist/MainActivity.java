package com.example.eddy.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.example.eddy.todolist.activities.SecondActivity;
import com.example.eddy.todolist.adapter.ToDoItemAdapter;
import com.example.eddy.todolist.model.ToDoItem;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_ADD = 100;
    public static final int REQUEST_CODE_EDIT = 101;

    private ToDoItemAdapter recyclerAdapter;

    private ToDoItemAdapter.OnItemSelectedListener mOnItemSelectedListener = new ToDoItemAdapter.OnItemSelectedListener() {
        @Override
        public void onItemSelected(ToDoItem todoItem) {
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            intent.putExtra(SecondActivity.ARG_TODO, todoItem);
            startActivityForResult(intent, REQUEST_CODE_EDIT);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                create();
            }
        });
        initRecyclerView();
    }

    private void initRecyclerView() {
        recyclerAdapter = new ToDoItemAdapter();
        recyclerAdapter.setOnItemSelectedListener(mOnItemSelectedListener);
        RecyclerView recyclerView = findViewById(R.id.recycler_activity_main);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(recyclerAdapter);
        //recyclerAdapter.addItems();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case REQUEST_CODE_ADD:
                if(resultCode == RESULT_OK){
                    ToDoItem toDoItem = data.getParcelableExtra(SecondActivity.ARG_TODO); //ARG_ADD
                    recyclerAdapter.addItem(toDoItem);
                }
                break;
            case REQUEST_CODE_EDIT: {
                if (resultCode == RESULT_OK) {
                    ToDoItem todoItem = data.getParcelableExtra(SecondActivity.ARG_TODO);
                    recyclerAdapter.updateItem(todoItem);
                }
            }
            break;
        }
    }

    public void create(){
        Intent intent = new Intent(this, SecondActivity.class);
        startActivityForResult(intent, REQUEST_CODE_ADD);
    }


}
