package dao;

import model.Employee;

public interface Dao {
	// metodo para conectar al base de datos
	void connect();
	
	// metodo para obetener empleado base su ID y contraseña
	Employee getEmployee (int employeeId, String password);
	
	// desconectar base de datos
	void disconnect();
	
}
