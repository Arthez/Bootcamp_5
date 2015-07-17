package com.droidsonroids.bootcamp.soccertablemanager.event;

public class JoinRequestEvent {

	private final int mTableId;
	private final int mUserId;

	public JoinRequestEvent(final int tableId, final int userId) {
		mTableId = tableId;
		mUserId = userId;
	}

	public int getTableId() {
		return mTableId;
	}

	public int getUserId() {
		return mUserId;
	}
}
