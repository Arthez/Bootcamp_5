package com.droidsonroids.bootcamp.soccertablemanager.event;

public class LeaveRequestEvent {

	private final int mTableId;
	private final int mUserId;

	public LeaveRequestEvent(final int tableId, final int userId) {
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
