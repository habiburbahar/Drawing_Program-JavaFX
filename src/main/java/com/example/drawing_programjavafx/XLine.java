package com.example.drawing_programjavafx;

import javafx.scene.paint.Color;

public class XLine extends XShape{
    double ratioA, ratioB, ratioC;
    double tolerance;
    private double length;

    public XLine(double x, double y, Color shapeColor, int z, double tol) {
        super(x, y,shapeColor,z);
        tolerance = tol;
    }

    @Override
    public void resize(double x, double y) {
        right = x;
        bottom = y;
        recalculateRatios();
    }

    public void move(double dX, double dY) {
        super.move(dX,dY);
        recalculateRatios();
    }

    private void recalculateRatios() {
        length = dist(left,top,right,bottom);
        ratioA = (top-bottom) / length;
        ratioB = (right-left) / length;
        ratioC = -1 * ((top-bottom) * left + (right-left) * top) / length;
    }

    private double dist(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x2-x1,2) + Math.pow(y2-y1,2));
    }

    @Override
    public boolean contains(double x, double y) {
        System.out.println("distance from line: " + Math.abs(distanceFromLine(x,y)) + " vs tolerance: " + tolerance);
        return Math.abs(distanceFromLine(x,y)) < tolerance &&
                dist(x,y,left,top) < length &&
                dist(x,y,right,bottom) < length;
    }

    private double distanceFromLine(double x, double y) {
        return ratioA * x + ratioB * y + ratioC;
    }
}

