import java.awt.*;

/**
 * Created by Ruben on 04/02/2014.
 */
public class Cell extends Component {


    public Cell(int rotation,int x, int y, double emf) {
        super(rotation,x-100,y-100,200,200,0,emf);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (rotation == 1) {
            g.drawLine(0, 100, 95, 100);
            g.drawLine(105, 100, 200, 100);
            g.drawLine(95, 70, 95, 130);
            g.drawLine(105, 90, 105, 110);
            if (emf!=0) {
                g.drawString(emf + "V", 85, 150);
            }
        }
        if (rotation == 2) {
            g.drawLine(100, 0, 100, 95);
            g.drawLine(100, 105, 100, 200);
            g.drawLine(70, 95, 130, 95);
            g.drawLine(90, 105, 110, 105);
            if (emf!=0) {
                g.drawString(emf + "V", 35, 105);
            }
        }
        if (rotation == 3) {
            g.drawLine(0, 100, 95, 100);
            g.drawLine(105, 100, 200, 100);
            g.drawLine(95, 90, 95, 110);
            g.drawLine(105, 70, 105, 130);
            if (emf!=0) {
                g.drawString(emf + "V", 85, 150);
            }
        }
        if (rotation == 4) {
            g.drawLine(100, 0, 100, 95);
            g.drawLine(100, 105, 100, 200);
            g.drawLine(90, 95, 110, 95);
            g.drawLine(70, 105, 130, 105);
            if (emf!=0) {
                g.drawString(emf + "V", 35, 105);
            }
        }
    }
}

