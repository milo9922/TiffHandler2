import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.DecimalFormat;
import java.util.LinkedList;

class UserInterface extends JFrame implements ActionListener {

    private JLabel l;
    private String currentPath = null;

    private UserInterface() {

        // this label displays result
        l = new JLabel();
        l.setBounds(50, 55, 400, 40);

        // this button coverts file to .jpg
        JButton b = new JButton("Konwersja do JPG");
        b.setBounds(50, 120, 200, 50);
        b.addActionListener(this);

        // this button converts file to .png
        JButton b2 = new JButton("Konwersja do PNG");
        b2.setBounds(50, 190, 200, 50);
        b2.addActionListener(this);

        // this button checks file resolution
        JButton b3 = new JButton("Sprawdzenie rozdzielczości");
        b3.setBounds(50, 260, 200, 50);
        b3.addActionListener(this);

        // this button checks file dimensions
        JButton b4 = new JButton("Sprawdzenie wymiarów");
        b4.setBounds(50, 330, 200, 50);
        b4.addActionListener(this);

        // this button ends program
        JButton b5 = new JButton("Zmień plik");
        b5.setBounds(50, 400, 200, 50);
        b5.addActionListener(this);

        // this button changes path
        JButton b6 = new JButton("Wyjście");
        b6.setBounds(50, 470, 200, 50);
        b6.addActionListener(this);


        // set atributes of program gui
        add(b);
        add(l);
        add(b2);
        add(b3);
        add(b4);
        add(b5);
        add(b6);
        setSize(750, 590);
        setTitle("TiffHandler");
        Color color = new Color(235, 235, 255);
        setBackground(color);
        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new UserInterface();
    }

    // path setter
    private void setCurrentPath(String currentPath) {
        this.currentPath = currentPath;
    }


    public void actionPerformed(ActionEvent e) {
        Functions functions = new Functions();
        String action = e.getActionCommand();
        JFileChooser fileChooser = new JFileChooser();
        int result = 0;

        switch (action) {

            // convertion to jpg
            case "Konwersja do JPG": {
                if (currentPath == null) {
                    fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
                    result = fileChooser.showOpenDialog(this);
                    setCurrentPath(fileChooser.getSelectedFile().getAbsolutePath());
                }

                try {
                    if (result == JFileChooser.APPROVE_OPTION) {
                        functions.tiffToJpg(currentPath);
                        l.setText("Konwersja zakończona sukcesem!");
                    }
                } catch (Exception ex) {
                    l.setText("Błąd!");
                }
                break;
            }

            // convertion to png
            case "Konwersja do PNG": {

                try {
                    if (currentPath == null) {
                        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
                        result = fileChooser.showOpenDialog(this);
                        setCurrentPath(fileChooser.getSelectedFile().getAbsolutePath());
                    }

                    if (result == JFileChooser.APPROVE_OPTION) {
                        functions.tiffToPng(currentPath);
                        l.setText("Konwersja zakończona sukcesem!");

                    }
                } catch (Exception ex) {
                    l.setText("Błąd!");
                }
                break;
            }

            // resolution check
            case "Sprawdzenie rozdzielczości": {
                try {
                    if (currentPath == null) {
                        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
                        result = fileChooser.showOpenDialog(this);
                        setCurrentPath(fileChooser.getSelectedFile().getAbsolutePath());
                    }
                    if (result == JFileChooser.APPROVE_OPTION) {
                        int width = functions.getWidth(currentPath);
                        int height = functions.getHeight(currentPath);
                        l.setText("Rozdzielczość: " + width + " x " + height);
                    }
                } catch (Exception ex) {
                    l.setText("Błąd!");
                }
                break;
            }

            // internal dimensions check
            case "Sprawdzenie wymiarów": {
                try {
                    if (currentPath == null) {
                        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
                        result = fileChooser.showOpenDialog(this);
                        setCurrentPath(fileChooser.getSelectedFile().getAbsolutePath());
                    }

                    if (result == JFileChooser.APPROVE_OPTION) {
                        l.setText("Sprawdzanie wymiarów");
                        LinkedList<Integer> measurementResult = functions.colorReader(currentPath);
                        int measuredWidth = measurementResult.get(0);
                        int measuredHeight = measurementResult.get(1);
                        DecimalFormat df2 = new DecimalFormat("#.##");
                        double inch = 25.4;
                        double dpi = 2400;

                        System.out.println("Operacja zakończona sukcesem!");
                        l.setText("Wymiary w cm: " + df2.format(((measuredWidth * inch) / dpi) / 10) + " x " + df2.format(((measuredHeight * inch) / dpi) / 10)
                                + " (" + measuredWidth + " x " + measuredHeight + " px)");
                    }
                } catch (Exception ex) {
                    l.setText("Błąd!");
                }
                break;
            }

            // path change
            case "Zmień plik": {
                fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
                result = fileChooser.showOpenDialog(this);
                setCurrentPath(fileChooser.getSelectedFile().getAbsolutePath());
                l.setText(currentPath);
                break;
            }

            // exit
            case "Wyjście": {
                System.exit(0);
            }

            // default
            default: {
                System.out.println("Wrong choose of action");
                break;
            }
        }

    }
}