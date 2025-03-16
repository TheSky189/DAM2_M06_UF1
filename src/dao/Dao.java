package dao;


import model.Employee;
import java.util.List;
import model.Product;

// Modificar po atributo shop.dao como DaoImplJaxb 

public interface Dao {
	// metodo para conectar al base de datos
	public void connect();
	
	// metodo para obetener empleado base su ID y contraseña
	public Employee getEmployee (int employeeId, String password);
	
	// desconectar base de datos
	public void disconnect();
	
	// --- NUEVAS FUNCIONALIDADES PARA INVENTARIO. v5 ---
	
	// metodo para obtener el inventario desde archivo
	public List<Product> getInventory(); // devuelve lista de producto
	
	// metodo para escribir el inventario al archivo
	// devuelve true si esta correcto, false error
	public boolean writeInventory(List<Product> inventory);

	boolean exportInventoryToHistory(List<Product> inventory);

	public void addProduct(Product product);

	public void deleteProduct(String productName);

	public void addStock(String productName, int additionalStock);
	
}
