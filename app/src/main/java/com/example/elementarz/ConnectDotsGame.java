package com.example.elementarz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;


public class ConnectDotsGame extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_dots_game);


    }


    @Override
    protected void onStart() {
        super.onStart();
        ImageView btn1 = (ImageView)findViewById(R.id.triangle);
        btn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openConnectDotsTriangle();
            }
        });

        ImageView btn2 = (ImageView)findViewById(R.id.squareIcon);
        btn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openConnectDotsSquare();
            }
        });

        ImageView btn3 = (ImageView)findViewById(R.id.star);
        btn3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openConnectDotsStar();
            }
        });

        Button buttonBack = (Button)findViewById(R.id.button_back);
        buttonBack.setOnClickListener(v -> {
            try {
                Intent intent = new Intent(ConnectDotsGame.this, MainActivity.class);
                startActivity(intent);
                finish();
            }catch (Exception e){

            }
        });

    }

    public void openConnectDotsTriangle(){
        Intent intent = new Intent(ConnectDotsGame.this, ConnectDotsTriangle.class);
        startActivity(intent);
    }

    public void openConnectDotsSquare(){
        Intent intent = new Intent(ConnectDotsGame.this, ConnectDotsSquare.class);
        startActivity(intent);
    }

    public void openConnectDotsStar(){
        Intent intent = new Intent(ConnectDotsGame.this, ConnectDotsStar.class);
        startActivity(intent);
    }
}

