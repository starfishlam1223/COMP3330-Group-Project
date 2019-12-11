package com.example.yellowobjects.ui.schedule;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.yellowobjects.R;
import com.example.yellowobjects.ui.database.DatabaseQuery;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ScheduleFragment extends Fragment {

    private ScheduleViewModel scheduleViewModel;
    private Calendar calendar = Calendar.getInstance();
    private TextView textViewDate;
    private String displayDate;
    private DatabaseQuery query;
    private RelativeLayout layout;
    private int eventIndex;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        scheduleViewModel =
                ViewModelProviders.of(this).get(ScheduleViewModel.class);
        View root = inflater.inflate(R.layout.fragment_schedule, container, false);

        query = new DatabaseQuery(this.getContext());
        layout = (RelativeLayout) root.findViewById(R.id.left_event_column);
        eventIndex = layout.getChildCount();
        Log.d("eventIndex", String.valueOf(layout.getChildCount()));
        displayDate = DateFormat.getDateInstance(DateFormat.LONG).format(calendar.getTime());
        textViewDate = (TextView) root.findViewById(R.id.display_current_date);
        textViewDate.setText(displayDate);
        displayEvents();
        Log.d("eventIndex", String.valueOf(layout.getChildCount()));
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
                while (layout.getChildCount() > eventIndex) {
                    layout.removeViewAt(eventIndex - 1);
                }
                query.addEvent();
                displayEvents();
            }
        });

        return root;
    }
    private void previousDay(){
        while (layout.getChildCount() > eventIndex) {
            layout.removeViewAt(eventIndex - 1);
        }
        Log.d("eventIndex", String.valueOf(layout.getChildCount()));
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        displayDate = DateFormat.getDateInstance(DateFormat.LONG).format(calendar.getTime());
        textViewDate.setText(displayDate);
        displayEvents();
        Log.d("eventIndex", String.valueOf(layout.getChildCount()));
    }
    private void nextDay(){
        while (layout.getChildCount() > eventIndex) {
            layout.removeViewAt(eventIndex - 1);
        }
        Log.d("eventIndex", String.valueOf(layout.getChildCount()));
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        displayDate = DateFormat.getDateInstance(DateFormat.LONG).format(calendar.getTime());
        textViewDate.setText(displayDate);
        displayEvents();
        Log.d("eventIndex", String.valueOf(layout.getChildCount()));
    }
    private void displayEvents(){
        Date cDate = calendar.getTime();
        List<EventObject> events = query.getAllFutureEvents(cDate);
        for (EventObject e : events){
            int id = e.getId();
            Date startDate = e.getStartdt();
            Date endDate = e.getEnddt();
            String title = e.getTitle();
//            int starty = e.getStarty();
//            int startm = e.getStartm();
//            int startd = e.getStartd();
//            int starth = e.getStarth();
//            int starti = e.getStarti();
//            int starts = e.getStarts();
//            int endy = e.getEndy();
//            int endm = e.getEndm();
//            int endd = e.getEndd();
//            int endh = e.getEndh();
//            int endi = e.getEndi();
//            int ends = e.getEnds();
            int eventBlockHeight = getEventTimeFrame(startDate, endDate);
            Log.d(ScheduleFragment.class.getSimpleName(), "Height " + eventBlockHeight);
            displayEventSection(startDate, eventBlockHeight, id, title);
            Log.d("Notif", "displayEvent called!");
        }
    }
    private int getEventTimeFrame(Date start, Date end){
        long timeDifference = end.getTime() - start.getTime();
        Calendar Cal = Calendar.getInstance();
        Cal.setTimeInMillis(timeDifference);
        int hours = Cal.get(Calendar.HOUR);
        int minutes = Cal.get(Calendar.MINUTE);
        return (hours * 60) + ((minutes * 60) / 100);
    }
    private void displayEventSection(Date date, int height, int id, String title){
        SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
        String displayValue = timeFormatter.format(date);
        String[]hourMinutes = displayValue.split(":");
        int hours = Integer.parseInt(hourMinutes[0]);
        int minutes = Integer.parseInt(hourMinutes[1]);
        Log.d(ScheduleFragment.class.getSimpleName(), "Hour value " + hours);
        Log.d(ScheduleFragment.class.getSimpleName(), "Minutes value " + minutes);
        int topViewMargin = (hours * 60) + ((minutes * 60) / 100);
        Log.d(ScheduleFragment.class.getSimpleName(), "Margin top " + topViewMargin);
        createEventView(topViewMargin, height, id, title);
        Log.d("Notif", "displayEventSection called!");
    }
    private void createEventView(int topMargin, int height, final int id, String title){
        TextView mEventView = new TextView(ScheduleFragment.this.getContext());
        RelativeLayout.LayoutParams lParam = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lParam.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        lParam.topMargin = topMargin * 2;
        lParam.leftMargin = 24;
        mEventView.setLayoutParams(lParam);
        mEventView.setPadding(24, 0, 24, 0);
        mEventView.setHeight(height * 2);
        mEventView.setGravity(0x11);
        mEventView.setTextColor(Color.parseColor("#ffffff"));
        mEventView.setText(title);
        mEventView.setBackgroundColor(Color.parseColor("#3F51B5"));
        mEventView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                while (layout.getChildCount() > eventIndex) {
                    layout.removeViewAt(eventIndex - 1);
                }
                query.deleteEvent(id);
                displayEvents();
            }
        });
        layout.addView(mEventView, layout.getChildCount() - 1);
        Log.d("Notif", "createEventView called!");
        Log.d("Notif", "New view added at " + String.valueOf(layout.getChildCount() - 1) + "!");
    }
}