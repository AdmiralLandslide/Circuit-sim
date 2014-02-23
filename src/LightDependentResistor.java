import java.awt.*;

/**
 * Created by Ruben on 04/02/2014.
 */
public class LightDependentResistor extends Component {


    public LightDependentResistor(int rotation,int x, int y) {
        super(rotation,x-100,y-100,200,200);

    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (rotation % 2 == 1) {
            g.drawRect(50, 85, 100, 30);
            g.drawLine(0, 100, 50, 100);
            g.drawLine(150, 100, 200, 100);
            g.drawLine(50, 55, 80, 75);
            g.drawLine(80, 75, 70, 75);
            g.drawLine(80, 75, 76, 65);
            g.drawLine(70, 55, 100, 75);
            g.drawLine(100, 75, 90, 75);
            g.drawLine(100, 75, 96, 65);
        }
        if (rotation % 2 == 0) {
            g.drawRect(85, 50, 30, 100);
            g.drawLine(100, 0, 100, 50);
            g.drawLine(100, 150, 100, 200);
            g.drawLine(145, 50, 125, 80);
            g.drawLine(125, 80, 125, 70);
            g.drawLine(125, 80, 135, 76);
            g.drawLine(145 ,70 ,125 ,100);
            g.drawLine(125 ,100 ,125 ,90);
            g.drawLine(125 ,100 ,135 ,96);
        }
    }
}

