package com.example.yellowobjects.ui.schedule;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yellowobjects.R;
import com.example.yellowobjects.ui.database.DatabaseQuery;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AddEvent extends AppCompatActivity {
    private DatabaseQuery query;

    private TextView title;

    private EditText starty;
    private EditText startm;
    private EditText startd;
    private EditText starth;
    private EditText starti;
    private EditText starts;

    private EditText endy;
    private EditText endm;
    private EditText endd;
    private EditText endh;
    private EditText endi;
    private EditText ends;

    private EditText venue;
    private EditText desc;

    private Button add_event;
    private Button reset_db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        query = new DatabaseQuery(this);

        title = findViewById(R.id.titleInput);
        venue = findViewById(R.id.venueInput);
        desc = findViewById(R.id.descInput);

        starty = findViewById(R.id.starty);
        startm = findViewById(R.id.startm);
        startd = findViewById(R.id.startd);
        starth = findViewById(R.id.starth);
        starti = findViewById(R.id.starti);
        starts = findViewById(R.id.starts);

        endy = findViewById(R.id.endy);
        endm = findViewById(R.id.endm);
        endd = findViewById(R.id.endd);
        endh = findViewById(R.id.endh);
        endi = findViewById(R.id.endi);
        ends = findViewById(R.id.ends);

        add_event = findViewById(R.id.add_button);
        reset_db = findViewById(R.id.reset_database);

        Intent parent = getIntent();
        String titleStrEx = parent.getStringExtra("title");
        String descStrEx = parent.getStringExtra("desc");
        String venueStrEx = parent.getStringExtra("venue");

        String syearStrEx = parent.getStringExtra("starty");
        String smonthStrEx = parent.getStringExtra("startm");
        String sdayStrEx = parent.getStringExtra("startd");
        String shourStrEx = parent.getStringExtra("starth");
        String sminuteStrEx = parent.getStringExtra("starti");
        String ssecondStrEx = parent.getStringExtra("starts");

        String eyearStrEx = parent.getStringExtra("endy");
        String emonthStrEx = parent.getStringExtra("endm");
        String edayStrEx = parent.getStringExtra("endd");
        String ehourStrEx = parent.getStringExtra("endh");
        String eminuteStrEx = parent.getStringExtra("endi");
        String esecondStrEx = parent.getStringExtra("ends");

        title.setText(titleStrEx);
        venue.setText(venueStrEx);
        desc.setText(descStrEx);

        starty.setText(syearStrEx);
        startm.setText(smonthStrEx);
        startd.setText(sdayStrEx);
        starth.setText(shourStrEx);
        starti.setText(sminuteStrEx);
        starts.setText(ssecondStrEx);

        endy.setText(eyearStrEx);
        endm.setText(emonthStrEx);
        endd.setText(edayStrEx);
        endh.setText(ehourStrEx);
        endi.setText(eminuteStrEx);
        ends.setText(esecondStrEx);

        final Context c = this;

        add_event.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String titleStr = title.getText().toString();
                String descStr = desc.getText().toString();
                String venueStr = venue.getText().toString();

                String syearStr = starty.getText().toString();
                String smonthStr = startm.getText().toString();
                String sdayStr = startd.getText().toString();
                String shourStr = starth.getText().toString();
                String sminuteStr = starti.getText().toString();
                String ssecondStr = starts.getText().toString();

                String eyearStr = endy.getText().toString();
                String emonthStr = endm.getText().toString();
                String edayStr = endd.getText().toString();
                String ehourStr = endh.getText().toString();
                String eminuteStr = endi.getText().toString();
                String esecondStr = ends.getText().toString();

                if (!isNull(titleStr, venueStr, syearStr, smonthStr, sdayStr, shourStr, sminuteStr, ssecondStr, eyearStr, emonthStr, edayStr, ehourStr, eminuteStr, esecondStr)) {

                    int syear = Integer.parseInt(syearStr);
                    int smonth = Integer.parseInt(smonthStr);
                    int sday = Integer.parseInt(sdayStr);
                    int shour = Integer.parseInt(shourStr);
                    int sminute = Integer.parseInt(sminuteStr);
                    int ssecond = Integer.parseInt(ssecondStr);

                    int eyear = Integer.parseInt(eyearStr);
                    int emonth = Integer.parseInt(emonthStr);
                    int eday = Integer.parseInt(edayStr);
                    int ehour = Integer.parseInt(ehourStr);
                    int eminute = Integer.parseInt(eminuteStr);
                    int esecond = Integer.parseInt(esecondStr);

                    if (formatCorrect(syear, smonth, sday, shour, sminute, ssecond) && formatCorrect(eyear, emonth, eday, ehour, eminute, esecond)) {
                        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                        try {
                            Date startdt = f.parse( sday + "/" + smonth + "/" + syear + " " + String.format("%02d", shour) + ":" + String.format("%02d", sminute) + ":" + String.format("%02d", ssecond));
                            Log.d("Start", sday + "/" + smonth + "/" + syear + " " + String.format("%02d", shour) + ":" + String.format("%02d", sminute) + ":" + String.format("%02d", ssecond));

                            Date enddt = f.parse(eday + "/" + emonth + "/" + eyear + " " + String.format("%02d", ehour) + ":" + String.format("%02d", eminute) + ":" + String.format("%02d", esecond));
                            Log.d("Start", eday + "/" + emonth + "/" + eyear + " " + String.format("%02d", ehour) + ":" + String.format("%02d", eminute) + ":" + String.format("%02d", esecond));

                            if (startdt.before(enddt)) {
                                List<EventObject> events = query.getAllFutureEvents(startdt);
                                boolean isOverlap = false;
                                for (EventObject e : events) {
                                    Date eventStart = e.getStartdt();
                                    Date eventEnd = e.getEnddt();
                                    if ((startdt.compareTo(eventEnd) < 0) && (enddt.compareTo(eventStart) > 0)) {
                                        isOverlap = true;
                                    }
                                }

                                if (isOverlap) {
                                    Toast.makeText(view.getContext(), "Your newly added event overlaps with another", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(view.getContext(), "An event has been added", Toast.LENGTH_LONG).show();
                                }

                                query.addEvent(titleStr, descStr, venueStr, startdt, enddt);

                                Intent intent = new Intent();
                                setResult(RESULT_OK, intent);
                                finish();

                            } else {
                                Toast.makeText(view.getContext(), "Your event cannot end before it starts!", Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            Toast.makeText(view.getContext(), "Cannot parse the date!", Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(view.getContext(), "Wrong numbers in the date and time!", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        reset_db.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                query.resetDB();
            }
        });
    }

    private boolean isNull(String titleStr, String venueStr, String syearStr, String smonthStr, String sdayStr, String shourStr, String sminuteStr, String ssecondStr, String eyearStr, String emonthStr, String edayStr, String ehourStr, String eminuteStr, String esecondStr) {
        if (titleStr.isEmpty()) {
            Toast.makeText(this, "You have to enter the title!", Toast.LENGTH_LONG).show();
            return true;
        } else {
            if (venueStr.isEmpty()) {
                Toast.makeText(this, "You have to enter the venue!", Toast.LENGTH_LONG).show();
                return true;
            } else {
                if ((syearStr.isEmpty()) || (smonthStr.isEmpty()) || (sdayStr.isEmpty()) || (shourStr.isEmpty()) || (sminuteStr.isEmpty()) || (ssecondStr.isEmpty()) ) {
                    Toast.makeText(this, "You miss something in the start date and time!", Toast.LENGTH_LONG).show();
                    return true;
                } else {
                    if ((eyearStr.isEmpty()) || (emonthStr.isEmpty()) || (edayStr.isEmpty()) || (ehourStr.isEmpty()) || (eminuteStr.isEmpty()) || (esecondStr.isEmpty()) ) {
                        Toast.makeText(this, "You miss something in the end date and time!", Toast.LENGTH_LONG).show();
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private boolean formatCorrect(int y, int m, int d, int h, int i, int s) {
        if (y < 1900 && y > 9999) {
            return false;
        }
        if (m < 1 && m > 12) {
            return false;
        }
        if (d < 1 && m > 31) {
            return false;
        } else {
            if (m == 2) {
                if (y % 4 == 0 && y % 100 != 0) {
                    if (y > 29) {
                        return false;
                    }
                } else if (y % 400 == 0) {
                    if (y > 29) {
                        return false;
                    }
                } else {
                    if (y > 28) {
                        return false;
                    }                }
            } else if (m == 4 || m == 6 || m == 9 || m == 11) {
                if (d > 30) {
                    return false;
                }
            }
        }

        if (h < 0 && h > 24) {
            return false;
        }
        if (i < 0 && i > 24) {
            return false;
        }
        if (s < 0 && s > 24) {
            return false;
        }
        return true;
    }
}
