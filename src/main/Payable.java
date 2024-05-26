package main;

import model.Amount;

public interface Payable {
	// metodo pay, objeto amount como entrada y devuelve un booleano
	boolean pay (Amount amount);

}
