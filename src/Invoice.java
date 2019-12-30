import javax.swing.*;

public class Invoice {

    public Invoice(){

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Invoice");

            frame.setSize(500,500);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });

    }

    public static void main(String[] args){
        Invoice invoice = new Invoice();
    }

}
