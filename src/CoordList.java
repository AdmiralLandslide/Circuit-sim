import java.util.ArrayList;

/**
* Created by Ruben on 04/03/2014.
*/
public class CoordList extends ArrayList<Coordinate> {

    public boolean containsCoord(int x, int y) {
        for (int i=0; i<size(); i++) {
            if (get(i).getX() == x && get(i).getY() == y) {
                return true;
            }
        }
        return false;
    }

    public void printList() {
        for (int i=0; i<size(); i++) {
            System.out.println(this.get(i).getX() + " " + this.get(i).getY());
        }
    }
}
