package com.droidsonroids.bootcamp.soccertablemanager.event;

public class JoinResponseEvent {

	private final String mStatus;
	private final ApiError mApiError;

	public JoinResponseEvent(final String status) {
		this(status, null);
	}

	public JoinResponseEvent(final ApiError apiError) {
		this(null, apiError);
	}

	public JoinResponseEvent(final String status, final ApiError apiError) {
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
