package com.mksm.youcanapp.presenters;

import android.support.annotation.NonNull;

import com.mksm.youcanapp.activities.MVPView;

import java.lang.ref.WeakReference;

/**
 * Created by mksm on 16.03.2016.
 */
public abstract class PresenterImpl implements Presenter {

	MVPView view;

	@Override
	public void bindView(@NonNull MVPView view) {
		this.view = view;
		updateView();
	}

	public abstract void updateView();

	@Override
	public void unbindView() {
		this.view = null;
	}
}
