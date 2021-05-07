package com.example.elementarz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.Html;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/*Add game
* -change gameAmount
* -edit buttonStart->onclicklistener
* -add game img to SliderAdapter->slide_imges
* -add javaclass to manifest*/

public class MainActivity extends AppCompatActivity {

    private final int gameAmount = 2;
    private long backPressedTime;
    private Toast backToast;
    private ViewPager mSlideViewPager;
    private LinearLayout mDotLayout;
    private TextView[] mDots;
    private SliderAdapter sliderAdapter;
    private int mCurrentPage;

    private String numbersGameImages[] = {"doggo", "kitten"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSlideViewPager = (ViewPager)findViewById(R.id.slideViewPager);
        mDotLayout = (LinearLayout)findViewById(R.id.dotsLayout);
        sliderAdapter = new SliderAdapter(this);
        mSlideViewPager.setAdapter(sliderAdapter);
        addDotsIndicator(0);
        mSlideViewPager.addOnPageChangeListener(viewListener);

        Button buttonStart = (Button)findViewById(R.id.button_start);
        buttonStart.setOnClickListener(v -> {
            try {
                Intent intent;
                if (mCurrentPage == 0){ //nauka literek
                    intent = new Intent(MainActivity.this, LettersGameLevels.class);
                }
                else{//nauka cyferek
                    intent = new Intent(MainActivity.this, NumbersGame.class);//todo change
                    intent.putExtra("image_name", numbersGameImages[0]);
                }
                MediaPlayer mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.przejscie_1);
                mediaPlayer.start();
                startActivity(intent);
                finish();
            }catch (Exception e){

            }
        });

        //fullscreen
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public void addDotsIndicator(int position){
        mDots = new TextView[gameAmount];
        mDotLayout.removeAllViews();
        for (int i = 0; i < mDots.length;i++){
            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.white75));

            mDotLayout.addView(mDots[i]);
        }
        if (mDots.length > 0){
            mDots[position].setTextColor(getResources().getColor(R.color.white));
        }
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDotsIndicator(position);
            MediaPlayer mediaPlayer;
            if(position > mCurrentPage) {
                mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.dzwiek_1);
            } else{
                mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.dzwiek_2);
            }
            mediaPlayer.start();
            mCurrentPage = position;
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    //system button back
    @Override
    public void onBackPressed(){
        if (backPressedTime + 2000 > System.currentTimeMillis()){
            backToast.cancel();
            super.onBackPressed();
            return;
        } else {
            backToast = Toast.makeText(getBaseContext(), R.string.exit_text, Toast.LENGTH_SHORT);
            backToast.show();
        }

        backPressedTime = System.currentTimeMillis();
    }
}