package com.example.drawing_programjavafx;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class CanvasView extends StackPane implements InteractionModelSubscriber,ShapeModelSubscriber{
    Canvas myCanvas;
    GraphicsContext gc;
    ShapeModel model;
    InteractionModel iModel;
    double workspaceWidth, workspaceHeight;
    double viewScale;
    double shapeLeft, shapeTop, shapeRight, shapeBottom, shapeWidth, shapeHeight;
    double[] dashPattern = {5,5};

    public CanvasView(double canvasWidth, double canvasHeight, double wsWidth, double wsHeight,
                      double newScale, String newColor) {
        workspaceWidth = wsWidth;
        workspaceHeight = wsHeight;
        viewScale = newScale;
        myCanvas = new Canvas(canvasWidth, canvasHeight);
        gc = myCanvas.getGraphicsContext2D();
        this.getChildren().add(myCanvas);
        this.setStyle("-fx-background-color: " + newColor);
    }
    public void setModel(ShapeModel newModel) {
        model = newModel;
        model.addSubscriber(this);    }

    public void setInteractionModel(InteractionModel newIModel) {
        iModel = newIModel;
        iModel.addSubscriber(this);
        iModel.setViewExtents(myCanvas.getWidth()/workspaceWidth, myCanvas.getHeight()/workspaceHeight);
        iModel.setWorldExtents(workspaceWidth,workspaceHeight);
    }

    public void draw() {
        gc.clearRect(0, 0, myCanvas.getWidth(), myCanvas.getHeight());
        model.getShapes().forEach(shape -> {
            gc.setFill(shape.myColor);
            gc.setStroke(Color.BLACK);
            gc.setLineWidth(1.0);
            calculateShapeBounds(shape);
            switch (shape) {
                case XRectangle xs -> this.drawRectangle(shape);
                case XSquare xs -> this.drawRectangle(shape);
                case XCircle xs -> this.drawOval(shape);
                case XOval xs -> this.drawOval(shape);
                case XLine xs -> this.drawLine(shape);
                case XShape xs -> System.out.println("Its an XShape");
            }
        });
    }
    public void setController(DrawingController controller) {
        myCanvas.setOnMousePressed(e -> controller.handlePressed(
                e.getX() / workspaceWidth,
                e.getY() / workspaceHeight, e));
        myCanvas.setOnMouseDragged(e -> controller.handleDragged(
                e.getX() / workspaceWidth,
                e.getY() / workspaceHeight, e));
        myCanvas.setOnMouseReleased(e -> controller.handleReleased(
                e.getX() / workspaceWidth,
                e.getY() / workspaceHeight, e));
    }
    protected void drawSelectedBox() {
        gc.setLineDashes(dashPattern);
        gc.setFill(null);
        gc.setStroke(Color.ORANGERED);
        gc.setLineWidth(2.0);
        gc.strokeRect(shapeLeft,shapeTop,shapeWidth,shapeHeight);
        gc.setLineDashes(null);
        gc.setFill(Color.YELLOW);
        gc.fillOval(shapeRight-5,shapeBottom-5,10,10);
        gc.setLineWidth(1.0);
        gc.setStroke(Color.BLACK);
        gc.strokeOval(shapeRight-5,shapeBottom-5,10,10);
    }

    protected void drawSelectedLine() {
        gc.setLineDashes(dashPattern);
        gc.setStroke(Color.ORANGERED);
        gc.setLineWidth(2.0);
        gc.strokeLine(shapeLeft,shapeTop,shapeRight, shapeBottom);
        gc.setLineDashes(null);
        gc.setFill(Color.YELLOW);
        gc.fillOval(shapeRight-5,shapeBottom-5,10,10);
        gc.setLineWidth(1.0);
        gc.setStroke(Color.BLACK);
        gc.strokeOval(shapeRight-5,shapeBottom-5,10,10);
    }

    protected void calculateShapeBounds(XShape xs) {
        shapeLeft = (xs.left - iModel.viewLeft) * workspaceWidth * viewScale;
        shapeTop = (xs.top - iModel.viewTop) * workspaceHeight * viewScale;
        shapeRight = (xs.right - iModel.viewLeft) * workspaceWidth * viewScale;
        shapeBottom = (xs.bottom - iModel.viewTop) * workspaceHeight * viewScale;
        shapeWidth = shapeRight - shapeLeft;
        shapeHeight = shapeBottom - shapeTop;
    }

    protected void drawLine(XShape shape) {
        gc.setStroke(shape.myColor);
        gc.setLineWidth(3.0);
        gc.strokeLine(shapeLeft, shapeTop, shapeRight, shapeBottom);
        if (shape == iModel.selectedShape) drawSelectedLine();
    }

    protected void drawOval(XShape shape) {
        gc.fillOval(shapeLeft, shapeTop, shapeWidth, shapeHeight);
        gc.strokeOval(shapeLeft, shapeTop, shapeWidth, shapeHeight);
        if (shape == iModel.selectedShape) drawSelectedBox();
    }

    protected void drawRectangle(XShape shape) {
        gc.fillRect(shapeLeft, shapeTop, shapeWidth, shapeHeight);
        gc.strokeRect(shapeLeft, shapeTop, shapeWidth, shapeHeight);
        if (shape == iModel.selectedShape) drawSelectedBox();
    }

    @Override
    public void iModelChanged() {
        draw();
    }

    @Override
    public void modelChanged() {
        draw();
    }

    public void doResize(double w, double h) {
        myCanvas.setWidth(w);
        myCanvas.setHeight(h);
        iModel.setViewExtents(myCanvas.getWidth()/workspaceWidth, myCanvas.getHeight()/workspaceHeight);
        draw();
    }
}
