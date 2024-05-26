package model;

public abstract class Person {
	protected String name;   // Atributo name protegido
	
	// Constructor
	public Person (String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public boolean pay(Amount amount) {
		// TODO Auto-generated method stub
		return false;
	}
}