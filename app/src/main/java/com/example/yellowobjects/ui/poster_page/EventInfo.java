package com.example.yellowobjects.ui.poster_page;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yellowobjects.R;
import com.example.yellowobjects.ui.schedule.AddEvent;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class EventInfo extends AppCompatActivity {
    String id;

    ImageView poster;

    TextView title;
    TextView desc;
    TextView startdt;
    TextView enddt;
    TextView venue;

    Button addEvent;
    Button back;

    String titleStr;
    String descStr;
    String venueStr;

    String syearStr;
    String smonthStr;
    String sdayStr;
    String shourStr;
    String sminuteStr;
    String ssecondStr;

    String eyearStr;
    String emonthStr;
    String edayStr;
    String ehourStr;
    String eminuteStr;
    String esecondStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_info);

        poster = findViewById(R.id.poster);
        title = findViewById(R.id.title);
        desc = findViewById(R.id.desc);
        startdt = findViewById(R.id.startdt);
        enddt = findViewById(R.id.enddt);
        venue = findViewById(R.id.venue);

        addEvent = findViewById(R.id.add_event);
        back = findViewById(R.id.back);

        Intent parent = getIntent();
        id = parent.getStringExtra("id");

        connect(id);
    }

    public void connect(final String id){
        final ProgressDialog pdialog = new ProgressDialog(this);

        pdialog.setCancelable(false);
        pdialog.setMessage("Connecting ...");
        pdialog.show();

        final String url = "https://i.cs.hku.hk/~hslam/comp3330/yellow-objects/php/event.php?id=" + android.net.Uri.encode(id, "UTF-8");

        AsyncTask<String, Void, String> task = new AsyncTask<String, Void, String>() {
            boolean success;
            String jsonString;

            @Override
            protected String doInBackground(String... arg0) {
                // TODO Auto-generated method stub
                success = true;
                pdialog.setMessage("Loading...");
                pdialog.show();
                jsonString = getJsonPage(url);
                if (jsonString.equals("Fail to login"))
                    success = false;
                return null;
            }

            @Override
            protected void onPostExecute(String result) {
                if (success) {
                    parse_JSON_String_and_Switch_Activity(jsonString);
                } else {
                    alert( "Error", "Fail to connect" );
                }
                pdialog.hide();
            }
        }.execute("");
    }

    public void parse_JSON_String_and_Switch_Activity(String JSONString) {
        try {
            JSONObject jsonEvent = new JSONObject(JSONString);

            titleStr = jsonEvent.getString("title");
            title.setText(titleStr);

            descStr = jsonEvent.getString("description");
            desc.setText(descStr);

            JSONObject jstartdt = jsonEvent.getJSONObject("startdt");
            syearStr = jstartdt.getString("year");
            smonthStr = jstartdt.getString("month");
            sdayStr = jstartdt.getString("day");
            shourStr = jstartdt.getString("hour");
            sminuteStr = jstartdt.getString("minute");
            ssecondStr = jstartdt.getString("second");
            String startdtStr = syearStr + "-" + smonthStr + "-" + sdayStr + " " + shourStr + ":" + sminuteStr + ":" + ssecondStr;
            startdt.setText(startdtStr);

            JSONObject jenddt = jsonEvent.getJSONObject("enddt");
            eyearStr = jenddt.getString("year");
            emonthStr = jenddt.getString("month");
            edayStr = jenddt.getString("day");
            ehourStr = jenddt.getString("hour");
            eminuteStr = jenddt.getString("minute");
            esecondStr = jenddt.getString("second");
            String enddtStr = eyearStr + "-" + emonthStr + "-" + edayStr + " " + ehourStr + ":" + eminuteStr + ":" + esecondStr;
            enddt.setText(enddtStr);

            venueStr = jsonEvent.getString("venue");
            venue.setText(venueStr);

            String imageStr = jsonEvent.getString("image");
            if (imageStr != "null") {
                new DownloadImageTask(poster).execute(imageStr);
            } else {
                poster.setImageDrawable(getResources().getDrawable(R.drawable.bg));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentTrigger();
            }
        });
    }

    private void intentTrigger () {
        Intent addIntent = new Intent(this, AddEvent.class);
        addIntent.putExtra("title", titleStr);
        addIntent.putExtra("desc", descStr);
        addIntent.putExtra("venue", venueStr);

        addIntent.putExtra("starty", syearStr);
        addIntent.putExtra("startm", smonthStr);
        addIntent.putExtra("startd", sdayStr);
        addIntent.putExtra("starth", shourStr);
        addIntent.putExtra("starti", sminuteStr);
        addIntent.putExtra("starts", ssecondStr);

        addIntent.putExtra("endy", eyearStr);
        addIntent.putExtra("endm", emonthStr);
        addIntent.putExtra("endd", edayStr);
        addIntent.putExtra("endh", ehourStr);
        addIntent.putExtra("endi", eminuteStr);
        addIntent.putExtra("ends", esecondStr);

        startActivity(addIntent);
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

    public String getJsonPage(String url) {
        HttpURLConnection conn_object = null;
        final int HTML_BUFFER_SIZE = 2*1024*1024;
        char htmlBuffer[] = new char[HTML_BUFFER_SIZE];

        try {
            URL url_object = new URL(url);
            conn_object = (HttpURLConnection) url_object.openConnection();
            conn_object.setInstanceFollowRedirects(true);

            BufferedReader reader_list = new BufferedReader(new InputStreamReader(conn_object.getInputStream()));
            String HTMLSource = ReadBufferedHTML(reader_list, htmlBuffer, HTML_BUFFER_SIZE);
            reader_list.close();
            return HTMLSource;
        } catch (Exception e) {
            return "Fail to login";
        } finally {
            // When HttpClient instance is no longer needed,
            // shut down the connection manager to ensure
            // immediate deallocation of all system resources
            if (conn_object != null) {
                conn_object.disconnect();
            }
        }
    }

    public String ReadBufferedHTML(BufferedReader reader, char [] htmlBuffer, int bufSz) throws java.io.IOException
    {
        htmlBuffer[0] = '\0';
        int offset = 0;
        do {
            int cnt = reader.read(htmlBuffer, offset, bufSz - offset);
            if (cnt > 0) {
                offset += cnt;
            } else {
                break;
            }
        } while (true);
        return new String(htmlBuffer);
    }

    protected void alert(String title, String mymessage){
        new AlertDialog.Builder(this)
                .setMessage(mymessage)
                .setTitle(title)
                .setCancelable(true)
                .setNegativeButton(android.R.string.cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton){}
                        }
                )
                .show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
