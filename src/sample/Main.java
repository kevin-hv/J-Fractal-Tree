package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

public class Main extends Application {


    private boolean isMacMenuBarDarkMode() {
        //src: https://stackoverflow.com/questions/33477294/menubar-icon-for-dark-mode-on-os-x-in-java (Sebastian S, 12.12.2018)
        try {
            // check for exit status only. Once there are more modes than "dark" and "default", we might need to analyze string contents..
            final Process proc = Runtime.getRuntime().exec(new String[] {"defaults", "read", "-g", "AppleInterfaceStyle"});
            proc.waitFor(100, TimeUnit.MILLISECONDS);
            return proc.exitValue() == 0;
        } catch (IOException | InterruptedException | IllegalThreadStateException ex) {
            // IllegalThreadStateException thrown by proc.exitValue(), if process didn't terminate
            return false;
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception{

        //Loads resources for different languages
        ResourceBundle stringResourceBundle = PropertyResourceBundle.getBundle("strings", Locale.getDefault());
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"), stringResourceBundle);

        Scene scene = new Scene(root, 650, 500);

        //Sets the theme
        if(isMacMenuBarDarkMode()){
            scene.getStylesheets().add("darkMode.css");
        }else{
            scene.getStylesheets().add("lightMode.css");
        }

        primaryStage.setTitle(stringResourceBundle.getString("windowTitle"));
        primaryStage.setScene(scene);
        primaryStage.show();
    }




    public static void main(String[] args) {
        launch(args);
    }
}
