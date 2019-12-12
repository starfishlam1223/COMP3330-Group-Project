package hk.hkucs.yellowobjects.ui.schedule;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import hk.hkucs.yellowobjects.R;
import hk.hkucs.yellowobjects.ui.database.DatabaseQuery;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class UserEventInfo extends AppCompatActivity {
    private DatabaseQuery query;

    int id;

    TextView title;
    TextView desc;
    TextView startdt;
    TextView enddt;
    TextView venue;

    Button deleteEvent;
    Button back;

    String titleStr;
    String descStr;
    String venueStr;
    String startStr;
    String endStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_event_info);

        final Context ctx = this;
        query = new DatabaseQuery(ctx);
        EventObject event;

        title = findViewById(R.id.title);
        desc = findViewById(R.id.desc);
        startdt = findViewById(R.id.startdt);
        enddt = findViewById(R.id.enddt);
        venue = findViewById(R.id.venue);

        deleteEvent = findViewById(R.id.delete_event);
        back = findViewById(R.id.back);

        Intent parent = getIntent();
        id = parent.getIntExtra("id", -1);

        event = query.getEvent(id);

        if (event != null) {
            title.setText(event.getTitle());
            desc.setText(event.getDesc());
            venue.setText(event.getVenue());

            DateFormat f = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
            String startStr = f.format(event.getStartdt());
            String endStr = f.format(event.getEnddt());

            startdt.setText(startStr);
            enddt.setText(endStr);

            deleteEvent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ctx);

                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int i) {
                            query.deleteEvent(id);
                            Toast.makeText(ctx, "The event has been deleted!", Toast.LENGTH_LONG);
                            dialog.dismiss();

                            Intent intent = new Intent();
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int i) {
                            dialog.dismiss();
                        }
                    });
                    builder.setMessage("Do you wish to delete the event?").setTitle("Confirm");
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });

            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    setResult(RESULT_CANCELED, intent);
                    finish();
                }
            });

        } else {
            Toast.makeText(this, "Cannot display event!", Toast.LENGTH_LONG);
            Intent intent = new Intent();
            setResult(RESULT_CANCELED, intent);
            finish();
        }
    }
}
