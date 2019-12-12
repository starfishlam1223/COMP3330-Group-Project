package com.example.yellowobjects.ui.ybmap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yellowobjects.R;

public class Question extends AppCompatActivity {
    Context ctx = this;

    ShopList qList;

    TextView currentQuestion;
    TextView shopName;
    ImageView shopImage;

    Button yButton;
    Button bButton;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("resultCode", String.valueOf(requestCode));
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                qList.answer(true);
            } else {
                qList.answer(false);
            }

            if (qList.currentQuestion > 10) {
                Intent result = new Intent(ctx, Result.class);
                result.putExtra("score", qList.getCorrectCount());
                startActivityForResult(result, 1);
            } else {
                showNewQuestion(ctx);
            }
        } else if (requestCode == 1) {
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        qList = new ShopList();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        currentQuestion = findViewById(R.id.questionCount);
        shopName = findViewById(R.id.shopName);
        shopImage = findViewById(R.id.shopImage);

        yButton = findViewById(R.id.yButton);
        bButton = findViewById(R.id.bButton);

        showNewQuestion(ctx);
    }

    public void showNewQuestion(final Context ctx) {
        Shop shop = qList.getRandomShop();

        currentQuestion.setText("Question " + qList.getCurrentQuestion());
        shopName.setText(shop.getName());
        shopImage.setImageResource(shop.getImageId());

        final int id = shop.getId();

        bButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent blue = new Intent(ctx, Answer.class);
                blue.putExtra("id", id);
                blue.putExtra("isYellow", false);
                startActivityForResult(blue, 0);
            }
        });

        yButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent yellow = new Intent(ctx, Answer.class);
                yellow.putExtra("id", id);
                yellow.putExtra("isYellow", true);
                startActivityForResult(yellow, 0);
            }
        });
    }
}
