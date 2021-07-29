package com.nounapps.mareu.ui.meeting_list;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


import com.nounapps.mareu.R;
import com.nounapps.mareu.databinding.ActivityAddMeetingBinding;
import com.nounapps.mareu.di.DI;
import com.nounapps.mareu.model.Meeting;
import com.nounapps.mareu.service.MeetingApiService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class AddMeetingActivity extends AppCompatActivity    {

    private ActivityAddMeetingBinding binding;
    private MeetingApiService mMeetingApiService;

    private int meetingHour, meetingMinute;
    private String spinnerSelection;

    private final Calendar globalCalendar = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddMeetingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        mMeetingApiService = DI.getMeetingApiService();
        binding.create.setEnabled(false);



        Spinner spinner = findViewById(R.id.sMeetingRoom);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.meetings_room_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                binding.create.setEnabled(position != 0);
                spinnerSelection = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });



        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        binding.tvSelectedDate.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    AddMeetingActivity.this, (view, year1, month1, day1) -> {
                        globalCalendar.set(year1, month1, day1);
                        month1 = month1 + 1;

                        String meetingDate = day1 + "/" + month1 + "/" + year1;
                        binding.tvSelectedDate.setText(meetingDate);
                    }, year, month, day);
            datePickerDialog.show();
        });

        binding.tvSelectedHour.setOnClickListener(v -> {
            Calendar cal = Calendar.getInstance();
            int hour = cal.get(Calendar.HOUR);
            int minute = cal.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(
                    AddMeetingActivity.this,
                    android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                    (view, hourOfDay, minute1) -> {
                        globalCalendar.set(Calendar.HOUR, hourOfDay);
                        globalCalendar.set(Calendar.MINUTE, minute1);
                        meetingHour = hourOfDay;
                        meetingMinute = minute1;
                        String meetingTime = meetingHour + ":" + meetingMinute;
                        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdfHour = new SimpleDateFormat(
                                "HH:mm"
                        );
                        try {
                            Date date = sdfHour.parse(meetingTime);
                            assert date != null;
                            binding.tvSelectedHour.setText(sdfHour.format(date));

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }, hour, minute, true);
            timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            timePickerDialog.updateTime(meetingHour, meetingMinute);
            timePickerDialog.show();
        });

        binding.create.setOnClickListener(v -> {
            Meeting meeting = new Meeting(
                    System.currentTimeMillis(),
                    Objects.requireNonNull(binding.tfObject.getEditText()).getText().toString(),
                    spinnerSelection,
                    globalCalendar.getTime(),
                    Objects.requireNonNull(binding.tfParticipants.getEditText()).getText().toString()
            );
            mMeetingApiService.createMeeting(meeting);
            finish();
        });
    }
}
