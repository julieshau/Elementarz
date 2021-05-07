package com.example.elementarz;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ConnectDotsStar extends AppCompatActivity {

    //lista, która ma poprawne początki i poprawne końce kolejnych "poziomów"
    ArrayList<Answer> answers = new ArrayList<>();
    ArrayList<Map<ImageButton, Boolean>> highlighted = new ArrayList<>();
    private CanvasView connectDotsCanvas;
    private View mainView;
    ImageButton b1, b2, b3, b4, b5, b6, b7, b8, b9, b10;
    Dialog dialogEnd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_dots_star);
        connectDotsCanvas = (CanvasView) findViewById(R.id.canvas_view);
        mainView = (View)findViewById(R.id.star_main);

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
                Intent intent_close = new Intent(ConnectDotsStar.this, MainActivity.class);
                startActivity(intent_close);
                finish();
            }catch (Exception e){

            }
            dialogEnd.dismiss();
        });
        buttonContinueEnd.setOnClickListener(v -> {
            try {
                Intent intent_cont = new Intent(ConnectDotsStar.this, ConnectDotsGame.class);
                startActivity(intent_cont);
                finish();
            }catch (Exception e){

            }
            dialogEnd.dismiss();
        });


        connectDotsCanvas.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener(){
            public void onGlobalLayout(){
                b1 = (ImageButton) findViewById(R.id.btn_star1);
                b2 = (ImageButton) findViewById(R.id.btn_star2);
                b3 = (ImageButton) findViewById(R.id.btn_star3);
                b4 = (ImageButton) findViewById(R.id.btn_star4);
                b5 = (ImageButton) findViewById(R.id.btn_star5);
                b6 = (ImageButton) findViewById(R.id.btn_star6);
                b7 = (ImageButton) findViewById(R.id.btn_star7);
                b8 = (ImageButton) findViewById(R.id.btn_star8);
                b9 = (ImageButton) findViewById(R.id.btn_star9);
                b10 = (ImageButton) findViewById(R.id.btn_star10);

                Drawable highlightedButton = getResources().getDrawable(R.drawable.highlighted_button);
                Drawable notActiveButton = getResources().getDrawable(R.drawable.roundbutton);

                b1.setBackground(highlightedButton);
                b5.setBackground(highlightedButton);
                Point coord_b1 = getCenterCoordinates(b1);
                Point coord_b2 = getCenterCoordinates(b2);
                Point coord_b3 = getCenterCoordinates(b3);
                Point coord_b4 = getCenterCoordinates(b4);
                Point coord_b5 = getCenterCoordinates(b5);
                Point coord_b6 = getCenterCoordinates(b6);
                Point coord_b7 = getCenterCoordinates(b7);
                Point coord_b8 = getCenterCoordinates(b8);
                Point coord_b9 = getCenterCoordinates(b9);
                Point coord_b10 = getCenterCoordinates(b10);

                answers.add(new Answer(coord_b1, coord_b5));
                answers.add(new Answer(coord_b5, coord_b8));
                answers.add(new Answer(coord_b8, coord_b10));
                answers.add(new Answer(coord_b10, coord_b9));
                answers.add(new Answer(coord_b9, coord_b7));
                answers.add(new Answer(coord_b7, coord_b6));
                answers.add(new Answer(coord_b6, coord_b4));
                answers.add(new Answer(coord_b4, coord_b3));
                answers.add(new Answer(coord_b3, coord_b2));
                answers.add(new Answer(coord_b2, coord_b1));


                for(int i = 0; i < 9; i++){
                    HashMap<ImageButton, Boolean> m = new HashMap<ImageButton, Boolean>(){{
                        put(b1, Boolean.FALSE);
                        put(b2, Boolean.FALSE);
                        put(b3, Boolean.FALSE);
                        put(b4, Boolean.FALSE);
                        put(b5, Boolean.FALSE);
                        put(b6, Boolean.FALSE);
                        put(b7, Boolean.FALSE);
                        put(b8, Boolean.FALSE);
                        put(b9, Boolean.FALSE);
                        put(b10, Boolean.FALSE);
                    }};
                    highlighted.add(m);
                }
                highlighted.get(0).replace(b5, Boolean.TRUE);
                highlighted.get(0).replace(b8, Boolean.TRUE);

                highlighted.get(1).replace(b10, Boolean.TRUE);
                highlighted.get(1).replace(b8, Boolean.TRUE);

                highlighted.get(2).replace(b10, Boolean.TRUE);
                highlighted.get(2).replace(b9, Boolean.TRUE);

                highlighted.get(3).replace(b9, Boolean.TRUE);
                highlighted.get(3).replace(b7, Boolean.TRUE);

                highlighted.get(4).replace(b7, Boolean.TRUE);
                highlighted.get(4).replace(b6, Boolean.TRUE);

                highlighted.get(5).replace(b4, Boolean.TRUE);
                highlighted.get(5).replace(b6, Boolean.TRUE);

                highlighted.get(6).replace(b4, Boolean.TRUE);
                highlighted.get(6).replace(b3, Boolean.TRUE);

                highlighted.get(7).replace(b3, Boolean.TRUE);
                highlighted.get(7).replace(b2, Boolean.TRUE);

                highlighted.get(8).replace(b2, Boolean.TRUE);
                highlighted.get(8).replace(b1, Boolean.TRUE);

                HashMap<ImageButton, Boolean> m = new HashMap<ImageButton, Boolean>(){{
                    put(b1, Boolean.TRUE);
                    put(b2, Boolean.TRUE);
                    put(b3, Boolean.TRUE);
                    put(b4, Boolean.TRUE);
                    put(b5, Boolean.TRUE);
                    put(b6, Boolean.TRUE);
                    put(b7, Boolean.TRUE);
                    put(b8, Boolean.TRUE);
                    put(b9, Boolean.TRUE);
                    put(b10, Boolean.TRUE);
                }};
                highlighted.add(m);

                connectDotsCanvas.setAnswers(answers);
                connectDotsCanvas.setHighlightingDrawable(highlightedButton, notActiveButton);
                connectDotsCanvas.setHighlighted(highlighted);
                connectDotsCanvas.setNrOfButtons(10);
                connectDotsCanvas.setEndDialog(dialogEnd);

            }
        });
    }


    private Point getCenterCoordinates(ImageButton b){
        int[] locations = new int[2];
        int offset = connectDotsCanvas.getOffsetY();
        b.getLocationInWindow(locations);
        float left = locations[0];
        float x_center = left + b.getWidth()/2.0f;
        float top = locations[1];
        top -= offset;
        float y_center = top + b.getHeight()/2.0f;
        //return new Point(left, top);
        return new Point(x_center, y_center);
    }
}