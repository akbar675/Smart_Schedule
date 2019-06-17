package com.kelompoksembilan.smartschedule;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class EditActivity extends AppCompatActivity {

    DatabaseHelper scheduleDB;

    CardView CardCancel, CardEdit, CardDate, CardTime;
    EditText etTitle, etPlace;
    Calendar Cal;
    DatePickerDialog DatePickDial;
    TimePickerDialog TimePickDial;
    TextView dTv, tTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        scheduleDB = new DatabaseHelper(this);

        etTitle = (EditText) findViewById(R.id.edit_title);
        etPlace = (EditText) findViewById(R.id.edit_place);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CardCancel = (CardView) findViewById(R.id.cardviewCancelEdit);
        CardCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHome();
                Toast.makeText(getApplicationContext(), "Back to Home", Toast.LENGTH_LONG).show();
            }
        });

        CardEdit = (CardView) findViewById(R.id.cardviewEdit);
        CardEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = etTitle.getText().toString();
                String place = etPlace.getText().toString();
                String date = dTv.getText().toString();
                String time = tTv.getText().toString();

                boolean editData = scheduleDB.CardEdit(title, place, date, time);

                if (editData){
                    openMySchedule();
                    Toast.makeText(getApplicationContext(), "Successfully Update! Opening My Schedule", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Something went wrong.", Toast.LENGTH_LONG).show();
                }

//                openMySchedule();
//                Toast.makeText(getApplicationContext(), "Successfully Added! Opening My Schedule", Toast.LENGTH_LONG).show();
            }
        });

        dTv = (TextView) findViewById(R.id.DateTextview);

        CardDate = (CardView) findViewById(R.id.cardviewDate);
        CardDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Cal = Calendar.getInstance();
                int day = Cal.get(Calendar.DAY_OF_MONTH);
                int month = Cal.get(Calendar.MONTH);
                int year = Cal.get(Calendar.YEAR);

                DatePickDial = new DatePickerDialog(EditActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datepicker, int mYear, int mMonth, int mDay) {
                        dTv.setText(mDay + "/" + (mMonth+1) + "/" + mYear);
                    }
                }, day, month, year);
                DatePickDial.show();
            }
        });

        tTv = (TextView) findViewById(R.id.TimeTextview);

        CardTime = (CardView) findViewById(R.id.cardviewTime);
        CardTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickDial = new TimePickerDialog(EditActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int mHour, int mMinute) {
                        tTv.setText(mHour + ":" + mMinute);
                    }
                },00,00, false );
                TimePickDial.show();
            }
        });

    }


    public void openHome() {
        Intent intent01 = new Intent(this, HomeActivity.class);
        startActivity(intent01);
    }
    public void openMySchedule() {
        Intent intent02 = new Intent(this, MyScheduleActivity.class);
        startActivity(intent02);
    }
}
