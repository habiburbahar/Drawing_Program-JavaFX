package com.example.drawing_programjavafx;

import javafx.scene.paint.Color;

public class XSquare extends XShape{
    public XSquare(double x, double y, Color shapeColor, int z) {
        super(x, y,shapeColor,z);
    }

    @Override
    public void resize(double x, double y) {
        super.resize(x,y);
        double minDim = Math.min(bottom-top,right-left);
        if (x < startX) {
            left = startX - minDim;
            right = startX;
        } else {
            left = startX;
            right = startX + minDim;
        }
        if (y < startY) {
            top = startY - minDim;
            bottom = startY;
        } else {
            top = startY;
            bottom = startY + minDim;
        }
    }

    @Override
    public boolean contains(double x, double y) {
        return x >= left && x <= right && y >= top && y <= bottom;
    }
}
