import java.awt.*;
import java.awt.geom.Arc2D;
import java.math.BigDecimal;
import java.math.MathContext;

/**
 * Created by Ruben on 04/02/2014.
 */
public class Voltmeter extends Component {

    private double PD;

    public double getPD() { return PD;}
    public void setPD(double p) {PD = p;}
    public double getCurrent() { return PD;}
    public Voltmeter(int rotation, int x, int y) {
        super(rotation,x-100,y-100,200,200, Float.POSITIVE_INFINITY,0);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (rotation % 2 == 1) {
            g.drawOval(75, 75, 50, 50);
            g.drawLine(0, 100, 75, 100);
            g.drawLine(125, 100, 200, 100);
            }
        if (rotation % 2 == 0) {
            g.drawOval(75, 75, 50, 50);
            g.drawLine(100, 0, 100, 75);
            g.drawLine(100, 125, 100, 200);
        }
        if (getPD() == 0) {
            g.drawLine(100, 117, 115, 87);
            g.drawLine(100, 117, 85, 87);
        } else {
            if (getPD() < 1) {
                g.drawString(String.valueOf(new BigDecimal(getPD()).round(new MathContext(2))) + "V", 85, 105);
            } else g.drawString(String.valueOf(new BigDecimal(getPD()).round(new MathContext(3))) + "V", 85, 105);
        }
    }
}

