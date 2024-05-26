package view;

import javax.swing.JDialog;
import javax.swing.JTextField;

import main.Shop;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import java.awt.Font;

public class CashView extends JDialog {

    private static final long serialVersionUID = 1L;

    private JTextField cashField;
    private final JLabel lblNewLabel = new JLabel("");

    public CashView(Shop shop) {
        setTitle("Contar caja");
        setSize(300, 196);
        getContentPane().setLayout(null);

        cashField = new JTextField();
        cashField.setHorizontalAlignment(SwingConstants.CENTER);
        cashField.setBounds(42, 74, 200, 30);
        cashField.setEditable(false);
        cashField.setText(String.valueOf(shop.cash.getValue() + "€")); // Assuming shop is accessible
        
        getContentPane().add(cashField);
        
        JLabel lblDineroCaja = new JLabel("Dinero en caja: ");
        lblDineroCaja.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblDineroCaja.setBounds(42, 51, 129, 16);
        getContentPane().add(lblDineroCaja);
        lblNewLabel.setBounds(0, 0, 286, 159);
        getContentPane().add(lblNewLabel);
        
        

        setVisible(true);
    }
}
