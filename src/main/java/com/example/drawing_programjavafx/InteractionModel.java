package com.example.drawing_programjavafx;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Map;

import static java.util.Map.entry;

public class InteractionModel {

    ArrayList<InteractionModelSubscriber> subscribers;
    Tool currentTool;
    XShape selectedShape;
    double viewLeft, viewTop;
    double worldWidth, worldHeight;
    double viewWidth, viewHeight;
    Color currentColour;

    enum Tool {
        RECT, SQUARE, CIRCLE, OVAL, LINE
    }

    Map<String, Tool> toolTable = Map.ofEntries(
            entry("Rect", Tool.RECT),
            entry("Square", Tool.SQUARE),
            entry("Circle", Tool.CIRCLE),
            entry("Oval", Tool.OVAL),
            entry("Line", Tool.LINE)
    );
    public InteractionModel() {
        subscribers = new ArrayList<>();
        viewLeft = 0;
        viewTop = 0;    }

    public void clearSelection() {
        selectedShape = null;
        notifySubscribers();
    }

    private double dist(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x2-x1,2) + Math.pow(y2-y1,2));
    }

    public boolean onResizeHandle(double x, double y) {
        if (selectedShape != null) {
            return dist(x,y,selectedShape.right, selectedShape.bottom) <= (7/worldWidth);
        } else {
            return false;
        }
    }

    public boolean checkViewfinderHit(double x, double y) {
        //System.out.println("checkViewfinder:  viewLeft: " + viewLeft + "  viewTop: " + viewTop + "   viewWidth:" +
        //        viewWidth + "   viewHeight:" + viewHeight + "   " + x + "," + y);
        return x >= viewLeft && x <= viewLeft + viewWidth &&
                y >= viewTop && y <= viewTop + viewHeight;
    }

    public void setViewExtents(double newWidth, double newHeight) {
        //public void setViewExtents(double newWidth, double newHeight, double newRight, double newBottom) {
//        viewMaxRight = newRight;
//        viewMaxBottom = newBottom;
        viewWidth = newWidth;
        viewHeight = newHeight;
        notifySubscribers();
    }

    public void setWorldExtents(double newWidth, double newHeight) {
        worldWidth = newWidth;
        worldHeight = newHeight;
    }

    public void moveView(double dX, double dY) {
        viewLeft -= dX;
        viewTop -= dY;
        if (viewLeft < 0) viewLeft = 0;
        if (viewLeft > (1.0 - viewWidth)) viewLeft = 1.0 - viewWidth;
        if (viewTop < 0) viewTop = 0;
        if (viewTop > (1.0 - viewHeight)) viewTop = 1.0 - viewHeight;
        //System.out.println("viewLeft: " + viewLeft + "  viewTop: " + viewTop + "   " + dX + "," + dY);
        notifySubscribers();
    }

    public void setSelected(XShape newSelection) {
        selectedShape = newSelection;
        notifySubscribers();
    }

    public boolean checkCurrentTool(String text) {
        return toolTable.get(text) == currentTool;
    }


    public void addSubscriber(InteractionModelSubscriber aSub) {
        subscribers.add(aSub);
    }

    private void notifySubscribers() {
        subscribers.forEach(sub -> sub.iModelChanged());
    }

    public void setCurrentTool(String shapeName) {
        currentTool = toolTable.get(shapeName);
        notifySubscribers();
    }

    public void setCurrentColour(String colourName) {
        currentColour = Color.valueOf(colourName);
        notifySubscribers();
    }
}
