package com.example.drawing_programjavafx;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.util.Optional;

public class DrawingController {

    InteractionModel iModel;
    ShapeModel model;
    double prevX, prevY;
    double dX, dY;

    protected enum State {
        READY, CREATE_OR_UNSELECT, PANNING, DRAGGING, RESIZING
    }

    protected State currentState;

    public DrawingController() {
        currentState = State.READY;
    }

    public void setInteractionModel(InteractionModel newModel) {
        iModel = newModel;
    }

    public void setModel(ShapeModel newModel) {
        model = newModel;
    }

    public void handlePressed(double x, double y, MouseEvent event) {
        prevX = x;
        prevY = y;
        switch (currentState) {
            case READY -> {
                if (event.getButton().equals(MouseButton.SECONDARY)) {
                    currentState = State.PANNING;
                } else {
                    // check if we are on a resize handle
                    if (iModel.onResizeHandle(x+iModel.viewLeft,y+ iModel.viewTop)) {
                        currentState = State.RESIZING;
                    } else {
                        // check if we are on a shape
                        Optional<XShape> selected = model.detectHit(x+iModel.viewLeft,y+ iModel.viewTop);
                        if (selected.isEmpty()) {
                            // create new shape as soon as we see a move, or cancel if just a click
                            currentState = State.CREATE_OR_UNSELECT;
                        } else {
                            // on a shape, so select it and prepare for move
                            iModel.setSelected(selected.get());
                            model.raiseShape(selected.get());
                            currentState = State.DRAGGING;
                        }
                    }
                }
            }
        }
    }

    public void handleDragged(double x, double y, MouseEvent event) {
        dX = x - prevX;
        dY = y - prevY;
        prevX = x;
        prevY = y;
        switch (currentState) {
            case CREATE_OR_UNSELECT -> {
                handleCreate(prevX+iModel.viewLeft, prevY+ iModel.viewTop);
                model.resize(iModel.selectedShape, x + iModel.viewLeft, y + iModel.viewTop);
                currentState = State.RESIZING;
            }
            case RESIZING -> model.resize(iModel.selectedShape, x + iModel.viewLeft, y + iModel.viewTop);
            case DRAGGING -> model.moveShape(iModel.selectedShape, dX, dY);
            case PANNING -> iModel.moveView(dX, dY);
        }
    }
    public void handleReleased(double x, double y, MouseEvent event) {
        switch (currentState) {
            case CREATE_OR_UNSELECT -> iModel.clearSelection();
            case RESIZING -> {
                if (iModel.selectedShape != null) {
                    iModel.selectedShape.resetStartCoords();
                }
            }
        }
        currentState = State.READY;
    }

    protected void handleCreate(double x, double y) {
        switch (iModel.currentTool) {
            case RECT -> iModel.setSelected(model.createRectangle(x, y,iModel.currentColour));
            case SQUARE -> iModel.setSelected(model.createSquare(x, y,iModel.currentColour));
            case CIRCLE -> iModel.setSelected(model.createCircle(x, y,iModel.currentColour));
            case OVAL -> iModel.setSelected(model.createOval(x, y,iModel.currentColour));
            case LINE -> iModel.setSelected(model.createLine(x, y,iModel.currentColour,15.0/iModel.worldWidth));
        }
    }

    public void setTool(String shapeName) {
        iModel.setCurrentTool(shapeName);
    }

    public void setColor(String colourName) {
        iModel.setCurrentColour(colourName);
    }

    public void handleKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.DELETE)) {
            model.deleteShape(iModel.selectedShape);
        }
    }
}
