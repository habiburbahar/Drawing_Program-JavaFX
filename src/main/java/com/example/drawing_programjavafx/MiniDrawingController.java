package com.example.drawing_programjavafx;

import javafx.scene.input.MouseEvent;

import java.util.Optional;

public class MiniDrawingController extends DrawingController {
    public void handlePressed(double x, double y, MouseEvent event) {
        prevX = x;
        prevY = y;
        switch (currentState) {
            case READY -> {
                if (iModel.checkViewfinderHit(x, y)) {
                    currentState = State.PANNING;
                } else {
                    Optional<XShape> selected = model.detectHit(x, y);
                    if (selected.isEmpty()) {
                        // create new shape as soon as we see a move, or cancel if just a click
                        currentState = State.CREATE_OR_UNSELECT;
                    } else {
                        iModel.setSelected(selected.get());
                        currentState = State.DRAGGING;
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
                handleCreate(prevX, prevY);
                model.resize(iModel.selectedShape, x, y);
                currentState = State.RESIZING;
            }
            case RESIZING -> model.resize(iModel.selectedShape, x, y);
            case DRAGGING -> model.moveShape(iModel.selectedShape, dX, dY);
            case PANNING -> iModel.moveView(dX * -1, dY * -1);
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
}

