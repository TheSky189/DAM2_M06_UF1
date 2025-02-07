package dao;

//import exception.LimitLoginException;
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
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/shop?serverTimezone=UTC", "root", "");
            connection.setAutoCommit(true);
            
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
    public List<Product> getInventory() {
        List<Product> inventory = new ArrayList<>();
        //String query = "SELECT id, product, price, stock, available FROM inventory";   THIS IS TEST FOR ERROR
        String query = "SELECT id, name, price, stock, available FROM inventory";
        
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                // leer desde bbdd
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                double price = resultSet.getDouble("price");
                int stock = resultSet.getInt("stock");
                boolean available = resultSet.getBoolean("available");

                // crear producto y añdir a la lista
                Product product = new Product(name, price, available, stock);
                product.setId(id); // configurar id de los productos
                inventory.add(product);
            }
        } catch (SQLException e) {
            System.err.println("Error al cargar inventario desde la base de datos: " + e.getMessage());
            e.printStackTrace();
        }
        return inventory; 
    }

    
    // metodo para escribir el inventario en bbdd
    @Override
    public boolean writeInventory(List<Product> inventory) {
    	String query = "INSERT INTO inventory (name, price, stock, available) VALUES (?, ?, ?, ?) "
                + "ON DUPLICATE KEY UPDATE price = VALUES(price), stock = VALUES(stock), available = VALUES(available)";


        try (PreparedStatement statement = connection.prepareStatement(query)) {
            for (Product product : inventory) {
                statement.setString(1, product.getName()); // asignar nombre producto
                statement.setDouble(2, product.getWholesalerPrice().getValue()); // asignar precio prod.
                statement.setInt(3, product.getStock()); // asignar stock product
                statement.setBoolean(4, product.isAvailable()); // asignar disponibilidad
                statement.addBatch(); // añadir consulta al batch
            }
            statement.executeBatch(); // ejecutar todas las consultas en el batch
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // devolver false en caso de error
        }
        
    }

    
    
    @Override
    public boolean exportInventoryToHistory(List<Product> inventory) {
        String query = "INSERT INTO historical_inventory (id_product, name, wholesalerPrice, stock, available, created_at) VALUES (?, ?, ?, ?, ?, NOW())";
        try {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                for (Product product : inventory) {
                    statement.setInt(1, product.getId());
                    statement.setString(2, product.getName());
                    statement.setDouble(3, product.getWholesalerPrice().getValue());
                    statement.setInt(4, product.getStock());
                    statement.setBoolean(5, product.isAvailable());

                    
                    System.out.println("Adding to batch: " + statement.toString());

                    statement.addBatch();
                }
                int[] results = statement.executeBatch();
                connection.commit();
                System.out.println("Batch executed successfully. Rows affected: " + results.length);
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error during export: " + e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException rollbackEx) {
                System.err.println("Error during rollback: " + rollbackEx.getMessage());
            }
            return false;
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }







    
    @Override
    public boolean addProduct(Product product) {
        String query = "INSERT INTO inventory (name, price, stock, available) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, product.getName());
            statement.setDouble(2, product.getWholesalerPrice().getValue());
            statement.setInt(3, product.getStock());
            statement.setBoolean(4, product.isAvailable());

            // 打印调试信息
            System.out.println("Executing SQL: " + statement.toString());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
            return false;
        }
    }








    @Override
    public boolean addStock(String productName, int additionalStock) {
    	String query = "UPDATE inventory SET stock = stock + ? WHERE name = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, additionalStock);
            statement.setString(2, productName);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    
    @Override
    public boolean deleteProduct(String productName) {
    	String query = "DELETE FROM inventory WHERE name = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, productName);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    

    
}
