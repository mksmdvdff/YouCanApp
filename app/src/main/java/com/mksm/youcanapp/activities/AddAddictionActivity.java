package com.mksm.youcanapp.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mksm.youcanapp.R;

import com.mksm.youcanapp.presenters.AddCheckablePresenter;

import javax.inject.Inject;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

@ContentView(R.layout.activity_add_addiction)
public class AddAddictionActivity extends RoboActivity implements View.OnClickListener {
    @InjectView(R.id.addictionText) EditText addictionText;
    @InjectView(R.id.addictionDate) EditText addictionStartDate;
    @InjectView(R.id.addictionDuration) EditText addictionDuration;
    @InjectView(R.id.addiction_save_button)Button save;
    @InjectView(R.id.addiction_clear_button) Button clear;
    @Inject
    AddCheckablePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_addiction);
        init();
    }

    private void init() {
        save.setOnClickListener(this);
        clear.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.addiction_save_button):
                String text = addictionText.getText().toString();
                String date = addictionStartDate.getText().toString();
                String duration = addictionDuration.getText().toString();
                presenter.saveAddiction(text, date, duration);
                break;
            case (R.id.addiction_clear_button) :
                addictionText.getText().clear();
                addictionStartDate.getText().clear();
                addictionDuration.getText().clear();

        }


    }


}
