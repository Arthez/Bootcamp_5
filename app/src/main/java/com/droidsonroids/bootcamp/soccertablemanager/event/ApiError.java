package com.droidsonroids.bootcamp.soccertablemanager.event;

public class ApiError {

	private final String mErrorCode;
	private final String mErrorMessage;

	public ApiError(final String errorCode, final String errorMessage) {
		mErrorCode = errorCode;
		mErrorMessage = errorMessage;
	}

	public String getErrorCode() {
		return mErrorCode;
	}

	public String getErrorMessage() {
		return mErrorMessage;
	}
}
