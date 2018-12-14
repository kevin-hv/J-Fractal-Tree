package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        //Loads resources for different languages
        ResourceBundle stringResourceBundle = PropertyResourceBundle.getBundle("strings", Locale.getDefault());
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"), stringResourceBundle);

        Scene scene = new Scene(root, 750, 500);

        primaryStage.setTitle(stringResourceBundle.getString("windowTitle"));
        primaryStage.setScene(scene);
        primaryStage.show();
    }




    public static void main(String[] args) {
        launch(args);
    }
}
