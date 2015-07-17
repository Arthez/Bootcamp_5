package com.droidsonroids.bootcamp.soccertablemanager.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.droidsonroids.bootcamp.soccertablemanager.R;
import com.droidsonroids.bootcamp.soccertablemanager.api.model.Table;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class RegisterActivity extends AppCompatActivity {

    @Bind(R.id.user_name_edit_text)
    EditText mUserNameEditText;

    @Bind(R.id.table_time_edit_text)
    EditText mTableTimeEditText;

    @Bind(R.id.table_listview)
    ListView mTableListView;

    private final int mTableId = 20;
    private List<Table> mTableList = new ArrayList<>();

    private SharedPreferences mSharedPreferences;
    private final String KEY_USER_ID = "keyUserID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        onItemClickCallback();
        getTables();
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @OnClick(R.id.register_button)
    public void onClickRegisterButton() {
        String userName = mUserNameEditText.getText().toString();
        if (!userName.equals("")) {
            registerUser(userName);
        } else {
            Toast.makeText(getApplicationContext(), R.string.error_invalid_username, Toast.LENGTH_SHORT).show();
        }
    }

    public void registerUser(String userName) {
        EventBus.getDefault().post(new RegisterRequestEvent(userName));
    }

    @OnClick(R.id.create_table_button)
    public void onClickCreateTableButton() {
        String tableTime = mTableTimeEditText.getText().toString();
        if (!tableTime.equals("")) {
            createTable(tableTime);
        } else {
            Toast.makeText(getApplicationContext(), R.string.error_invalid_table_time, Toast.LENGTH_SHORT).show();
        }
    }

    private void createTable(String time) {
        int userId = mSharedPreferences.getInt(KEY_USER_ID, -1);
        EventBus.getDefault().post(new CreateTableRequestEvent(time, userId));
    }

    @OnClick(R.id.button_get_tables)
    public void onClickGetTablesButton() {
        getTables();
    }

    private void getTables() {
        EventBus.getDefault().post(new GetTablesRequestEvent());
    }

    private void joinTable(int tableId) {
        int userId = mSharedPreferences.getInt(KEY_USER_ID, -1);
        EventBus.getDefault().post(new JoinRequestEvent(tableId, userId));
    }

    private void leaveTable(int tableId) {
        int userId = mSharedPreferences.getInt(KEY_USER_ID, -1);
        EventBus.getDefault().post(new LeaveRequestEvent(tableId, userId));
    }

    @SuppressWarnings("unused")
    public void onEventMainThread(RegisterResponseEvent registerResponseEvent) {
        if (registerResponseEvent.getApiError() != null) {
            Toast.makeText(getApplicationContext(), R.string.error_username_taken, Toast.LENGTH_SHORT).show();
        } else {
            mSharedPreferences.edit().putInt(KEY_USER_ID, registerResponseEvent.getUserId()).apply();
            Toast.makeText(getApplicationContext(), R.string.success_username_registered, Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressWarnings("unused")
    public void onEventMainThread(CreateTableResponseEvent createTableResponseEvent) {
        if (createTableResponseEvent.getApiError() != null) {
            Toast.makeText(getApplicationContext(), R.string.error_table_time_taken, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), R.string.success_create_table, Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressWarnings("unused")
    public void onEventMainThread(GetTablesResponseEvent getTablesResponseEvent) {
        if (getTablesResponseEvent.getApiError() != null) {
            Toast.makeText(getApplicationContext(), R.string.error_get_tables, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), R.string.success_get_tables, Toast.LENGTH_SHORT).show();
            mTableList = getTablesResponseEvent.getTables();
            populateTableList();
        }
    }

    @SuppressWarnings("unused")
    public void onEventMainThread(JoinResponseEvent joinResponseEvent) {
        if (joinResponseEvent.getApiError() != null) {
            Toast.makeText(getApplicationContext(), R.string.error_join_table, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), R.string.success_join_table, Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressWarnings("unused")
    public void onEventMainThread(LeaveResponseEvent leaveResponseEvent) {
        if (leaveResponseEvent.getApiError() != null) {
            Toast.makeText(getApplicationContext(), R.string.error_leave_table, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), R.string.success_leave_table, Toast.LENGTH_SHORT).show();
        }
    }

    private void populateTableList() {
        mTableListView.setAdapter(new TableListAdapter());
    }

    private void onItemClickCallback() {

        mTableListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                joinTable(mTableId);
            }
        });

        mTableListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                leaveTable(mTableId);
                return true;
            }
        });
    }


    private class TableListAdapter extends ArrayAdapter<Table> {

        public TableListAdapter() {
            super(RegisterActivity.this, R.layout.table_listview, mTableList);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.table_listview, parent, false);
            }

            Table currentTable = mTableList.get(position);
            TextView tableIdTextView = (TextView) itemView.findViewById(R.id.table_id);
            TextView tableTimeTextView = (TextView) itemView.findViewById(R.id.table_time);
            TextView tableFreePositionsTextView = (TextView) itemView.findViewById(R.id.table_free_positions);

            tableIdTextView.setText(Integer.toString(currentTable.getTableId()));
            tableTimeTextView.setText(currentTable.getTime());
            tableFreePositionsTextView.setText(Integer.toString(currentTable.getFreeSpotsNumber()));

            return itemView;
        }

    }
}
