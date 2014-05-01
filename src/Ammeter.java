import java.awt.*;
import java.math.BigDecimal;
import java.math.MathContext;

/**
 * Created by Ruben on 04/02/2014.
 */
public class Ammeter extends Component {

    public Ammeter(int rotation, int x, int y) {
        super(rotation,x-100,y-100,200,200,0,0);
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
        if (getCurrent()==0) {
            g.drawLine(100, 83, 115, 113);
            g.drawLine(100, 83, 85, 113);
            g.drawLine(92, 100, 108, 100);
        }
        else {
            if (getCurrent() < 1) {
                g.drawString(String.valueOf(new BigDecimal(getCurrent()).round(new MathContext(2))) + "A", 85, 105);
            } else g.drawString(String.valueOf(new BigDecimal(getCurrent()).round(new MathContext(3))) + "A", 85, 105);
        }
    }
}