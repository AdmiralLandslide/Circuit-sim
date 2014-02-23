//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.io.*;
//
///**
// * Created by Ruben on 05/02/2014.
// */
//
//
//public class CompCanvas extends JPanel {
//
//    static String[] names = {"WIRE", "RESISTOR", "AMMETER", "VOLTMETER", "BULB", "POTENTIOMETER", "CELL", "LDR", "LED", "CAPACITOR", "SWITCH", "SPDT"};
//    static String[] shortName = {"WRE", "RST", "AMT", "VLT", "BLB", "POT", "CEL", "LDR", "LED", "CAP", "SWT", "SPD"};
//    static Boolean componentSelected = false;
//
//    public CompCanvas() {
//        CompCanvas.setLayout(new BoxLayout(CompCanvas, BoxLayout.Y_AXIS));
//        for (int s=0; s<13; s++){
//        final int s2 = s;
//        final String name = names[s];
//            JButton x = new JButton(name);
//            CompCanvas.add(x);
//            x.setFocusPainted(false);
//            x.setContentAreaFilled(false);
//            x.setBorderPainted(false);
//            x.setRolloverEnabled(false);
//            x.setFont(new Font("Apple Gothic", Font.PLAIN, 20));
//            x.setMaximumSize(new Dimension(900, 100));
//            x.setMinimumSize(new Dimension(200, 100));
//            x.addActionListener(
//                    new ActionListener() {
//                        @Override
//                        public void actionPerformed(ActionEvent actionEvent) {
//                            File file = new File("/Users/Ruben/Desktop/data.txt");
//                            Writer writer = null;
//                            try {PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
//                                out.println(shortName[s2]);
//                                out.close();
//                                componentSelected = true;
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                            finally {
//                                if (writer != null) try { writer.close(); } catch (IOException ignore) {}
//                            }
//
//                        }
//                    }
//            );
//       }
//    }
//}
