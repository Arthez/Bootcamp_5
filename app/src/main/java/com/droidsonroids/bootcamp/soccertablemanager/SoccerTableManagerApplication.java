package com.droidsonroids.bootcamp.soccertablemanager;

import android.app.Application;

import com.droidsonroids.bootcamp.soccertablemanager.api.ApiManger;

public class SoccerTableManagerApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		new ApiManger();
	}
}
