package com.example.drawing_programjavafx;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

public class MainUserInterface extends StackPane {
    ToolView tools;
    ColorView colours;
    DrawingView drawing;

    public MainUserInterface() {
        tools = new ToolView();
        colours = new ColorView();
        drawing = new DrawingView();
        tools.setMinWidth(60);
        colours.setMinWidth(60);
        BorderPane root = new BorderPane();
        root.setLeft(tools);
        root.setCenter(drawing);
        root.setRight(colours);
        this.getChildren().add(root);
    }

    public void setModel(ShapeModel newModel) {
        tools.setModel(newModel);
        drawing.setModel(newModel);
    }

    public void setInteractionModel(InteractionModel newIModel) {
        tools.setInteractionModel(newIModel);
        colours.setInteractionModel(newIModel);
        drawing.setInteractionModel(newIModel);
    }

    public void setInitialTool() {
        tools.setInitialTool();
    }

    public void setControllers(DrawingController mainViewController, MiniDrawingController miniController) {
        tools.setController(mainViewController);
        colours.setController(mainViewController);
        drawing.setControllers(mainViewController,miniController);
    }

    @Override
    public void layoutChildren() {
        super.layoutChildren();
        drawing.doResize(this.getWidth()-120, this.getHeight());
    }

    public void setInitialColour() {
        colours.setInitialColour();
    }
}
