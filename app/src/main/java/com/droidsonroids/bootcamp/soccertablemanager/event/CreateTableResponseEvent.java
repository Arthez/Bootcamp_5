package com.droidsonroids.bootcamp.soccertablemanager.event;

public class CreateTableResponseEvent {

	private final String mStatus;
	private final int mTableId;
	private final ApiError mApiError;

	public CreateTableResponseEvent(final String status, final int tableId) {
		this(status, tableId, null);
	}

	public CreateTableResponseEvent(final ApiError apiError) {
		this(null, -1, apiError);
	}

	public CreateTableResponseEvent(final String status, final int tableId, final ApiError apiError) {
		mStatus = status;
		mTableId = tableId;
		mApiError = apiError;
	}

	public String getStatus() {
		return mStatus;
	}

	public int getTableId() {
		return mTableId;
	}

	public ApiError getApiError() {
		return mApiError;
	}
}
