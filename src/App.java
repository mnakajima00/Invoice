import javax.swing.*;

public class App {

    public App(){

        SwingUtilities.invokeLater(() -> { //Threads: Read about SwingUtilities.invokeLater
            MainFrame mf = new MainFrame(); //Initializes JFrame
        });

    }

    public static void main(String[] args){
        App invoice = new App();
    }

}
