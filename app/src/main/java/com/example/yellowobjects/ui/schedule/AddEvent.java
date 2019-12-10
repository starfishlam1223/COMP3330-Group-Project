package com.example.yellowobjects.ui.schedule;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.yellowobjects.R;

public class AddEvent extends AppCompatActivity {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

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

        Intent parent = getIntent();
        String titleStr = parent.getStringExtra("title");
        String descStr = parent.getStringExtra("desc");
        String venueStr = parent.getStringExtra("venue");

        String syearStr = parent.getStringExtra("starty");
        String smonthStr = parent.getStringExtra("startm");
        String sdayStr = parent.getStringExtra("startd");
        String shourStr = parent.getStringExtra("starth");
        String sminuteStr = parent.getStringExtra("starti");
        String ssecondStr = parent.getStringExtra("starts");

        String eyearStr = parent.getStringExtra("endy");
        String emonthStr = parent.getStringExtra("endm");
        String edayStr = parent.getStringExtra("endd");
        String ehourStr = parent.getStringExtra("endh");
        String eminuteStr = parent.getStringExtra("endi");
        String esecondStr = parent.getStringExtra("ends");

        title.setText(titleStr);
        venue.setText(venueStr);
        desc.setText(descStr);

        starty.setText(syearStr);
        startm.setText(smonthStr);
        startd.setText(sdayStr);
        starth.setText(shourStr);
        starti.setText(sminuteStr);
        starts.setText(ssecondStr);

        endy.setText(eyearStr);
        endm.setText(emonthStr);
        endd.setText(edayStr);
        endh.setText(ehourStr);
        endi.setText(eminuteStr);
        ends.setText(esecondStr);
    }
}
