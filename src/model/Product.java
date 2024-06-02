package model;

//import java.util.Arrays;

public class Product {
	//private int id;
    private String name;
    private double publicPrice;
    private double wholesalerPrice;
    private boolean available;
    private int stock;
    //private static int totalProducts;
    
    static double EXPIRATION_RATE=0.60;
    
    public Product(String name, double wholesalerPrice, boolean available, int stock) {
        this.name = name;
        this.wholesalerPrice = wholesalerPrice;
        this.available = available;
        this.stock = stock;
        this.publicPrice = 2 * wholesalerPrice;  // Calcular precio publico
        expire(); // Calcular precio público con descuento
    }


	
	//public int getId() {
	//	return id;
	//}

	//public void setId(int id) {
	//	this.id = id;
	//}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPublicPrice() {
		return publicPrice;
	}

	public void setPublicPrice(double publicPrice) {
		this.publicPrice = publicPrice;
	}

	public double getWholesalerPrice() {
		return wholesalerPrice;
	}

	public void setWholesalerPrice(double wholesalerPrice) {
		this.wholesalerPrice = wholesalerPrice;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}
	
	public void expire() {
	    this.publicPrice *= (1 - 0.60); // Calcular precio público con descuento
	}

	//public static int getTotalProducts() {
	//	return totalProducts;
	//}

	//public static void setTotalProducts(int totalProducts) {
	//	Product.totalProducts = totalProducts;
	//}
	
    
	@Override
	public String toString() {
	    return "Product [ name=" + name + ", publicPrice=" + publicPrice + ", wholesalerPrice=" + wholesalerPrice
	            + ", available=" + available + stock + "]";
	}

    
}