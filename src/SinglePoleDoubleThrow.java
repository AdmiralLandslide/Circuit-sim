import java.awt.*;

/**
 * Created by Ruben on 04/02/2014.
 */
public class SinglePoleDoubleThrow extends Component {


    public SinglePoleDoubleThrow(int rotation, int x, int y) {
        super(rotation,x-100,y-100,200,200,0,0);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (rotation == 1) {
            g.drawLine(0 ,100 ,50 ,100);
            g.drawLine(150, 50, 200, 50);
            g.drawLine(150, 150, 200, 150);
            g.fillRect(48, 98, 5, 5);
            g.fillRect(148, 48, 5, 5);
            g.fillRect(148, 148, 5, 5);
            g.drawLine(50, 100, 150, 50);
        }
        if (rotation == 2) {
            g.drawLine(100 ,0 ,100 ,50);
            g.drawLine(50, 150, 50, 200);
            g.drawLine(150, 150, 150, 200);
            g.fillRect(98, 48, 5, 5);
            g.fillRect(48, 148, 5, 5);
            g.fillRect(148, 148, 5, 5);
            g.drawLine(100, 50, 50, 150);
        }
        if (rotation == 3) {
            g.drawLine(0 ,50 ,50 ,50);
            g.drawLine(0, 150, 50, 150);
            g.drawLine(150, 100, 200, 100);
            g.fillRect(148, 98, 5, 5);
            g.fillRect(48, 48, 5, 5);
            g.fillRect(48, 148, 5, 5);
            g.drawLine(50, 50, 150, 100);
        }
        if (rotation == 4) {
            g.drawLine(50,0,50,50);
            g.drawLine(150, 0, 150, 50);
            g.drawLine(100, 150, 100, 200);
            g.fillRect(48, 48, 5, 5);
            g.fillRect(148, 48, 5, 5);
            g.fillRect(98, 148, 5, 5);
            g.drawLine(50, 50, 100, 150);
        }

    }
}