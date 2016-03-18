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
import com.mksm.youcanapp.presenters.AddCheckablePresenter;

import javax.inject.Inject;

import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

@ContentView(R.layout.activity_add_task)
public class AddTaskActivity extends AppCompatActivity implements View.OnClickListener {
    @InjectView(R.id.task_text)EditText taskText;
    @InjectView(R.id.task_date)EditText taskDate;
    @InjectView(R.id.task_save_button) Button save;
    @InjectView(R.id.task_clear_button) Button cancel;
    @Inject
    AddCheckablePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        save.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.task_save_button):
                if (checkFields()) {
                    String text = taskText.getText().toString();
                    String date = taskDate.getText().toString();
                    Task task = new Task(text, DatabaseUtils.longToDate(Long.parseLong(date)));
                    task.save();
                }
                break;
            case (R.id.task_clear_button) :
                taskText.getText().clear();
                taskDate.getText().clear();

        }


    }

    private boolean checkFields() {
        return !taskText.getText().toString().isEmpty() && !taskDate.getText().toString().isEmpty();
    }
}
