package com.metekzck.catchtheosman;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    TextView timerText;
    TextView yakalamaText;
    ImageView imageView;
    int yakalamaSayisi;

    Random random;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            yakalamaSayisi = 0;
            random = new Random();
            timerText = findViewById(R.id.timerText);
            yakalamaText = findViewById(R.id.yakalamaText);
            imageView = findViewById(R.id.imageView);
            Toast.makeText(this, "GERI SAYIM BASLADI!!", Toast.LENGTH_SHORT).show();
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    yakalamaSayisi++;
                    yakalamaText.setText("Yakalama sayisi:"+yakalamaSayisi);
                    moveImageRandomly();
                }
            });
            new CountDownTimer(20000,1000){
                @Override
                public void onTick(long millisUntilFinished) {
                    timerText.setText("Geri kalan zaman:"+millisUntilFinished/1000);
                }

                @Override
                public void onFinish() {
                    timerText.setText("Zaman bitti!!!");
                    imageView.setVisibility(View.INVISIBLE);
                    Toast.makeText(MainActivity.this,"SURENIZ BITTI!!!", Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                    alert.setTitle("Oyun bitti!!");
                    alert.setMessage("Tekrar oynamak ister misiniz?");
                    alert.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            recreate();
                        }
                    });
                    alert.setNegativeButton("Hayir", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
                    alert.show();
                }

            }.start();

            return insets;
        });
    }
    private void moveImageRandomly() {
        int screenWidth = findViewById(android.R.id.content).getWidth();
        int screenHeight = findViewById(android.R.id.content).getHeight();

        if (screenWidth > imageView.getWidth() && screenHeight > imageView.getHeight()) {
            float newX = random.nextInt(screenWidth - imageView.getWidth());
            float newY = random.nextInt(screenHeight - imageView.getHeight());
            imageView.setX(newX);
            imageView.setY(newY);
        } else {
            Log.e("Error", "Screen size is too small for the ImageView.");
        }
    }

}