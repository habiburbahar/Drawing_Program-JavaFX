package com.example.drawing_programjavafx;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class ShapeModel {
    private ArrayList<XShape> shapes;
    private ArrayList<ShapeModelSubscriber> subscribers;
    private int nextZ;

    public ShapeModel() {
        subscribers = new ArrayList<>();
        shapes = new ArrayList<>();
        nextZ = 0;
    }
    public void addSubscriber(ShapeModelSubscriber newSub) {
        subscribers.add(newSub);
    }

    private void notifySubscribers() {
        subscribers.forEach(subscriber -> subscriber.modelChanged());
    }

    public List<XShape> getShapes() {
        return shapes;
    }

    public void resize(XShape shape, double x, double y) {
        shape.resize(x,y);
        notifySubscribers();
    }

    private XShape addShape(XShape shape) {
        shapes.add(shape);
        notifySubscribers();
        return shape;
    }

    public XShape createRectangle(double x, double y, Color shapeColor) {
        return addShape(new XRectangle(x,y,shapeColor,nextZ++));
    }

    public XShape createSquare(double x, double y, Color shapeColor) {
        return addShape(new XSquare(x,y,shapeColor,nextZ++));
    }

    public XShape createCircle(double x, double y, Color shapeColor) {
        return addShape(new XCircle(x,y,shapeColor,nextZ++));
    }

    public XShape createOval(double x, double y, Color shapeColor) {
        return addShape(new XOval(x,y,shapeColor,nextZ++));
    }

    public XShape createLine(double x, double y, Color shapeColor, double tolerance) {
        return addShape(new XLine(x,y,shapeColor,nextZ++,tolerance));
    }

    public Optional<XShape> detectHit(double x, double y) {
        return shapes.stream().filter(s -> s.contains(x, y)).reduce((first, second) -> second);
    }

    public void moveShape(XShape shape, double dX, double dY) {
        shape.move(dX,dY);
        notifySubscribers();
    }

    public void raiseShape(XShape shape) {
        shape.setZ(nextZ++);
        shapes.sort(Comparator.comparingInt(XShape::getZ));
        notifySubscribers();
    }

    public void deleteShape(XShape selectedShape) {
        if (selectedShape != null) {
            shapes.remove(selectedShape);
            notifySubscribers();
        }
    }

}
