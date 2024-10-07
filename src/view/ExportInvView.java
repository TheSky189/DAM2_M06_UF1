package view;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import model.Product;
import main.Shop;

public class ExportInvView extends JDialog {

    private static final long serialVersionUID = 1L;
    private Shop shop;

    public ExportInvView(Shop shop, File file) {
        this.shop = shop;
        setTitle("Inventario Exportado");
        setSize(500, 400);
        setLayout(new BorderLayout());

        // Crear JTextArea para mostrar el contenido del archivo
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);  // No editable
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));  // Usar fuente monospaciada

        // Leer el contenido del archivo y mostrarlo en el JTextArea
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            StringBuilder content = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");  // Añadir cada línea del archivo al StringBuilder
            }
            textArea.setText(content.toString());  // Establecer el contenido en el JTextArea
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al leer el archivo exportado.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        // Añadir el JTextArea a un JScrollPane para hacerlo scrollable
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);

        // Panel inferior con botón para cerrar
        JPanel buttonPanel = new JPanel();
        JButton closeButton = new JButton("Cerrar");
        closeButton.addActionListener(e -> dispose());  // Cerrar el diálogo
        buttonPanel.add(closeButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Hacer visible el diálogo
        setVisible(true);
    }
}
