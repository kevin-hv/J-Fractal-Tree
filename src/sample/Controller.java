package sample;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import javax.swing.event.ChangeListener;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.Random;
import java.util.ResourceBundle;

public class Controller {

    private Branch tree;

    @FXML
    private ResourceBundle strings ;

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
    public void grow(){
        tree.grow(sliderThickness.getValue(), sliderLength.getValue(), sliderAngle.getValue());
        reDraw(mainCanvas);
    }

    @FXML
    public void newTree(){
        tree = new Branch(null, getStartColor(), sliderThickness.getValue(), sliderLength.getValue()*2, 90);
        reDraw(mainCanvas);
    }

    public void initialize(){
        strings = PropertyResourceBundle.getBundle("strings", Locale.getDefault());

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
