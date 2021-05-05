package com.example.elementarz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LettersGameLevels extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gamelevels);

        SharedPreferences save = getSharedPreferences("Save", MODE_PRIVATE);
        final int level = save.getInt("Level", 1);

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Button buttonBack = (Button)findViewById(R.id.button_back);
        buttonBack.setOnClickListener(v -> {
            try {
                Intent intent = new Intent(LettersGameLevels.this, MainActivity.class);
                startActivity(intent);
                finish();
            }catch (Exception e){

            }
        });

        //first level button
        TextView textView1 = (TextView)findViewById(R.id.textView1);
        textView1.setOnClickListener(v -> {
            try {
                if (level <=1) {
                    Intent intent = new Intent(LettersGameLevels.this, LettersLevel1.class);
                    startActivity(intent);
                    finish();
                }
            }catch (Exception e){

            }
        });

        //second level button
        TextView textView2 = (TextView)findViewById(R.id.textView2);
        textView2.setOnClickListener(v -> {
            try {
                if (level <= 2) {
                    Intent intent = new Intent(LettersGameLevels.this, LettersLevel2.class);
                    startActivity(intent);
                    finish();
                }
            }catch (Exception e){

            }
        });

        final int[] x = {
                R.id.textView1,
                R.id.textView2,
                R.id.textView3,
                R.id.textView4,
                R.id.textView5,
                R.id.textView6,
                R.id.textView7,
                R.id.textView8,
                R.id.textView9,
                R.id.textView10,
                R.id.textView11,
                R.id.textView12,
                R.id.textView13,
                R.id.textView14,
                R.id.textView15,
                R.id.textView16
        };
        for (int i = 0; i < level; i++){
            TextView tv = findViewById(x[i]);
            tv.setText(""+(i+1));
        }
    }
    //system button back
    @Override
    public void onBackPressed(){
        try {
            Intent intent = new Intent(LettersGameLevels.this, MainActivity.class);
            startActivity(intent);
            finish();
        }catch (Exception e){

        }
    }
}