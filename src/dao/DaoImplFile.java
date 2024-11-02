package dao;

import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.time.format.DateTimeFormatter;

import model.Employee;
import model.Product;
/*
public class DaoImplFile implements Dao {
	
	// metodo para obtener inventario desde archivo
    @Override
    public List<Product> getInventory() {
        List<Product> inventory = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("files/inputInventory.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(";");  // Dividir la linea por el delimitador ";"
                
                // Extraer los campos del producto
                if (data.length >=3) {
                String productName = data[0].split(":")[1];  // Nombre del producto
                double price = Double.parseDouble(data[1].split(":")[1]);  // Precio mayorista
                int stock = Integer.parseInt(data[2].split(":")[1]);  // Stock del producto
                boolean available = Boolean.parseBoolean(data[2].split(":")[1]);  // Disponibilidad del producto
                Product product = new Product(productName, price, available, stock);

                // Añadir el producto a la lista de inventario
                inventory.add(product);
            }else {
            	System.out.println("Linea malformada: " + line);
            }
          }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inventory;  // Devolver la lista de productos
    }
    
    // metodo para escribir el inventario en archivo
    @Override
    public boolean writeInventory(List<Product> inventory) {
        try {
        	
        	// generar archivo con formato inventory_yyyy-mm-dd.txt
        	String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        	FileWriter writer = new FileWriter("files/inventory_" + date + ".txt");
        	
        	// escribir los datos del inventario al archivo
        	int count = 0;
        	for (Product product : inventory) {
        		count++;
        		writer.write(count + ", Product:" + product.getName() + ",Stock:" + product.getStock() + ";\n");
        	}
        	writer.write("Numero total de productos:" + count + ";\n");
        	
        	writer.close();
         
            return true;  // Devolver true si todo sale bien
        } catch (IOException e) {
            e.printStackTrace();
            return false;  // Devolver false en caso expection
        }
    }

    // M3todos de la interface sin implementacion
    @Override
    public Employee getEmployee(int employeeId, String password) {
        throw new UnsupportedOperationException("Operación no soportada en DaoImplFile.");
    }

    @Override
    public void connect() {
        // No se requiere implementacion para archivos
    }

    @Override
    public void disconnect() {
        // No se requiere implementacion para archivos
    }
   
    
}
*/
