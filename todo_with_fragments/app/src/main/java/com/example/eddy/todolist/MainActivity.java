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
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.example.eddy.todolist.activities.SecondActivity;
import com.example.eddy.todolist.adapter.ToDoItemAdapter;
import com.example.eddy.todolist.fragments.FragmentAdd;
import com.example.eddy.todolist.model.ToDoItem;

public class MainActivity extends AppCompatActivity implements  FragmentAdd.SendData {

    public static final int REQUEST_CODE_ADD = 100;
    public static final int REQUEST_CODE_EDIT = 101;
    public static final String  CODE_FOR_EDIT = "edit_code";

    private ToDoItemAdapter recyclerAdapter;

    private ToDoItemAdapter.OnItemSelectedListener mOnItemSelectedListener = new ToDoItemAdapter.OnItemSelectedListener() {
        @Override
        public void onItemSelected(ToDoItem todoItem) {

            Log.d("click hendling", "onItemSelected: handle");
            Bundle bundle = new Bundle();
            bundle.putParcelable(FragmentAdd.ARG_TODO,todoItem);
            FragmentAdd fragment = new FragmentAdd();
            fragment.setArguments(bundle);
            findViewById(R.id.container).setVisibility(View.VISIBLE);

            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();

        }
    };

    /*public void upDateAfterEdit(Intent data){
        findViewById(R.id.container).setVisibility(View.GONE);
        ToDoItem todoItem = data.getParcelableExtra(FragmentAdd.ARG_TODO);
        recyclerAdapter.updateItem(todoItem);

    }*/

    /*public void upDateAfterAdd(Intent data){
        findViewById(R.id.container).setVisibility(View.GONE);
        ToDoItem todoItem = data.getParcelableExtra(FragmentAdd.ARG_TODO);
        recyclerAdapter.addItem(todoItem);
    }*/

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

    public void create(){

        findViewById(R.id.container).setVisibility(View.VISIBLE);
        FragmentAdd fragmentAdd = new FragmentAdd();
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragmentAdd).commit();
    }


    @Override
    public void addNewItem(Intent intent, FragmentAdd fragment) {
        findViewById(R.id.container).setVisibility(View.GONE);
        ToDoItem todoItem = intent.getParcelableExtra(FragmentAdd.ARG_TODO);
        recyclerAdapter.addItem(todoItem);
        //getSupportFragmentManager().beginTransaction().remove(fragment);
    }

    @Override
    public void upDateItem(Intent intent) {
        findViewById(R.id.container).setVisibility(View.GONE);
        ToDoItem todoItem = intent.getParcelableExtra(FragmentAdd.ARG_TODO);
        recyclerAdapter.updateItem(todoItem);
    }
}
