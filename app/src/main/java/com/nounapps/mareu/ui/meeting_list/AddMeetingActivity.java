package com.nounapps.mareu.ui.meeting_list;

import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.nounapps.mareu.R;
import com.nounapps.mareu.databinding.ActivityAddMeetingBinding;
import com.nounapps.mareu.model.Meeting;
import com.nounapps.mareu.service.MeetingApiService;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;


public class AddMeetingActivity extends AppCompatActivity {


    private ActivityAddMeetingBinding binding;
    private MeetingApiService mMeetingApiService;

    private DatePickerDialog.OnDateSetListener onDateSetListener;
    private TimePickerDialog.OnTimeSetListener onTimeSetListener;


    private int meetingHour, meetingMinute;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddMeetingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        /** Array Adapter **/
        Spinner spinner = (Spinner) findViewById(R.id.sMeetingRoom);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.meetings_room_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);


// init button createMeeting
        binding.create.setEnabled(true);

        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        binding.tvSelectedDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        AddMeetingActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month=month+1;

                        String meetingDate = day+"/"+month+"/"+year;
                        binding.tvSelectedDate.setText(meetingDate);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });


        binding.tvSelectedHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int hour = cal.get(Calendar.HOUR);
                int minute = cal.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        AddMeetingActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                meetingHour = hourOfDay;
                                meetingMinute = minute;
                                String meetingTime = meetingHour+":"+meetingMinute;
                                SimpleDateFormat sdfHour = new SimpleDateFormat(
                                        "HH:mm"
                                );
                                try {
                                    Date date = sdfHour.parse(meetingTime);
                                    binding.tvSelectedHour.setText(sdfHour.format(date));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, hour, minute, true);
                    timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    timePickerDialog.updateTime(meetingHour,meetingMinute);
                    timePickerDialog.show();
            }
        });

//        binding.create.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Meeting meeting = new Meeting(
//                        System.currentTimeMillis(),
//                        binding.tfObject.toString(),
//                        binding.sMeetingRoom.getSelectedItem().toString(),
//                        calendar.getTime(),
//                        binding.tfParticipants.toString()
//                );
//
//                mMeetingApiService.createMeeting(meeting);
//                finish();
//            }
//        });

    }
}
