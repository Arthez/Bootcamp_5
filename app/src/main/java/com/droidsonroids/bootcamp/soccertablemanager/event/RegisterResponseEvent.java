package com.droidsonroids.bootcamp.soccertablemanager.event;

public class RegisterResponseEvent {

	private final String mStatus;
	private final int mUserId;
	private final ApiError mApiError;

	public RegisterResponseEvent(final String status, final int userId) {
		this(status, userId, null);
	}

	public RegisterResponseEvent(final ApiError apiError) {
		this(null, -1, apiError);
	}

	public RegisterResponseEvent(final String status, final int userId, final ApiError apiError) {
		mStatus = status;
		mUserId = userId;
		mApiError = apiError;
	}

	public String getStatus() {
		return mStatus;
	}

	public int getUserId() {
		return mUserId;
	}

	public ApiError getApiError() {
		return mApiError;
	}
}
