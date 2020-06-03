package com.kingshark.classapp.Fragments;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.tlaabs.timetableview.Schedule;
import com.github.tlaabs.timetableview.Time;
import com.github.tlaabs.timetableview.TimetableView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.kingshark.classapp.R;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dmax.dialog.SpotsDialog;

public class TimeTableFragment extends Fragment {

    private Unbinder unbinder;

    @BindView(R.id.timetable)
    TimetableView timetable;
    @BindView(R.id.add_fab)
    FloatingActionButton fab;

    public TimeTableFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_time_table, container, false);
        unbinder = ButterKnife.bind(this,view);

        init();


        return  view;
    }

    private void init() {
        Calendar calendar = Calendar.getInstance();
        int header = calendar.get(Calendar.DAY_OF_WEEK);

        timetable.setHeaderHighlight(header);

        fab.setOnClickListener(v -> showAddScheduleDialog());

    }

    private void loadUserSchedule(String data) {
        if( data == null) return;
        timetable.load(data);
        timetable.setOnStickerSelectEventListener(new TimetableView.OnStickerSelectedListener() {
            @Override
            public void OnStickerSelected(int idx, ArrayList<Schedule> schedules) {
                Toast.makeText(getContext(), "selected", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showAddScheduleDialog() {
        AlertDialog dialog = new AlertDialog.Builder(getContext()).create();
        dialog.setTitle("ADD SUBJECT");
        dialog.setCanceledOnTouchOutside(false);



        LayoutInflater inflater = this.getLayoutInflater();
        View add_subject = inflater.inflate(R.layout.add_event_layout,null);

        EditText edt_subject = add_subject.findViewById(R.id.edt_subject);
        EditText edt_class = add_subject.findViewById(R.id.edt_classLocation);
        EditText edt_lec = add_subject.findViewById(R.id.edt_Lecturer);
        TextView startTime = add_subject.findViewById(R.id.start_time);
        TextView endTime = add_subject.findViewById(R.id.end_time);
        Spinner day_spinner = add_subject.findViewById(R.id.day_spinner);
        Button btn_save = add_subject.findViewById(R.id.btn_save);



        ArrayList<Schedule> schedules = new ArrayList<>();
        Schedule schedule = new Schedule();
        schedule.setProfessorName(edt_lec.getText().toString());
        schedule.setClassPlace(edt_class.getText().toString());
        schedule.setClassTitle(edt_subject.getText().toString());

        day_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                schedule.setDay(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Clicked", Toast.LENGTH_SHORT).show();
                TimePickerDialog timeDialog = new TimePickerDialog(getContext(), listener, schedule.getStartTime().getHour(), schedule.getStartTime().getMinute(), android.text.format.DateFormat.is24HourFormat(getContext()));
                timeDialog.show();

            }

            private TimePickerDialog.OnTimeSetListener listener = (view, hourOfDay, minute) -> {
                startTime.setText(hourOfDay + ":" + minute);
                schedule.setStartTime(new Time(hourOfDay, minute));

            };
        });

        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timeDialog = new TimePickerDialog(getContext(), listener, schedule.getEndTime().getHour(), schedule.getEndTime().getMinute(), android.text.format.DateFormat.is24HourFormat(getContext()));
                timeDialog.show();

            }

            private TimePickerDialog.OnTimeSetListener listener = (view, hourOfDay, minute) -> {
                endTime.setText(hourOfDay + ":" + minute);
                schedule.setEndTime(new Time(hourOfDay, minute));


            };
        });


        btn_save.setOnClickListener(v -> {
            schedules.add(schedule);
            timetable.add(schedules);

            String data = timetable.createSaveData();
            loadUserSchedule(data);
            dialog.dismiss();


        });


        dialog.setView(add_subject);
        dialog.show();

    }



    @Override
    public void onResume() {
        super.onResume();

    }
}