package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
//import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import main.Shop;
import model.Product;
import utils.Constants;

import java.awt.Font;

public class ProductView extends JDialog implements ActionListener {

    private static final long serialVersionUID = 1L;
    private JTextField productNameField;
    private JTextField stockField;
    private JTextField priceField;
    private Shop shop;
    private int option;
	private JButton okButton;
	private JLabel stockLabel;
	private JLabel priceLabel;


    public ProductView(Shop shop, int option) {
        this.shop = shop;
        this.option = option;
        setTitle(getDialogTitle(option)); // Establecer el titulo de la ventana segun la opcion
        setSize(507, 458);
        getContentPane().setLayout(null);

        // Crear etiquetas y campos de texto para ingresar datos del producto
        JLabel nameLabel = new JLabel("Nombre:");
        nameLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
        nameLabel.setBounds(153, 91, 80, 20);
        getContentPane().add(nameLabel);

        productNameField = new JTextField();
        productNameField.setBounds(153, 121, 210, 20);
        getContentPane().add(productNameField);

        stockLabel = new JLabel("Stock:");
        stockLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
        stockLabel.setBounds(153, 151, 80, 20);
        getContentPane().add(stockLabel);

        stockField = new JTextField();
        stockField.setBounds(153, 181, 210, 20);
        getContentPane().add(stockField);

        priceLabel = new JLabel("Precio público:");
        priceLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
        priceLabel.setBounds(153, 211, 100, 20);
        getContentPane().add(priceLabel);

        priceField = new JTextField();
        priceField.setBounds(153, 241, 210, 20);
        getContentPane().add(priceField);
        

        // Crear boton "OK" para confirmar la operacion
        okButton = new JButton("OK");
        okButton.setBounds(153, 289, 100, 30);
        getContentPane().add(okButton);
        okButton.addActionListener(this); 
        
        JButton cancelButton = new JButton("Cancelar");
        cancelButton.setBounds(263, 289, 100, 30);
        getContentPane().add(cancelButton);
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        
        configureView(option);
        
    }
    
    private void configureView(int option) {
        switch (option) {
            case Constants.OPTION_ADD_PRODUCT:
                priceField.setVisible(true);
                break;
            case Constants.OPTION_ADD_STOCK:
                priceField.setVisible(false);
                priceLabel.setVisible(false);
                break;
            case Constants.OPTION_REMOVE_PRODUCT:
                stockField.setVisible(false);
                stockLabel.setVisible(false);
                priceField.setVisible(false);
                priceLabel.setVisible(false);
                break;
            default:
                break;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // en caso de hacer clic en el botón OK
        if (e.getSource() == okButton) {
            Product product;
            switch (this.option) {
                case Constants.OPTION_ADD_PRODUCT:
                    // verificar que el producto no existe
                    product = shop.findProduct(productNameField.getText());
                    if (product != null) {
                        JOptionPane.showMessageDialog(null, "Producto ya existe ", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        try {
                            // Convertir el valor de Amount a double
                            double wholesalerPrice = Double.parseDouble(priceField.getText());
                            product = new Product(productNameField.getText(), wholesalerPrice, true, Integer.parseInt(stockField.getText()));
                            shop.addProduct(product);
                            JOptionPane.showMessageDialog(null, "Producto añadido ", "Information", JOptionPane.INFORMATION_MESSAGE);
                            // liberar pantalla actual
                            dispose();
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(null, "Formato de número inválido", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    break;

                case Constants.OPTION_ADD_STOCK:
                    // verificar que el producto existe
                    product = shop.findProduct(productNameField.getText());
                    if (product == null) {
                        JOptionPane.showMessageDialog(null, "Producto no existe ", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        try {
                            product.setStock(product.getStock() + Integer.parseInt(stockField.getText()));
                            JOptionPane.showMessageDialog(null, "Stock actualizado ", "Information", JOptionPane.INFORMATION_MESSAGE);
                            // liberar pantalla actual
                            dispose();
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(null, "Formato de número inválido", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    break;

                case Constants.OPTION_REMOVE_PRODUCT:
                    // verificar que el producto existe
                    product = shop.findProduct(productNameField.getText());
                    if (product == null) {
                        JOptionPane.showMessageDialog(null, "Producto no existe ", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        shop.getInventory().remove(product);
                        JOptionPane.showMessageDialog(null, "Producto eliminado", "Information", JOptionPane.INFORMATION_MESSAGE);
                        // liberar pantalla actual
                        dispose();
                    }
                    break;
                    
                // Extra: he intentado pero no me dio tiempo, ERROR
                case Constants.OPTION_LOAD_INVENTORY:
                    break;
                    
                default:
                    JOptionPane.showMessageDialog(this, "Opción no válida.");
                    break;
            }
        } 
    }



    // Añadir un nuevo producto
    private void addProduct(String name, int stock, double price) {
        // Verificar si el producto ya existe
        if (shop.findProduct(name) != null) {
            JOptionPane.showMessageDialog(this, "El producto ya existe", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            // Agregar el nuevo producto al inventario
            shop.addProduct(new Product(name, price, true, stock));
            JOptionPane.showMessageDialog(this, "Producto añadido correctamente", "Informacion", JOptionPane.INFORMATION_MESSAGE);
        }
        dispose(); // cerrar ventana
    }

    // Añadir stock a un producto existente
    private void addStock(String name, int stock) {
        Product product = shop.findProduct(name);
        if (product != null) {
        	// Actualizar
            product.setStock(product.getStock() + stock);
            JOptionPane.showMessageDialog(this, "Stock añadido correctamente", "Informacion", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "El producto no existe", "Error", JOptionPane.ERROR_MESSAGE);
        }
        dispose();
    }

    // Eliminar un producto
    private void removeProduct(String name) {
        Product product = shop.findProduct(name);
        if (product != null) {
        	// Eliminar producto
            shop.removeProduct();
            JOptionPane.showMessageDialog(this, "Producto eliminado correctamente", "Información", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "El producto no existe", "Error", JOptionPane.ERROR_MESSAGE);
        }
        dispose();
    }
    


    // Obtener el titulo de la ventana segun la opcion seleccionada
    private String getDialogTitle(int option) {
        switch (option) {
            case Constants.OPTION_ADD_PRODUCT:
                return "Añadir Producto";
            case Constants.OPTION_ADD_STOCK:
                return "Añadir Stock";
            case Constants.OPTION_REMOVE_PRODUCT:
                return "Eliminar Producto";
                
            // Extra: LOAD_INVENTORY no funciona.    
            case Constants.OPTION_LOAD_INVENTORY:
                return "Cargar Inventario";
            default:
                return "Producto";
        }
    }
}