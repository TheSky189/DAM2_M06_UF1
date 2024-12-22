package view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import main.Shop;
import utils.Constants;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.ImageIcon;

public class ShopView extends JFrame implements ActionListener, KeyListener{

	
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Shop shop;
	private JButton btnContarCaja;
	private JButton btnAnadirProducto;
	private JButton btnAnadirStock;
	private JButton btnEliminarProducto;
	private JButton btnLoadInventory;  // Extra
	private JButton btnExportInventory;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ShopView frame = new ShopView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ShopView() {
		setTitle("MENU");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(300, 300, 750, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		// create shop
		shop = new Shop();
		shop.loadInventory();
		
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblMenu = new JLabel("Seleccione o pulse una opción:");
		lblMenu.setForeground(new Color(51, 255, 255));
		lblMenu.setHorizontalAlignment(SwingConstants.CENTER);
		lblMenu.setFont(new Font("Tahoma", Font.BOLD, 21));
		lblMenu.setBounds(200, 87, 343, 48);
		contentPane.add(lblMenu);
		
        // Añadir boton "0. Exportar Inventario"
        btnExportInventory = new JButton("0. Exportar inventario");
        btnExportInventory.setForeground(new Color(51, 255, 255));
        btnExportInventory.setBackground(new Color(51, 102, 204));
        btnExportInventory.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnExportInventory.setBounds(103, 166, 249, 42);
        contentPane.add(btnExportInventory);
        btnExportInventory.addActionListener(this);

        // Eventos del teclado
        contentPane.requestFocusInWindow();
        contentPane.addKeyListener(this);
        this.addKeyListener(this);
        this.setFocusable(true);
		
		btnContarCaja = new JButton("1. Contar caja");
		btnContarCaja.setForeground(new Color(102, 255, 255));
		btnContarCaja.setBackground(new Color(51, 102, 204));
		btnContarCaja.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnContarCaja.setBounds(391, 166, 249, 42);
		contentPane.add(btnContarCaja);
        btnContarCaja.addActionListener(this);
        
		
		btnAnadirProducto = new JButton("2. Añadir producto");
		btnAnadirProducto.setBackground(new Color(0, 153, 255));
		btnAnadirProducto.setForeground(new Color(51, 255, 255));
		btnAnadirProducto.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnAnadirProducto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnAnadirProducto.setBounds(103, 232, 249, 42);
		contentPane.add(btnAnadirProducto);
        btnAnadirProducto.addActionListener(this);

		
		btnAnadirStock = new JButton("3. Añadir stock");
		btnAnadirStock.setBackground(new Color(51, 153, 204));
		btnAnadirStock.setForeground(new Color(102, 255, 255));
		btnAnadirStock.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnAnadirStock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnAnadirStock.setBounds(391, 232, 249, 42);
		contentPane.add(btnAnadirStock);
        btnAnadirStock.addActionListener(this);

		
		btnEliminarProducto = new JButton("9. Eliminar producto");
		btnEliminarProducto.setForeground(new Color(51, 255, 255));
		btnEliminarProducto.setBackground(new Color(51, 102, 153));
		btnEliminarProducto.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnEliminarProducto.setBounds(254, 368, 249, 42);
		contentPane.add(btnEliminarProducto);
        btnEliminarProducto.addActionListener(this);
        
        
        // Extra LoadInventory NO ACABADO.ERROR
        btnLoadInventory = new JButton("5. Cargar inventario");
        btnLoadInventory.setForeground(new Color(51, 255, 255));
        btnLoadInventory.setBackground(new Color(51, 102, 153));
        btnLoadInventory.setFont(new Font("Tahoma", Font.BOLD, 11));
        btnLoadInventory.setBounds(103, 300, 249, 42);
        contentPane.add(btnLoadInventory);
        btnLoadInventory.addActionListener(this);

        
		
		JLabel lblWallpaper = new JLabel("");
		// Conseguir ruta directorio del wallpaper
		String imgDirectory = System.getProperty("user.dir") + File.separator + "images";
		lblWallpaper.setIcon(new ImageIcon(imgDirectory + File.separator + "desktop-wallpaper-3d-science-earth-science.jpg"));
		
		lblWallpaper.setHorizontalAlignment(SwingConstants.CENTER);
		lblWallpaper.setBackground(new Color(0, 0, 102));
		lblWallpaper.setBounds(0, 0, 736, 463);
		contentPane.add(lblWallpaper);
		
        // Eventos del teclado
		contentPane.requestFocusInWindow();
		contentPane.addKeyListener(this);
		
        // listen al frame
        //addKeyListener(this);
        //setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        this.addKeyListener(this); // la propia ventana frame se observara a si misma si alguien da una tecla si tiene el foco, el cursor
        this.setFocusable(true); // hacemos que la ventana pueda tener el foco para recibir el evento keypressed
        
		
	}
	
	// Manejar acciones de botones

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
        if (e.getSource() == btnExportInventory) {
            this.exportInventory(); // Exportar inventario y abrir vista de exportacion
        }
		if (e.getSource() == btnContarCaja) {
			this.openCashView();
		} 
		if (e.getSource() == btnAnadirProducto) {
			this.openProductView(Constants.OPTION_ADD_PRODUCT);
		}
		if (e.getSource() == btnAnadirStock) {
			this.openProductView(Constants.OPTION_ADD_STOCK);
		}
		if (e.getSource() == btnEliminarProducto) {
			this.openProductView(Constants.OPTION_REMOVE_PRODUCT);
		}
        if (e.getSource() == btnLoadInventory) {  // Extra no acabado.ERROR
            this.loadInventory();
        }
		
}
	
    public void openCashView() {
        // Crear dialog Box
    	//CashView dialog = new CashView(shop);
    	
    	// Resetear visibilidad del dialog
    	//dialog.setVisible(true);
        new CashView(shop).setVisible(true);  // nuevo metodo mas sencillo
    }

    private void openProductView(int option) {
    	//ProductView dialog = new ProductView(shop, option);
    	//dialog.setVisible(true);
        new ProductView(shop, option).setVisible(true);
    }
    
    private void loadInventory() {
        // Cargar el inventario desde el archivo en Shop
        shop.readInventory();
        
        // Crear una nueva ventana InventoryView y pasar el objeto Shop con el inventario cargado
        InventoryView inventoryView = new InventoryView(shop);
        inventoryView.setVisible(true);  // Mostrar la ventana con el inventario
    }


    // Metodo para exportar el inventario
    private void exportInventory() {
    	
        if (shop.getInventory() != null && !shop.getInventory().isEmpty()) {
            new ExportInvView(shop.getInventory());
        } else {
            JOptionPane.showMessageDialog(this, "No hay productos en el inventario.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    	
//        boolean result = shop.writeInventory();  // Exportar el inventario
//        File exportFile = new File("files/outputInventory.txt");  // Archivo exportado
//
//        if (result) {
//            JOptionPane.showMessageDialog(this, "Inventario exportado correctamente!");
//
//            // Mostrar el archivo exportado en la nueva vista
//            //new ExportInvView(shop, exportFile);  // Abre la nueva ventana con el contenido del archivo
//            new ExportInvView(shop.getInventory());
//            
//        } else {
//            JOptionPane.showMessageDialog(this, "Error al exportar el inventario.", "Error", JOptionPane.ERROR_MESSAGE);
//        }
    }

    
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
        switch (e.getKeyCode()) {
        case KeyEvent.VK_0:
            exportInventory();
            break;
        case KeyEvent.VK_1:
            openCashView();
            break;
        case KeyEvent.VK_2:
            openProductView(Constants.OPTION_ADD_PRODUCT);
            break;
        case KeyEvent.VK_3:
            openProductView(Constants.OPTION_ADD_STOCK);
            break;
        case KeyEvent.VK_5: 
            loadInventory();
            break;
        case KeyEvent.VK_9:
            openProductView(Constants.OPTION_ADD_STOCK);
            break;
        default:
            break;
    }
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
