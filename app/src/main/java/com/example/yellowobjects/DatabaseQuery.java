package com.example.yellowobjects.database;

import android.content.Context;
import android.database.Cursor;

import com.example.yellowobjects.ui.poster_page.Event;

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

    public List<Event> getAllFutureEvents(Date mDate){
        Calendar calDate = Calendar.getInstance();
        Calendar dDate = Calendar.getInstance();
        calDate.setTime(mDate);

        int calDay = calDate.get(Calendar.DAY_OF_MONTH);
        int calMonth = calDate.get(Calendar.MONTH) + 1;
        int calYear = calDate.get(Calendar.YEAR);

        List<Event> events = new ArrayList<>();
        String query = "select * from user_events";
        Cursor cursor = this.getDbConnection().rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
                String venue = cursor.getString(cursor.getColumnIndexOrThrow("venue"));
                String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
                String startdt = cursor.getString(cursor.getColumnIndexOrThrow("startdt"));
                String enddt = cursor.getString(cursor.getColumnIndexOrThrow("enddt"));
                //convert start date to date object
                Date startdtd = convertStringToDate(startdt);
                Date enddtd = convertStringToDate(enddt);

                int starty = startdtd.getYear();
                int startm = startdtd.getMonth();
                int startd = startdtd.getDay();
                int starth = startdtd.getHours();
                int starti = startdtd.getMinutes();
                int starts = startdtd.getSeconds();
                int endy = enddtd.getYear();
                int endm = enddtd.getMonth();
                int endd = enddtd.getDay();
                int endh = enddtd.getHours();
                int endi = enddtd.getMinutes();
                int ends = enddtd.getSeconds();

//                Date reminderDate = convertStringToDate(startDate);
//                Date end = convertStringToDate(endDate);
                dDate.setTime(startdtd);
                int dDay = dDate.get(Calendar.DAY_OF_MONTH);
                int dMonth = dDate.get(Calendar.MONTH) + 1;
                int dYear = dDate.get(Calendar.YEAR);

                if(calDay == dDay && calMonth == dMonth && calYear == dYear){
                    events.add(new Event(null,title,venue,description,String.valueOf(id),starty,startm,startd,starth,starti,starts,endy,endm,endd,endh,endi,ends));
                }
            }while (cursor.moveToNext());
        }
        cursor.close();
        return events;
    }

    private Date convertStringToDate(String dateInString){
        DateFormat format = new SimpleDateFormat("d-MM-yyyy HH:mm", Locale.ENGLISH);
        Date date = null;
        try {
            date = format.parse(dateInString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
