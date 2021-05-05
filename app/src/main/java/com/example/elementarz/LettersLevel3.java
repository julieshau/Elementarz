package com.example.elementarz;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class LettersLevel3 extends AppCompatActivity {

    Dialog dialog;
    Dialog dialogEnd;

    public int numLeft;
    public int numRight;
    public int num;
    public boolean leftCorrect;
    public int count = 0;

    Letters array = new Letters();
    Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.universal);

        TextView text_levels = findViewById(R.id.text_levels);
        text_levels.setText(R.string.level1);

        final ImageView img_left = (ImageView)findViewById(R.id.img_left);
        img_left.setClipToOutline(true);
        final ImageView img_right = (ImageView)findViewById(R.id.img_right);
        img_right.setClipToOutline(true);

        final TextView text_temp = findViewById(R.id.text_temp);//todo remove

        //fullscreen
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //dialog window
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.previewdialogletters);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        //dialog close
        TextView buttonClose = (TextView)dialog.findViewById(R.id.btnclose);
        buttonClose.setOnClickListener(v -> {
            try {
                Intent intent = new Intent(LettersLevel3.this, GameLevels.class);
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
//        dialogEnd = new Dialog(this);
//        dialogEnd.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialogEnd.setContentView(R.layout.previewdialogwin);
//        dialogEnd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        dialogEnd.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
//                WindowManager.LayoutParams.MATCH_PARENT);
//        dialogEnd.setCancelable(false);
//        //dialog close
//        TextView buttonCloseEnd = (TextView)dialogEnd.findViewById(R.id.btnclose);
//        buttonCloseEnd.setOnClickListener(v -> {
//            try {
//                Intent intent = new Intent(LettersLevel3.this, GameLevels.class);
//                startActivity(intent);
//                finish();
//            }catch (Exception e){
//
//            }
//            dialogEnd.dismiss();
//        });
//
//        //button continue
//        Button buttonContinueEnd = (Button)dialogEnd.findViewById(R.id.btncontinue);
//        buttonContinueEnd.setOnClickListener(v -> {
//            try {
//                Intent intent = new Intent(LettersLevel3.this, LettersLevel3.class);
//                startActivity(intent);
//                finish();
//            }catch (Exception e){
//
//            }
//            dialogEnd.dismiss();
//        });

        //______________________________

        //button back
        Button buttonBack = (Button)findViewById(R.id.button_back);
        buttonBack.setOnClickListener(v -> {
            try {
                Intent intent = new Intent(LettersLevel3.this, GameLevels.class);
                startActivity(intent);
                finish();
            }catch (Exception e){

            }
            dialog.dismiss();
        });

        //array for  game progress
        final int[] progress = {
                R.id.point1, R.id.point2, R.id.point3,
                R.id.point4, R.id.point5, R.id.point6, R.id.point7};

        //animation
        Animation a = AnimationUtils.loadAnimation(LettersLevel3.this, R.anim.alpha);

        //logic is here
        num = random.nextInt(array.images.length);
        text_temp.setText(array.answer[num]);//todo remove
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

        img_left.setImageResource(array.images[numLeft]);
        img_right.setImageResource(array.images[numRight]);

        //click on left image
        img_left.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    img_right.setEnabled(false); //blocking right image
                    if (numLeft == num){
                        img_left.setImageResource(R.drawable.img_true);
                    } else {
                        img_left.setImageResource(R.drawable.img_false);
                    }
                } else if (event.getAction() == MotionEvent.ACTION_UP){
                    if (numLeft == num){ //right answer
                        if (count < progress.length){
                            count++;
                        }
                        //coloring progress
                        for (int i = 0; i < progress.length; i++){ //todo change cycles
                            TextView tv = findViewById(progress[i]);
                            tv.setBackgroundResource(R.drawable.style_points);
                        }
                        for (int i = 0; i < count; i++){
                            TextView tv = findViewById(progress[i]);
                            tv.setBackgroundResource(R.drawable.style_points_green);
                        }
                    } else { //wrong answer
                        if (count > 0){
                            count--;
                        }
                        //coloring progress
                        for (int i = 0; i < progress.length - 1; i++){ //todo change cycles
                            TextView tv = findViewById(progress[i]);
                            tv.setBackgroundResource(R.drawable.style_points);
                        }
                        for (int i = 0; i < count; i++){
                            TextView tv = findViewById(progress[i]);
                            tv.setBackgroundResource(R.drawable.style_points_green);
                        }
                    }
                    if (count == progress.length){
                        //exit level
                    }
                    else {
                        num = random.nextInt(array.images.length);
                        text_temp.setText(array.answer[num]);//todo remove
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

                        img_left.setImageResource(array.images[numLeft]);
                        img_left.startAnimation(a);
                        img_right.setImageResource(array.images[numRight]);
                        img_right.startAnimation(a);
                        img_right.setEnabled(true);
                    }
                }
                return true;
            }
        });

        //click on right image
        img_right.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    img_left.setEnabled(false); //blocking left image
                    if (numRight == num){
                        img_right.setImageResource(R.drawable.img_true);
                    } else {
                        img_right.setImageResource(R.drawable.img_false);
                    }
                } else if (event.getAction() == MotionEvent.ACTION_UP){
                    if (numRight == num){ //right answer
                        if (count < progress.length){
                            count++;
                        }
                        //coloring progress
                        for (int i = 0; i < progress.length; i++){ //todo change cycles
                            TextView tv = findViewById(progress[i]);
                            tv.setBackgroundResource(R.drawable.style_points);
                        }
                        for (int i = 0; i < count; i++){
                            TextView tv = findViewById(progress[i]);
                            tv.setBackgroundResource(R.drawable.style_points_green);
                        }
                    } else { //wrong answer
                        if (count > 0){
                            count--;
                        }
                        //coloring progress
                        for (int i = 0; i < progress.length - 1; i++){ //todo change cycles
                            TextView tv = findViewById(progress[i]);
                            tv.setBackgroundResource(R.drawable.style_points);
                        }
                        for (int i = 0; i < count; i++){
                            TextView tv = findViewById(progress[i]);
                            tv.setBackgroundResource(R.drawable.style_points_green);
                        }
                    }
                    if (count == progress.length){
                        //exit level
                    }
                    else {
                        num = random.nextInt(array.images.length);
                        text_temp.setText(array.answer[num]);//todo remove
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

                        img_left.setImageResource(array.images[numLeft]);
                        img_left.startAnimation(a);
                        img_right.setImageResource(array.images[numRight]);
                        img_right.startAnimation(a);
                        img_left.setEnabled(true);
                    }
                }
                return true;
            }
        });

    }
    //system button back
    @Override
    public void onBackPressed(){
        try {
            Intent intent = new Intent(LettersLevel3.this, GameLevels.class);
            startActivity(intent);
            finish();
        }catch (Exception e){

        }
    }
}