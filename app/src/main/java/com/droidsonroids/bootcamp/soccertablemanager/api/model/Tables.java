package com.droidsonroids.bootcamp.soccertablemanager.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Tables {

	@SerializedName(value = "tables") List<Table> mTables;

	public List<Table> getTables() {
		return mTables;
	}
}
