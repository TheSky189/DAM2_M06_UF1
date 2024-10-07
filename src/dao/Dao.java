package dao;

import java.sql.SQLException;

import exception.LimitLoginException;
import model.Employee;
import java.util.List;
import model.Product;



public interface Dao {
	// metodo para conectar al base de datos
	public void connect();
	
	// metodo para obetener empleado base su ID y contraseña
	Employee getEmployee (int employeeId, String password);
	
	// desconectar base de datos
	public void disconnect();
	
	// --- NUEVAS FUNCIONALIDADES PARA INVENTARIO. v5 ---
	
	// metodo para obtener el inventario desde archivo
	List<Product> getInventory(); // devuelve lista de producto
	
	// metodo para escribir el inventario al archivo
	// devuelve true si esta correcto, false error
	boolean writeInventory(List<Product> inventory);
	
}
