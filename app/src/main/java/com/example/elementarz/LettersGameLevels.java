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

    public static final int LVL_AMOUNT = 6;

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

        //level buttons
        TextView[] textViews = {findViewById(R.id.textView1), findViewById(R.id.textView2),
                findViewById(R.id.textView3), findViewById(R.id.textView4),
                findViewById(R.id.textView5), findViewById(R.id.textView6)};

        for (int i = 1; i <= 6; i++){
            int finalI = i;
            textViews[i - 1].setOnClickListener(v -> {
                try {
                    if (level == finalI) {
                        Intent intent = new Intent(LettersGameLevels.this, LettersLevel.class);
                        startActivity(intent);
                        finish();
                    }
                }catch (Exception e){

                }
            });
        }
        //add numbers to completed levels
        final int[] x = {
                R.id.textView1,
                R.id.textView2,
                R.id.textView3,
                R.id.textView4,
                R.id.textView5,
                R.id.textView6,
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