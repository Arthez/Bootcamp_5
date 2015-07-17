package com.droidsonroids.bootcamp.soccertablemanager.event;

public class RegisterRequestEvent {

	private final String mUserName;

	public RegisterRequestEvent(final String userName) {
		mUserName = userName;
	}

	public String getUserName() {
		return mUserName;
	}
}
