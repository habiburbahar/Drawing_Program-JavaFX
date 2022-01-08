package com.example.drawing_programjavafx;

import javafx.scene.paint.Color;

public class XRectangle extends XShape{
    public XRectangle(double x, double y, Color shapeColor, int z) {
        super(x, y,shapeColor,z);
    }

    @Override
    public boolean contains(double x, double y) {
        return x >= left && x <= right && y >= top && y <= bottom;
    }

}
