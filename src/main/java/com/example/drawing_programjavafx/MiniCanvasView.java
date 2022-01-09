package com.example.drawing_programjavafx;

import javafx.scene.paint.Color;

public class MiniCanvasView extends CanvasView {
    public MiniCanvasView(double canvasWidth, double canvasHeight, double wsWidth, double wsHeight, double newScale, String newColor) {
        super(canvasWidth, canvasHeight, wsWidth, wsHeight, newScale, newColor);
        setMaxSize(canvasWidth, canvasHeight);
    }

    @Override
    public void setInteractionModel(InteractionModel newIModel) {
        iModel = newIModel;
        iModel.addSubscriber(this);
    }

    @Override
    public void draw() {
        super.draw();
        // now draw viewfinder
        gc.setFill(Color.rgb(255,255,255,0.25));
        gc.fillRect(iModel.viewLeft*myCanvas.getWidth(),
                iModel.viewTop*myCanvas.getHeight(),
                iModel.viewWidth * myCanvas.getWidth(),
                iModel.viewHeight * myCanvas.getHeight());
        gc.setStroke(Color.YELLOW);
        gc.setLineWidth(1.0);
        gc.strokeRect(iModel.viewLeft*myCanvas.getWidth(),
                iModel.viewTop*myCanvas.getHeight(),
                iModel.viewWidth * myCanvas.getWidth(),
                iModel.viewHeight * myCanvas.getHeight());
    }

    @Override
    protected void calculateShapeBounds(XShape xs) {
        shapeLeft = xs.left * workspaceWidth * viewScale;
        shapeTop= xs.top * workspaceHeight * viewScale;
        shapeRight= xs.right * workspaceWidth * viewScale;
        shapeBottom= xs.bottom * workspaceHeight * viewScale;
        shapeWidth = shapeRight - shapeLeft;
        shapeHeight = shapeBottom - shapeTop;
    }

    @Override
    protected void drawSelectedBox() {
        gc.setFill(null);
        gc.setStroke(Color.ORANGERED);
        gc.setLineWidth(1.0);
        gc.strokeRect(shapeLeft,shapeTop,shapeWidth,shapeHeight);
        gc.setLineDashes(null);
        gc.setStroke(Color.BLACK);
    }

    protected void drawSelectedLine() {
        gc.setStroke(Color.ORANGERED);
        gc.setLineWidth(2.0);
        gc.strokeLine(shapeLeft,shapeTop,shapeRight, shapeBottom);
    }

    @Override
    public void setController(DrawingController controller) {
        myCanvas.setOnMousePressed(e -> controller.handlePressed(
                e.getX() / myCanvas.getWidth(),
                e.getY() / myCanvas.getHeight(), e));
        myCanvas.setOnMouseDragged(e -> controller.handleDragged(
                e.getX() / myCanvas.getWidth(),
                e.getY() / myCanvas.getHeight(), e));
        myCanvas.setOnMouseReleased(e -> controller.handleReleased(
                e.getX() / myCanvas.getWidth(),
                e.getY() / myCanvas.getHeight(), e));
    }
    @Override
    public void layoutChildren() {
    }
}

