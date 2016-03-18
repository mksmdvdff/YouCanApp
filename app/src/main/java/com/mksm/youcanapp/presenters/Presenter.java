package com.mksm.youcanapp.presenters;

import com.mksm.youcanapp.activities.MVPView;

/**
 * Created by mksm on 16.03.2016.
 */
public interface Presenter {
	public void bindView(MVPView mvpView) ;

	public void unbindView() ;
}
