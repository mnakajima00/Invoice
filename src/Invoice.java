import sun.applet.Main;

import javax.swing.*;

public class Invoice {

    public Invoice(){

        SwingUtilities.invokeLater(() -> { //Threads: Read about SwingUtilities.invokeLater
            MainFrame mf = new MainFrame(); //Initializes JFrame
        });

    }

    public static void main(String[] args){
        Invoice invoice = new Invoice();
    }

}
