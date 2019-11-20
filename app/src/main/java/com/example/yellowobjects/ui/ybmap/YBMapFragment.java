package com.example.yellowobjects.ui.ybmap;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.yellowobjects.R;

public class YBMapFragment extends Fragment {

    private YBMapViewModel YBMapViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        YBMapViewModel =
                ViewModelProviders.of(this).get(YBMapViewModel.class);
        View root = inflater.inflate(R.layout.fragment_ybmap, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        YBMapViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}