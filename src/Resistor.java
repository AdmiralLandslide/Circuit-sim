import java.awt.*;

/**
 * Created by Ruben on 04/02/2014.
 */
public class Resistor extends Component {


    public Resistor(int rotation, int x, int y) {
        super(rotation, x-100,y-100,200,200);

    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (rotation % 2 == 1) {
            g.drawRect(50, 85, 100, 30);
            g.drawLine(0, 100, 50, 100);
            g.drawLine(150, 100, 200, 100);
        }
        if (rotation % 2 == 0) {
            g.drawRect(85, 50, 30, 100);
            g.drawLine(100, 0, 100, 50);
            g.drawLine(100, 150, 100, 200);
        }

    }
}
