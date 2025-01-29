package view.GUI;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.fxml.*;
import javafx.stage.Stage;

import java.io.IOException;

public class GUI extends Application{

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("selectprogramwindow/selectprogramwindow.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setMinWidth(700);
        stage.setMinHeight(500);

        stage.setTitle("Java Toy Language Interpreter - Select Program Window");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
