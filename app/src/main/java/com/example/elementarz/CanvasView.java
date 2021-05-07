package com.example.elementarz;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.Map;

public class CanvasView extends View {
    private Bitmap bitmap;
    private Bitmap bitmapBackup;
    private Canvas canvas;
    private Path path;

    Context context;
    private Paint paintLine;
    private float currX, currY;
    private static final float TOLERANCE = 80;

    private int it = 0;
    private ArrayList<Answer> answers;
    private ArrayList<Map<ImageButton, Boolean>> highlighted;
    private Point touch_beg;
    Drawable highlightedButton;
    Drawable notActiveButton;
    private int BUTTONS_NR;
    Dialog dialogEnd;


    public CanvasView(Context c, AttributeSet attrs) {
        super(c, attrs);
        context = c;
        path = new Path();
        paintLine = new Paint();
        paintLine.setAntiAlias(true);
        paintLine.setColor(Color.GREEN);
        paintLine.setStyle(Paint.Style.STROKE);
        paintLine.setStrokeJoin(Paint.Join.ROUND);
        paintLine.setStrokeWidth(45f);
        paintLine.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        bitmapBackup = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPath(path, paintLine);
        for(int i = 0; i < it; i++){
            Answer a = answers.get(i);
            canvas.drawLine(a.beg().x(), a.beg().y(), a.end().x(), a.end().y(), paintLine);

        }
    }

    private void startTouch(float x, float y) {
        //backupCanvas.drawBitmap(bitmap, 0, 0, paint); //zapamietuje stan sprzed
        path.moveTo(x, y);
        currX = x;
        currY = y;
        touch_beg = new Point(x, y);
    }

    // when ACTION_MOVE move touch according to the x,y values
    private void moveTouch(float x, float y) {
        float dx = Math.abs(x - currX);
        float dy = Math.abs(y - currY);
        if (dx >= TOLERANCE || dy >= TOLERANCE) {
            path.quadTo(currX, currY, (x + currX) / 2, (y + currY) / 2);
            currX = x;
            currY = y;
        }
    }


    // when ACTION_UP stop touch
    private void upTouch() {
        path.lineTo(currX, currY);
        Point touch_end = new Point(currX, currY);
        if(check_answer(answers.get(it), touch_beg, touch_end )){
            Map<ImageButton, Boolean> new_highlighting = highlighted.get(it);
            for(ImageButton b: new_highlighting.keySet()){
                if(new_highlighting.get(b)){
                    b.setBackground(highlightedButton);
                } else {
                    b.setBackground(notActiveButton);
                }
            }
           it++;
        }

        path.reset();
    }

    private boolean check_answer(Answer a, Point touch_beg, Point touch_end){
        if(a.beg().equalsWithTolerance(touch_beg, TOLERANCE) && a.end().equalsWithTolerance(touch_end, TOLERANCE))
            return true;
        if(a.beg().equalsWithTolerance(touch_end, TOLERANCE) && a.end().equalsWithTolerance(touch_beg, TOLERANCE))
            return true;
        return false;
    }

    //override the onTouchEvent
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startTouch(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                moveTouch(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                upTouch();
                invalidate();
                break;
        }
        if (it == BUTTONS_NR){
            dialogEnd.show();
//            Intent intent_close = new Intent(context, ConnectDotsGame.class);
//            context.startActivity(intent_close);
        }
        return true;
    }

    public void setAnswers(ArrayList<Answer> answers) {
        this.answers = answers;
    }


    public int getOffsetY() {
        int mOffset[] = new int[2];
        getLocationOnScreen(mOffset);
        return mOffset[1];
    }

    public void setHighlightingDrawable(Drawable highlightedButton, Drawable notActiveButton) {
        this.highlightedButton = highlightedButton;
        this.notActiveButton = notActiveButton;
    }

    public void setHighlighted(ArrayList<Map<ImageButton, Boolean>> highlighted) {
        this.highlighted = highlighted;
    }

    public void setNrOfButtons(int n){
        this.BUTTONS_NR = n;
    }

    public void setEndDialog(Dialog dialogEnd) {
        this.dialogEnd = dialogEnd;
    }
}


