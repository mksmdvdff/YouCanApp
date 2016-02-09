package com.mksm.youcanapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mksm.youcanapp.R;
import com.mksm.youcanapp.database.DatabaseUtils;
import com.mksm.youcanapp.database.handlers.TaskDatabaseHandler;
import com.mksm.youcanapp.entities.implementations.Task;

public class AddTaskActivity extends AppCompatActivity implements View.OnClickListener {
    EditText taskText;
    EditText taskDate;
    Button save;
    Button cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        init();
    }

    private void init() {
        taskText = (EditText) findViewById(R.id.taskText);
        taskDate = (EditText) findViewById(R.id.taskDate);
        save = (Button) findViewById(R.id.task_save_button);
        cancel = (Button) findViewById(R.id.task_clear_button);
        save.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.task_save_button):
                String text = taskText.getText().toString();
                String date = taskDate.getText().toString();
                if (text.isEmpty() || date.isEmpty()) {
                    Toast.makeText(this, "Не заполнено одно из полей", Toast.LENGTH_SHORT);
                }
                else {
                    Task task = new Task(text, DatabaseUtils.longToDate(Long.parseLong(date)));
                    TaskDatabaseHandler tdh = new TaskDatabaseHandler(this);
                    tdh.open();
                    tdh.addNewTask(task);
                    tdh.close();
                }
                break;
            case (R.id.task_clear_button) :
                taskText.getText().clear();
                taskDate.getText().clear();

        }


    }
}
