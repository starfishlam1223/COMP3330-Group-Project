package com.example.yellowobjects.ui.database;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.widget.Toast;

import com.example.yellowobjects.ui.poster_page.Event;
import com.example.yellowobjects.ui.schedule.EventObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DatabaseQuery extends DatabaseObject{

    private static final String TAG = Database.class.getSimpleName();

    public DatabaseQuery(Context context) {
        super(context);
    }

    public List<EventObject> getAllFutureEvents(Date mDate){
        Calendar calDate = Calendar.getInstance();
        Calendar dDate = Calendar.getInstance();
        calDate.setTime(mDate);

        int calDay = calDate.get(Calendar.DAY_OF_MONTH);
        int calMonth = calDate.get(Calendar.MONTH) + 1;
        int calYear = calDate.get(Calendar.YEAR);

        List<EventObject> events = new ArrayList<>();
        String query = "select * from yellow_objects";
        Cursor cursor = this.getDbConnection().rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                Log.d("Event", "Event found");
                int id = cursor.getInt(0);
                String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
                String venue = cursor.getString(cursor.getColumnIndexOrThrow("venue"));
                String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
                String startdt = cursor.getString(cursor.getColumnIndexOrThrow("startdt"));
                String enddt = cursor.getString(cursor.getColumnIndexOrThrow("enddt"));
                //convert start date to date object
                Date startdtd = convertStringToDate(startdt);
                Log.d("Date", startdt);
                Date enddtd = convertStringToDate(enddt);

//                int starty = startdtd.getYear();
//                int startm = startdtd.getMonth();
//                int startd = startdtd.getDay();
//                int starth = startdtd.getHours();
//                int starti = startdtd.getMinutes();
//                int starts = startdtd.getSeconds();
//                int endy = enddtd.getYear();
//                int endm = enddtd.getMonth();
//                int endd = enddtd.getDay();
//                int endh = enddtd.getHours();
//                int endi = enddtd.getMinutes();
//                int ends = enddtd.getSeconds();

//                Date reminderDate = convertStringToDate(startDate);
//                Date end = convertStringToDate(endDate);
                dDate.setTime(startdtd);
                int dDay = dDate.get(Calendar.DAY_OF_MONTH);
                int dMonth = dDate.get(Calendar.MONTH) + 1;
                int dYear = dDate.get(Calendar.YEAR);

                if(calDay == dDay && calMonth == dMonth && calYear == dYear){
                    events.add(new EventObject(id,title,description,venue,startdtd,enddtd));
                }
            }while (cursor.moveToNext());
        }
        cursor.close();
        return events;
    }

    public void addEvent(String title, String desc, String venue, Date startdt, Date enddt) {
        SimpleDateFormat f = new SimpleDateFormat("d-MM-yyyy HH:mm:ss");
        try {
            String startStr = f.format(startdt);
            Log.d("Start", startStr);

            String endStr = f.format(enddt);
            Log.d("End", startStr);

            String query = "INSERT INTO yellow_objects(title, description, startdt, enddt, venue)\n" +
                    "  VALUES(?, ?, ?, ?, ?)\n";
            this.getDbConnection().execSQL(query, new String [] {title, desc, startStr, endStr, venue});
            Log.d("Event", "Event added!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addTestEvent() {
        String query = "INSERT INTO yellow_objects(title, description, startdt, enddt, venue)\n" +
                "  VALUES(\"today\", \"test\", \"12-12-2019 16:30:00\", \"12-12-2019 18:00:00\", \"test\")\n";
        this.getDbConnection().execSQL(query);
        Log.d("Event", "Event added!");
    }

    public void deleteEvent(int id) {
        String query = "DELETE FROM yellow_objects\n" +
                "  WHERE id=?\n";
        this.getDbConnection().execSQL(query, new String[]{String.valueOf(id)});
        Log.d("Event", "Event deleted!");
    }

    public void resetDB() {
        String query = "DELETE FROM yellow_objects";
        this.getDbConnection().execSQL(query);
        Log.d("Event", "Database reset!");
    }

    private Date convertStringToDate(String dateInString){
        DateFormat format = new SimpleDateFormat("d-MM-yyyy HH:mm:ss", Locale.ENGLISH);
        Date date = null;
        try {
            date = format.parse(dateInString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
