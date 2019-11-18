package com.example.yellowobjects.ui.dashboard;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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
                pdialog.setMessage("Before ...");
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

            String titleStr = jsonEvent.getString("title");
            title.setText(titleStr);

            String descStr = jsonEvent.getString("description");
            desc.setText(descStr);

            JSONObject jstartdt = jsonEvent.getJSONObject("startdt");
            String syearStr = jstartdt.getString("year");
            String smonthStr = jstartdt.getString("month");
            String sdayStr = jstartdt.getString("day");
            String shourStr = jstartdt.getString("hour");
            String sminuteStr = jstartdt.getString("minute");
            String ssecondStr = jstartdt.getString("second");
            String startdtStr = syearStr + "-" + smonthStr + "-" + sdayStr + " " + shourStr + ":" + sminuteStr + ":" + ssecondStr;
            startdt.setText(startdtStr);

            JSONObject jenddt = jsonEvent.getJSONObject("startdt");
            String eyearStr = jenddt.getString("year");
            String emonthStr = jenddt.getString("month");
            String edayStr = jenddt.getString("day");
            String ehourStr = jenddt.getString("hour");
            String eminuteStr = jenddt.getString("minute");
            String esecondStr = jenddt.getString("second");
            String enddtStr = eyearStr + "-" + emonthStr + "-" + edayStr + " " + ehourStr + ":" + eminuteStr + ":" + esecondStr;
            enddt.setText(enddtStr);

            String venueStr = jsonEvent.getString("venue");
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
