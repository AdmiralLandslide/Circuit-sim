import java.awt.*;

/**
 * Created by Ruben on 04/02/2014.
 */
public class Switch extends Component {


    public Switch(int rotation, int x, int y) {
        super(rotation, x-100,y-100,200,200,0,0);

    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (rotation % 2 == 1) {
            g.drawLine(0, 100, 50, 100);
            g.drawLine(150, 100 , 200, 100);
            g.drawLine(50, 100, 140, 80);
            g.fillRect(48, 98, 5, 5);
            g.fillRect(148, 98, 5, 5);
        }
        if (rotation % 2 == 0) {
            g.drawLine(100, 0, 100, 50);
            g.drawLine(100, 150, 100, 200);
            g.drawLine(100, 50, 120, 140);
            g.fillRect(98, 48, 5, 5);
            g.fillRect(98, 148, 5, 5);
        }
    }
}

