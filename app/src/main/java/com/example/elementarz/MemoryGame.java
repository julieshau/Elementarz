package com.example.elementarz;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MemoryGame extends AppCompatActivity {

    Dialog dialog;
    Dialog dialogEnd;
    int clicked = 0;
    int matched = 0;
    boolean turnOver = false;
    int lastClicked = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.memory);

        //fullscreen
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //dialog window
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.previewdialogmemory);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        //dialog close
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
        //button continue
        Button buttonContinue = (Button)dialog.findViewById(R.id.btncontinue);
        buttonContinue.setOnClickListener(v -> {
            dialog.dismiss();
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
                Intent intent_close = new Intent(MemoryGame.this, MainActivity.class);
                startActivity(intent_close);
                finish();
            }catch (Exception e){

            }
            dialogEnd.dismiss();
        });

        //button continue
        buttonContinueEnd.setOnClickListener(v -> {
            try {
                Intent intent_cont = new Intent(MemoryGame.this, MainActivity.class);
                startActivity(intent_cont);
                finish();
            }catch (Exception e){

            }
            dialogEnd.dismiss();
        });

        Integer[] images = {
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

        //main logic is here
        List<Integer> temp = Arrays.asList(images);
        Collections.shuffle(temp);
        temp.toArray(images);

        for (int i = 0; i < images.length; i++){
            //buttons[i].setBackgroundResource(R.drawable.duck);
            buttons[i].setText("cardBack");
            buttons[i].setTextSize(0.0F);
            int finalI = i;
            buttons[i].setOnClickListener(v -> {
                if (buttons[finalI].getText() == "cardBack" && !turnOver){
                    buttons[finalI].setBackgroundResource(images[finalI]);
                    buttons[finalI].setText(images[finalI]);
                    if (clicked == 0){
                        lastClicked = finalI;
                    }
                    clicked++;
                }
                else if (buttons[finalI].getText() != "cardBack"){
                    buttons[finalI].setBackgroundResource(R.drawable.cardback);
                    buttons[finalI].setText("cardBack");
                    clicked--;
                }
                if (clicked == 2){
                    turnOver = true;
                    if (buttons[finalI].getText() == buttons[lastClicked].getText()){
                        buttons[finalI].setEnabled(false);
                        buttons[lastClicked].setEnabled(false);
                        turnOver = false;
                        clicked = 0;
                        matched += 2;
                        if (matched == 12){
                            dialogEnd.show();
                        }
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
            Intent intent = new Intent(MemoryGame.this, LettersGameLevels.class);
            startActivity(intent);
            finish();
        }catch (Exception e){

        }
    }
}
