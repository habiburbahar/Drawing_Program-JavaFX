package com.example.drawing_programjavafx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
public class Main extends Application {
    @Override
    public void start(Stage stage) {

        MainUserInterface view = new MainUserInterface();
        DrawingController mainViewController = new DrawingController();
        MiniDrawingController miniController = new MiniDrawingController();
        ShapeModel model = new ShapeModel();
        InteractionModel iModel = new InteractionModel();

        mainViewController.setModel(model);
        mainViewController.setInteractionModel(iModel);
        miniController.setModel(model);
        miniController.setInteractionModel(iModel);

        view.setModel(model);
        view.setInteractionModel(iModel);
        view.setControllers(mainViewController,miniController);
        view.setInitialTool();
        view.setInitialColour();

        Scene scene = new Scene(view);
        stage.setTitle("Drawing Program- JavaFX");
        stage.setScene(scene);
        stage.show();

        scene.setOnKeyPressed(mainViewController::handleKeyPressed);
    }

    public static void main(String[] args) {
        launch();
    }
}