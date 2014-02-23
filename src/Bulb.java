import java.awt.*;

/**
 * Created by Ruben on 04/02/2014.
 */
public class Bulb extends Component {


    public Bulb(int rotation, int x, int y) {
        super(rotation,x-100,y-100,200,200);

    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (rotation % 2 == 1) {
            g.drawOval(75, 75, 50, 50);
            g.drawLine(0, 100, 75, 100);
            g.drawLine(125, 100, 200, 100);
            g.drawLine(83, 83, 117, 117);
            g.drawLine(83, 117, 117, 83);
        }
        if (rotation % 2 == 0) {
            g.drawOval(75, 75, 50, 50);
            g.drawLine(100, 0, 100, 75);
            g.drawLine(100, 125, 100, 200);
            g.drawLine(83, 83, 117, 117);
            g.drawLine(83, 117, 117, 83);
        }
    }
}

