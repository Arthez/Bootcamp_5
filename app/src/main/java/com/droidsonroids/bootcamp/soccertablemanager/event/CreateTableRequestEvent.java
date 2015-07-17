package com.droidsonroids.bootcamp.soccertablemanager.event;

public class CreateTableRequestEvent {

	private final int mUserId;
	private final String mTimeText;

	public CreateTableRequestEvent(final String timeText, final int userId) {
		mTimeText = timeText;
		mUserId = userId;
	}

	public int getUserId() {
		return mUserId;
	}

	public String getTimeText() {
		return mTimeText;
	}
}
