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

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        scheduleViewModel =
                ViewModelProviders.of(this).get(ScheduleViewModel.class);
        View root = inflater.inflate(R.layout.fragment_schedule, container, false);
<<<<<<< Updated upstream
        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.LONG).format(calendar.getTime());
        TextView textViewDate = (TextView) root.findViewById(R.id.display_current_date);
        textViewDate.setText(currentDate);
        ImageView prevDay = (ImageView) root.findViewById(R.id.previous_day);
        prevDay.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

            }
        });
        ImageView nextDay = (ImageView) root.findViewById(R.id.next_day);
        prevDay.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

            }
        });
        ImageView addToSchedule = (ImageView) root.findViewById(R.id.add_to_schedule);
        prevDay.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

            }
        });
=======
>>>>>>> Stashed changes
//        final TextView textView = root.findViewById(R.id.text_notifications);
//        scheduleViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }
}