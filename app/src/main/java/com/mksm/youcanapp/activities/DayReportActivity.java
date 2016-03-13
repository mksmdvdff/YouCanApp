package com.mksm.youcanapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.mksm.youcanapp.R;
import com.mksm.youcanapp.database.handlers.AddictionDatabaseHandler;
import com.mksm.youcanapp.database.handlers.TaskDatabaseHandler;
import com.mksm.youcanapp.entities.implementations.Addiction;
import com.mksm.youcanapp.entities.implementations.Task;
import com.mksm.youcanapp.entities.interfaces.Checkable;
import com.mksm.youcanapp.session.CheckableAdapter;
import com.mksm.youcanapp.session.YouCanSession;

import java.util.List;

public class DayReportActivity extends AppCompatActivity implements View.OnClickListener {

    Button save;
    Button cancel;
    List<Task> tasks;
    List<Addiction> addictions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_report);
        init();
        initListViews();
    }

    private void init() {
        save = (Button) findViewById(R.id.report_save);
        save.setOnClickListener(this);
        cancel = (Button) findViewById(R.id.report_cancel);
        cancel.setOnClickListener(this);
    }

    private void initListViews() {
        {
            ListView taskListView = (ListView) findViewById(R.id.report_task_list_view);
            TaskDatabaseHandler tdh = new TaskDatabaseHandler(this);
            tasks = tdh.getTasksByDate(YouCanSession.get().getDate());
            ArrayAdapter<Checkable> adapter = new CheckableAdapter(this, tasks);
            taskListView.setAdapter(adapter);
            ActivityUtils.setDynamicHeight(taskListView);
        }
        {
            ListView addictionListView = (ListView) findViewById(R.id.report_addiction_list_view);
            AddictionDatabaseHandler adh = new AddictionDatabaseHandler(this);
            addictions = adh.getAddictionsByDate(YouCanSession.get().getDate());
            ArrayAdapter<Checkable> adapter = new CheckableAdapter(this, addictions);
            addictionListView.setAdapter(adapter);
            ActivityUtils.setDynamicHeight(addictionListView);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.report_save :
                TaskDatabaseHandler tdh = new TaskDatabaseHandler(this);
                tdh.updateTasks(tasks);
                AddictionDatabaseHandler adh = new AddictionDatabaseHandler(this);
                adh.updateAddictions(addictions);
                finish();
                break;
            case R.id.report_cancel :
                finish();
        }
    }
}
