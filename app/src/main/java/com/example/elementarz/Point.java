package com.example.elementarz;

import static java.lang.Math.abs;

public class Point {
    private float x;
    private float y;

    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float x() {
        return x;
    }

    public float y() {
        return y;
    }

    public void setX(float x){this.x=x;}

    public void setY(float y){this.y = y;}

    public boolean equalsWithTolerance(Point touch_beg, float tolerance) {
        if(abs(x - touch_beg.x) <= tolerance && abs(y - touch_beg.y) <= tolerance)
            return true;
        return false;
    }
}
