package com.example.yellowobjects.ui.poster_page;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yellowobjects.R;
import com.example.yellowobjects.ui.schedule.AddEvent;

import java.io.InputStream;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class EventListAdapter extends ArrayAdapter<Event> {
    Context mCtx;
    int resource;
    List<Event> eventList;

    public EventListAdapter(Context mCtx, int resource, List<Event> eventList) {
        super(mCtx, resource, eventList);

        this.mCtx = mCtx;
        this.resource = resource;
        this.eventList = eventList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);

        final View view = inflater.inflate(resource, null);

        TextView title = view.findViewById(R.id.title);
        TextView desc = view.findViewById(R.id.desc);
        TextView time = view.findViewById(R.id.time);
        ImageView poster = view.findViewById(R.id.poster);
        Button button = view.findViewById(R.id.button);

        final Event event = eventList.get(position);

        title.setText(event.getTitle());
        desc.setText(event.getDesc());
        time.setText(event.getStartTime());

        if (event.getImage() != "null") {
            new DownloadImageTask(poster).execute(event.getImage());
        } else {
            poster.setImageDrawable(mCtx.getResources().getDrawable(R.drawable.bg));
        }

        poster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent eiIntent = new Intent(view.getContext(), EventInfo.class);
                eiIntent.putExtra("id", event.getId());
                mCtx.startActivity(eiIntent);
            }
        });

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent addIntent = new Intent(view.getContext(), AddEvent.class);
                addIntent.putExtra("title", event.getTitle());
                addIntent.putExtra("desc", event.getDesc());
                addIntent.putExtra("venue", event.getVenue());

                addIntent.putExtra("starty", String.format("%02d", event.getStarty()));
                addIntent.putExtra("startm", String.format("%02d", event.getStartm()));
                addIntent.putExtra("startd", String.format("%02d", event.getStartd()));
                addIntent.putExtra("starth", String.format("%02d", event.getStarth()));
                addIntent.putExtra("starti", String.format("%02d", event.getStarti()));
                addIntent.putExtra("starts", String.format("%02d", event.getStarts()));

                addIntent.putExtra("endy", String.format("%02d", event.getEndy()));
                addIntent.putExtra("endm", String.format("%02d", event.getEndm()));
                addIntent.putExtra("endd", String.format("%02d", event.getEndd()));
                addIntent.putExtra("endh", String.format("%02d", event.getEndh()));
                addIntent.putExtra("endi", String.format("%02d", event.getEndi()));
                addIntent.putExtra("ends", String.format("%02d", event.getEnds()));

                mCtx.startActivity(addIntent);
            }
        });

        return view;
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
}
