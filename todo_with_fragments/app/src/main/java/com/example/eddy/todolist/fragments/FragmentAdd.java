package com.example.eddy.todolist.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.eddy.todolist.MainActivity;
import com.example.eddy.todolist.R;
import com.example.eddy.todolist.activities.SecondActivity;
import com.example.eddy.todolist.model.ToDoItem;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class FragmentAdd extends Fragment {

    public static final String ARG_ADD = "add";
    public static final String ARG_TODO = "getToDo";
    boolean isCalledForEdit = false;

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
        switch (mToDoItem.getPriority()){
            case ToDoItem.PRIORITY_WHITE:
                colorIndex.setText("White");
                break;
            case ToDoItem.PRIORITY_YELLOW:
                colorIndex.setText("Yellow");
                break;
            case ToDoItem.PRIORITY_BLUE:
                colorIndex.setText("Blue");
                break;
            case ToDoItem.PRIORITY_GREEN:
                colorIndex.setText("Green");
                break;
            case ToDoItem.PRIORITY_RED:
                colorIndex.setText("Red");
                break;
        }
        priority = mToDoItem.getPriority();
        mRepeatRadioGroup.setVisibility(View.VISIBLE);
    }

    private void saveData() {
        if(checkInput()){
            createToDoItem();
            Bundle bundle = new Bundle();
            Intent intent = new Intent(getActivity().getBaseContext(), MainActivity.class);
            bundle.putParcelable(ARG_TODO, mToDoItem);
            intent.putExtras(bundle);
            if (isCalledForEdit){
//                MainActivity mainActivity = (MainActivity) getActivity();
//                mainActivity.upDateAfterEdit(intent);
                sendData.upDateItem(intent);
            }else{
//                MainActivity mainActivity = (MainActivity) getActivity();
//                mainActivity.upDateAfterAdd(intent);
                sendData.addNewItem(intent, this);
            }
            isCalledForEdit = false;
            getFragmentManager().beginTransaction().remove(this).commit();
            //getActivity().startActivity(intent);
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


    public FragmentAdd(){
        //required
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_add, container, false);
        colorIndex = rootView.findViewById(R.id.priority_index_textView_second);
        pickedDate = rootView.findViewById(R.id.text_view_for_date_second);
        mRepeatCheckBox = (CheckBox) rootView.findViewById(R.id.check_box_repeat_second);
        mRepeatRadioGroup = (RadioGroup) rootView.findViewById(R.id.RadioGroup);
        title =  rootView.findViewById(R.id.title_edit_text_second);
        description = rootView.findViewById(R.id.description_edit_text_second);
        save_btn = rootView.findViewById(R.id.btn_save_second);
        dateToDo = Calendar.getInstance().getTime();
        if(getArguments() != null) {
            if (!getArguments().isEmpty()) { //hasExtra(ARG_TODO)
                mToDoItem = getArguments().getParcelable(ARG_TODO);//getParcelableExtra(ARG_TODO);
                setFills(mToDoItem);
                isCalledForEdit = true;
            }
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
                datePickerDialog = new DatePickerDialog(getActivity(),
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
        rootView.findViewById(R.id.textView_yellow_second).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                priority = ToDoItem.PRIORITY_YELLOW;
                colorIndex.setText("Yellow");
            }
        });
        rootView.findViewById(R.id.textView_blue_second).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                priority = ToDoItem.PRIORITY_BLUE;
                colorIndex.setText("Blue");
            }
        });
        rootView.findViewById(R.id.textView_green_second).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                priority = ToDoItem.PRIORITY_GREEN;
                colorIndex.setText("Green");
            }
        });
        rootView.findViewById(R.id.textView_red_second).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                priority = ToDoItem.PRIORITY_RED;
                colorIndex.setText("Red");
            }
        });
        rootView.findViewById(R.id.textView_white_second).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                priority = ToDoItem.PRIORITY_WHITE;
                colorIndex.setText("White");
            }
        });
        return rootView;
    }

    public SendData sendData;
    public interface SendData {
        void addNewItem(Intent intent, FragmentAdd fragment);
        void upDateItem(Intent intent);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            sendData = (SendData) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException("Error in retrieving data. Please try again");
        }
    }
}