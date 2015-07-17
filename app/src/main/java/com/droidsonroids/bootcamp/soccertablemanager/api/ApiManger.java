package com.droidsonroids.bootcamp.soccertablemanager.api;

import com.droidsonroids.bootcamp.soccertablemanager.api.model.Error;
import com.droidsonroids.bootcamp.soccertablemanager.api.model.ResponseStatus;
import com.droidsonroids.bootcamp.soccertablemanager.api.model.Tables;
import com.droidsonroids.bootcamp.soccertablemanager.event.ApiError;
import com.droidsonroids.bootcamp.soccertablemanager.event.CreateTableRequestEvent;
import com.droidsonroids.bootcamp.soccertablemanager.event.CreateTableResponseEvent;
import com.droidsonroids.bootcamp.soccertablemanager.event.GetTablesRequestEvent;
import com.droidsonroids.bootcamp.soccertablemanager.event.GetTablesResponseEvent;
import com.droidsonroids.bootcamp.soccertablemanager.event.JoinRequestEvent;
import com.droidsonroids.bootcamp.soccertablemanager.event.JoinResponseEvent;
import com.droidsonroids.bootcamp.soccertablemanager.event.LeaveRequestEvent;
import com.droidsonroids.bootcamp.soccertablemanager.event.LeaveResponseEvent;
import com.droidsonroids.bootcamp.soccertablemanager.event.RegisterRequestEvent;
import com.droidsonroids.bootcamp.soccertablemanager.event.RegisterResponseEvent;

import de.greenrobot.event.EventBus;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.android.AndroidLog;
import retrofit.client.Response;

public class ApiManger {

	private static final String ENDPOINT = "https://dor-inpost.herokuapp.com";

	public static final String INTERNAL_SERVER_ERROR = "Internal Server Error";
	public static final String PAGE_NOT_FOUND = "Page not found";
	public static final String UNKNOWN_ERROR = "Unknown error";

	private final ApiInterface mApiInterface;
	private final SoccerTableManagerErrorHandler mErrorHandler;

	public ApiManger() {
		mApiInterface = new RestAdapter.Builder()
				.setEndpoint(ENDPOINT)
				.setLog(new AndroidLog("soccer-table"))
				.setLogLevel(RestAdapter.LogLevel.FULL)
				.build().create(ApiInterface.class);

		mErrorHandler = new SoccerTableManagerErrorHandler();

		EventBus.getDefault().register(this);
	}

	private synchronized void getTables() {
		try {
			final Tables tables = mApiInterface.getTables();
			postEvent(new GetTablesResponseEvent(tables.getTables()));
		} catch (Exception exception) {
			final ApiError apiError = mErrorHandler.handleError(exception);
			postEvent(new GetTablesResponseEvent(apiError));
		}
	}

	private synchronized void register(final String userName) {
		try {
			final ResponseStatus status = mApiInterface.register(userName);
			postEvent(new RegisterResponseEvent(status.getStatusText(), status.getId()));
		} catch (Exception exception) {
			final ApiError apiError = mErrorHandler.handleError(exception);
			postEvent(new RegisterResponseEvent(apiError));
		}
	}

	private synchronized void createTable(final int userId, final String timeText) {
		try {
			final ResponseStatus status = mApiInterface.createTable(userId, timeText);
			postEvent(new CreateTableResponseEvent(status.getStatusText(), status.getId()));
		} catch (Exception exception) {
			final ApiError apiError = mErrorHandler.handleError(exception);
			postEvent(new CreateTableResponseEvent(apiError));
		}
	}

	private synchronized void joinToTable(final int tableId, final int userId) {
		try {
			final ResponseStatus status = mApiInterface.joinToTable(tableId, userId);
			postEvent(new JoinResponseEvent(status.getStatusText()));
		} catch (Exception exception) {
			final ApiError apiError = mErrorHandler.handleError(exception);
			postEvent(new JoinResponseEvent(apiError));
		}
	}

	private synchronized void leaveTable(final int tableId, final int userId) {
		try {
			final ResponseStatus status = mApiInterface.leaveTable(tableId, userId);
			postEvent(new LeaveResponseEvent(status.getStatusText()));
		} catch (Exception exception) {
			final ApiError apiError = mErrorHandler.handleError(exception);
			postEvent(new LeaveResponseEvent(apiError));
		}
	}

	private void postEvent(final Object event) {
		EventBus.getDefault().post(event);
	}

	/** EVENTS SUBSCRIPTIONS **/

	@SuppressWarnings("unused")
	public void onEventBackgroundThread(GetTablesRequestEvent event) {
		getTables();
	}

	@SuppressWarnings("unused")
	public void onEventBackgroundThread(RegisterRequestEvent event) {
		register(event.getUserName());
	}

	@SuppressWarnings("unused")
	public void onEventBackgroundThread(CreateTableRequestEvent event) {
		createTable(event.getUserId(), event.getTimeText());
	}

	@SuppressWarnings("unused")
	public void onEventBackgroundThread(JoinRequestEvent event) {
		joinToTable(event.getTableId(), event.getUserId());
	}

	@SuppressWarnings("unused")
	public void onEventBackgroundThread(LeaveRequestEvent event) {
		leaveTable(event.getTableId(), event.getUserId());
	}

	/*******************************/

	class SoccerTableManagerErrorHandler {

		public ApiError handleError(final Throwable throwable) {
			if (throwable instanceof RetrofitError) {
				return handleRetrofitError(throwable);
			} else {
				throw new RuntimeException(throwable);
			}
		}

		private ApiError handleRetrofitError(final Throwable throwable) {
			final RetrofitError retrofitError = (RetrofitError) throwable;
			final Response response = retrofitError.getResponse();

			if (response != null) {
				final int status = response.getStatus();
				switch (status) {
					case 422:
						return handleKnownErrors(retrofitError, response);
					case 500:
						return new ApiError(status + "", INTERNAL_SERVER_ERROR);
					case 404:
						return new ApiError(status + "", PAGE_NOT_FOUND);
					default:
						return new ApiError(status + "", UNKNOWN_ERROR);
				}
			} else {
				throw new RuntimeException(throwable);
			}
		}

		private ApiError handleKnownErrors(final RetrofitError retrofitError, final Response response) {
			final Error Error = (Error) retrofitError.getBodyAs(Error.class);
			return new ApiError(response.getStatus() + "", Error.getErrorMessage());
		}
	}
}
