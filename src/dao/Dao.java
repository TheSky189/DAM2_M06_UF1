package dao;

import java.sql.SQLException;

import exception.LimitLoginException;
import model.Employee;

public interface Dao {
	// metodo para conectar al base de datos
	public void connect();
	
	// metodo para obetener empleado base su ID y contraseña
	Employee getEmployee (int employeeId, String password);
	
	// desconectar base de datos
	public void disconnect();
	
	
	
}
