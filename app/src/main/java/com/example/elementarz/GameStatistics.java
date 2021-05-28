package com.example.elementarz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class GameStatistics extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context context = getApplicationContext();
        setContentView(R.layout.activity_game_statistics);
        String game_name = getIntent().getExtras().getString("game", "");
        Button game_name_btn = (Button)findViewById(R.id.game_name);;
        Button reset_btn = (Button)findViewById(R.id.reset_statistics);

        TextView time_in_game_stats = (TextView)findViewById(R.id.time_in_game_stats);
        TextView correct_answers_stats = (TextView)findViewById(R.id.correct_answers_stats);
        TextView wrong_answers_stats = (TextView)findViewById(R.id.wrong_answers_stats);
        TextView completed_game_stats = (TextView)findViewById(R.id.completed_game_stats);
        TextView average_time_completing_game = (TextView)findViewById(R.id.average_time_completing_game);
        TextView best_time_completing_game = (TextView)findViewById(R.id.best_time_completing_game);

        SharedPreferences stats;
        switch (game_name) {
            case "letters":
                game_name_btn.setText("LITERKI");
                stats = context.getSharedPreferences("letters_game_stats", MODE_PRIVATE);

                break;
            case "numbers":
                game_name_btn.setText("CYFERKI");
                stats = context.getSharedPreferences("number_game_stats", MODE_PRIVATE);
                break;
            case "memory1":
                game_name_btn.setText("MEMORY ŁATWE");
                stats = context.getSharedPreferences("memory1_game_stats", MODE_PRIVATE);

                break;
            case "memory2":
                game_name_btn.setText("MEMORY TRUDNE");
                stats = context.getSharedPreferences("memory2_game_stats", MODE_PRIVATE);

                break;
            default:  // Connect Dots
                game_name_btn.setText("ŁĄCZENIE KROPEK");
                stats = context.getSharedPreferences("connect_dots_game_stats", MODE_PRIVATE);
                break;
        }

        reset_btn.setOnClickListener(v-> {
            try {
                SharedPreferences.Editor edit = stats.edit();
                edit.clear().apply();
                Intent intent = new Intent(GameStatistics.this, GameStatistics.class);
                intent.putExtra("game", game_name);
                startActivity(intent);
                finish();
            }catch (Exception ignored){}
        });

        long elapsed_time = stats.getLong("elapsed_time", 0);
        append_time(time_in_game_stats, elapsed_time);
        int correct = stats.getInt("correct_answers", 0);
        correct_answers_stats.append(correct+"");
        int wrong = stats.getInt("wrong_answers", 0);
        wrong_answers_stats.append(wrong+"");
        int completed = stats.getInt("completed_games", 0);
        completed_game_stats.append(completed+"");

        long average = stats.getLong("average", 0);
        append_time(average_time_completing_game, average);

        long best = stats.getLong("best", 0);
        append_time(best_time_completing_game, best);

    }

    private void append_time(TextView textView, long elapsed_time) {
        int minutes, seconds, hours;
        minutes = (int) ((elapsed_time / (1000*60)) % 60);
        seconds = (int) (elapsed_time / 1000) % 60 ;
        hours   = (int) ((elapsed_time / (1000*60*60)) % 24);
        textView.append(hours+"h"+" "+minutes+"m "+seconds+"s");
    }

    //system button back
    @Override
    public void onBackPressed() {
        try {
            Intent intent = new Intent(GameStatistics.this, Statistics.class);
            startActivity(intent);
            finish();
        } catch (Exception e) {

        }
    }
}