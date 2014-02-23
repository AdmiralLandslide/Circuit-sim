import java.awt.*;

/**
 * Created by Ruben on 04/02/2014.
 */
public class LightEmittingDiode extends Component {


    public LightEmittingDiode(int rotation,int x, int y) {
        super(rotation,x-100,y-100,200,200);

    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (rotation == 1) {
            g.drawLine(0, 100, 80, 100);
            g.drawLine(120, 100, 200, 100);
            g.drawLine(120, 78, 120, 122);
            g.drawLine(80, 78, 80, 122);
            g.drawLine(80, 78, 120, 100);
            g.drawLine(80, 122, 120, 100);
            g.drawLine(94, 81, 102, 65);
            g.drawLine(102, 65, 102, 75);
            g.drawLine(102, 65, 94, 71);
            g.drawLine(104, 81, 112, 65);
            g.drawLine(112, 65, 112, 75);
            g.drawLine(112, 65, 104, 71);
        }
        if (rotation == 2) {
            g.drawLine(100, 0, 100, 80);
            g.drawLine(100, 120, 100, 200);
            g.drawLine(78, 120, 122, 120);
            g.drawLine(78, 80, 122, 80);
            g.drawLine(78,80, 100, 120);
            g.drawLine(122, 80, 100, 120);
            g.drawLine(119, 94, 135, 102);
            g.drawLine(135, 102, 125, 102);
            g.drawLine(135, 102, 129, 94);
            g.drawLine(119, 104, 135, 112);
            g.drawLine(135, 112, 125, 112);
            g.drawLine(135, 112, 129, 104);
        }
        if (rotation == 3) {
            g.drawLine(0, 100, 80, 100);
            g.drawLine(120, 100, 200, 100);
            g.drawLine(120, 78, 120, 122);
            g.drawLine(80, 78, 80, 122);
            g.drawLine(120, 78, 80, 100);
            g.drawLine(120, 122, 80, 100);
            g.drawLine(106, 81, 98, 65);
            g.drawLine(98, 65, 98, 75);
            g.drawLine(98, 65, 106, 71);
            g.drawLine(96, 81, 88, 65);
            g.drawLine(88, 65, 88, 75);
            g.drawLine(88, 65, 96, 71);
        }
        if (rotation == 4) {
            g.drawLine(100, 0, 100, 80);
            g.drawLine(100, 120, 100, 200);
            g.drawLine(78, 120, 122, 120);
            g.drawLine(78, 80, 122, 80);
            g.drawLine(78, 120, 100, 80);
            g.drawLine(122, 120, 100, 80);
            g.drawLine(119, 106, 135, 98);
            g.drawLine(135, 98, 125, 98);
            g.drawLine(135, 98, 129, 106);
            g.drawLine(119, 96, 135, 88);
            g.drawLine(135, 88, 125, 88);
            g.drawLine(135, 88, 129, 96);
        }

    }
}

