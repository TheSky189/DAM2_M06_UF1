package model;

public class Amount {
    private double value;
    private String currency = "€"; // Valor fijo
	private double multiplier;

    public Amount(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        return value + " " + currency;
    }

	public Amount multiply(int i) {
		// TODO Auto-generated method stub
        return new Amount(this.value * multiplier);
	}
}
