package com.mksm.youcanapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mksm.youcanapp.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button taskButton;
    Button addictionButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        taskButton = (Button) findViewById(R.id.main_add_task);
        addictionButton = (Button) findViewById(R.id.main_add_addiction);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.task_add_button:
                Intent taskIntent = new Intent(this, AddTaskActivity.class);
                startActivity(taskIntent);
                break;
            case R.id.main_add_addiction :
                Intent addIntent = new Intent(this, AddAddictionActivity.class);
                startActivity(addIntent);
                break;
            default:
                break;
        }
    }
}
