package com.mksm.youcanapp.presenters;

import android.os.Bundle;

import com.mksm.youcanapp.activities.MVPView;

import org.roboguice.shaded.goole.common.cache.Cache;
import org.roboguice.shaded.goole.common.cache.CacheBuilder;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by mksm on 16.03.2016.
 */
public class PresenterManager {
	private static final String SIS_KEY_PRESENTER_ID = "presenter_id";
	private static PresenterManager instance;

	private final AtomicLong currentId;

	private final Cache<Long, Presenter> presenters;

	private PresenterManager(long maxSize, long expirationValue, TimeUnit expirationUnit) {
		currentId = new AtomicLong();

		presenters = CacheBuilder.newBuilder()
				.maximumSize(maxSize)
				.expireAfterWrite(expirationValue, expirationUnit) //TODO ОБЯЗАТЕЛЬНО РАЗОБРАТЬСЯ С ВРЕМЕНЕМ! ССЫЛКА: https://habrahabr.ru/post/278769/
				.build();
	}

	public static PresenterManager getInstance() {
		if (instance == null) {
			instance = new PresenterManager(10, 30, TimeUnit.SECONDS);
		}
		return instance;
	}

	public <P extends Presenter> P restorePresenter(Bundle savedInstanceState) {
		Long presenterId = savedInstanceState.getLong(SIS_KEY_PRESENTER_ID);
		P presenter = (P) presenters.getIfPresent(presenterId);
		presenters.invalidate(presenterId);
		return presenter;
	}

	public void savePresenter(Presenter presenter, Bundle outState) {
		long presenterId = currentId.incrementAndGet();
		presenters.put(presenterId, presenter);
		outState.putLong(SIS_KEY_PRESENTER_ID, presenterId);
	}

	public Presenter getPresenter(MVPView mvpView) {
		return null; //TODO!!!!
	}
}
