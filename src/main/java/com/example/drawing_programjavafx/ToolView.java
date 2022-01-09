package com.example.drawing_programjavafx;

import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

import java.util.ArrayList;

public class ToolView extends StackPane implements InteractionModelSubscriber {
    ShapeModel model;
    InteractionModel iModel;
    ArrayList<ToggleButton> shapeButtons;

    public ToolView() {
        VBox buttonBar = new VBox();
        shapeButtons = new ArrayList<>();
        Shape[] shapes = {new Rectangle(25,20), new Rectangle(25,25),new Circle(12.5),
                new Ellipse(12.5,10), new Line(5,5,20,20)};
        String[] shapeNames = {"Rect", "Square", "Circle", "Oval", "Line"};
        for (int i = 0; i < shapeNames.length; i++) {
            ToggleButton tb = new ToggleButton(shapeNames[i],shapes[i]);
            tb.setMinSize(60,60);
            tb.setMaxHeight(Double.MAX_VALUE);
            tb.setContentDisplay(ContentDisplay.TOP);
            VBox.setVgrow(tb, Priority.ALWAYS);
            buttonBar.getChildren().add(tb);
            shapeButtons.add(tb);
        }

        // lay out view
        this.getChildren().add(buttonBar);
    }

    public void setModel(ShapeModel newModel) {
        model = newModel;
    }

    public void setController(DrawingController controller) {
        shapeButtons.forEach(sb -> sb.setOnAction(e -> controller.setTool(sb.getText())));

    }

    public void setInteractionModel(InteractionModel newIModel) {
        iModel = newIModel;
        iModel.addSubscriber(this);
    }

    @Override
    public void iModelChanged() {
        shapeButtons.forEach(sb -> {
            if (iModel.checkCurrentTool(sb.getText())) {
                sb.setSelected(true);
                ((Shape)(sb.getGraphic())).setFill(iModel.currentColour);
                ((Shape)(sb.getGraphic())).setStroke(iModel.currentColour);
            } else {
                sb.setSelected(false);
                ((Shape)(sb.getGraphic())).setFill(Color.BLACK);
                ((Shape)(sb.getGraphic())).setStroke(Color.BLACK);
            }
        });
    }

    public void setInitialTool() {
        shapeButtons.get(0).fire();
    }
}

