package com.example.drawing_programjavafx;

import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;

public class DrawingView extends StackPane {
        CanvasView mainCanvas;
        CanvasView miniCanvas;
        ShapeModel model;
        InteractionModel iModel;

        public DrawingView() {
            mainCanvas = new CanvasView(500, 500, 2000, 2000, 1.0, "LightGray");
            miniCanvas = new MiniCanvasView(100, 100, 2000, 2000, 0.05, "Gray");
            StackPane.setAlignment(miniCanvas, Pos.TOP_LEFT);
            this.getChildren().addAll(mainCanvas, miniCanvas);
        }

        public void setModel(ShapeModel newModel) {
            mainCanvas.setModel(newModel);
            miniCanvas.setModel(newModel);
        }

        public void setControllers(DrawingController mainController, MiniDrawingController miniController) {
            mainCanvas.setController(mainController);
            miniCanvas.setController(miniController);
        }

        public void setInteractionModel(InteractionModel newIModel) {
            mainCanvas.setInteractionModel(newIModel);
            miniCanvas.setInteractionModel(newIModel);
        }

        @Override
        public void layoutChildren() {
            super.layoutChildren();
        }

        public void doResize(double w, double h) {
            mainCanvas.doResize(w,h);
        }
}
