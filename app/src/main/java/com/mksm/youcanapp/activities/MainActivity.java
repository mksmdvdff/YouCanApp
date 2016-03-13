package com.mksm.youcanapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mksm.youcanapp.R;
import com.mksm.youcanapp.database.handlers.AddictionDatabaseHandler;
import com.mksm.youcanapp.database.handlers.TaskDatabaseHandler;
import com.mksm.youcanapp.entities.implementations.Addiction;
import com.mksm.youcanapp.entities.implementations.Task;
import com.mksm.youcanapp.session.YouCanSession;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button taskButton;
    Button addictionButton;
    Button getMyDay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        onlyTest();
    }

    private void onlyTest() {
        TaskDatabaseHandler tdh = new TaskDatabaseHandler(this);
        Task task1 = new Task("1", Calendar.getInstance());
        Task task2 = new Task("2", Calendar.getInstance());
        tdh.addNewTask(task1);
        tdh.addNewTask(task2);
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.DAY_OF_MONTH, -5);
        Addiction add1 = new Addiction("1", startDate, 45);
        Addiction add2 = new Addiction("2", startDate, 45);
        AddictionDatabaseHandler adh = new AddictionDatabaseHandler(this);
        adh.addNewAddiction(add1);
        adh.addNewAddiction(add2);
        tdh.getAll();
    }

    private void init() {
        YouCanSession.initNewSession(Calendar.getInstance());
        taskButton = (Button) findViewById(R.id.main_add_task);
        addictionButton = (Button) findViewById(R.id.main_add_addiction);
        getMyDay = (Button) findViewById(R.id.main_get_tasks);
        taskButton.setOnClickListener(this);
        addictionButton.setOnClickListener(this);
        getMyDay.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_add_task:
                Intent taskIntent = new Intent(this, AddTaskActivity.class);
                startActivity(taskIntent);
                break;
            case R.id.main_add_addiction :
                Intent addIntent = new Intent(this, AddAddictionActivity.class);
                startActivity(addIntent);
                break;
            case R.id.main_get_tasks :
                Intent getDayIntent = new Intent(this, DayReportActivity.class);
                startActivity(getDayIntent);
                break;
            default:
                break;
        }
    }
}
