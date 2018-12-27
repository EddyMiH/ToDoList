package com.example.eddy.todolist.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.eddy.todolist.R;
import com.example.eddy.todolist.model.ToDoItem;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SecondActivity extends AppCompatActivity {
    public static final String ARG_ADD = "add";
    public static final String ARG_TODO = "getToDo";

    TextView pickedDate;
    private TextView colorIndex;
    Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog datePickerDialog;
    Date dateToDo;
    //private TextInputEditText title;
    private EditText title;
    private EditText description;
    private CheckBox mRepeatCheckBox;
    private RadioGroup mRepeatRadioGroup;
    private String repeat;
    private Button save_btn;
    private ToDoItem mToDoItem;
    private int priority = ToDoItem.MIN_PRIORITY;

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

    };

    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        pickedDate.setText("Date: " + sdf.format(myCalendar.getTime()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        colorIndex = findViewById(R.id.priority_index_textView_second);
        pickedDate = findViewById(R.id.text_view_for_date_second);
        mRepeatCheckBox = (CheckBox) findViewById(R.id.check_box_repeat_second);
        mRepeatRadioGroup = (RadioGroup) findViewById(R.id.RadioGroup);
        title =  findViewById(R.id.title_edit_text_second);
        description = findViewById(R.id.description_edit_text_second);
        save_btn = findViewById(R.id.btn_save_second);
        dateToDo = Calendar.getInstance().getTime();
        if(getIntent().hasExtra(ARG_TODO)){
            mToDoItem = getIntent().getParcelableExtra(ARG_TODO);
            setFills(mToDoItem);
        }
        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();

            }
        });


        mRepeatCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                toggleRepeatTypeGroupVisibility(isChecked); }
        });
        pickedDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog = new DatePickerDialog(SecondActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text

                                String date1 = dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year;
                                pickedDate.setText("Date: " + date1);
                                try{
                                    dateToDo = new SimpleDateFormat("MM/dd/yyyy").parse(date1);
                                }catch (Exception ex){
                                    ex.printStackTrace();
                                }

                            }
                        }, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();

            }
        });
        findViewById(R.id.textView_yellow_second).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                priority = ToDoItem.PRIORITY_YELLOW;
                colorIndex.setText("Yellow");
            }
        });
        findViewById(R.id.textView_blue_second).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                priority = ToDoItem.PRIORITY_BLUE;
                colorIndex.setText("Blue");
            }
        });
        findViewById(R.id.textView_green_second).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                priority = ToDoItem.PRIORITY_GREEN;
                colorIndex.setText("Green");
            }
        });
        findViewById(R.id.textView_red_second).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                priority = ToDoItem.PRIORITY_RED;
                colorIndex.setText("Red");
            }
        });
        findViewById(R.id.textView_white_second).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                priority = ToDoItem.PRIORITY_WHITE;
                colorIndex.setText("White");
            }
        });
    }

    private void setFills(ToDoItem mToDoItem) {
        title.setText(mToDoItem.getTitle());
        description.setText(mToDoItem.getDescription());
        dateToDo =  mToDoItem.getDate();
        pickedDate.setText(dateToDo.toString());
            switch (mToDoItem.getRepeat()) {
                case ToDoItem.NONE:
                    mRepeatCheckBox.setChecked(false);
                    break;
                case ToDoItem.DAILY:
                    mRepeatRadioGroup.check(R.id.radio_activity_todo_item_daily);
                    mRepeatCheckBox.setChecked(true);
                    break;
                case ToDoItem.WEEKLY:
                    mRepeatRadioGroup.check(R.id.radio_activity_todo_item_weekly);
                    mRepeatCheckBox.setChecked(true);
                    break;
                case ToDoItem.MONTHLY:
                    mRepeatRadioGroup.check(R.id.radio_activity_todo_item_monthly);
                    mRepeatCheckBox.setChecked(true);
                    break;
            }
            mRepeatRadioGroup.setVisibility(View.VISIBLE);
    }

    private void saveData() {
        if(checkInput()){
            createToDoItem();
            Bundle bundle = new Bundle();
            Intent intent = new Intent();
            bundle.putParcelable(ARG_TODO, mToDoItem);
            intent.putExtras(bundle);
            setResult(RESULT_OK, intent);
            finish();
        }

    }

    private void createToDoItem() {
        if (mToDoItem == null) {
            mToDoItem = new ToDoItem();
        }
        mToDoItem.setTitle(title.getText().toString());
        mToDoItem.setDescription(description.getText().toString());
        mToDoItem.setDate(dateToDo);
        mToDoItem.setPriority(priority);
        if (mRepeatCheckBox.isChecked()) {
            switch (mRepeatRadioGroup.getCheckedRadioButtonId()) {
                case R.id.radio_activity_todo_item_daily:
                    mToDoItem.setRepeat(ToDoItem.DAILY);
                    break;
                case R.id.radio_activity_todo_item_weekly:
                    mToDoItem.setRepeat(ToDoItem.WEEKLY);
                    break;
                case R.id.radio_activity_todo_item_monthly:
                    mToDoItem.setRepeat(ToDoItem.MONTHLY);
                    break;
                default:
                    mToDoItem.setRepeat(ToDoItem.NONE);
            }

        }
    }
    private boolean checkInput(){
        boolean isValid;
        if (TextUtils.isEmpty(title.getText().toString())) {
            isValid = false;
            title.setError("Title field is required");
        } else {
            isValid = true;
            title.setError(null);
        }

        return isValid;
    }
    private void toggleRepeatTypeGroupVisibility(boolean isChecked) {
        if (isChecked) {
            mRepeatRadioGroup.setVisibility(View.VISIBLE);
        } else {
            mRepeatRadioGroup.setVisibility(View.GONE);
        }
    }
}



