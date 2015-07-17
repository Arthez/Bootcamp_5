package com.droidsonroids.bootcamp.soccertablemanager.api;

import com.droidsonroids.bootcamp.soccertablemanager.api.model.ResponseStatus;
import com.droidsonroids.bootcamp.soccertablemanager.api.model.Tables;

import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

public interface ApiInterface {

	@GET("/api/tables")
	Tables getTables();

	@POST("/api/register")
	ResponseStatus register(@Query("name") String name);

	@POST("/api/tables")
	ResponseStatus createTable(@Query("user_id") int userId, @Query("time") String time);

	@POST("/api/tables/{table_id}/join")
	ResponseStatus joinToTable(@Path("table_id") int tableId, @Query("user_id") int userId);

	@POST("/api/tables/{table_id}/leave")
	ResponseStatus leaveTable(@Path("table_id") int tableId, @Query("user_id") int userId);
}
