module com.example.drawing_programjavafx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.drawing_programjavafx to javafx.fxml;
    exports com.example.drawing_programjavafx;
}