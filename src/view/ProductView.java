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
                priceLabel.setVisible(true);
                stockField.setVisible(true);
                stockLabel.setVisible(true);
                break;
            case Constants.OPTION_ADD_STOCK:
                priceField.setVisible(false);
                priceLabel.setVisible(false);
                stockField.setVisible(true);
                stockLabel.setVisible(true);
                break;
            case Constants.OPTION_REMOVE_PRODUCT:
                priceField.setVisible(false);
                priceLabel.setVisible(false);
                stockField.setVisible(false);
                stockLabel.setVisible(false);
                break;
            default:
                break;
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == okButton) {
            if (productNameField.getText().isEmpty() || 
                (option != Constants.OPTION_REMOVE_PRODUCT && stockField.getText().isEmpty()) || 
                (option == Constants.OPTION_ADD_PRODUCT && priceField.getText().isEmpty())) {
                JOptionPane.showMessageDialog(this, "Por favor, rellena todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Product product;
            switch (this.option) {
                case Constants.OPTION_ADD_PRODUCT:
                    product = shop.findProduct(productNameField.getText());
                    if (product != null) {
                        JOptionPane.showMessageDialog(null, "Producto ya existe ", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        try {
                            double wholesalerPrice = Double.parseDouble(priceField.getText());
                            System.out.println("Precio: " + wholesalerPrice);
                            int stock = Integer.parseInt(stockField.getText());
                            product = new Product(productNameField.getText(), wholesalerPrice, true, stock);
                            shop.addProduct(product);
                            JOptionPane.showMessageDialog(null, "Producto añadido correctamente.", "Información", JOptionPane.INFORMATION_MESSAGE);
                            dispose();
                            
                            System.out.println(product.getPrice());
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(this, "Formato de número inválido", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    break;

                case Constants.OPTION_ADD_STOCK:
                    product = shop.findProduct(productNameField.getText());
                    if (product == null) {
                        JOptionPane.showMessageDialog(this, "Producto no existe", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        try {
                            int additionalStock = Integer.parseInt(stockField.getText());
                            shop.addStock(productNameField.getText(), additionalStock); // Llamar directamente
                            product.setStock(product.getStock() + additionalStock);
                            JOptionPane.showMessageDialog(this, "Stock añadido correctamente.", "Información", JOptionPane.INFORMATION_MESSAGE);
                            dispose();
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(this, "Formato de número inválido", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    break;


                case Constants.OPTION_REMOVE_PRODUCT:
                    product = shop.findProduct(productNameField.getText());
                    if (product == null) {
                        JOptionPane.showMessageDialog(this, "Producto no existe", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        shop.removeProduct(productNameField.getText()); // Llamar sin condición
                        shop.getInventory().remove(product);
                        JOptionPane.showMessageDialog(this, "Producto eliminado correctamente.", "Información", JOptionPane.INFORMATION_MESSAGE);
                        dispose();
                    }
                    break;



                default:
                    JOptionPane.showMessageDialog(this, "Opción no válida.", "Error", JOptionPane.ERROR_MESSAGE);
                    break;
            }
        }
    }




    // Añadir un nuevo producto
    private void addProduct(String name,double wholesalerPrice, int stock) {
        // Verificar si el producto ya existe
        if (shop.findProduct(name) != null) {
            JOptionPane.showMessageDialog(this, "El producto ya existe", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            // Agregar el nuevo producto al inventario
            shop.addProduct(new Product(name, wholesalerPrice, true, stock));
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
            shop.getInventory().remove(product);  // Eliminar producto del inventario
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