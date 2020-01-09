package main;

import javax.swing.*;

public class App {

    public App(){

        SwingUtilities.invokeLater(() -> {
            CustomerFrame mf = new CustomerFrame(); //Initializes JFrame
        });

    }

    public static void main(String[] args){
        App invoice = new App();
    }

}
