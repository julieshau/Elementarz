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
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ConnectDotsNew extends AppCompatActivity {
    ArrayList<Answer> answers = new ArrayList<>();
    ArrayList<Map<ImageButton, Boolean>> highlighted = new ArrayList<>();
    private CanvasView connectDotsCanvas;
    private View mainView;
    ImageButton[] buttons;

    Dialog dialogEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connect_dots_new);
        connectDotsCanvas = (CanvasView) findViewById(R.id.canvas_view);
        mainView = (View)findViewById(R.id.connect_own_dots);


        Intent myIntent = getIntent();
        float[] x = myIntent.getFloatArrayExtra("points_x");
        float[] y = myIntent.getFloatArrayExtra("points_y");

        buttons = new ImageButton[x.length];

        for (int i = 0; i < buttons.length; i++){
            RelativeLayout.LayoutParams rel_btn = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            RelativeLayout layout = (RelativeLayout) findViewById(R.id.connect_dots_buttons);
            rel_btn.width = 110;
            rel_btn.height = 110;
            rel_btn.leftMargin = (int)x[i];//x position
            rel_btn.topMargin = (int)y[i];;//y position
            buttons[i] = new ImageButton(this);
            buttons[i].setLayoutParams(rel_btn);
            buttons[i].setBackgroundResource(R.drawable.roundbutton);
            layout.addView(buttons[i]);
        }

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
                Intent intent_close = new Intent(ConnectDotsNew.this, MainActivity.class);
                startActivity(intent_close);
                finish();
            }catch (Exception e){

            }
            dialogEnd.dismiss();
        });
        buttonContinueEnd.setOnClickListener(v -> {
            try {
                Intent intent_cont = new Intent(ConnectDotsNew.this, ConnectDotsGame.class);
                startActivity(intent_cont);
                finish();
            }catch (Exception e){

            }
            dialogEnd.dismiss();
        });

        connectDotsCanvas.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener(){
            public void onGlobalLayout(){
                Drawable highlightedButton = getResources().getDrawable(R.drawable.highlighted_button);
                Drawable notActiveButton = getResources().getDrawable(R.drawable.roundbutton);

                buttons[0].setBackground(highlightedButton);
                buttons[1].setBackground(highlightedButton);
                Point[] coord = new Point[buttons.length];
                for (int i = 0; i < buttons.length; i++){
                    coord[i] = getCenterCoordinates(buttons[i]);
                    //coord[i] = new Point((int)x[i], (int)y[i]);
                }
                answers.add(new Answer(coord[0], coord[1]));
                for (int i = 1; i < buttons.length - 1; i++){
                    Map<ImageButton, Boolean> map = new HashMap();
                    for (int j = 0; j < buttons.length; j++){
                        if (j == i || j == i + 1){
                            map.put(buttons[j], Boolean.TRUE);
                        }
                        else{
                            map.put(buttons[j], Boolean.FALSE);
                        }
                    }
                    highlighted.add(map);
                    answers.add(new Answer(coord[i], coord[i + 1]));
                }
                Map<ImageButton, Boolean> map = new HashMap();
                for (int k = 1; k < buttons.length - 1; k++){
                    map.put(buttons[k], Boolean.FALSE);
                }
                map.put(buttons[0], Boolean.TRUE);
                map.put(buttons[buttons.length - 1], Boolean.TRUE);
                highlighted.add(map);
                answers.add(new Answer(coord[buttons.length - 1], coord[0]));

                Map<ImageButton, Boolean> true_map = new HashMap();
                for (int i = 0; i < buttons.length; i++){
                    true_map.put(buttons[i], Boolean.TRUE);
                }

                connectDotsCanvas.setAnswers(answers);
                connectDotsCanvas.setHighlightingDrawable(highlightedButton, notActiveButton);
                connectDotsCanvas.setHighlighted(highlighted);
                connectDotsCanvas.setNrOfButtons(buttons.length);
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
            Intent intent = new Intent(ConnectDotsNew.this, ConnectDotsGame.class);
            startActivity(intent);
            finish();
        }catch (Exception e){

        }
    }
}
