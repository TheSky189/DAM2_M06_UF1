package model;

import main.Logable;
import dao.Dao; 
//import dao.DaoImplJDBC;
import dao.DaoImplMongoDB;


public class Employee extends Person implements Logable {
	
	//Define constantes finales USER con valor 123 y PASSWORD con valor "test".
	//private static final int USER = 123;
	//private static final String PASSWORD = "test";
	
	private int employeeId;
	private String password;
	// Connection using JDBC SQL
	//private Dao dao = new DaoImplJDBC();  // Objeto DAO para la conexion a la base de datos
	Dao dao = new DaoImplMongoDB();
	
	//Implementa la interfaz Logable.
	
	//Constructor para inicializar los atributos del empleado
	public Employee (int employeeId, String name, String password) {
		super(name);
		this.employeeId = employeeId;
		this.password = password;  // nuevo
	}
	
	public Employee() {
		super();
	}
	
	
    //GETTERs Y SETTERs
    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }
    
    
    public String getName() {
    	return name;
    }
    
    public void setName(String name) {
    	this.name = name;
    }
    
    // nuevo getter y setter de contraseña
    public String getPassword() {
    	return password;
    }
	
    public void setPassword(String password) {
    	this.password = password;
    }
    
	
	/*devuelve true si el número de usuario y la contraseña coinciden 
	 * con los valores fijos (123, test), y false en caso contrario.*/
    @Override
    public boolean login (int user, String password) {
    	//return (user == USER && password.equals(PASSWORD));
    	
    	// new add
    	//if (USER == user && PASSWORD.equals(password)) {
    		//return true;
    	//}
    	boolean success = false;  // Variable para almacenar el resultado de la autenticacion
    	
    	// conexion a datos
    	dao.connect();
    	
        // Obtener el empleado por ID
        Employee employee = dao.getEmployee(user, password);

    	// get employee datos
        if (employee != null && employee.getPassword().equals(password)) {
            success = true;
        }
    	
    	// desconectar datos
    	dao.disconnect();
    	return success;
    }
    

}
