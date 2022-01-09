package com.example.drawing_programjavafx;

import javafx.scene.control.ToggleButton;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import java.lang.String;
import javafx.scene.paint.Color;

import java.util.*;

public class ColorView extends StackPane implements InteractionModelSubscriber{
    InteractionModel iModel;
    ArrayList<ToggleButton> colorButtons;
    String[] colorNames = {"[AQUA]","[VIOLET]","[GREEN]", "[Gold]", "[Orange]",
            "[Coral]", "[Fuchsia]", "[Peru]"};
    public ColorView() {
        VBox buttonBar = new VBox();

        colorButtons = new ArrayList<>();
        Color[] colours = {Color.AZURE, Color.BLUEVIOLET, Color.CHARTREUSE, Color.GOLDENROD,
                Color.ORANGERED, Color.YELLOW, Color.HOTPINK, Color.INDIGO};
        for (int i = 0; i < colorNames.length; i++) {
            ToggleButton tb = new ToggleButton(colorNames[i]);
            tb.setStyle("-fx-background-color: " + colorNames[i]);

            tb.setMinSize(60,5);
            tb.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
            VBox.setVgrow(tb, Priority.ALWAYS);
            buttonBar.getChildren().add(tb);
            colorButtons.add(tb);
        }

        // lay out view
        this.getChildren().add(buttonBar);
    }

    public void setController(DrawingController controller) {
        colorButtons.forEach(sb -> sb.setOnAction(e -> controller.setColor(sb.getText())));
    }

    public void setInteractionModel(InteractionModel newIModel) {
        iModel = newIModel;
        iModel.addSubscriber(this);
    }
    @Override
    public void iModelChanged() {
        ToggleButton tb;
        for (int i = 0; i < colorButtons.size(); i++) {
            tb = colorButtons.get(i);
            if (Color.valueOf(colorNames[i]).equals(iModel.currentColor)) {
                tb.setSelected(true);
                tb.setStyle("-fx-background-color: " + colorNames[i] + "; -fx-background-insets: 0;");
                //tb.setStyle("-fx-background-insets: 0;");
                //System.out.println(tb.getStyle());
            } else {
                tb.setSelected(false);
                tb.setStyle("-fx-background-color: " + colorNames[i] + "; -fx-background-insets: 3;");
            }
        }

    }

    public void setInitialColour() {
        colorButtons.get(0).fire();
    }



}
