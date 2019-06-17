package com.kelompoksembilan.smartschedule;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyScheduleActivity extends AppCompatActivity {

    private ScheduleAdapter mAdapter;
    private List<DatabaseHelper.Schedule> scheduleList = new ArrayList<>();
    private RecyclerView recyclerView;

//    private Button buttonDelete;

    DatabaseHelper scheduleDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_schedule);
        recyclerView = findViewById(R.id.recycler_view);

        scheduleDB = new DatabaseHelper(this);
        scheduleList = scheduleDB.getAllNotes();
//        setButtons();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAdapter = new ScheduleAdapter(this, scheduleList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16));
        recyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new ScheduleAdapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(int position) {
                deleteSchedule(position);
            }

            @Override
            public void onEditClick(int position) {
                editSchedule(position);
            }

            @Override
            public void onFavouriteClick(int position) {

            }
        });
    }

    public void deleteSchedule(int position){
        scheduleList.remove(position);
        mAdapter.notifyItemRemoved(position);

        SQLiteDatabase db = scheduleDB.getWritableDatabase();
        db.execSQL("delete from schedule_table where ID = '"+position+"'");
    }

    public void editSchedule (int position){
        Intent intent = new Intent(this, EditActivity.class);
        startActivity(intent);
    }
}
