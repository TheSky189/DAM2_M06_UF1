package model;

//import javax.xml.bind.annotation.XmlAttribute;
//import javax.xml.bind.annotation.XmlElement;
//import javax.xml.bind.annotation.XmlType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import javax.xml.bind.annotation.XmlRootElement;


//@XmlType(propOrder = {"name", "available", "wholesalerPrice", "publicPrice", "stock"})
@XmlRootElement(name = "product")
@XmlType(propOrder = {"available", "wholesalerPrice", "publicPrice", "stock"})


@Entity
@Table(name = "inventory")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
	private int id;
    @Column 
    private String name;
    @Column
    private double price;

	@Transient
    private Amount publicPrice;
    @Transient
    private Amount wholesalerPrice;
    
    @Column
    private boolean available;
    @Column 
    private int stock;
    private static int totalProducts = 0;
    
    static double EXPIRATION_RATE=0.60;  // rebaja al 60% por aprx. caducacion
    
    public Product() {}
    
    public Product(String name, double wholesalerPrice, boolean available, int stock) {
    	super();
    	this.id = ++totalProducts;
        this.name = name;
        this.wholesalerPrice = new Amount(wholesalerPrice);
        this.price = wholesalerPrice;
        this.available = available;
        this.stock = stock;
        this.publicPrice = new Amount (2 * wholesalerPrice);  // Calcular precio publico
        expire(); // aplicar descuento si es necesario
    }

    public double getPrice() {
    	return price;
    }
    
    public void setPrice(double price) {
    	this.price = price;
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
	public Amount getPublicPrice() {
		return publicPrice;
	}

	public void setPublicPrice(Amount publicPrice) {
		this.publicPrice = publicPrice;
	}

	@XmlElement(name = "wholesalerPrice")
		public Amount getWholesalerPrice() {
		return wholesalerPrice;
	}

	public void setWholesalerPrice(Amount wholesalerPrice) {
		this.publicPrice = new Amount (wholesalerPrice.getValue()*2 );
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
		if (stock > 0) {
			this.available = true;
		}
	}
	
	public static int getTotalProducts() {
		return totalProducts;
	}

	public static void setTotalProducts(int totalProducts) {
		Product.totalProducts = totalProducts;
	}

	
	public void expire() {
	    this.publicPrice.setValue(this.getPublicPrice().getValue() * EXPIRATION_RATE);// aplicar descuento 
	}
	
	// Incluir anotaciones javax.xml.bind
	
	
	
	@Override
	public String toString() {
	    return "Product [ name=" + name + ", publicPrice=" + publicPrice + ", wholesalerPrice=" + wholesalerPrice
	            + ", available=" + available + ", stock=" + stock + "]";
	}

    
}