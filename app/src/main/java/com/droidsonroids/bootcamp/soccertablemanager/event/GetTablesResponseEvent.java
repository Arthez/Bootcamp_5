package com.droidsonroids.bootcamp.soccertablemanager.event;

import com.droidsonroids.bootcamp.soccertablemanager.api.model.Table;

import java.util.List;

public class GetTablesResponseEvent {

	private final List<Table> mTableList;
	private final ApiError mApiError;

	public GetTablesResponseEvent(final List<Table> tableList) {
		this(tableList, null);
	}

	public GetTablesResponseEvent(final ApiError apiError) {
		this(null, apiError);
	}

	public GetTablesResponseEvent(final List<Table> tableList, final ApiError apiError) {
		mTableList = tableList;
		mApiError = apiError;
	}

	public List<Table> getTables() {
		return mTableList;
	}

	public ApiError getApiError() {
		return mApiError;
	}
}
