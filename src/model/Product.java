package model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"name", "available", "wholesalerPrice", "publicPrice", "stock"})
public class Product {
	private int id;
    private String name;
    private double publicPrice;
    private double wholesalerPrice;
    private boolean available;
    private int stock;
    //private static int totalProducts;
    
    static double EXPIRATION_RATE=0.60;  // rebaja al 60% por aprx. caducacion
    
    public Product() {}
    
    public Product(String name, double wholesalerPrice, boolean available, int stock) {
        this.name = name;
        this.wholesalerPrice = wholesalerPrice;
        this.available = available;
        this.stock = stock;
        this.publicPrice = 2 * wholesalerPrice;  // Calcular precio publico
        expire(); // aplicar descuento si es necesario
    }


	

    @XmlAttribute(name = "id")
    public int getId() {
    	return id;
    }
    
    public void setId(int id) {
    	this.id = id;
    }
    
    
    @XmlAttribute(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlElement(name = "publicPrice")
	public double getPublicPrice() {
		return publicPrice;
	}

	public void setPublicPrice(double publicPrice) {
		this.publicPrice = publicPrice;
	}

	@XmlElement(name = "wholesalerPrice")
	public double getWholesalerPrice() {
		return wholesalerPrice;
	}

	public void setWholesalerPrice(double wholesalerPrice) {
		this.wholesalerPrice = wholesalerPrice;
	}

	@XmlElement(name = "available")
	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	@XmlElement(name = "stock")
	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}
	
	
	public void expire() {
	    this.publicPrice *= (1 - EXPIRATION_RATE); // aplicar descuento 
	}
	
	// Incluir anotaciones javax.xml.bind
	
	
	
	@Override
	public String toString() {
	    return "Product [ name=" + name + ", publicPrice=" + publicPrice + ", wholesalerPrice=" + wholesalerPrice
	            + ", available=" + available + ", stock=" + stock + "]";
	}

    
}