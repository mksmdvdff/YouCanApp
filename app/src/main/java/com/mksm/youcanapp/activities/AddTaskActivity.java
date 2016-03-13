package com.mksm.youcanapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mksm.youcanapp.R;
import com.mksm.youcanapp.database.DatabaseUtils;
import com.mksm.youcanapp.database.handlers.TaskDatabaseHandler;
import com.mksm.youcanapp.entities.implementations.Task;

import javax.inject.Inject;

import roboguice.inject.InjectView;

public class AddTaskActivity extends AppCompatActivity implements View.OnClickListener {
    @InjectView(R.id.task_text)EditText taskText;
    @InjectView(R.id.task_date)EditText taskDate;
    @InjectView(R.id.task_save_button) Button save;
    @InjectView(R.id.task_clear_button) Button cancel;
    @Inject

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        init();
    }

    private void init() {

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
                    Toast.makeText(this, "Не заполнено одно из полей", Toast.LENGTH_SHORT).show();
                }
                else {
                    Task task = new Task(text, DatabaseUtils.longToDate(Long.parseLong(date)));
                    TaskDatabaseHandler tdh = new TaskDatabaseHandler(this);
                    tdh.addNewTask(task);
                }
                break;
            case (R.id.task_clear_button) :
                taskText.getText().clear();
                taskDate.getText().clear();

        }


    }
}
