package com.nounapps.mareu.ui.meeting_list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;


import com.nounapps.mareu.R;
import com.nounapps.mareu.databinding.ActivityAddMeetingBinding;
import com.nounapps.mareu.di.DI;
import com.nounapps.mareu.model.Meeting;
import com.nounapps.mareu.service.MeetingApiService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class AddMeetingActivity extends AppCompatActivity    {

    private ActivityAddMeetingBinding binding;
    private MeetingApiService mMeetingApiService;
    private RecyclerView.Adapter adapter;

    private int meetingHour, meetingMinute;
    private String spinnerSelection;

    private Calendar globalCalendar = Calendar.getInstance();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddMeetingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        mMeetingApiService = DI.getMeetingApiService();
        binding.create.setEnabled(true);
        selectRoom();
        selectDate();
        selectHour();
        createMeeting();
    }


    private void selectRoom() {
            Spinner spinner = findViewById(R.id.sMeetingRoom);
// Create an ArrayAdapter using the string array and a default spinner layout
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                    R.array.meetings_room_array, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    spinnerSelection = parent.getItemAtPosition(position).toString();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }

    private void selectDate() {
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
    }

    private void selectHour() {
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
                        SimpleDateFormat sdfHour = new SimpleDateFormat("HH:mm");
                        try {
                            Date date = sdfHour.parse(meetingTime);
                            binding.tvSelectedHour.setText(sdfHour.format(date));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }, hour, minute, true);
            timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            timePickerDialog.updateTime(meetingHour, meetingMinute);
            timePickerDialog.show();
        });
    }
    private void emptyMeetingMessage(){
        Toast.makeText(this, "Please fill in the empty fields", Toast.LENGTH_SHORT).show();
    }


    public void formatGlobalCalendar(){


    }

    public void createMeeting(){
        binding.create.setOnClickListener(v -> {

            String objet = binding.tfObject.getEditText().getText().toString();
            String location = spinnerSelection;
            Date meetingDate = globalCalendar.getTime();
            String participant = binding.tfParticipants.getEditText().getText().toString();

            if (objet.isEmpty()) {
                binding.tfObject.setError("Please type an object");
                return;
            }

            if (location.matches("None")){
                Toast.makeText(this, "Please choose a location", Toast.LENGTH_SHORT).show();
                return;
            }

            if (binding.tvSelectedDate.getText().toString().matches("../../..")){
                Toast.makeText(this, "Please choose a date", Toast.LENGTH_SHORT).show();
                return;
            }

            if (binding.tvSelectedHour.getText().toString().matches(".. : ..")){
                Toast.makeText(this, "Please choose a hour", Toast.LENGTH_SHORT).show();
                return;
            }

            if (participant.isEmpty()) {
                binding.tfParticipants.setError("Please type a participant");
                return;
            }

            mMeetingApiService.createMeeting(new Meeting(0,objet,location,meetingDate,participant));
            Toast.makeText(this, "Meeting created !", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}

