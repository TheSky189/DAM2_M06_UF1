package model;

import main.Logable;

public class Employee extends Person implements Logable {
	private int employeeId;
	
	//Define constantes finales USER con valor 123 y PASSWORD con valor "test".
	private static final int USER = 123;
	private static final String PASSWORD = "test";
	
	//Implementa la interfaz Logable.

	//Constructor
	public Employee (String name, int employeeId) {
		super(name);
		this.employeeId = employeeId;
	}
	
	/*devuelve true si el número de usuario y la contraseña coinciden 
	 * con los valores fijos (123, test), y false en caso contrario.*/
    @Override
    public boolean login (int user, String password) {
    	return (user == USER && password.equals(PASSWORD));
    }
    
    //GETTER Y SETTER
    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

}
