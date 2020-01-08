import javax.swing.*;

public class App {

    public App(){

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainFrame mf = new MainFrame(); //Initializes JFrame
            }
        });

    }

    public static void main(String[] args){
        App invoice = new App();
    }

}
