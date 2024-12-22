package model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="products")
public class ProductList {
	private int total; 
	
	private ArrayList<Product> products = new ArrayList<>();  // Initialize products	
	  
	public ProductList() {};	
	
	// para inicializar con una lista de productos
	public ProductList(List<Product> inventory) {
		this.products = new ArrayList<>(inventory); // Convierte List a ArrayList internamente
		this.total = inventory.size();
	}

	// para establecer la lista de productos y actualizar el total
	public void setProductList(ArrayList<Product> products) {
		this.products = products;
	    this.updateTotal(); // Actualizar el total automaticamente
	}

	
    // para obtener la lista de productos
    @XmlElement(name = "product")
    public ArrayList<Product> getProducts() {
        return products;
    }

    //  para obtener numero total de productos
    @XmlAttribute(name = "total")
    public int getTotal() {
        return total;
    }

    // para actualizar el total si se modifica la lista externamente
    public void updateTotal() {
        this.total = this.products.size();
    }
	
}