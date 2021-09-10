package com.nounapps.mareu.ui.meeting_list;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.style.UpdateAppearance;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.nounapps.mareu.R;
import com.nounapps.mareu.databinding.ActivityAddMeetingBinding;
import com.nounapps.mareu.di.DI;
import com.nounapps.mareu.model.Meeting;
import com.nounapps.mareu.service.DummyMeetingApiService;
import com.nounapps.mareu.service.DummyMeetingGenerator;
import com.nounapps.mareu.service.MeetingApiService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddMeetingActivity extends AppCompatActivity    {

    private ActivityAddMeetingBinding binding;
    private MeetingApiService mMeetingApiService;

    private int meetingStartHour, meetingStartMinute;
    private String spinnerSelection;
    private int hourSelection;

    private Calendar globalCalendar = Calendar.getInstance();
    private String[] addMails;
    private String objet;
    private String location ;
    private Date meetingStartDate;
    private int meetingDuration;
    private String[] participant;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddMeetingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        mMeetingApiService = DI.getMeetingApiService();
        binding.mbAddMailButton.setEnabled(true);
        binding.create.setEnabled(true);
        selectRoom();
        selectDate();
        selectStartHour();
        selectMeetingDuration();
        addMailTag();
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
    public void addMailTag(){
        binding.mbAddMailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.tfParticipants.getEditText().getText().toString().isEmpty()) {
                    binding.tfParticipants.setError("Please type a participant");
                } else {


                addMails = binding.tfParticipants.getEditText().getText().toString().split(" ");
                LayoutInflater participantsInflater = LayoutInflater.from(AddMeetingActivity.this);
                for (String text : addMails) {
                    Chip chipMail = (Chip) participantsInflater.inflate(R.layout.chip_mail_item, null, false);
                    chipMail.setText(text);
                    chipMail.setOnCloseIconClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            binding.cgMail.removeView(v);
                        }
                    });
                    binding.cgMail.addView(chipMail);
                }
            }
            }
        });
    }

    public boolean checkMeetingIsComplete(){
        if (objet.isEmpty()) {
            binding.tfObject.setError("Please type an object");
            return false;
        }
        if (location.matches("None")){
            Toast.makeText(this, "Please choose a location", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (binding.tvSelectedDate.getText().toString().matches("../../..")){
            Toast.makeText(this, "Please choose a date", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (binding.tvSelectedHourStart.getText().toString().matches(".. : ..")){
            Toast.makeText(this, "Please choose a hour", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (hourSelection == 0){
            Toast.makeText(this, "Please choose a duration", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (Arrays.toString(participant).contains("null")) {
            binding.tfParticipants.setError("Please type a participant");
            return false;
        }
        return true;

    }
    public void createMeeting(){
        binding.create.setOnClickListener(v -> {

            objet = binding.tfObject.getEditText().getText().toString();
            location = spinnerSelection;
            meetingStartDate = globalCalendar.getTime();
            meetingDuration = hourSelection;
            participant = addMails;

            if(checkMeetingIsComplete()){

            mMeetingApiService.createMeeting(new Meeting(objet,location,meetingStartDate,meetingDuration,participant));
            Toast.makeText(this, "Meeting added !", Toast.LENGTH_SHORT).show();
            finish();}
        });
    }



    public static boolean isEmailValid(String mail) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(mail);
        return matcher.matches();
    }
}

