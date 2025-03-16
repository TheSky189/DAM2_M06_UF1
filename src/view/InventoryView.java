// InventoryView.java
package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import main.Shop;
import model.Product;
import java.util.List;

public class InventoryView extends JDialog {

    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
    private Shop shop;  
    
    public InventoryView(Shop shop) {
        this.shop = shop;  
        setTitle("Inventario Cargado");
        setBounds(100, 100, 500, 400);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(new BorderLayout(0, 0));  // UsaR BorderLayout para el JTextArea

        // Crear JTextArea para mostrar el inventario
        JTextArea inventoryTextArea = new JTextArea();
        inventoryTextArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        inventoryTextArea.setEditable(false);  // No editable

        // Llenar el JTextArea con el inventario
        StringBuilder inventoryText = new StringBuilder();
        List<Product> inventory = shop.getInventory();  // Obtener el inventario de Shop
        if (inventory != null && !inventory.isEmpty()) {
            for (Product product : inventory) {
                inventoryText.append("Nombre: ").append(product.getName())
                             .append(" | Stock: ").append(product.getStock())
                             .append(" | Precio: ").append(product.getWholesalerPrice()).append("\n");
            }
        } else {
            inventoryText.append("No hay productos en el inventario.");
        }

        // Establecer el texto del inventario en el JTextArea
        inventoryTextArea.setText(inventoryText.toString());

        // Añadir el JTextArea a un JScrollPane
        JScrollPane scrollPane = new JScrollPane(inventoryTextArea);
        contentPanel.add(scrollPane, BorderLayout.CENTER);  // Añadir JScrollPane al panel

        // Crear panel de botones en la parte inferior
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        JButton okButton = new JButton("OK");
        okButton.setActionCommand("OK");
        okButton.addActionListener(e -> dispose());  // Cerrar la ventana al presionar OK
        buttonPane.add(okButton);
        getRootPane().setDefaultButton(okButton);
    }
}
