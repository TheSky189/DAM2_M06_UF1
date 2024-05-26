package exception;

public class LimitLoginException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LimitLoginException() {
		super("Se ha alcanzado el numero maximo intentos.");
	}
	
}
