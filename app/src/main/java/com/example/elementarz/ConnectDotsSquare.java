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

public class ConnectDotsSquare extends AppCompatActivity {

    //lista, która ma poprawne początki i poprawne końce kolejnych "poziomów"
    ArrayList<Answer> answers = new ArrayList<>();
    ArrayList<Map<ImageButton, Boolean>> highlighted = new ArrayList<>();
    private CanvasView connectDotsCanvas;
    private View mainView;
    ImageButton b1, b2, b3, b4;

    Dialog dialogEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_connect_dots_square);
        connectDotsCanvas = (CanvasView) findViewById(R.id.canvas_view);
        mainView = (View)findViewById(R.id.square_main);

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
                Intent intent_close = new Intent(ConnectDotsSquare.this, MainActivity.class);
                startActivity(intent_close);
                finish();
            }catch (Exception e){

            }
            dialogEnd.dismiss();
        });
        buttonContinueEnd.setOnClickListener(v -> {
            try {
                Intent intent_cont = new Intent(ConnectDotsSquare.this, ConnectDotsGame.class);
                startActivity(intent_cont);
                finish();
            }catch (Exception e){

            }
            dialogEnd.dismiss();
        });

        connectDotsCanvas.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener(){
            public void onGlobalLayout(){
                b1 = (ImageButton) findViewById(R.id.btn_square1);
                b2 = (ImageButton) findViewById(R.id.btn_square2);
                b3 = (ImageButton) findViewById(R.id.btn_square3);
                b4 = (ImageButton) findViewById(R.id.btn_square4);
                Drawable highlightedButton = getResources().getDrawable(R.drawable.highlighted_button);
                Drawable notActiveButton = getResources().getDrawable(R.drawable.roundbutton);

                b1.setBackground(highlightedButton);
                b2.setBackground(highlightedButton);
                Point coord_b1 = getCenterCoordinates(b1);
                Point coord_b2 = getCenterCoordinates(b2);
                Point coord_b3 = getCenterCoordinates(b3);
                Point coord_b4 = getCenterCoordinates(b4);

                answers.add(new Answer(coord_b1, coord_b2));

                highlighted.add(new HashMap<ImageButton, Boolean>(){{
                    put(b1, Boolean.FALSE);
                    put(b2, Boolean.TRUE);
                    put(b3, Boolean.FALSE);
                    put(b4, Boolean.TRUE);

                }});
                answers.add(new Answer(coord_b2, coord_b4));

                highlighted.add(new HashMap<ImageButton, Boolean>(){{
                    put(b1, Boolean.FALSE);
                    put(b2, Boolean.FALSE);
                    put(b3, Boolean.TRUE);
                    put(b4, Boolean.TRUE);

                }});
                answers.add(new Answer(coord_b3, coord_b4));

                highlighted.add(new HashMap<ImageButton, Boolean>(){{
                    put(b1, Boolean.TRUE);
                    put(b4, Boolean.FALSE);
                    put(b3, Boolean.TRUE);
                    put(b2, Boolean.FALSE);

                }});
                answers.add(new Answer(coord_b1, coord_b3));

                highlighted.add(new HashMap<ImageButton, Boolean>(){{
                    put(b1, Boolean.TRUE);
                    put(b4, Boolean.TRUE);
                    put(b3, Boolean.TRUE);
                    put(b2, Boolean.TRUE);

                }});

                connectDotsCanvas.setAnswers(answers);
                connectDotsCanvas.setHighlightingDrawable(highlightedButton, notActiveButton);
                connectDotsCanvas.setHighlighted(highlighted);
                connectDotsCanvas.setNrOfButtons(4);
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

    //system button back
    @Override
    public void onBackPressed(){
        try {
            Intent intent = new Intent(ConnectDotsSquare.this, ConnectDotsGame.class);
            startActivity(intent);
            finish();
        }catch (Exception e){

        }
    }
}