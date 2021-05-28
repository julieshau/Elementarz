package com.example.elementarz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AppOpsManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Button;
import android.widget.TextView;

import static android.app.AppOpsManager.MODE_ALLOWED;
import static android.app.AppOpsManager.OPSTR_GET_USAGE_STATS;
import android.os.Process;

import java.util.Calendar;
import java.util.Map;
// Czas spędzony w aplikacji
// Statystyki dla:
// Literki
// Cyferki
// Memory łatwe
// Memory trudne

// Progres dziecka dla każdej z gier:
// ile było poprawnych odpowiedzi "małych" a ile błędnych (literek)
// ile razy udało się dziecku przejść każdą z gier
// ile średnio czasu zajmuje przejście gry


// przycisk resetuj statystyki

public class Statistics extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        TextView time_info = (TextView)findViewById(R.id.time_in_game);
        TextView completed_info = (TextView)findViewById(R.id.games_completed_info);

        Context context = getApplicationContext();

        set_games_info(completed_info, time_info);

        Button letters_btn, numbers_btn, memory1_btn, memory2_btn, connect_dots_btn;
        letters_btn = (Button)findViewById(R.id.letters_statistics);
        numbers_btn = (Button)findViewById(R.id.numbers_statistics);
        memory1_btn = (Button)findViewById(R.id.memory_easy_statistics);
        memory2_btn = (Button)findViewById(R.id.memory_hard_statistics);
        connect_dots_btn = (Button)findViewById(R.id.connect_dots_statistics);
        letters_btn.setOnClickListener(v-> {
            try {
                Intent intent = new Intent(Statistics.this, GameStatistics.class);
                intent.putExtra("game", "letters");
                startActivity(intent);
                finish();
            }catch (Exception ignored){}
        });

        numbers_btn.setOnClickListener(v-> {
            try {
                Intent intent = new Intent(Statistics.this, GameStatistics.class);
                intent.putExtra("game", "numbers");
                startActivity(intent);
                finish();
            }catch (Exception ignored){}
        });

        memory1_btn.setOnClickListener(v-> {
            try {
                Intent intent = new Intent(Statistics.this, GameStatistics.class);
                intent.putExtra("game", "memory1");
                startActivity(intent);
                finish();
            }catch (Exception ignored){

            }
        });

        memory2_btn.setOnClickListener(v-> {
            try {
                Intent intent = new Intent(Statistics.this, GameStatistics.class);
                intent.putExtra("game", "memory2");
                startActivity(intent);
                finish();
            }catch (Exception ignored){}
        });

        connect_dots_btn.setOnClickListener(v-> {
            try {
                Intent intent = new Intent(Statistics.this, GameStatistics.class);
                intent.putExtra("game", "connect_dots");
                startActivity(intent);
                finish();
            }catch (Exception ignored){}
        });

    }

    private void set_games_info(TextView completed_info, TextView time_info) {
        Context context = getApplicationContext();
        SharedPreferences stats_letters = context.getSharedPreferences("letters_game_stats", MODE_PRIVATE);
        SharedPreferences stats_numbers = context.getSharedPreferences("number_game_stats", MODE_PRIVATE);
        SharedPreferences stats_memory1 = context.getSharedPreferences("memory1_game_stats", MODE_PRIVATE);
        SharedPreferences stats_memory2 = context.getSharedPreferences("memory2_game_stats", MODE_PRIVATE);
        SharedPreferences stats_connect_dots = context.getSharedPreferences("connect_dots_game_stats", MODE_PRIVATE);

        int completed_games = stats_letters.getInt("completed_games", 0) + stats_numbers.getInt("completed_games", 0) +
                              stats_memory1.getInt("completed_games", 0) + stats_memory2.getInt("completed_games", 0) +
                              stats_connect_dots.getInt("completed_games", 0);
        completed_info.setText("Łącznie ukończono "+completed_games+" gier");

        long elapsed_time = stats_letters.getLong("elapsed_time", 0) + stats_numbers.getLong("elapsed_time", 0) +
                stats_memory1.getLong("elapsed_time", 0) + stats_memory2.getLong("elapsed_time", 0) +
                stats_connect_dots.getLong("elapsed_time", 0);


        int minutes, seconds, hours;
        minutes = (int) ((elapsed_time / (1000*60)) % 60);
        seconds = (int) (elapsed_time / 1000) % 60 ;
        hours   = (int) ((elapsed_time / (1000*60*60)) % 24);
        time_info.setText("Czas spędzony w grach: "+hours+"h"+" "+minutes+"m "+seconds+"s");

    }

    //system button back
    @Override
    public void onBackPressed(){
        try {
            Intent intent = new Intent(Statistics.this, MainActivity.class);
            startActivity(intent);
            finish();
        }catch (Exception e){

        }
    }

}
