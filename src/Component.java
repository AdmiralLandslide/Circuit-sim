import javax.swing.*;
import java.awt.*;

/**
 * Created by Ruben on 04/02/2014.
 */
public abstract class Component extends JComponent {

    public int rotation;
    private Color color = Color.black;
    static int width = Main.mainFrame.getWidth();
    public double resistance;
    public double emf;
    public int x;
    public int y;
    public int xone;
    public int yone;
    public int xtwo;
    public int ytwo;
    private double current;

    public void setCurrent(double d) {current = d;}
    public double getCurrent() { return current;}
    public double getPotentialDifference() { return (current * resistance);}

    public Component(int rotation, int x, int y,int w, int h, double resistance, double emf) {
        super();
        this.setSize(w,h);
        this.setLocation(x,y);
        this.rotation = rotation;
        this.resistance = resistance;
        this.emf = emf;
        this.x = x+100;
        this.y = y+100;
        if (this.rotation % 2 == 1) {
            xone = this.x - 100;
            xtwo = this.x + 100;
            yone = this.y;
            ytwo = this.y;
        }
        if (this.rotation %2 == 0) {
            xone = this.x;
            xtwo = this.x;
            yone = this.y + 100;
            ytwo = this.y - 100;
        }
    }
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(getWidth(), getHeight());
    }
    protected void paintComponent(Graphics g){
        g.setColor(color);

    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Coordinate getEndPoint1() {
        Coordinate coord1 = new Coordinate(0,0);
        if ((rotation % 2) == 1 ) {
            coord1 = new Coordinate(x-100, y);
        }
        if ((rotation % 2) == 0 ) {
            coord1 = new Coordinate(x, y-100);
        }
        return coord1;
    }
    public Coordinate getEndPoint2() {

        Coordinate coord2 = new Coordinate(0,0);
        if ((rotation % 2) == 1 ) {
            coord2 = new Coordinate(x+100, y);
        }if ((rotation % 2) == 0 ) {
            coord2 = new Coordinate(x, y+100);
        }
        return coord2;
    }

}
