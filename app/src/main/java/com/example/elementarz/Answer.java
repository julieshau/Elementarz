package com.example.elementarz;

public class Answer {
    private Point beg;
    private Point end;

    public Answer(Point beg, Point end){
        this.beg = beg;
        this.end = end;
    }

    public Point beg(){
        return beg;
    }

    public Point end(){
        return end;
    }

//    Map<Point, Point> correctCoordinates;
//    int d; //dokładność

//    public boolean checkIfCorrect(Point p1, Point p2){ // d dokładność
//        for(Point p : correctCoordinates.keySet()){
//            if(check_point(p, p1) && check_point(correctCoordinates.get(p), p2))
//                return true;
//        }
//        return false;
//    }
//
//    // sprawdza czy p1 i p2 są mniej więcej w tym samym miejscu
//    private boolean check_point(Point p1, Point p2){
//        if(abs(p1.x() - p2.x()) < d && abs(p1.y() - p2.y()) < d)
//            return true;
//        return false;
//    }
}
