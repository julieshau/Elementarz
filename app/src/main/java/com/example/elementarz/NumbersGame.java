package com.example.elementarz;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

// aktywność wyświetlająca pewną ilość obrazków i pewną i
public class NumbersGame extends AppCompatActivity {
    Dialog dialog;
    Dialog dialogEnd;

    private static int lesson[][] = {{1, 5, 2}, {2, 3, 6}, {1, 5, 3}, {4, 9, 5}, {5, 4, 3}, {1, 3, 6}, {7, 1, 4}, {5, 2, 8}, {5, 9, 4}}; // gotowa lekcja, po kolei te cyferki, pierwsza jest poprawna
    private static int correct[] = {2, 1, 0, 2, 1, 2, 0, 2, 1}; // nr guzika na ktorym jest poprawna odpowiedz
    private static int current = 0;
    private int mainImageId;
    private List<ImageView> options = new ArrayList<>();
    private List<ImageView> helper = new ArrayList<>(); // trzymane są view obrazków do liczenia w kolejności: _00, _01, _02, _10, _11, _12, _20, _21, _22
    // czyli _ij jest pod indeksem 3*i + j

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.numbers_game);

        //fullscreen
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Intent intent = getIntent();
        mainImageId = getImageId(this, intent.getStringExtra("image_name"));
        options.add((ImageView) findViewById(R.id.op1));
        options.add((ImageView) findViewById(R.id.op2));
        options.add((ImageView) findViewById(R.id.op3));

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int id = getResources().getIdentifier("_" + i + "" + j, "id", getPackageName());
                ImageView view = findViewById(id);
                helper.add(view);
                view.setImageResource(mainImageId);
            }
        }

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
                Intent intent_close = new Intent(NumbersGame.this, MainActivity.class);
                startActivity(intent_close);
                finish();
            }catch (Exception e){

            }
            dialogEnd.dismiss();
        });

        //button continue
        buttonContinueEnd.setOnClickListener(v -> {
            try {
                Intent intent_cont = new Intent(NumbersGame.this, MainActivity.class);
                startActivity(intent_cont);
                finish();
            }catch (Exception e){

            }
            dialogEnd.dismiss();
        });
    }

    @Override
    protected void onStart() {
        if(current == 9){
            current = 0;
            dialogEnd.show();
        }
        super.onStart();
        // rozmieszczam obrazki z lesson[current][j] (opcje)
        for (int i = 0; i < 3; i++) {
            int id = getImageId(this, "_" + lesson[current][i]);
            options.get(i).setImageResource(id);
        }

        // rozmieszczam obrazki do liczenia:
        arrange_images();

        for (int i = 0; i < 3; i++) {
            Intent intent = getIntent();
            if (i == correct[current]) {
                options.get(i).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(), "DOBRZE", Toast.LENGTH_SHORT).show();
                        current++;
                        startActivity(intent);
                    }
                });
            } else {
                options.get(i).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(), "ŹLE", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                    }
                });
            }
        }
    }

    // https://stackoverflow.com/questions/6783327/setimageresource-from-a-string
    public static int getImageId(Context context, String imageName) {
        return context.getResources().getIdentifier("drawable/" + imageName, null, context.getPackageName());
    }

    // czyli _ij jest pod indeksem 3*i + j
    public void arrange_images() { // załóżmy że wszystko jest widoczne, muszę tylko wyłączyć te niepotrzebne
        int items = lesson[current][correct[current]];
        switch (items) {
            case 1:
                ((LinearLayout) findViewById(R.id.row0)).setVisibility(View.GONE);
                ((LinearLayout) findViewById(R.id.row2)).setVisibility(View.GONE);
                helper.get(3 * 1 + 0).setVisibility(View.GONE);
                helper.get(3 * 1 + 2).setVisibility(View.GONE);

                break;
            case 2:
                ((LinearLayout) findViewById(R.id.row0)).setVisibility(View.GONE);
                ((LinearLayout) findViewById(R.id.row2)).setVisibility(View.GONE);
                helper.get(3 + 1).setVisibility(View.GONE);
                break;
            case 3:
                ((LinearLayout) findViewById(R.id.row2)).setVisibility(View.GONE);
                helper.get(0).setVisibility(View.GONE);
                helper.get(2).setVisibility(View.GONE);
                helper.get(4).setVisibility(View.GONE);
                break;
            case 4:
                ((LinearLayout) findViewById(R.id.row2)).setVisibility(View.GONE);
                helper.get(2).setVisibility(View.GONE);
                helper.get(5).setVisibility(View.GONE);
                break;
            case 5:
                helper.get(1).setVisibility(View.GONE);
                helper.get(3).setVisibility(View.GONE);
                helper.get(5).setVisibility(View.GONE);
                helper.get(7).setVisibility(View.GONE);
                break;
            case 6:
                ((LinearLayout) findViewById(R.id.row2)).setVisibility(View.GONE);
                break;
            case 7:
                helper.get(0).setVisibility(View.GONE);
                helper.get(6).setVisibility(View.GONE);
            case 8:
                helper.get(6).setVisibility(View.GONE);

        }
    }

}
