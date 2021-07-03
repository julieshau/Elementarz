package com.example.elementarz;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class MemoryGame extends AppCompatActivity {

    public static final int CARD_AMOUNT = 12;

    Dialog dialog;
    Dialog dialogEnd;
    int clicked = 0;
    int matched = 0;
    boolean turnOverForbidden = false;
    int lastClicked = -1;

    private SharedPreferences stats;
    private SharedPreferences.Editor edit;
    private static long start = 0;

    private Integer[] images = {
            R.drawable.dog,
            R.drawable.duck,
            R.drawable.elefant,
            R.drawable.fish,
            R.drawable.pingwin,
            R.drawable.zebra,
            R.drawable.dog,
            R.drawable.duck,
            R.drawable.elefant,
            R.drawable.fish,
            R.drawable.pingwin,
            R.drawable.zebra
    };

    private void initGame(){
        Button[] buttons = {
                (Button) findViewById(R.id.mem_card1),
                (Button) findViewById(R.id.mem_card2),
                (Button) findViewById(R.id.mem_card3),
                (Button) findViewById(R.id.mem_card4),
                (Button) findViewById(R.id.mem_card5),
                (Button) findViewById(R.id.mem_card6),
                (Button) findViewById(R.id.mem_card7),
                (Button) findViewById(R.id.mem_card8),
                (Button) findViewById(R.id.mem_card9),
                (Button) findViewById(R.id.mem_card10),
                (Button) findViewById(R.id.mem_card11),
                (Button) findViewById(R.id.mem_card12),
        };
        //shuffle pictures
        List<Integer> temp = Arrays.asList(images);
        Collections.shuffle(temp);
        temp.toArray(images);

        //set images to buttons
        for (int i = 0; i < images.length; i++){
            buttons[i].setBackgroundResource(images[i]);
        }
        //handler for 5s display of pictures(player should remember the location of each)
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            for (Button button : buttons) {
                button.setBackgroundResource(R.drawable.cardback);
            }
        }, 5000);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.memory);

        stats = getApplicationContext().getSharedPreferences("memory2_game_stats", MODE_PRIVATE);
        edit = stats.edit();
        start =  Calendar.getInstance().getTimeInMillis();

        //fullscreen
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //start dialog window
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.previewdialogmemory);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        //button to close game from start dialog
        TextView buttonClose = (TextView)dialog.findViewById(R.id.btnclose);
        buttonClose.setOnClickListener(v -> {
            try {
                Intent intent = new Intent(MemoryGame.this, MainActivity.class);
                startActivity(intent);
                finish();
            }catch (Exception e){

            }
            dialog.dismiss();
        });
        //button to continue game from start dialog
        Button buttonContinue = (Button)dialog.findViewById(R.id.btncontinue);
        buttonContinue.setOnClickListener(v -> {
            dialog.dismiss();
            //init game is here, because we want 5 sec display of pics AFTER dialog window
            initGame();
        });
        dialog.show();
        MediaPlayer mediaplayer = MediaPlayer.create(MemoryGame.this, R.raw.obrazki);
        mediaplayer.start();

        //winning dialog window
        dialogEnd = new Dialog(this);
        dialogEnd.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogEnd.setContentView(R.layout.previewdialogwin);
        Button buttonContinueEnd = (Button)dialogEnd.findViewById(R.id.btncontinue);
        buttonContinueEnd.setText(R.string.textend);
        dialogEnd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogEnd.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);
        dialogEnd.setCancelable(false);
        //button to close game after winning
        TextView buttonCloseEnd = (TextView)dialogEnd.findViewById(R.id.btnclose);
        buttonCloseEnd.setOnClickListener(v -> {
            try {
                Intent intentClose = new Intent(MemoryGame.this, MainActivity.class);
                startActivity(intentClose);
                finish();
            }catch (Exception e){

            }
            dialogEnd.dismiss();
        });
        //button to continue game after winning(there is only 1 lvl, so the same as close )
        buttonContinueEnd.setOnClickListener(v -> {
            try {
                Intent intentCont = new Intent(MemoryGame.this, MainActivity.class);
                startActivity(intentCont);
                finish();
            }catch (Exception e){

            }
            dialogEnd.dismiss();
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        Button[] buttons = {
                (Button) findViewById(R.id.mem_card1),
                (Button) findViewById(R.id.mem_card2),
                (Button) findViewById(R.id.mem_card3),
                (Button) findViewById(R.id.mem_card4),
                (Button) findViewById(R.id.mem_card5),
                (Button) findViewById(R.id.mem_card6),
                (Button) findViewById(R.id.mem_card7),
                (Button) findViewById(R.id.mem_card8),
                (Button) findViewById(R.id.mem_card9),
                (Button) findViewById(R.id.mem_card10),
                (Button) findViewById(R.id.mem_card11),
                (Button) findViewById(R.id.mem_card12),
        };

        for (int i = 0; i < images.length; i++){
            //cardback+0F means that card isn't turned over
            buttons[i].setText("cardBack");
            buttons[i].setTextSize(0.0F);
            int finalI = i;
            buttons[i].setOnClickListener(v -> {
                if (buttons[finalI].getText() == "cardBack" && !turnOverForbidden){
                    //now card is turned over
                    buttons[finalI].setBackgroundResource(images[finalI]);
                    buttons[finalI].setText(images[finalI]);
                    //if it was first turned over card we should remember it to compare with second later
                    if (clicked == 0){
                        lastClicked = finalI;
                    }
                    clicked++;
                }
                if (clicked == 2){
                    //we can only turn over 2 cards at the same time
                    turnOverForbidden = true;
                    //comparison of two turned over cards
                    if (buttons[finalI].getText() == buttons[lastClicked].getText()){
                        MediaPlayer mediaPlayerGoodAnswer = MediaPlayer.create(MemoryGame.this, R.raw.dobrze_1);
                        mediaPlayerGoodAnswer.start();
                        mediaPlayerGoodAnswer = MediaPlayer.create(MemoryGame.this, R.raw.brawo_1);
                        mediaPlayerGoodAnswer.start();

                        buttons[finalI].setEnabled(false);
                        buttons[lastClicked].setEnabled(false);
                        //continue game
                        turnOverForbidden = false;
                        clicked = 0;
                        matched += 2;

                        edit.putInt("correct_answers", stats.getInt("correct_answers", 0) + 1);
                        edit.apply();
                        //all cards matched
                        if (matched == CARD_AMOUNT){
                            int completed = stats.getInt("completed_games", 0);
                            long completeTimeSum = stats.getLong("complete_time_sum", 0);
                            long timeElapsed = Calendar.getInstance().getTimeInMillis() - start;

                            edit.putInt("completed_games", completed + 1);
                            edit.putLong("complete_time_sum", completeTimeSum + timeElapsed);
                            if(timeElapsed < stats.getLong("best", Long.MAX_VALUE)) {
                                edit.putLong("best", timeElapsed);
                            }
                            edit.putLong("average", (completeTimeSum + timeElapsed)/(completed + 1));
                            edit.apply();
                            dialogEnd.show();
                        }
                    }
                    else{
                        MediaPlayer mediaPlayerWrongAnswer = MediaPlayer.create(MemoryGame.this, R.raw.zle_1);
                        mediaPlayerWrongAnswer.start();
                        mediaPlayerWrongAnswer = MediaPlayer.create(MemoryGame.this, R.raw.blad_1);
                        mediaPlayerWrongAnswer.start();
                        Handler handler = new Handler();
                        handler.postDelayed(() -> {
                            buttons[finalI].setBackgroundResource(R.drawable.cardback);
                            buttons[finalI].setText("cardBack");
                            buttons[lastClicked].setBackgroundResource(R.drawable.cardback);
                            buttons[lastClicked].setText("cardBack");
                            lastClicked = -1;
                            clicked = 0;
                            turnOverForbidden = false;
                            edit.putInt("wrong_answers", stats.getInt("wrong_answers", 0) + 1);
                            edit.apply();
                        }, 1000);
                    }
                }
                else if (clicked == 0){
                    turnOverForbidden = false;
                }
            });
        }
    }

    //system button back
    @Override
    public void onBackPressed(){
        try {
            Intent intent = new Intent(MemoryGame.this, MainActivity.class);
            startActivity(intent);
            finish();
        }catch (Exception e){

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        long timeElapsed = Calendar.getInstance().getTimeInMillis() - start;
        start = 0;
        edit.putLong("elapsed_time", stats.getLong("elapsed_time", 0) + timeElapsed);
        edit.apply();
        SharedPreferences generalStats = getApplicationContext().getSharedPreferences("general_stats", MODE_PRIVATE);
        SharedPreferences.Editor editGeneral = generalStats.edit();
        editGeneral.putLong("elapsed_time", generalStats.getLong("elapsed_time", 0) + timeElapsed);
        editGeneral.apply();
    }
}
