package view;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import model.Product;

public class ExportInvView extends JDialog {

    private static final long serialVersionUID = 1L;

    public ExportInvView(List<Product> inventory) {
        setTitle("Inventario Exportado");
        setSize(600, 400);
        setLayout(new BorderLayout());

        // Crear JTextArea para mostrar la lista de productos
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false); // No editable
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12)); // Fuente monoespaciada

        // Construir el contenido del inventario
        StringBuilder content = new StringBuilder();
        for (Product product : inventory) {
            content.append("ID: ").append(product.getId())
                    .append(", Nombre: ").append(product.getName())
                    .append(", Precio: ").append(product.getWholesalerPrice())
                    .append(", Stock: ").append(product.getStock())
                    .append(", Disponible: ").append(product.isAvailable())
                    .append("\n");
        }
        textArea.setText(content.toString());

        // Añadir el JTextArea a un JScrollPane
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);

        // Panel inferior con botón para cerrar
        JPanel buttonPanel = new JPanel();
        JButton closeButton = new JButton("Cerrar");
        closeButton.addActionListener(e -> dispose()); // Cerrar el diálogo
        buttonPanel.add(closeButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Hacer visible el diálogo
        setVisible(true);
    }
}
