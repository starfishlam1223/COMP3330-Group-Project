package com.example.yellowobjects.ui.ybmap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yellowobjects.R;

public class Answer extends AppCompatActivity {
    Context ctx = this;
    ShopList qlist;

    TextView result;
    TextView shopName;
    ImageView shopImage;
    TextView answer;

    Button cont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        qlist = new ShopList();

        Intent parent = getIntent();
        int id = parent.getIntExtra("id", -1);
        final Shop shop = qlist.getShop(id);

        result = findViewById(R.id.result);
        shopName = findViewById(R.id.shopName);
        shopImage = findViewById(R.id.shopImage);
        answer = findViewById(R.id.answer);

        cont = findViewById(R.id.cButton);

        final boolean isYellow = parent.getBooleanExtra("isYellow", false);
        if (shop.isYellow() == isYellow) {
            result.setText("Correct!");
        } else {
            result.setText("Wrong!");
        }
        if (shop.isYellow()) {
            answer.setText("The shop is a yellow one!");
        } else {
            answer.setText("The shop is a blue one!");
        }
        shopName.setText(shop.getName());
        shopImage.setImageResource(shop.getImageId());

        cont.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent();
                if (shop.isYellow() == isYellow) {
                    setResult(RESULT_OK, intent);
                } else {
                    setResult(RESULT_CANCELED, intent);
                }
                finish();
            }
        });
    }
}
