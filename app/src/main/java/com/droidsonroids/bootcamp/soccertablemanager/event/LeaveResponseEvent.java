package com.droidsonroids.bootcamp.soccertablemanager.event;

public class LeaveResponseEvent {

	private final String mStatus;
	private final ApiError mApiError;

	public LeaveResponseEvent(final String status) {
		this(status, null);
	}

	public LeaveResponseEvent(final ApiError apiError) {
		this(null, apiError);
	}

	public LeaveResponseEvent(final String status, final ApiError apiError) {
		mApiError = apiError;
		mStatus = status;
	}

	public String getStatus() {
		return mStatus;
	}

	public ApiError getApiError() {
		return mApiError;
	}
}
