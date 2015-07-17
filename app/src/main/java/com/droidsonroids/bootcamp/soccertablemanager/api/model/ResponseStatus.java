package com.droidsonroids.bootcamp.soccertablemanager.api.model;

import com.google.gson.annotations.SerializedName;

public class ResponseStatus {

	@SerializedName(value = "status") String mStatusText;

	@SerializedName(value = "id") int mId;

	public String getStatusText() {
		return mStatusText;
	}

	public int getId() {
		return mId;
	}
}
