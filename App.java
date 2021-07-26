import GUI.MainWindow;

import javax.swing.*;

public class App {
    public static void main(String[] args) throws Exception {
        JFrame window = new JFrame();
        MainWindow mainWindow = new MainWindow();
        mainWindow.listWindow();

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setTitle("L I S T A");
        window.pack();
        window.setSize(800, 600);
        window.setResizable(false);
        window.setVisible(true);
        
        window.add(mainWindow);
    }
}