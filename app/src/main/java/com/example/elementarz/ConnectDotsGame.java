package com.example.elementarz;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;


public class ConnectDotsGame extends Activity {

    int SELECT_PICTURE = 200;

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

        Button buttonAdd = (Button)findViewById(R.id.button_add);
        buttonAdd.setOnClickListener(v -> {
            try {
//                Intent intent = new Intent(ConnectDotsGame.this, ConnectDotsNew.class);
//                startActivity(intent);
//                finish();
                imageChooser();
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

    //system button back
    @Override
    public void onBackPressed(){
        try {
            Intent intent = new Intent(ConnectDotsGame.this, MainActivity.class);
            startActivity(intent);
            finish();
        }catch (Exception e){

        }
    }
    void imageChooser() {

        // create an instance of the
        // intent of the type image
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        // pass the constant to compare it
        // with the returned requestCode
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

    // this function is triggered when user
    // selects the image from the imageChooser
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == SELECT_PICTURE) {
                // Get the url of the image from data
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    Intent intent = new Intent(ConnectDotsGame.this, ConnectDotsImage.class);
                    intent.setData(selectedImageUri);
                    startActivity(intent);
                    finish();
                }
            }
        }
    }
}

