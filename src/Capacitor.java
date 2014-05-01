import java.awt.*;

/**
 * Created by Ruben on 04/02/2014.
 */
public class Capacitor extends Component {


    public Capacitor(int rotation,int x, int y) {
        super(rotation,x-100,y-100,200,200,0,0);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (rotation % 2 == 1) {
            g.drawLine(0, 100, 95, 100);
            g.drawLine(105, 100, 200, 100);
            g.drawLine(95, 75, 95, 125);
            g.drawLine(105, 75, 105, 125);
        }
        if (rotation % 2 == 0) {
            g.drawLine(100, 0, 100, 95);
            g.drawLine(100, 105, 100, 200);
            g.drawLine(75, 95, 125, 95);
            g.drawLine(75, 105, 125, 105);
        }
    }
}

