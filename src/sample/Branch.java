package sample;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.paint.RadialGradient;

import java.util.ArrayList;

public class Branch {
    private Branch root;
    private ArrayList<Branch> branches = new ArrayList<Branch>();

    private double thickness;
    private double length;
    private double angle;
    private Color color;

    public Branch(Branch root, Color color, double thickness, double length, double angle){
        this.root = root;
        this.thickness = thickness;
        this.length = length;
        this.angle = angle;
        this.color = color;
    }

    public void reDraw(Canvas canvas){
        GraphicsContext gC = canvas.getGraphicsContext2D();


        gC.setStroke(this.color);
        gC.setLineWidth(thickness);


        //Getting the starting coordinates of our Branch and pushing it to the BOTTOM CENTER of the Canvas
        double midX = (canvas.getWidth()/2) + getX();
        double midY = canvas.getHeight() - getY();

        //Drawing the Branch; Inside of it determining where the ending coordinates of our Branch are
        gC.strokeLine(midX, midY, midX + (this.length * Math.cos(Math.toRadians(this.angle))), midY - (this.length * Math.sin(Math.toRadians(this.angle))));

        //Drawing all the other Branches that grow from this one
        for (Branch branch : branches){
            branch.reDraw(canvas);
        }
    }

    public void grow(double thickness, double length, double angle){
        if(branches.isEmpty()){

            //Determining the new Color of our Branches that will grow out of this one

            double saturation = this.color.getSaturation();

            saturation += 0.035;

            if(saturation > 1){
                saturation = 1;
            }

            Color c = Color.hsb(this.color.getHue(), saturation,0.70);

            //Adding two new Branches to this one symmetrically

            branches.add(new Branch(this, c, thickness, length, this.angle + angle));
            branches.add(new Branch(this, c, thickness, length, this.angle - angle));
        }else{
            for(Branch branch : branches){
                branch.grow(thickness, length, angle);
            }
        }
    }

    public void cut(){
        if(root == null){
            branches.clear();
        }else{
            root.cut();
        }
    }

    public void setColor(Color color){
        this.color = color;
    }

    public double getX(){
        if(root != null){
            return root.getX() + (root.length * Math.cos(Math.toRadians(root.angle)));
        }else{
            return 0;
        }

    }

    public double getY(){
        if(root != null) {
            return root.getY() + (root.length * Math.sin(Math.toRadians(root.angle)));
        }else{
            return 0;
        }
    }
}
