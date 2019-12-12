package hk.hkucs.yellowobjects.ui.ybmap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import hk.hkucs.yellowobjects.R;

public class Result extends AppCompatActivity {
    ImageView resultImage;
    TextView scoreText;
    TextView comment;
    Button qButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        resultImage = findViewById(R.id.resultImage);
        scoreText = findViewById(R.id.score);
        comment = findViewById(R.id.comment);
        qButton = findViewById(R.id.qButton);

        Intent parent = getIntent();
        int score = parent.getIntExtra("score", -1);

        scoreText.setText(score + "/10");
        if (score <= 5) {
            resultImage.setImageResource(R.drawable.fail);
            comment.setText("Wake up! You can work better on identifying our enemies!");
        } else {
            resultImage.setImageResource(R.drawable.success);
            comment.setText("Good job there! You are becoming a awaken Hongkonger!");
        }

        qButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                finish();
            }
        });
    }
}
