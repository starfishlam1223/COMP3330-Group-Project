package com.example.yellowobjects.ui.poster_page;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.fragment.app.ListFragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.yellowobjects.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PosterPageFragment extends ListFragment implements AdapterView.OnItemClickListener {

    private PosterPageViewModel posterPageViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        posterPageViewModel =
                ViewModelProviders.of(this).get(PosterPageViewModel.class);
        View root = inflater.inflate(R.layout.fragment_poster_page, container, false);
//        final TextView textView = root.findViewById(R.id.text_dashboard);
//        posterPageViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        connect();
    }

    public void connect(){
        final ProgressDialog pdialog = new ProgressDialog(getActivity());

        pdialog.setCancelable(false);
        pdialog.setMessage("Connecting ...");
        pdialog.show();

        final String url = "https://i.cs.hku.hk/~hslam/comp3330/yellow-objects/php/list.php";

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
        List<Event> events = new ArrayList<Event>();

        try {
            JSONObject rootJSONObj = new JSONObject(JSONString);
            JSONArray jsonEvents = rootJSONObj.getJSONArray("list");

            for (int i = 0 ; i < jsonEvents.length(); i++) {
                JSONObject jsonEvent = jsonEvents.getJSONObject(i);

                String id = jsonEvent.getString("id");
                String title = jsonEvent.getString("title");
                String desc = jsonEvent.getString("description");

                JSONObject startdt = jsonEvent.getJSONObject("startdt");
                int year = Integer.parseInt(startdt.getString("year"));
                int month = Integer.parseInt(startdt.getString("month"));
                int day = Integer.parseInt(startdt.getString("day"));
                int hour = Integer.parseInt(startdt.getString("hour"));
                int minute = Integer.parseInt(startdt.getString("minute"));
                int second = Integer.parseInt(startdt.getString("second"));

                String image = jsonEvent.getString("image");

                Event event = new Event(image, title, desc, id, year, month, day, hour, minute, second);

                events.add(event);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        EventListAdapter adapter = new EventListAdapter(getActivity(), R.layout.event_list_item, events);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
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
        new AlertDialog.Builder(getActivity())
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Event event = (Event) parent.getItemAtPosition(position);

        Intent eiIntent = new Intent(view.getContext(), EventInfo.class);
        eiIntent.putExtra("id", event.getId());
        startActivityForResult(eiIntent, 0);
    }
}