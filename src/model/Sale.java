package model;

//import java.text.SimpleDateFormat;
//import java.util.Arrays;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Sale {
	private Client client; // nuevo modificado
	private ArrayList<Product> products;
	private Amount amount;
    private LocalDateTime dateTime; // Nuevo campo para la fecha y hora de la venta
	
	public Sale(Client client, ArrayList<Product> products, Amount amount, LocalDateTime dateTime) {
		//super();
		this.client = client;
		this.products = products;
		this.amount = amount;
        this.dateTime = dateTime; // Obtiene la fecha y hora actual
	}
	
	
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	public ArrayList<Product> getProducts() {
		return products;
	}
	public void setProducts(ArrayList<Product> products) {
		this.products = products;
	}
	public Amount getAmount() {
		return amount;
	}
	public void setAmount(Amount amount) {
		this.amount = amount;
	}

    // Método para obtener la fecha y hora de la venta en formato dd-MM-yyyy HH:mm:ss
    // Getter para obtener la fecha y hora en formato deseado
    public String getFormattedDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return dateTime.format(formatter);
    }
    
    // Getter y setter para la fecha y hora de la venta
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
	
    @Override
    public String toString() {
        return "Sale{" +
                "client='" + client + '\'' +
                ", products=" + products +
                ", amount=" + amount +
                ", dateTime=" + dateTime +
                '}';
    }

}
