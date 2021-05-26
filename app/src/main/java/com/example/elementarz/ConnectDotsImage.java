package com.example.elementarz;

import android.content.Intent;

import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;


public class ConnectDotsImage extends AppCompatActivity {
    private List<Float> x;
    private List<Float> y;
    RelativeLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        x = new ArrayList<>();
        y = new ArrayList<>();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.connect_dots_image);

        Intent myIntent = getIntent();
        Uri selectedImageUri = myIntent.getData();
        ImageView imageView = (ImageView)findViewById(R.id.approve_img);
        imageView.setImageURI(selectedImageUri);

        layout = (RelativeLayout) findViewById(R.id.connect_dots_select);

        Button button = (Button)findViewById(R.id.button_approve_img);
        button.setOnClickListener(v -> {
            try {
                float[] points_x = new float[x.size()];
                float[] points_y = new float[y.size()];
                for (int i = 0; i < x.size(); i++){
                    points_x[i] = x.get(i);
                    points_y[i] = y.get(i);
                }

                Intent intent = new Intent(ConnectDotsImage.this, ConnectDotsNew.class);
                intent.putExtra("points_x", points_x);
                intent.putExtra("points_y", points_y);

                startActivity(intent);
                finish();
            }catch (Exception e){

            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            float x_val = event.getX() - 55;//55 = btn.width/2
            float y_val = event.getY() - 55;
            if (x_val < 0) x_val = 0;
            if (y_val < 0) y_val = 0;
            x.add(x_val);
            y.add(y_val);
            RelativeLayout.LayoutParams rel_btn = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            rel_btn.width = 110;
            rel_btn.height = 110;
            rel_btn.leftMargin = (int)x_val;//x position
            rel_btn.topMargin = (int)y_val;;//y position
            ImageButton button = new ImageButton(this);
            button.setLayoutParams(rel_btn);
            button.setBackgroundResource(R.drawable.highlighted_button);
            layout.addView(button);
        }
        return super.onTouchEvent(event);
    }
}
