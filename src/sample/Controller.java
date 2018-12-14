package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

public class Controller {

    private boolean isMacMenuBarDarkMode() {
        //The source of this function: https://stackoverflow.com/questions/33477294/menubar-icon-for-dark-mode-on-os-x-in-java (Sebastian S, 12.12.2018)
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

    private Branch tree;

    @FXML
    private ResourceBundle strings ;

    @FXML
    private BorderPane rootPane;

    @FXML
    private Pane canvasWrap;

    @FXML
    private Canvas mainCanvas;

    @FXML
    private Slider sliderThickness;

    @FXML
    private Slider sliderLength;

    @FXML
    private Slider sliderAngle;

    @FXML
    private Label labelThickness;

    @FXML
    private Label labelLength;

    @FXML
    private Label labelAngle;

    @FXML
    private Button buttonSwapTheme ;

    @FXML
    public void grow(){
        tree.grow(sliderThickness.getValue(), sliderLength.getValue(), sliderAngle.getValue());
        reDraw(mainCanvas);
    }

    @FXML
    public void newTree(){
        tree = new Branch(null, getStartColor(), sliderThickness.getValue(), sliderLength.getValue()*2, 90);
        reDraw(mainCanvas);
    }

    @FXML
    public void swapTheme(ActionEvent actionEvent) {
        //Swaps the theme between Light and Dark
        if(rootPane.getStylesheets().contains("darkMode.css")){
            rootPane.getStylesheets().add("lightMode.css");
            rootPane.getStylesheets().removeAll("darkMode.css");
        }else{
            rootPane.getStylesheets().add("darkMode.css");
            rootPane.getStylesheets().removeAll("lightMode.css");
        }
        if(actionEvent.getSource() instanceof Labeled){
            ((Labeled) actionEvent.getSource()).setText(rootPane.getStylesheets().contains("lightMode.css") ? strings.getString("buttonTheme2") : strings.getString("buttonTheme1"));
        }

    }

    @FXML
    public void initialize(){
        strings = PropertyResourceBundle.getBundle("strings", Locale.getDefault());

        //Sets the initial theme according to the user's pereference
        if(isMacMenuBarDarkMode()){
            rootPane.getStylesheets().add("darkMode.css");
            rootPane.getStylesheets().removeAll("lightMode.css");
        }else{
            rootPane.getStylesheets().add("lightMode.css");
            rootPane.getStylesheets().removeAll("darkMode.css");
        }

        //Making the Canvas responsive
        mainCanvas.widthProperty().bind(canvasWrap.widthProperty());
        mainCanvas.heightProperty().bind(canvasWrap.heightProperty());

        mainCanvas.heightProperty().addListener(aE -> {
            reDraw(mainCanvas);
        });

        mainCanvas.widthProperty().addListener(aE -> {
            reDraw(mainCanvas);
        });

        //Making the exact values of the Sliders visible
        sliderThickness.valueProperty().addListener(aE -> {
            updateLabels();
        });
        sliderLength.valueProperty().addListener(aE -> {
            updateLabels();
        });
        sliderAngle.valueProperty().addListener(aE -> {
            updateLabels();
        });

        //Sets the Button's text according to the theme that the app uses
        buttonSwapTheme.setText(rootPane.getStylesheets().contains("lightMode.css") ? strings.getString("buttonTheme2") : strings.getString("buttonTheme1"));

        //Initializing the tree
        tree = new Branch(null, getStartColor(), sliderThickness.getValue(), sliderLength.getValue()*2, 90);

        updateLabels();
    }

    private void updateLabels(){
        //Updates the Labels to have the exact values of the Sliders
        labelThickness.setText(strings.getString("labelThickness") + ": " + Math.round(sliderThickness.getValue()));
        labelLength.setText(strings.getString("labelLength") + ": " + Math.round(sliderLength.getValue()));
        labelAngle.setText(strings.getString("labelAngle") + ": " + Math.round(sliderAngle.getValue()) + "°");
    }

    private Color getStartColor(){
        Random r1 = new Random();
        return Color.hsb(r1.nextDouble()*360,0.01,0.70);
    }

    public void reDraw(Canvas canvas){
        GraphicsContext gC = canvas.getGraphicsContext2D();

        //Clearing the whole Canvas
        gC.clearRect(0,0, canvas.getWidth(), canvas.getHeight());

        //Drawing the tree
        tree.reDraw(mainCanvas);



    }
}
