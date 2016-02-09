package com.mksm.youcanapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
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

public class DayReportActivity extends AppCompatActivity {

    private ListView taskListView;
    private ListView addictionListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_report);
        initListViews();
    }

    private void initListViews() {
        {
            taskListView = (ListView) findViewById(R.id.report_task_list_view);
            TaskDatabaseHandler tdh = new TaskDatabaseHandler(this);
            tdh.open();
            List<Task> tasks = tdh.getTasksByDate(YouCanSession.get().getDate());
            tdh.close();
            ArrayAdapter<Checkable> adapter = new CheckableAdapter(this, tasks);
            taskListView.setAdapter(adapter);
        }
        {
            addictionListView = (ListView) findViewById(R.id.report_addiction_list_view);
            AddictionDatabaseHandler adh = new AddictionDatabaseHandler(this);
            adh.open();
            List<Addiction> addictions = adh.getAddictionsByDate(YouCanSession.get().getDate());
            adh.close();
            ArrayAdapter<Checkable> adapter = new CheckableAdapter(this, addictions);
            taskListView.setAdapter(adapter);
        }
    }
}
