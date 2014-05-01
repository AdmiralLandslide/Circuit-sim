import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.io.*;
import java.util.*;
import Jama.Matrix;
import java.lang.Math.*;

public class Canvas extends JPanel {

    private Dimension preferredSize = new Dimension(600, 600);
    private Line2D[] lines = new Line2D[70];
    private Line2D[] lines2 = new Line2D[70];
    private double scale = 1;
    static public ArrayList<Component> components = new ArrayList<Component>();
    private CoordList coordList = new CoordList();
    public Component mouseComponent = null;
    boolean clicked = false;
    static Integer x2;
    static Integer y2;
    public float x;
    boolean valid;
    int m = 0;
    public static int coordListSize;
    public AdjacencyLists Adj1 = new AdjacencyLists(200);
    public AdjacencyLists Adj2;
    public AdjacencyLists Adj3;
//    private Wire tempWire;
//    private Stack visited;
    private Stack<Integer> path = new Stack<Integer>();
    private Stack visited = new Stack();
    private ArrayList<Stack<Integer>> pathList = new ArrayList<Stack<Integer>>();
    private Matrix lhsMatrix;
    private Matrix rhsMatrix;
    private ArrayList<ArrayList<Coordinate>> pathCoordList = new ArrayList(m);

    public Canvas() {
        super();
        JButton push4 = new JButton("rotate");

        push4.setSize(80, 20);
        push4.setLocation(0, 0);
        add(push4);
        push4.addActionListener(
                (new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        Main.rotation++;
                        if (mouseComponent != null) {
                            mouseComponent.rotation++;
                        }
                        if ((Main.rotation % 5) == 0) {
                            Main.rotation = 1;
                        }
                    }
                })
        );
        JButton push5 = new JButton("mouse");

        push5.setSize(80, 20);
        push5.setLocation(0, 20);
        add(push5);
        push5.addActionListener(
                (new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        Main.lastButtonPressed = null;
                        if (mouseComponent != null) remove(mouseComponent);
                        mouseComponent = null;
                        repaint();
                        clicked = false;
                        x2 = 0;
                        y2 = 0;
                    }
                })
        );

        JButton push6 = new JButton("run");
        push6.setSize(80, 20);
        push6.setLocation(0, 40);
        add(push6);
        push6.addActionListener(
                (new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        listData();

                    }
                })
        );

        JButton push7 = new JButton("save");

        push7.setSize(80, 20);
        push7.setLocation(0, 60);
        add(push7);
        push7.addActionListener(
                (new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        selectFileName();
                    }
                })
        );

//       Canvas.Layout(GridBagLayout);
        //generate lines
        for (int i = 0; i < lines.length; i++) {
            lines[i] = new Line2D.Float(
                    i * 50, 0,
                    i * 50, 4000);
            lines2[i] = new Line2D.Float(
                    0,i * 50,
                    4000, i * 50);
        }

        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent mouseEvent) {
            }

            @Override
            public void mouseMoved(MouseEvent mouseEvent) {
                int x = (mouseEvent.getX() - 100);
                int y = (mouseEvent.getY() - 100);
                if (mouseComponent != null) {
                    if (Main.lastButtonPressed.equals("WRE")) {
                        if (clicked) {
                            if ((x % 50 < 15) || x % 50 > 35) {
                                x = 50 * (Math.round((x + 25) / 50));
                            }

                            if ((y % 50 < 15) || y % 50 > 35) {
                                y = 50 * (Math.round((y + 25) / 50));
                            }
                            ((Wire) mouseComponent).setEndpoints(x2, y2, x +100 , y +100);
                            mouseComponent.repaint();
                            Main.canvas1.repaint();
                            RepaintManager.currentManager(mouseComponent).markCompletelyClean(mouseComponent);
                        }
                    } else {
                        if ((x % 50 < 15) || x % 50 > 35) {
                            x = 50 * (Math.round((x + 25) / 50));
                        }

                        if ((y % 50 < 15) || y % 50 > 35) {
                            y = 50 * (Math.round((y + 25) / 50));
                        }
                        mouseComponent.setLocation(x, y);
                        mouseComponent.repaint();
                        RepaintManager.currentManager(mouseComponent).markCompletelyClean(mouseComponent);
                    }
                }
            }
        });
        addMouseWheelListener(new MouseWheelListener() {
            public void mouseWheelMoved(MouseWheelEvent e) {
//                updatePreferredSize(e.getWheelRotation(), e.getPoint());

            }
        });
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String lst = Main.lastButtonPressed;
                Integer x = (50 * (Math.round((mouseEvent.getX() + 25) / 50)));
                Integer y = (50 * (Math.round((mouseEvent.getY() + 25) / 50)));
                Component component = null;
                if ("WRE".equals(lst)) {
                    if (!clicked) {
                        x2 = x;
                        y2 = y;
                        clicked = true;
                        return;
                    } else {
                        component = new Wire(Main.rotation, x2, y2, x, y);
                        x2 = null;
                        y2 = null;
                        x = null;
                        y = null;
                        clicked = false;
                    }
                } else if ("RST".equals(lst)) {
                    getResistance(Main.rotation,x,y,lst);
                    return;
                } else if ("AMT".equals(lst)) {
                    component = new Ammeter(Main.rotation, x, y);
                } else if ("VLT".equals(lst)) {
                    component = new Voltmeter(Main.rotation, x, y);
                } else if ("BLB".equals(lst)) {
                    getResistance(Main.rotation,x,y, lst);
                    return;
                } else if ("VAR".equals(lst)) {
                    component = new VariableResistor(Main.rotation, x, y);
                } else if ("CEL".equals(lst)) {
                    getEMF(Main.rotation,x,y);
                    return;
                } else if ("LDR".equals(lst)) {
                    component = new LightDependentResistor(Main.rotation, x, y);
                } else if ("LED".equals(lst)) {
                    getResistance(Main.rotation,x,y, lst);
                    return;
                } else if ("CAP".equals(lst)) {
                    component = new Capacitor(Main.rotation, x, y);
                } else if ("SWT".equals(lst)) {
                    component = new Switch(Main.rotation, x, y);
                } else if ("SPD".equals(lst)) {
                    component = new SinglePoleDoubleThrow(Main.rotation, x, y);
                }
                else return;
                components.add(component);
                add(component);
                repaint();
                getcoords(mouseEvent.getX(), mouseEvent.getY());
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {
            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {
            }
        });
    }

    public void selectFileName() {
        final JFrame fileFrame = new JFrame("Save as");
        fileFrame.setVisible(true);
        fileFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        final JFormattedTextField textField = new JFormattedTextField();
        textField.setPreferredSize(new Dimension(60, 22));
        JLabel label = new JLabel("Chose a file name");
        final JPanel panel = new JPanel();
        fileFrame.setSize(500, 70);
        panel.add(label);
        panel.add(textField);
        fileFrame.add(panel);
        textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                serialize(components, textField.getText());
                fileFrame.dispose();
            }
        });
    }

    private void updatePreferredSize(int n, Point p) {
        Double MAX_SCALE = 10.0;
        Double MIN_SCALE = 0.47;
        if (n == 0) return;
        double d = (double) n * 1.08;
        d = (n > 0) ? d : 1 / -d;

        int w = (int) (getWidth() * d);
        int h = (int) (getHeight() * d);
        double newScale = scale * d;
        if (newScale > MAX_SCALE || newScale < MIN_SCALE) return;
        scale = newScale;
        preferredSize.setSize(w, h);

        int offX = (int) (p.x * d) - p.x;
        int offY = (int) (p.y * d) - p.y;
        setLocation(getLocation().x - offX, getLocation().y - offY);

        getParent().doLayout();
    }

    public Dimension getPreferredSize() {
        return preferredSize;
    }

    private Line2D l = new Line2D.Float();

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(220, 220, 220));
        double w = scale;
        double h = scale;
        for (Line2D line : lines) {
            l.setLine(line.getX1() * w, line.getY1() * w,
                    line.getX2() * h, line.getY2() * h);
            ((Graphics2D) g).draw(l);
        }
        for (Line2D line : lines2) {
            l.setLine(line.getX1() * w, line.getY1() * w,
                    line.getX2() * h, line.getY2() * h);
            ((Graphics2D) g).draw(l);
        }
    }

    private void getcoords(float xCoord, float yCoord) {
        String X1 = "";
        String Y1 = "";
        String X2 = "";
        String Y2 = "";
        String X3 = "";
        String Y3 = "";
        if (Main.lastButtonPressed.equals("WRE")) {
            X1 = Integer.toString((int) (Math.round(((((Wire) mouseComponent).xone)) / (scale * (1 / 1.08))) / 50));
            Y1 = Integer.toString((int) (Math.round(((((Wire) mouseComponent).yone)) / (scale * (1 / 1.08))) / 50));
            X2 = Integer.toString((int) (Math.round(((((Wire) mouseComponent).xtwo)) / (scale * (1 / 1.08))) / 50));
            Y2 = Integer.toString((int) (Math.round(((((Wire) mouseComponent).ytwo)) / (scale * (1 / 1.08))) / 50));
        }
        if (Main.lastButtonPressed.equals("SPD")) {
            if (Main.rotation == 1) {
                X1 = Integer.toString((int) (Math.round((((xCoord) / (scale * (1 / 1.08))) / 50) - 2)));
                Y1 = Integer.toString((int) (Math.round((yCoord) / (scale * (1 / 1.08))) / 50));
                X2 = Integer.toString((int) (Math.round((((xCoord) / (scale * (1 / 1.08))) / 50) + 2)));
                Y2 = Integer.toString((int) (Math.round((yCoord) / (scale * (1 / 1.08))) / 50) - 1);
                X3 = Integer.toString((int) (Math.round((((xCoord) / (scale * (1 / 1.08))) / 50) + 2)));
                Y3 = Integer.toString((int) (Math.round((yCoord) / (scale * (1 / 1.08))) / 50) + 1);
            }
            if (Main.rotation == 2) {
                X1 = Integer.toString((int) (Math.round((xCoord) / (scale * (1 / 1.08))) / 50));
                Y1 = Integer.toString((int) (Math.round((((yCoord) / (scale * (1 / 1.08))) / 50) - 2)));
                X2 = Integer.toString((int) (Math.round((((xCoord) / (scale * (1 / 1.08))) / 50) - 1)));
                Y2 = Integer.toString((int) (Math.round((((yCoord) / (scale * (1 / 1.08))) / 50) + 2)));
                X3 = Integer.toString((int) (Math.round((((xCoord) / (scale * (1 / 1.08))) / 50) + 1)));
                Y3 = Integer.toString((int) (Math.round((((yCoord) / (scale * (1 / 1.08))) / 50) + 2)));
            }
            if (Main.rotation == 3) {
                X1 = Integer.toString((int) (Math.round((((xCoord) / (scale * (1 / 1.08))) / 50) + 2)));
                Y1 = Integer.toString((int) (Math.round((yCoord) / (scale * (1 / 1.08))) / 50));
                X2 = Integer.toString((int) (Math.round((((xCoord) / (scale * (1 / 1.08))) / 50) - 2)));
                Y2 = Integer.toString((int) (Math.round((yCoord) / (scale * (1 / 1.08))) / 50) - 1);
                X3 = Integer.toString((int) (Math.round((((xCoord) / (scale * (1 / 1.08))) / 50) - 2)));
                Y3 = Integer.toString((int) (Math.round((yCoord) / (scale * (1 / 1.08))) / 50) + 1);

            }
            if (Main.rotation == 4) {
                X1 = Integer.toString((int) (Math.round((xCoord) / (scale * (1 / 1.08))) / 50));
                Y1 = Integer.toString((int) (Math.round((((yCoord) / (scale * (1 / 1.08))) / 50) + 2)));
                X2 = Integer.toString((int) (Math.round((((xCoord) / (scale * (1 / 1.08))) / 50) + 1)));
                Y2 = Integer.toString((int) (Math.round((((yCoord) / (scale * (1 / 1.08))) / 50) - 2)));
                X3 = Integer.toString((int) (Math.round((((xCoord) / (scale * (1 / 1.08))) / 50) - 1)));
                Y3 = Integer.toString((int) (Math.round((((yCoord) / (scale * (1 / 1.08))) / 50) - 2)));
            }
        } else {
            if (Main.rotation % 2 == 1) {
                X1 = Integer.toString((int) (Math.round((((xCoord) / (scale * (1 / 1.08))) / 50) - 2)));
                Y1 = Integer.toString((int) (Math.round((yCoord) / (scale * (1 / 1.08))) / 50));
                X2 = Integer.toString((int) (Math.round((((xCoord) / (scale * (1 / 1.08))) / 50) + 2)));
                Y2 = Integer.toString((int) (Math.round((yCoord) / (scale * (1 / 1.08))) / 50));
            }
            if (Main.rotation % 2 == 0) {
                X1 = Integer.toString((int) (Math.round((xCoord) / (scale * (1 / 1.08))) / 50));
                Y1 = Integer.toString((int) (Math.round((((yCoord) / (scale * (1 / 1.08))) / 50) - 2)));
                X2 = Integer.toString((int) (Math.round((xCoord) / (scale * (1 / 1.08))) / 50));
                Y2 = Integer.toString((int) (Math.round((((yCoord) / (scale * (1 / 1.08))) / 50) + 2)));
            }
        }
        if (X1.length() < 2) {
            X1 = "0" + X1;
        }
        if (Y1.length() < 2) {
            Y1 = "0" + Y1;
        }
        if (X2.length() < 2) {
            X2 = "0" + X2;
        }
        if (Y2.length() < 2) {
            Y2 = "0" + Y2;
        }
        if (X3.length() < 2) {
            X3 = "0" + X3;
        }
        if (Y3.length() < 2) {
            Y3 = "0" + Y3;
        }
    }

    private void getResistance(final int r, final int xx, final int y, final String lst) {
        Component c;
        x = 0;
        final JFrame resFrame = new JFrame();
        resFrame.setVisible(true);
//        resFrame.setLayout(null);
        resFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        final JFormattedTextField textField = new JFormattedTextField();
        textField.setPreferredSize(new Dimension(60,22));
        JLabel label = new JLabel("Enter the Resistance of the component:");
        final JPanel panel = new JPanel();
        resFrame.setSize(500, 70);
        panel.add(label);
        panel.add(textField);
        resFrame.add(panel);
        valid = true;
        textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    x = Float.parseFloat(textField.getText());
                    resFrame.dispose();
                    addComponentWithResistance(r,xx,y,x, lst);
                } catch (NumberFormatException n) {
                    if (valid) {
                        panel.add(new JLabel("Invalid"));
                        valid = false;
                        panel.revalidate();
                        panel.repaint();
                    }
                }
            }
        });
    }

    private void addComponentWithResistance(int r, int xx, int y,float x, String lst) {
        Component c;
        if ("RST".equals(lst)) {
            c = new Resistor(r,xx,y,x);
        }
        else
            if ("BLB".equals(lst)) {
                c = new Bulb(r, xx, y, x);
            } else {
                c = new LightEmittingDiode(r, xx, y, x);
            }

        components.add(c);
        add(c);
        repaint();
    }

    private void getEMF(final int r, final int xx,final  int y) {
        x = 0;
        final JFrame resFrame = new JFrame();
        resFrame.setVisible(true);
//        resFrame.setLayout(null);
        resFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        final JFormattedTextField textField = new JFormattedTextField();
        textField.setPreferredSize(new Dimension(60, 22));
        JLabel label = new JLabel("Enter the EMF of the component:");
        final JPanel panel = new JPanel();
        resFrame.setSize(500, 70);
        panel.add(label);
        panel.add(textField);
        resFrame.add(panel);
        valid = true;
        textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    x = Float.parseFloat(textField.getText());
                    resFrame.dispose();
                    addComponentWithEMF(r,xx,y,x);
                } catch (NumberFormatException n) {
                    if (valid) {
                        panel.add(new JLabel("Invalid"));
                        valid = false;
                        panel.revalidate();
                        panel.repaint();
                    }


                }
            }
        });

    }
    private void addComponentWithEMF(int r, int xx, int y,float x) {
        Component c;
        c = new Cell(r, xx, y, x);
        components.add(c);
        add(c);
        repaint();
    }


    private void serialize(ArrayList comp, String str) {
        try {
            FileOutputStream fileOut = new FileOutputStream("/Users/Ruben/Documents/" + str + ".ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(comp);

            out.close();
            fileOut.close();
            Main.errorLabel.setText("Serialized data is saved in /Users/Ruben/Documents/" + str + ".ser");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    private void listData() {
        cleanCircuit();
        findCoords();
        coordListSize = coordList.size();
        createList(Adj1, false);

        Adj3 = new AdjacencyLists(200);
        m = findM();
        if (m==-1) return;
        System.out.println(m);
        lhsMatrix = new Matrix(m,m);
        rhsMatrix = new Matrix(m,1);
        Adj2 = new AdjacencyLists(Adj1);
        printList(Adj2);
        findLoop(0, 0, Adj2);
        getAllLoops();
        arrayofCoords();
        solveMatrices();
    }

    private boolean checkDeadEnds() {
        for (int i=0; i<coordListSize; i++) {
            if (Adj3.stackSize(i)<2) {
                return true;
            }
        }
        return false;
    }
    private void cleanCircuit() {
        coordList.clear();
        resetVandA();
        resetMatrices();
        resetPathList();
    }
    private void resetVandA() {
        for (Component c : components) {
            c.setCurrent(0);
        }
    }
    private void resetMatrices() {
        for (int n=0; n<m; n++) {
            rhsMatrix.set(n,0,0);
            for (int o=0; o<m; o++) {
                lhsMatrix.set(n,o,0);
            }
        }
    }
    private void resetPathList() {
            pathList.clear();
    }

    private void findCoords() {
        for (Component c : components) {
            if (!coordList.containsCoord(c.xone, c.yone)) {
                coordList.add(new Coordinate(c.xone, c.yone));
            }
            if (!coordList.containsCoord(c.xtwo, c.ytwo)) {
                coordList.add(new Coordinate(c.xtwo, c.ytwo));
            }
        }


    }
    private void findCoordsOld() {
        for (Component c :components) {
            if (c instanceof Wire) {
                int xcoord1 = ((Wire) c).xone;
                int ycoord1 = ((Wire) c).yone;
                int xcoord2 = ((Wire) c).xtwo;
                int ycoord2 = ((Wire) c).ytwo;
//                System.out.println(xcoord1 + " " + ycoord1 + " " + xcoord2 + " " + ycoord2);
                if (!(coordList.containsCoord(xcoord1, ycoord1))) {
                    coordList.add(new Coordinate(xcoord1, ycoord1));
                }
                if (!(coordList.containsCoord(xcoord2, ycoord2))) {
                    coordList.add(new Coordinate(xcoord2, ycoord2));
                }
            } else {
                int xcoord = c.x ;
                int ycoord = c.y ;
                if (c.rotation % 2 == 1) {
                    if (!(coordList.containsCoord(xcoord - 100, ycoord))) {
                        coordList.add(new Coordinate(xcoord - 100, ycoord));
                    }
                    if (!coordList.containsCoord(xcoord + 100, ycoord)) {
                        coordList.add(new Coordinate(xcoord + 100, ycoord));
                    }
                }
                if (c.rotation % 2 == 0) {
                    if (!(coordList.containsCoord(xcoord, ycoord - 100))) {
                        coordList.add(new Coordinate(xcoord, ycoord - 100));
                    }
                    if (!(coordList.containsCoord(xcoord, ycoord + 100))) {
                        coordList.add(new Coordinate(xcoord, ycoord + 100));
                    }
                }
            }
        }
    }

    private void createList(AdjacencyLists x, boolean includeVoltmeters) {
        for (Component c : components) {
            if (c instanceof Voltmeter && !includeVoltmeters){}
            else {
                x.addEdge(coordList.indexOf(new Coordinate(c.xone, c.yone)), coordList.indexOf(new Coordinate(c.xtwo, c.ytwo)));
                x.addEdge(coordList.indexOf(new Coordinate(c.xtwo, c.ytwo)), coordList.indexOf(new Coordinate(c.xone, c.yone)));
            }
        }
    }

    private void createListOld(AdjacencyLists x, boolean includeVoltmeters) {
        for (int i = 0; i < components.size(); i++) {
            if (components.get(i) instanceof Wire) {
                int xcoord1 = ((Wire) components.get(i)).xone;
                int ycoord1 = ((Wire) components.get(i)).yone;
                int xcoord2 = ((Wire) components.get(i)).xtwo;
                int ycoord2 = ((Wire) components.get(i)).ytwo;
//                System.out.println(xcoord1 + " " + ycoord1 + " " + xcoord2 + " " + ycoord2);
                x.addEdge(coordList.indexOf(new Coordinate(xcoord1, ycoord1)),
                        coordList.indexOf(new Coordinate(xcoord2, ycoord2)));
                x.addEdge(coordList.indexOf(new Coordinate(xcoord2, ycoord2)),
                        coordList.indexOf(new Coordinate(xcoord1, ycoord1)));
            } else
            if ((components.get(i) instanceof Voltmeter && includeVoltmeters) || !(components.get(i) instanceof Voltmeter)) {
                int xcoord = components.get(i).x;
                int ycoord = components.get(i).y;
                if (components.get(i).rotation % 2 == 1) {
                    Coordinate tempcoord1 = new Coordinate((xcoord - 100), ycoord);
                    Coordinate tempcoord2 = new Coordinate((xcoord + 100), ycoord);
                    x.addEdge(coordList.indexOf(tempcoord1),coordList.indexOf(tempcoord2));
                    x.addEdge(coordList.indexOf(tempcoord2),coordList.indexOf(tempcoord1));

                }
                if (components.get(i).rotation % 2 == 0) {
                    Coordinate tempcoord1 = new Coordinate(xcoord, (ycoord - 100));
                    Coordinate tempcoord2 = new Coordinate(xcoord, (ycoord + 100));
                    x.addEdge(coordList.indexOf(tempcoord1),coordList.indexOf(tempcoord2));
                    x.addEdge(coordList.indexOf(tempcoord2),coordList.indexOf(tempcoord1));
                }
            }

        }
//        x.printList();

 }

    private void printList(AdjacencyLists x) {
//        x.printList();

    }

    private int mostConnections() {
        return Adj1.mostConnections();
    }

    private int findM() {
        visited = new Stack();
        int x = Adj1.getNode(0, 0);
        if (x == -1) return x;
        m = Adj1.countEdges() - traverseTree(x) + 1;
//        System.out.println(m);
        return m;
    }

    private int traverseTree(int index) {

        visited.push(index);
        int n = 1;

        for (Integer nextNode : Adj1.getAdjacentNodes(index)) {
            if (!visited.contains(nextNode)) {
                n += traverseTree(nextNode);
            }
        }

        return n;
    }

//    private void findLoops(int current, int end) {
//        boolean fin = false;
//        int i=0;
//        if (current != end || path.size()==0) {
//            visited.push(current);
//            while (!fin) {
//                int nextNode = Adj1.getNode(current,i);
//                if (!visited.contains(nextNode)) {
//                    path.push(nextNode);
//                    findLoops(nextNode, end);
//                    fin = true;
//                }
//                i++;
//            }
//        }
//        return;
//
//    }

    private void getAllLoops(){
        for (int i = 0; i < coordListSize; i++) {
            while (Adj2.stackSize(i) > 1 && pathList.size()<m) {
                findLoop(i, i, Adj2);
                pathList.add(path);
                Adj2.removeEdge(path.get(0), path.get(1));
                path = new Stack();
                while (Adj2.removeDeadEnds()) {}
            }
        }
    }
    private boolean findLoop(int current, int end, AdjacencyLists w) {
        if (current == end && path.size() > 2) {
            return true;
        }
        for (int i = 0; i < w.getAdjacentNodes(current).size(); i++) {
            int nextNode = w.getNode(current,i);
            if (!path.contains(nextNode) && !(nextNode == end && path.size() <= 2)) {
                path.push(nextNode);
                if (findLoop(nextNode, end,w)) return true;
                else {
                    path.pop();
                }
            }
        }
        return false;
    }

    public void arrayofCoords() {
        Double tempVolt = 0.0;
        Double tempRes  = 0.0;

        for (int i=0; i<m; i++) {
            for (Component c : components) {
                Coordinate endPoint1 = c.getEndPoint1();
                Coordinate endPoint2 = c.getEndPoint2();
                int directioni = directionOfEdge(pathList.get(i),endPoint1, endPoint2);
//                System.out.println("Processing component, emf = " + c.emf + ", resistance = " + c.resistance + ". Presence in loop " + i + " is " + directioni);
                if (directioni != 0) {
                    double tV = c.emf*directioni;

                   rhsMatrix.set(i,0, rhsMatrix.get(i,0)+tV);
//                    System.out.println(tV);
                    for (int j=0; j<m; j++) {
                        try {
                            double tR = c.resistance;
                            tempRes = directioni * directionOfEdge(pathList.get(j), endPoint1, endPoint2) * tR;
                            lhsMatrix.set(i, j, lhsMatrix.get(i, j) + tempRes);
                        }
                        catch (IndexOutOfBoundsException r) {
                            Main.errorLabel.setText("Circuit is not connected");
                        }
                    }
                }
            }
        }
    }

    private int directionOfEdge (Stack<Integer> path, Coordinate c1, Coordinate c2) {
        if (coordList.get(path.get(0)).equals(c1) && coordList.get(path.get(path.size()-1)).equals(c2)) return -1;
        if (coordList.get(path.get(0)).equals(c2) && coordList.get(path.get(path.size()-1)).equals(c1)) return 1;
        for (int i=0; i<path.size(); i++) {
            if (coordList.get(path.get(i)).equals(c1)) {
                if (i!=0 && coordList.get(path.get(i-1)).equals(c2)) return -1;
                if (i<path.size()-1 && coordList.get(path.get(i+1)).equals(c2)) return 1;
            }
        }
        return 0;
    }

    private void solveCurrents(Matrix currents) {
        for (int i=0; i<m; i++) {
            for (Component c : components) {

                Coordinate endPoint1 = c.getEndPoint1();
                Coordinate endPoint2 = c.getEndPoint2();
                int directioni = directionOfEdge(pathList.get(i), endPoint1, endPoint2);
                c.setCurrent(c.getCurrent() + directioni * currents.get(i, 0));

            }
        }
    }

    private void solveVoltmeters() {
        ArrayList<Integer> listOfVoltmeters = new ArrayList<Integer>();
        boolean presOfVolt = false;
        for (int i=0; i<components.size(); i++) {
            if (components.get(i) instanceof Voltmeter) {
                presOfVolt = true;
                listOfVoltmeters.add(i);
            }
        }
//        System.out.println("Number of Voltmeters is : " + listOfVoltmeters.size());
        if (presOfVolt) {calculateVoltages(listOfVoltmeters);}
    }

    public void calculateVoltages(ArrayList<Integer> list) {
        createList(Adj3, true);
        if (checkDeadEnds()) {
            Main.errorLabel.setText("Circuit contains dead ends");
        }
        int tc1=0;
        int tc2=0;
        for (int i = 0; i < list.size(); i++) {

            Component c = components.get(list.get(i));
            Coordinate tempcoord1 = c.getEndPoint1();
            Coordinate tempcoord2 = c.getEndPoint2();
//            System.out.println("Want " + tempcoord1.getX() + "," + tempcoord1.getY());
//            System.out.println("And " + tempcoord2.getX() + "," + tempcoord2.getY());


            for (int j = 0; j < coordList.size(); j++) {
//                System.out.println(j);
//                System.out.println(coordList.get(j).getX()+","+coordList.get(j).getY());
                if (coordList.get(j).equals(tempcoord1)) {
                    tc1 = j;
                }
                if (coordList.get(j).equals(tempcoord2)) {
                    tc2 = j;
                }
            }

//            System.out.println(tc1 + " ... " + tc2);
            findLoop(tc1, tc2, Adj3);
//            System.out.println("CHECKPOINT");
            double PD = 0;
            System.out.println(path.size());
            for (int k = 0; k < path.size(); k++) {
//                System.out.println("CHECKPOINT");
                int x = ((coordList.get(path.get(k)).getX() + coordList.get(path.get((k + 1) % path.size())).getX()) / 2);
                int y = ((coordList.get(path.get(k)).getY() + coordList.get(path.get((k + 1) % path.size())).getY()) / 2);
                for (int l = 0; l < components.size(); l++) {
                    if (components.get(l).x == x && components.get(l).y == y) {
                        int directioni=1;
                        if (coordList.get(path.get(k)).getX() == components.get(l).getEndPoint2().getX() && coordList.get(path.get(k)).getY() == components.get(l).getEndPoint2().getY()) directioni = -1;
                        PD += components.get(l).getPotentialDifference() * directioni;
                        PD += components.get(l).emf  * directioni * -1;
//                        System.out.println("this PD = " + components.get(l).getPotentialDifference());
                    }
                }
//                System.out.println("Voltmeter says PD = " + PD);

            }
            Voltmeter voltmeter = (Voltmeter)components.get(list.get(i));

            voltmeter.setPD(PD);
            repaint();
        }


    }

    private void solveMatrices() {

//        System.out.println("Determinant = " + lhsMatrix.det());
        try {
            Matrix ans = lhsMatrix.solve(rhsMatrix);
            solveCurrents(ans);
            solveVoltmeters();
//        for (int i=0; i<m; i++) {
//            System.out.println("current for loop " + (i+1) + " = " + (ans.get(i, 0)));
//        }
            repaint();
        }
        catch (RuntimeException n) {
            Main.errorLabel.setText("Circuit is un-solveable");
        }


    }
}