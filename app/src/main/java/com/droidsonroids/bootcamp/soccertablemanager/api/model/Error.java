package com.droidsonroids.bootcamp.soccertablemanager.api.model;

import com.google.gson.annotations.SerializedName;

public class Error {

	@SerializedName(value = "error") String mErrorMessage;

	public String getErrorMessage() {
		return mErrorMessage;
	}
}
