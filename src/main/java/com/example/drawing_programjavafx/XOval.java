package com.example.drawing_programjavafx;

import javafx.scene.paint.Color;

public class XOval extends XShape{
    public XOval(double x, double y, Color shapeColor, int z) {
        super(x, y,shapeColor,z);
    }

    @Override
    public boolean contains(double x, double y) {
        // transform to origin
        double cx = (right+left)/2;
        double cy = (bottom+top)/2;
        double ox = x - cx;
        double oy = y - cy;
        double rx = cx - left;
        double ry = cy - top;
        double ratio = rx/ry;
        if (ratio > 1.0) {
            ox = ox / ratio;
        } else {
            oy = oy / ratio;
        }
        return Math.hypot(ox,oy) <= Math.min(rx,ry);
    }
}
