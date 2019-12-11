package com.example.yellowobjects.ui.schedule;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.yellowobjects.R;

import java.text.DateFormat;
import java.util.Calendar;

public class ScheduleFragment extends Fragment {

    private ScheduleViewModel scheduleViewModel;
    private Calendar calendar = Calendar.getInstance();
    private TextView textViewDate;
    private String displayDate;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        scheduleViewModel =
                ViewModelProviders.of(this).get(ScheduleViewModel.class);
        View root = inflater.inflate(R.layout.fragment_schedule, container, false);

        displayDate = DateFormat.getDateInstance(DateFormat.LONG).format(calendar.getTime());
        textViewDate = (TextView) root.findViewById(R.id.display_current_date);
        textViewDate.setText(displayDate);
        ImageView prevDay = (ImageView) root.findViewById(R.id.previous_day);
        prevDay.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                previousDay();
            }
        });
        ImageView nextDay = (ImageView) root.findViewById(R.id.next_day);
        nextDay.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                nextDay();
            }
        });
        ImageView addToSchedule = (ImageView) root.findViewById(R.id.add_to_schedule);
        addToSchedule.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

            }
        });
        return root;
    }
    private void previousDay(){
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        displayDate = DateFormat.getDateInstance(DateFormat.LONG).format(calendar.getTime());
        textViewDate.setText(displayDate);
    }
    private void nextDay(){
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        displayDate = DateFormat.getDateInstance(DateFormat.LONG).format(calendar.getTime());
        textViewDate.setText(displayDate);
    }
}