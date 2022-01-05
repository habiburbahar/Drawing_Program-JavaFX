package com.example.drawing_programjavafx;

import javafx.scene.paint.Color;

public abstract class XShape {
    double left,top,right,bottom;
    double startX,startY;
    public Color myColor;
    int zOrder;

    public XShape(double x, double y, Color newColor, int z) {
        startX = x;
        startY = y;
        left = x;
        top = y;
        right = x;
        bottom = y;
        myColor = newColor;
        zOrder = z;
    }

    public void resize(double x, double y) {
        left = Math.min(x, startX);
        right = Math.max(x, startX);
        top = Math.min(y, startY);
        bottom = Math.max(y, startY);
    }

    public abstract boolean contains(double x, double y);

    public void move(double dX, double dY) {
        left += dX;
        right += dX;
        startX += dX;
        top += dY;
        bottom += dY;
        startY += dY;
    }

    public void setZ(int z) {
        zOrder = z;
    }
    public int getZ() {
        return zOrder;
    }

    public void resetStartCoords() {
        startX = left;
        startY = top;
    }
}
