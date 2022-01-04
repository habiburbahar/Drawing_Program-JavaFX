package com.example.drawing_programjavafx;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;

public class CanvasView extends StackPane implements InteractionModelSubscriber,ShapeModelSubscriber{
    Canvas myCanvas;
    GraphicsContext gc;
    ShapeModel model;
    InteractionModel iModel;

    public CanvasView(double canvasWidth, double canvasHeight){
        myCanvas = new Canvas(canvasWidth, canvasHeight);
        gc = myCanvas.getGraphicsContext2D();
        this.getChildren().add(myCanvas);
    }
    public void setModel(ShapeModel newModel) {
        model = newModel;
    }

    public void setInteractionModel(InteractionModel newIModel) {
        iModel = newIModel;
    }
    public void setController(DrawingController controller) {

    }

    public void draw() {

    }

    @Override
    public void iModelChanged() {
        draw();
    }

    @Override
    public void modelChanged() {
        draw();
    }
}
