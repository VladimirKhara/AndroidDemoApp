package com.example.easyclusterapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class CalendActivity extends AppCompatActivity {

    private Calendar currentDateTime;
    private CheckBox checkBox;
    private Calendar maxLimit;
    private Calendar minLimit;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calend);

        final DatePicker datePicker = findViewById(R.id.act_calendar_dp);
        checkBox = findViewById(R.id.act_calendar_cb);
        final TextView textView = findViewById(R.id.act_calendar_tv0);
        textView.setText(datePicker.getDayOfMonth() + "." + (datePicker.getMonth() + 1) + "." + datePicker.getYear());

        final long minDate = datePicker.getMinDate();
        final long maxDate = datePicker.getMaxDate();
        currentDateTime = Calendar.getInstance();
        minLimit = (Calendar) currentDateTime.clone();
        minLimit.add(Calendar.DATE, -7);
        maxLimit = (Calendar) currentDateTime.clone();
        maxLimit.add(Calendar.DATE, 7);

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkBox.isChecked()) {
                    datePicker.setMaxDate(maxLimit.getTimeInMillis());
                    datePicker.setMinDate(minLimit.getTimeInMillis());

                } else {
                    datePicker.setMaxDate(maxDate);
                    datePicker.setMinDate(minDate);
                }
            }
        });

        datePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                textView.setText(datePicker.getDayOfMonth() + "." + (datePicker.getMonth() + 1) + "." + datePicker.getYear());
            }
        });

        final TimePicker timePicker = findViewById(R.id.act_calendar_tp);
        final TextView textView1 = findViewById(R.id.act_calendar_tv1);

        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                if (i1 > 9)
                    textView1.setText(String.valueOf(i) + ":" + String.valueOf(i1));
                else
                {
                    String stringMinute = "0" + String.valueOf(i1);
                    textView1.setText(String.valueOf(i) + ":" + stringMinute);
                }
            }
        });

        registerForContextMenu(findViewById(R.id.act_calendar_btn0));

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_picker, menu);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.cp_i0:
                DatePickerDialog datePickerDialog = new DatePickerDialog(this, null, currentDateTime.get(Calendar.YEAR),
                        currentDateTime.get(Calendar.MONTH), currentDateTime.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        TextView textView = findViewById(R.id.act_calendar_tv0);
                        textView.setText(String.valueOf(i2) + "." + String.valueOf(i1+1) + "." +
                                String.valueOf(i));
                    }
                });
                if (checkBox.isChecked()) {
                    datePickerDialog.getDatePicker().setMaxDate(maxLimit.getTimeInMillis());
                    datePickerDialog.getDatePicker().setMinDate(minLimit.getTimeInMillis());
                }
                datePickerDialog.show();
                break;
            case R.id.cp_i1:
                currentDateTime = Calendar.getInstance();
                TimePickerDialog timePickerDialog = new TimePickerDialog(this, timeSetListener,
                        currentDateTime.get(Calendar.HOUR_OF_DAY), currentDateTime.get(Calendar.MINUTE), false);
                timePickerDialog.show();
                break;
        }
        return true;
    }

    TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            TextView textView = findViewById(R.id.act_calendar_tv1);
            if (minute > 9)
                textView.setText(String.valueOf(hourOfDay) + ":" + String.valueOf(minute));
            else
            {
                String stringMinute = "0" + String.valueOf(minute);
                textView.setText(String.valueOf(hourOfDay) + ":" + stringMinute);
            }
        }
    };

}


