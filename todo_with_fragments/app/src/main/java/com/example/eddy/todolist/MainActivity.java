package com.example.eddy.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.eddy.todolist.adapter.ToDoItemAdapter;
import com.example.eddy.todolist.fragments.FragmentAdd;
import com.example.eddy.todolist.model.ToDoItem;

public class MainActivity extends AppCompatActivity implements  FragmentAdd.SendData {

    public static final int REQUEST_CODE_ADD = 100;
    public static final int REQUEST_CODE_EDIT = 101;
    public static final String  CODE_FOR_EDIT = "edit_code";

    private boolean isIncreaseTitle = false;
    private boolean isIncreaseDate = false;

    public static boolean isInActionMode = false;
    public Toolbar toolbar;
    ActionMode mActionMode;

    private ToDoItemAdapter recyclerAdapter;

//    public CheckBox selectedItems;
//    public ArrayList<ToDoItem> selectedItemsArray = new ArrayList<>();

    private ToDoItemAdapter.OnItemSelectedListener mOnItemSelectedListener = new ToDoItemAdapter.OnItemSelectedListener() {
        @Override
        public void onItemSelected(ToDoItem todoItem) {

            //Log.d("click handle", "onItemSelected: handle");
            Bundle bundle = new Bundle();
            bundle.putParcelable(FragmentAdd.ARG_TODO,todoItem);
            FragmentAdd fragment = new FragmentAdd();
            fragment.setArguments(bundle);
            findViewById(R.id.container).setVisibility(View.VISIBLE);

            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();

        }
    };

    private ToDoItemAdapter.OnItemLongClickedListener mOnItemLongClickedListener = new ToDoItemAdapter.OnItemLongClickedListener() {
        @Override
        public void onItemLongClicked() {
            isInActionMode = true;
            if(mActionMode == null){
                isInActionMode = true;
                mActionMode = startSupportActionMode(mActionModeCallBack);
                recyclerAdapter.notifyDataSetChanged();

            }
        }
    };

    private ActionMode.Callback mActionModeCallBack = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            actionMode.getMenuInflater().inflate(R.menu.menu_contextual_delete_activity_main, menu);
            //mActionMode.setTitle(0 + " selected");

            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            switch (menuItem.getItemId()){
                case R.id.delete_items:
                    deleteCheckedItems();
                    actionMode.finish();
                    return true;
                default:
                    return false;
            }

        }

        @Override
        public void onDestroyActionMode(ActionMode actionMode) {
            mActionMode = null;
            isInActionMode = false;
            recyclerAdapter.notifyDataSetChanged();
        }
    };

    public void changeAcionModeTitle(){
        mActionMode.setTitle(recyclerAdapter.getSelectedItemsQuantity() + " selected");
    }

    public void deleteCheckedItems(){
        recyclerAdapter.removeItems();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                create();
            }
        });
        initRecyclerView();


        final Button sortTitle = findViewById(R.id.btn_sort_activity_main);
        final Button sortDate = findViewById(R.id.btn_sort_date_activity_main);

        sortTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isIncreaseTitle = recyclerAdapter.sortDataByTitle(isIncreaseTitle);
                if (isIncreaseTitle){
                    sortTitle.setText("Title Sort(by Increase)");
                }else{
                    sortTitle.setText("Title Sort(by Decrease)");
                }
            }
        });

        sortDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isIncreaseDate = recyclerAdapter.sortDataByDate(isIncreaseDate);
                if (isIncreaseTitle){
                    sortDate.setText("Date Sort(by Increase)");
                }else{
                    sortDate.setText("Date Sort(by Decrease)");
                }
            }
        });
    }

    private void initRecyclerView() {
        recyclerAdapter = new ToDoItemAdapter();
        recyclerAdapter.setOnItemSelectedListener(mOnItemSelectedListener);
        recyclerAdapter.setOnItemLongClickedListener(mOnItemLongClickedListener);
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
