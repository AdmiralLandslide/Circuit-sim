import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Ruben on 04/02/2014.
 */
public class Wire extends Component {

    public Wire(int rotation,int x2, int y2, int x1, int y1) {
        super(rotation, 0, 0, Component.width, Main.mainFrame.getHeight(), 0,0);
        xone = x2;
        xtwo = x1;
        yone = y2;
        ytwo = y1;

    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawLine(xone, yone, xtwo, ytwo);
    }

    public void setEndpoints(int x2, int y2, int x1, int y1) {
        xone = x1;
        xtwo = x2;
        yone = y1;
        ytwo = y2;
    }
}

