package dao;

import exception.LimitLoginException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import model.Employee;

public class DaoImplJDBC implements Dao {
    private Connection connection;


    // metodo para conectar al base de datos
    @Override
    public void connect() {
        try {
        	// cargar el driver JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");
            // establecer coneccion
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/shop", "root", "");
        } catch (ClassNotFoundException | SQLException e) {
        	// imprimir exception en caso de error
            e.printStackTrace();
        }
    }

    @Override
    public Employee getEmployee(int employeeId, String password) {
        Employee employee = null; // inicializar el objeto Emplpyee
        String query = "SELECT * FROM employee WHERE employeeId = ? AND password = ?";

        try (
            PreparedStatement statement = connection.prepareStatement(query)){
            statement.setInt(1, employeeId);
            statement.setString(2, password);
            try (ResultSet resultSet = statement.executeQuery()) {
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

    @Override
    public void disconnect() {
        if (connection != null) {
            try {
            	// cerrar sesion
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
