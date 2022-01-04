package com.example.drawing_programjavafx;

public class DrawingController {

    InteractionModel iModel;
    ShapeModel model;

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




}
