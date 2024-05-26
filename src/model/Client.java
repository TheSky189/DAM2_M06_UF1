package model;

import main.Payable;

public class Client extends Person implements Payable {
    public static final int MEMBER_ID = 456;
    public static final double INITIAL_BALANCE = 50.00;

    private int memberId;
    private Amount balance;

    // Constructor
    public Client(String name, int memberId, Amount balance) {
        super(name);
        this.memberId = memberId;
        this.balance = balance;
    }

    // Implementación del método pay de la interfaz Payable
    @Override
    public boolean pay(Amount amount) {
        double newBalance = balance.getValue() - amount.getValue();
        if (newBalance >= 0) {
            balance.setValue(newBalance);
            return true;
        } else {
            return false;
        }
    }

    // Getters y setters
    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }
    
    public Amount getBalance() {
        return balance;
    }
    
    public void setBalance(Amount balance) {
        this.balance = balance;
    }

    // Metodo para establecer un saldo inicial fijo para todos los clientes
    public static Amount getInitialBalance() {
        return new Amount(INITIAL_BALANCE);
    }

}
