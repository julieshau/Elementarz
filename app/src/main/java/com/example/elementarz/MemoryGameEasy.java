package com.example.elementarz;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MemoryGameEasy extends AppCompatActivity {

    Dialog dialog;
    Dialog dialogEnd;
    int main;
    Random random = new Random();
    int clicked = 0;
    int matched = 0;
    boolean turnOver = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.memory_easy);

        //fullscreen
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Integer[] images = {
                R.drawable.dog,
                R.drawable.duck,
                R.drawable.elefant,
                R.drawable.fish,
                R.drawable.pingwin,
                R.drawable.zebra
        };
        Button[] buttons = {
                (Button) findViewById(R.id.mem_card1),
                (Button) findViewById(R.id.mem_card2),
                (Button) findViewById(R.id.mem_card3),
                (Button) findViewById(R.id.mem_card4),
                (Button) findViewById(R.id.mem_card5),
                (Button) findViewById(R.id.mem_card6)
        };
        Button mainButton = (Button) findViewById(R.id.mem_card_main);
        List<Integer> temp = Arrays.asList(images);
        ArrayList<Integer> mainOptions = new ArrayList<>(temp);
        Collections.shuffle(temp);
        temp.toArray(images);

        //dialog window
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.previewdialogmemory);

        TextView textDescr = (TextView)dialog.findViewById(R.id.textdescription);
        textDescr.setText(R.string.dialog_memory_easy_text);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        //dialog close
        TextView buttonClose = (TextView)dialog.findViewById(R.id.btnclose);
        buttonClose.setOnClickListener(v -> {
            try {
                Intent intent = new Intent(MemoryGameEasy.this, MainActivity.class);
                startActivity(intent);
                finish();
            }catch (Exception e){

            }
            dialog.dismiss();
        });
        //button continue
        Button buttonContinue = (Button)dialog.findViewById(R.id.btncontinue);
        buttonContinue.setOnClickListener(v -> {
            dialog.dismiss();
            mainButton.setBackgroundResource(R.drawable.cardback);
            for (int i = 0; i < images.length; i++){
                buttons[i].setBackgroundResource(images[i]);
            }
            Handler handler = new Handler();
            handler.postDelayed(() -> {
                main = random.nextInt(mainOptions.size());
                mainButton.setBackgroundResource(mainOptions.get(main));
                mainButton.setText(mainOptions.get(main));
                mainButton.setTextSize(0.0F);

                for (int i = 0; i < images.length; i++){
                    buttons[i].setBackgroundResource(R.drawable.cardback);
                }
            }, 5000);
        });
        dialog.show();
        //______________________________

        //win dialog window
        dialogEnd = new Dialog(this);
        dialogEnd.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogEnd.setContentView(R.layout.previewdialogwin);
        Button buttonContinueEnd = (Button)dialogEnd.findViewById(R.id.btncontinue);
        buttonContinueEnd.setText(R.string.textend);
        dialogEnd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogEnd.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);
        dialogEnd.setCancelable(false);
        //dialog close
        TextView buttonCloseEnd = (TextView)dialogEnd.findViewById(R.id.btnclose);
        buttonCloseEnd.setOnClickListener(v -> {
            try {
                Intent intent_close = new Intent(MemoryGameEasy.this, MainActivity.class);
                startActivity(intent_close);
                finish();
            }catch (Exception e){

            }
            dialogEnd.dismiss();
        });

        //button continue
        buttonContinueEnd.setOnClickListener(v -> {
            try {
                Intent intent_cont = new Intent(MemoryGameEasy.this, MainActivity.class);
                startActivity(intent_cont);
                finish();
            }catch (Exception e){

            }
            dialogEnd.dismiss();
        });

        //main logic is here
        for (int i = 0; i < images.length; i++){
            buttons[i].setText("cardBack");
            buttons[i].setTextSize(0.0F);
            int finalI = i;
            buttons[i].setOnClickListener(v -> {
                if (buttons[finalI].getText() == "cardBack" && !turnOver){
                    buttons[finalI].setBackgroundResource(images[finalI]);
                    buttons[finalI].setText(images[finalI]);
                    clicked++;
                }
                if (clicked == 1){
                    turnOver = true;
                    if (buttons[finalI].getText() == mainButton.getText()){
                        MediaPlayer mediaplayer = MediaPlayer.create(MemoryGameEasy.this, R.raw.dobrze_1);
                        mediaplayer.start();
                        Handler handler = new Handler();
                        handler.postDelayed(() -> {
                            buttons[finalI].setEnabled(false);
                            buttons[finalI].setVisibility(View.INVISIBLE);
                            turnOver = false;
                            clicked = 0;
                            matched++;
                            mainOptions.remove(images[finalI]);
                            if (mainOptions.isEmpty()){
                                dialogEnd.show();
                            }
                            else{
                                main = random.nextInt(mainOptions.size());
                                mainButton.setBackgroundResource(mainOptions.get(main));
                                mainButton.setText(mainOptions.get(main));
                            }
                        }, 1000);
                    }
                    else {
                        MediaPlayer mediaplayer = MediaPlayer.create(MemoryGameEasy.this, R.raw.zle_1);
                        mediaplayer.start();
                        Handler handler = new Handler();
                        handler.postDelayed(() -> {
                            buttons[finalI].setBackgroundResource(R.drawable.cardback);
                            buttons[finalI].setText("cardBack");
                            clicked = 0;
                            turnOver = false;
                        }, 1000);
                    }
                }
                else if (clicked == 0){
                    turnOver = false;
                }
            });
        }

    }

    //system button back
    @Override
    public void onBackPressed(){
        try {
            Intent intent = new Intent(MemoryGameEasy.this, MainActivity.class);
            startActivity(intent);
            finish();
        }catch (Exception e){

        }
    }
}