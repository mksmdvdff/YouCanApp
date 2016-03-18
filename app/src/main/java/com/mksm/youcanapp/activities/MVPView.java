package com.mksm.youcanapp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mksm.youcanapp.presenters.Presenter;
import com.mksm.youcanapp.presenters.PresenterManager;

/**
 * Created by mksm on 16.03.2016.
 */
public abstract class MVPView extends AppCompatActivity {
	Presenter presenter;
	Class presenterClass;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initPresenterClass(presenterClass);

		if (savedInstanceState != null) {
			presenter = PresenterManager.getInstance().restorePresenter(savedInstanceState);
		}

		if (presenter == null) {
			presenter = PresenterManager.getInstance().getPresenter(this);
		}
	}

	protected abstract void initPresenterClass(java.lang.Class presenterClass);

	@Override
	protected void onResume() {
		super.onResume();

		presenter.bindView(this);
	}

	@Override
	protected void onPause() {
		super.onPause();

		presenter.unbindView();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		PresenterManager.getInstance().savePresenter(presenter, outState);
	}
}
