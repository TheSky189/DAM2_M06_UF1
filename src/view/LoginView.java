package view;

import model.Employee;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import exception.LimitLoginException;

import javax.swing.JButton;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
//import java.awt.SystemColor;
//import javax.swing.ImageIcon;

public class LoginView extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;
	//private JButton btnAcceder;

	// limiteLogin
	private int loginAttempts = 0;
	private static final int MAX_LOGIN_ATTEMPTS = 3;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginView frame = new LoginView();
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
	/**
	 * Create the frame.
	 */
	public LoginView() {
		String imgDirectory = System.getProperty("user.dir") + File.separator + "images";
		setIconImage(Toolkit.getDefaultToolkit().getImage(imgDirectory + File.separator + "tienda-removebg-preview (1).png"));
		setTitle("LOGIN");
		setBackground(new Color(0, 0, 102));
		setForeground(new Color(0, 0, 102));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(200, 200, 650, 400);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setToolTipText("");
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblLogin = new JLabel("LOGIN");
		lblLogin.setForeground(new Color(102, 255, 255));
		lblLogin.setFont(new Font("Tahoma", Font.BOLD, 26));
		lblLogin.setBounds(270, 91, 92, 42);
		contentPane.add(lblLogin);
		
		JLabel lblEmpleado = new JLabel("Numero de empleado:");
		lblEmpleado.setForeground(new Color(102, 255, 255));
		lblEmpleado.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblEmpleado.setBounds(233, 164, 160, 17);
		contentPane.add(lblEmpleado);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setForeground(new Color(102, 255, 255));
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblPassword.setBounds(272, 220, 75, 17);
		contentPane.add(lblPassword);
		
		textField = new JTextField();
		textField.setBounds(220, 191, 186, 19);
		contentPane.add(textField);
		textField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(220, 247, 186, 19);
		contentPane.add(passwordField);
		
		JButton btnAcceder = new JButton("Acceder");
		btnAcceder.setBackground(new Color(51, 102, 204));
		btnAcceder.setForeground(new Color(102, 255, 255));
		btnAcceder.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnAcceder.setBounds(270, 287, 92, 21);
		contentPane.add(btnAcceder);
        btnAcceder.addActionListener(this);
		
		
		JLabel lblWallpaper = new JLabel("");
		lblWallpaper.setBackground(new Color(0, 0, 102));
		lblWallpaper.setHorizontalAlignment(SwingConstants.CENTER);
		
		// Conseguir ruta directorio del wa
		lblWallpaper.setIcon(new ImageIcon(imgDirectory + File.separator + "desktop-wallpaper-3d-science-earth-science.jpg"));
		lblWallpaper.setBounds(0, 0, 636, 363);
		contentPane.add(lblWallpaper);
		
        
			
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
        String username = textField.getText();
        String password = new String(passwordField.getPassword());
        
        Employee employee = new Employee(username, Integer.parseInt(username)); // Crear empleado con el nombre como ID
        
        // intentar iniciar sesion
        boolean loginSuccessful = employee.login(Integer.parseInt(username), password); // Intentar iniciar sesión

        if (loginSuccessful) {
            // Abrir la ventana ShopView si el inicio de sesion es true
            ShopView shopView = new ShopView();
            shopView.setVisible(true);
            this.dispose(); // Cerrar la ventana de inicio de sesion
        } else {
            // Mostrar un mensaje de error si el inicio de sesion falla
            JOptionPane.showMessageDialog(this, "Error de inicio de sesión", "Error", JOptionPane.ERROR_MESSAGE);
            
            // se suma los incorrectos
            loginAttempts++;
            if (loginAttempts >= MAX_LOGIN_ATTEMPTS) {
            	try {
            		throw new LimitLoginException();
            	} catch (LimitLoginException ex) {
            		JOptionPane.showMessageDialog(this,ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            		dispose(); // Cerrar aplicacion
            	}
            } else {
            	// mostrar mensaje error
            	JOptionPane.showMessageDialog(this, "Error inicio sesion. Intento " + loginAttempts + " de " + MAX_LOGIN_ATTEMPTS, "Error", JOptionPane.ERROR_MESSAGE);
            }
            
            // resetear
            textField.setText("");
            passwordField.setText("");
        }
    }		


	
	
}



