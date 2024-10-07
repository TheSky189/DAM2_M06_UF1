package dao;

import exception.LimitLoginException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Employee;
import model.Product;

public class DaoImplJDBC implements Dao {
    private Connection connection;


    // metodo para conectar al base de datos
    @Override
    public void connect() {
        try {
        	// cargar el driver JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");
            // establecer coneccion con bbdd
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/shop", "root", "");
        } catch (ClassNotFoundException | SQLException e) {
        	// imprimir exception en caso de error
            e.printStackTrace();
        }
    }

    // metodo para obtener empleado por su id y contraseña
    @Override
    public Employee getEmployee(int employeeId, String password) {
        Employee employee = null; // inicializar el objeto Emplpyee
        String query = "SELECT * FROM employee WHERE employeeId = ? AND password = ?";

        try (
        	// preparar para la consulta SQL
            PreparedStatement statement = connection.prepareStatement(query)){
            statement.setInt(1, employeeId);  // asignar valor ID empleado
            statement.setString(2, password);  // asignar valor contraseña 
            try (
            		ResultSet resultSet = statement.executeQuery()) {
                	// Si hay resultado, crear nuevo objeto Employee con los datos obtenidos
            		if (resultSet.next()) {
            		employee = new Employee(resultSet.getInt("employeeId"), resultSet.getString("name"), resultSet.getString("password"));
            	}
            }
        } catch (SQLException e) {
        	// En caso error en SQL
            e.printStackTrace();
        }
        return employee; // Devolver el objeto Employee o null si no se encuentra
    }

    // metodo para desconectar bbdd
    @Override
    public void disconnect() {
        if (connection != null) {
            try {
            	// cerrar sesion si no es nula
                connection.close();
            } catch (SQLException e) {
            	// en caso error
                e.printStackTrace();
            }
        }
    }
    
    // --- NUEVOS METODOS GESTION INVENTARIO ---
    
    // metodo para obtener inventario desde bbdd
    @Override
    public List<Product> getInventory(){
    	List<Product> inventory = new ArrayList<>();  // inicializar la lista de productos
        String query = "SELECT * FROM product";  // consultar sql para tener todos productos
        
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
        	// recorrer resultado consulta y agregar productos a la lista
            while (resultSet.next()) {
                String productName = resultSet.getString("productName");  // obtener nombre producto
                double price = resultSet.getDouble("price");  // obtener precio
                boolean available = resultSet.getBoolean("available"); // obtener disponibilidad
                int stock = resultSet.getInt("stock");  // obtener stock
                
                // Crear nuevo objeto producto 
                Product product = new Product(productName, price, available, stock);  
                // añadir producto al lista
                inventory.add(product);
            }
        } catch (SQLException e) {
        	// caso error
            e.printStackTrace();
        }
        return inventory;  // devolver la lista producto
    }
    
    // metodo para escribir el inventario en bbdd
    @Override
    public boolean writeInventory(List<Product> inventory) {
        String query = "INSERT INTO product (productName, price, stock) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE price = VALUES(price), stock = VALUES(stock)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            for (Product product : inventory) {
                statement.setString(1, product.getName()); // asignar nombre producto
                statement.setDouble(2, product.getWholesalerPrice());  // asignar precio prod.
                statement.setInt(3, product.getStock());  // asignar stock prodct.
                statement.addBatch();  // añadir consulta al batch
            }
            // se usa batch cuando tiene muchas operaciones repetitivas
            statement.executeBatch();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;  // devolver false en caso error
        }
    }
    
}
