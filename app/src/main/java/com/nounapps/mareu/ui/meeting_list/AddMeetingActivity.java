package com.nounapps.mareu.ui.meeting_list;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.style.UpdateAppearance;
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
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class AddMeetingActivity extends AppCompatActivity    {

    private ActivityAddMeetingBinding binding;
    private MeetingApiService mMeetingApiService;

    private int meetingStartHour, meetingStartMinute;
    private String spinnerSelection;
    private int hourSelection;

    private Calendar globalCalendar = Calendar.getInstance();
    private Calendar endMeetingCalendar = Calendar.getInstance();



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
        selectStartHour();
        selectMeetingDuration();
        createMeeting();
    }

    private void selectRoom() {
            Spinner spinner = findViewById(R.id.sMeetingRoom);
            ArrayAdapter<CharSequence> roomAdapter = ArrayAdapter.createFromResource(this,
                    R.array.meetings_room_array, android.R.layout.simple_spinner_item);
            roomAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(roomAdapter);
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

    private void selectStartHour() {
        binding.tvSelectedHourStart.setOnClickListener(v -> {
            Calendar cal = Calendar.getInstance();
            int hour = cal.get(Calendar.HOUR_OF_DAY);
            int minute = cal.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(
                    AddMeetingActivity.this,
                    android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                    (view, hourOfDay, minute1) -> {
                        globalCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        globalCalendar.set(Calendar.MINUTE, minute1);
                        meetingStartHour = hourOfDay;
                        meetingStartMinute = minute1;
                        String meetingTime = meetingStartHour + ":" + meetingStartMinute;
                        SimpleDateFormat sdfHour = new SimpleDateFormat("HH:mm");
                        try {
                            Date date = sdfHour.parse(meetingTime);
                            binding.tvSelectedHourStart.setText(sdfHour.format(date));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }, hour, minute, true);
            timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            timePickerDialog.updateTime(meetingStartHour, meetingStartMinute);
            timePickerDialog.show();
        });
    }

    private void selectMeetingDuration() {
        Spinner spinnerRoom = findViewById(R.id.sDurationMeeting);
        Integer[] meetingHourItems = new Integer[]{0,1,2,3,4};
        ArrayAdapter<Integer> durationAdapter = new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_item, meetingHourItems);
        spinnerRoom.setAdapter(durationAdapter);
        spinnerRoom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                hourSelection = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void createMeeting(){
        binding.create.setOnClickListener(v -> {

            String objet = binding.tfObject.getEditText().getText().toString();
            String location = spinnerSelection;
            Date meetingStartDate = globalCalendar.getTime();
            int meetingDuration = hourSelection;
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
            if (binding.tvSelectedHourStart.getText().toString().matches(".. : ..")){
                Toast.makeText(this, "Please choose a hour", Toast.LENGTH_SHORT).show();
                return;
            }
            if (hourSelection == 0){
                Toast.makeText(this, "Please choose a duration", Toast.LENGTH_SHORT).show();
                return;
            }
            if (participant.isEmpty()) {
                binding.tfParticipants.setError("Please type a participant");
                return;
            }

            mMeetingApiService.createMeeting(new Meeting(objet,location,meetingStartDate,meetingDuration,participant));
            Toast.makeText(this, "Meeting added !", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}

