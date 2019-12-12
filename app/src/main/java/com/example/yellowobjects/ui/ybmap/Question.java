package com.example.yellowobjects.ui.ybmap;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.yellowobjects.R;

public class Question extends AppCompatActivity {
    ShopList qList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        qList = new ShopList();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
    }
}
