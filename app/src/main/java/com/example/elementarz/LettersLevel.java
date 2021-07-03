package com.example.elementarz;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Random;

public class LettersLevel extends AppCompatActivity {

    Dialog dialog;
    Dialog dialogEnd;

    private int numLeft;
    private int numRight;
    private int num;
    private boolean leftCorrect;
    private int count = 0;

    private ImageView imgLeft;
    private ImageView imgRight;

    Letters array = new Letters();
    LettersSounds sounds = new LettersSounds();
    Random random = new Random();

    private SharedPreferences stats;
    private SharedPreferences.Editor edit;
    private SharedPreferences save;
    private static long start = 0;

    private final int[] progress = {
            R.id.point1, R.id.point2, R.id.point3,
            R.id.point4, R.id.point5, R.id.point6, R.id.point7};

    private void initLetters(){
        count = 0;
        //choose answer
        num = random.nextInt(array.images.length);
        MediaPlayer mediaplayer = MediaPlayer.create(LettersLevel.this, sounds.sounds[num]);
        mediaplayer.start();
        //chose correct button
        leftCorrect = random.nextBoolean();
        //choose incorrect answer
        if (leftCorrect){
            numLeft = num;
            do {
                numRight = random.nextInt(array.images.length);
            } while (numLeft == numRight);
        } else {
            numRight = num;
            do {
                numLeft = random.nextInt(array.images.length);
            } while (numLeft == numRight);
        }

        imgLeft.setImageResource(array.images[numLeft]);
        imgRight.setImageResource(array.images[numRight]);
    }

    private void colorProgress(){
        for (int value : progress) {
            TextView tv = findViewById(value);
            tv.setBackgroundResource(R.drawable.style_points);
        }
        for (int i = 0; i < count; i++){
            TextView tv = findViewById(progress[i]);
            tv.setBackgroundResource(R.drawable.style_points_green);
        }
    }

    private void generateNextLetters(){
        num = random.nextInt(array.images.length);
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            MediaPlayer mediaplayer = MediaPlayer.create(LettersLevel.this, sounds.sounds[num]);
            mediaplayer.start();
        }, 2000);
        leftCorrect = random.nextBoolean();
        if (leftCorrect){
            numLeft = num;
            do {
                numRight = random.nextInt(array.images.length);
            } while (numLeft == numRight);
        } else {
            numRight = num;
            do {
                numLeft = random.nextInt(array.images.length);
            } while (numLeft == numRight);
        }
        Animation a = AnimationUtils.loadAnimation(LettersLevel.this, R.anim.alpha);
        imgLeft.setImageResource(array.images[numLeft]);
        imgLeft.startAnimation(a);
        imgRight.setImageResource(array.images[numRight]);
        imgRight.startAnimation(a);
        imgRight.setEnabled(true);
        imgLeft.setEnabled(true);
    }

    private void checkEnd(int newLevel){
        if (count == progress.length){
            //exit level
            if (newLevel <= LettersGameLevels.LVL_AMOUNT){
                SharedPreferences.Editor editor = save.edit();
                editor.putInt("Level", newLevel);
                editor.apply();
            }
            update_stats();
            dialogEnd.show();
        }
        else {
            generateNextLetters();
        }
    }

    private void checkAnswer(boolean leftButton, MotionEvent event){
        if (event.getAction() == MotionEvent.ACTION_DOWN){ //click
            if (leftButton){
                imgRight.setEnabled(false); //blocking right image
                if (numLeft == num){
                    imgLeft.setImageResource(R.drawable.img_true);
                } else {
                    imgLeft.setImageResource(R.drawable.img_false);
                }
            }
            else {
                imgLeft.setEnabled(false); //blocking left image
                if (numRight == num){
                    imgRight.setImageResource(R.drawable.img_true);
                } else {
                    imgRight.setImageResource(R.drawable.img_false);
                }
            }
        }
        else if (event.getAction() == MotionEvent.ACTION_UP){ //click ended
            if ((leftButton && numLeft == num) || (!leftButton && numRight == num)) { //right answer
                if (count < progress.length) { //number of right answers
                    count++;
                }
                MediaPlayer mediaplayer = MediaPlayer.create(LettersLevel.this, R.raw.dobrze_1);
                mediaplayer.start();
                mediaplayer = MediaPlayer.create(LettersLevel.this, R.raw.brawo_1);
                mediaplayer.start();
                edit.putInt("correct_answers", stats.getInt("correct_answers", 0) + 1);
            }
            else { //wrong answer
                if (count > 0){
                    count--;
                }
                MediaPlayer mediaplayer = MediaPlayer.create(LettersLevel.this, R.raw.zle_1);
                mediaplayer.start();
                mediaplayer = MediaPlayer.create(LettersLevel.this, R.raw.blad_1);
                mediaplayer.start();
                edit.putInt("wrong_answers", stats.getInt("wrong_answers", 0) + 1);
            }
            edit.apply();
            colorProgress();
            int level = save.getInt("Level", 1);
            checkEnd(level + 1);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.universal);

        stats = getApplicationContext().getSharedPreferences("letters_game_stats", MODE_PRIVATE);
        edit = stats.edit();
        start =  Calendar.getInstance().getTimeInMillis();

        save = getSharedPreferences("Save", MODE_PRIVATE);
        final int level = save.getInt("Level", 1);
        TextView text_levels = findViewById(R.id.text_levels);
        text_levels.setText(R.string.level);
        text_levels.append(" "+level);

        imgLeft = (ImageView)findViewById(R.id.img_left);
        imgLeft.setClipToOutline(true);
        imgRight = (ImageView)findViewById(R.id.img_right);
        imgRight.setClipToOutline(true);

        //fullscreen
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //start dialog window
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.previewdialogletters);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        //button to close start dialog
        TextView buttonClose = (TextView)dialog.findViewById(R.id.btnclose);
        buttonClose.setOnClickListener(v -> {
            try {
                Intent intent = new Intent(LettersLevel.this, LettersGameLevels.class);
                startActivity(intent);
                finish();
            }catch (Exception e){

            }
            dialog.dismiss();
        });

        //button to start game
        Button buttonContinue = (Button)dialog.findViewById(R.id.btncontinue);
        buttonContinue.setOnClickListener(v -> {
            dialog.dismiss();
            initLetters();
        });
        dialog.show();

        //winning dialog window
        dialogEnd = new Dialog(this);
        dialogEnd.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogEnd.setContentView(R.layout.previewdialogwin);
        dialogEnd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogEnd.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);
        dialogEnd.setCancelable(false);
        //button to close game after winning
        TextView buttonCloseEnd = (TextView)dialogEnd.findViewById(R.id.btnclose);
        buttonCloseEnd.setOnClickListener(v -> {
            try {
                Intent intent = new Intent(LettersLevel.this, LettersGameLevels.class);
                startActivity(intent);
                finish();
            }catch (Exception e){

            }
            dialogEnd.dismiss();
        });

        //button to continue game on next lvl
        Button buttonContinueEnd = (Button)dialogEnd.findViewById(R.id.btncontinue);
        buttonContinueEnd.setOnClickListener(v -> {
            try {
                if (level == LettersGameLevels.LVL_AMOUNT){
                    SharedPreferences.Editor editor = save.edit();
                    editor.putInt("Level", 1);
                    editor.apply();
                    Intent intent = new Intent(LettersLevel.this, LettersGameLevels.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Intent intent = new Intent(LettersLevel.this, LettersLevel.class);
                    startActivity(intent);
                    finish();
                }
            }catch (Exception e){

            }
            dialogEnd.dismiss();
        });
        MediaPlayer mediaplayer = MediaPlayer.create(LettersLevel.this, R.raw.literki);
        mediaplayer.start();

        //button back
        Button buttonBack = (Button)findViewById(R.id.button_back);
        buttonBack.setOnClickListener(v -> {
            try {
                Intent intent = new Intent(LettersLevel.this, LettersGameLevels.class);
                startActivity(intent);
                finish();
            }catch (Exception e){

            }
            dialog.dismiss();
        });

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onStart() {
        super.onStart();
        imgLeft.setOnTouchListener((v, event) -> {
            checkAnswer(true, event);
            return true;
        });

        //click on right image
        imgRight.setOnTouchListener((v, event) -> {
            checkAnswer(false, event);
            return true;
        });
    }

    private void update_stats(){
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
    }

    //system button back
    @Override
    public void onBackPressed(){
        try {
            Intent intent = new Intent(LettersLevel.this, LettersGameLevels.class);
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