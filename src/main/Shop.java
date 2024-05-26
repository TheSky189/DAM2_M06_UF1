package main;

import model.Amount;
import model.Client;
import model.Employee;
import model.Product;
import model.Sale;

import java.util.ArrayList;  // nuevo agregado
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

/*Muy buen trabajo Jiajao, 
 * a pesar de la ausencia en clase entregaste una gran practica. 
 * 1. Sale no usa ArrayList products, 
 * 3. Mejor pasar Date al constructor Sale 
 * 5. Linea extra, no hay datos productos                      <-  UF3_P1      
*/    


public class Shop {
	public Amount cash = new Amount(100.00);
	//private Product[] inventory; modifado 
    private ArrayList<Product> inventory;
	//private int numberProducts;
	//private Sale[] sales; modificado
	private ArrayList<Sale> sales;
	

	final static double TAX_RATE = 1.04;

	public Shop() {
	    //inventory = new Product[10];
	    //sales = new Sale[10]; // Añade esta línea para inicializar el array sales
        inventory = new ArrayList<>();
        sales = new ArrayList<>();
	}

    public ArrayList<Product> getInventory() {
        return inventory;
    }
    
    public ArrayList<Sale> getSales() {
        return sales;
    }
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		Shop shop = new Shop();
		shop.loadInventory();
		
		// Iniciar sesion
		if (shop.initSession(scanner)){
			//si la sesion se inicia correctamente, muestra el menu principal
			shop.showMainMenu(scanner);
		}else {
			System.out.println("Error: Datos de inicio de sesion incorrecto.");
			}

		}
		
		// Metodo para iniciar sesion
	public boolean initSession(Scanner scanner) {
		System.out.print("Numero de empleado: ");
		int employeeId = scanner.nextInt();
		System.out.println("Contraseña: ");
		String password = scanner.next();
		
		Employee employee = new Employee("test", employeeId); // nombre de empleado fijo "test" temporal
		return employee.login(employeeId, password);
		
	    }
		
		//metodo para mostrar el menu principal
	public void showMainMenu(Scanner scanner) {
		int opcion;
		boolean exit = false;

		do {
			
			System.out.println("\n===========================");
			System.out.println("Menu principal miTienda.com");
			System.out.println("===========================");
			System.out.println("1) Contar caja");
			System.out.println("2) Añadir producto");
			System.out.println("3) Añadir stock");
			System.out.println("4) Marcar producto proxima caducidad");
			System.out.println("5) Ver inventario");
			System.out.println("6) Venta");
			System.out.println("7) Ver ventas");
			System.out.println("8) Ver ventas total");
			System.out.println("9) Eliminar producto");
			System.out.println("10) Salir programa");
			System.out.print("Seleccione una opción: ");
			opcion = scanner.nextInt();

			switch (opcion) {
			case 1:
				showCash();
				break;

			case 2:
				addProduct();
				break;

			case 3:
				addStock();
				break;

			case 4:
				setExpired();
				break;

			case 5:
				showInventory();
				break;

			case 6:
				sale();
				break;

			case 7:
				showSales();
				break;

			case 8:
				showTotalSales();
				break;
				
			case 9:
				removeProduct();
				break;
				
			case 10:
				System.out.println("Hasta luego!");
				exit = true;
				break;
				
			default:
				System.out.println("Error. Fuera del rango! Vuelve a introducir");
				break;
			}
				
		} while (!exit);

	}
		
		
    	


	

	/**
	 * load initial inventory to shop
	 */
	public void loadInventory() {
		//addProduct(new Product("Manzana", 10.00, true, 10));
		//addProduct(new Product("Pera", 20.00, true, 20));
		//addProduct(new Product("Hamburguesa", 30.00, true, 30));
		//addProduct(new Product("Fresa", 5.00, true, 20));
      loadInventoryFromFile("inputInventory.txt");  // nuevo 

	}
	
	

	/**
	 * show current total cash
	 */
	private void showCash() {
	    System.out.println("Dinero actual en caja: " + cash.getValue() + " " + cash.getCurrency());
	}


	/**
	 * add a new product to inventory getting data from console
	 */
	public void addProduct() {
		Scanner scanner = new Scanner(System.in);
		//if (isInventoryFull()) {
		//	System.out.println("No se pueden añadir más productos");
		//	return;
		//}
		System.out.print("Nombre: ");
		String name = scanner.nextLine();
		System.out.print("Precio mayorista: ");
		double wholesalerPrice = scanner.nextDouble();
		System.out.print("Stock: ");
		int stock = scanner.nextInt();

		addProduct(new Product(name, wholesalerPrice, true, stock));
		System.out.println("Producto añadido correctamente");
		

	}
	

	/**
	 * add stock for a specific product
	 */
	public void addStock() {
		Scanner scanner = new Scanner(System.in);
	    System.out.print("Seleccione un nombre de producto: ");
	    String name = scanner.next();
	    Product product = findProduct(name);

	    if (product != null) {
	        // ask for stock
	        System.out.print("Seleccione la cantidad a añadir: ");
	        int stockToAdd = scanner.nextInt();
	        // update stock product
	        product.setStock(product.getStock() + stockToAdd); // Sumar al stock actual
	        System.out.println("El stock del producto " + name + " ha sido actualizado a " + product.getStock());
	    } else {
	        System.out.println("No se ha encontrado el producto con nombre " + name);
	    }
	

	}

	/**
	 * set a product as expired
	 */
	public void setExpired() {
		Scanner scanner = new Scanner(System.in);
	    System.out.print("Seleccione un nombre de producto: ");
	    String name = scanner.next();
	    Product product = findProduct(name);

	    if (product != null) {
	        product.expire(); // Llamar al método expire() para ajustar el precio al público
	        System.out.println("El producto " + name + " ha sido marcado como próximo a caducar.");
	    } else {
	        System.out.println("No se ha encontrado el producto con nombre " + name);
	    }
		
	}

	/**
	 * show all inventory
	 */
	public void showInventory() {
	    System.out.println("Contenido actual de la tienda:");
	    for (Product product : inventory) {
	        if (product != null) {
	            System.out.println(product);
	        }
	    }
	}


	/**
	 * make a sale of products to a client
	 */
	
	
	public void sale() {
		Scanner scanner = new Scanner(System.in);
		// ask for client name
		System.out.println("Realizar venta, escribir nombre cliente");
		String clientName = scanner.nextLine();
		//String client = scanner.nextLine();

		//Crear objeto Cliente con el nombre introducido
	    Client client = new Client(clientName, Client.MEMBER_ID, Client.getInitialBalance());

		// Vender productos 
		double totalAmount = 0.0;
		String name = "";
		while (!name.equals("0")) {
			System.out.println("Introduce el nombre del producto, escribir 0 para terminar:");
			name = scanner.nextLine();

			if (name.equals("0")) {
				break;
			}
			
			Product product = findProduct(name);
			boolean productAvailable = false;

			if (product != null && product.isAvailable()) {
				productAvailable = true;
				totalAmount += product.getPublicPrice();
				product.setStock(product.getStock() - 1);
				// if no more stock, set as not available to sale
				if (product.getStock() == 0) {
					product.setAvailable(false);
				}
				System.out.println("Producto añadido con éxito");
			}

			if (!productAvailable) {
				System.out.println("Producto no encontrado o sin stock");
			}
		}

		// show cost total
        totalAmount = totalAmount * TAX_RATE;
        Amount totalAmountWithTax = new Amount(totalAmount);
        // Verificar si el cliente tiene saldo suficiente para realizar la compra
        if (client.getBalance().getValue() >= totalAmountWithTax.getValue()) {
            // Si el cliente tiene saldo suficiente, realizar el pago y actualizar el saldo de la tienda
            if (client.pay(totalAmountWithTax)) {
                cash = new Amount(cash.getValue() + totalAmountWithTax.getValue());
                System.out.println("Venta realizada con éxito, total: " + totalAmountWithTax);
                //Saldo cliente despues de la compra
        		double amountSaldo = client.getBalance().getValue() - totalAmountWithTax.getValue();
                System.out.println("El saldo de la cuenta cliente: " + amountSaldo + "€");
            	}
        	} else {
        		// Si el cliente no tiene saldo suficiente, mostrar mensaje con la cantidad adeudada
        		double amountDue = client.getBalance().getValue() - totalAmountWithTax.getValue();
        		System.out.println("El cliente debe: " + amountDue + "€");
        	}
        
		
	    // Crear una nueva venta
        ArrayList<Product> productsSold = new ArrayList<>();
        for (Product product : inventory) {
            if (product != null && !product.isAvailable()) {
                productsSold.add(product);
            }
        }
        Sale sale = new Sale(client, productsSold, totalAmountWithTax, LocalDateTime.now());

	    // Agregar la fecha y hora dev la venta
	    sale.setDateTime(LocalDateTime.now()); // Agregar la fecha y hora actual

	    // Agregar la venta al registro de ventas
	    addSale(sale);
	}
	

	/**
	 * show all sales
	 */
	private void showSales() {
		Scanner scanner = new Scanner(System.in);
	    System.out.println("Lista de venta:");
	    for (Sale sale : sales) {
	        if (sale != null) {
	            String clientName = sale.getClient().getName(); // Obtener el nombre del cliente
	            String clientUpperCase = clientName.toUpperCase(); // Convertir el nombre del cliente a mayúsculas
	            System.out.println("Cliente: " + clientUpperCase  + " - Precio: " + sale.getAmount() + " - Fecha y hora: " + sale.getFormattedDateTime());
	        }
	    }
	    // Preguntar al usuario si desea exportar las ventas a un archivo
	    System.out.print("¿Desea exportar todas las ventas a un archivo? (Si/No): ");
	    String answer = scanner.nextLine().trim(); // Eliminar espacios en blanco alrededor de la entrada
	    System.out.println("Respuesta del usuario: " + answer); // Debugging
	    if (answer.equalsIgnoreCase("Si")) {
	        System.out.println("Exportando ventas...");
	        exportSalesToFile("sales_" + java.time.LocalDate.now() + ".txt");
	    } else {
	        System.out.println("No se ha seleccionado la exportación de ventas.");
	    }
	}


	public void exportSalesToFile(String filename) {
	    try {
	        FileWriter writer = new FileWriter("files/" + filename);
	        int saleNumber = 1;
	        for (Sale sale : sales) {
	            if (sale != null) {
	                writer.write(saleNumber + ";Client=" + sale.getClient() + ";Date=" + sale.getFormattedDateTime() + ";\n");
	                writer.write(saleNumber + ";Products=");
	                for (Product product : sale.getProducts()) {
	                    writer.write(product.getName() + "," + product.getPublicPrice() + "€;");
	                }
	                writer.write("\n" + saleNumber + ";Amount=" + sale.getAmount() + "\n\n");
	                saleNumber++;
	            }
	        }
	        writer.close();
	        System.out.println("Ventas exportadas correctamente al archivo: " + filename);
	    } catch (IOException e) {
	        System.out.println("Error al exportar las ventas al archivo: " + e.getMessage());
	    }
	}
	    
	
	public void showTotalSales() {
        Amount totalSalesAmount = new Amount( 0.0);
        for (Sale sale : sales) {
            if (sale != null) {
                totalSalesAmount.setValue(totalSalesAmount.getValue() + sale.getAmount().getValue());
            }
        }
        System.out.println("Total sales : " + totalSalesAmount);
    }


	/**
	 * add a product to inventory
	 * 
	 * @param product
	 */
	public void addProduct(Product product) {
		//if (isInventoryFull()) {
		//	System.out.println("No se pueden añadir más productos, se ha alcanzado el máximo de " + inventory.length);
		//	return;
		//}
		//inventory[numberProducts] = product;
		//numberProducts++;
        inventory.add(product);
	}

	/**
	 * check if inventory is full or not
	 */
	//public boolean isInventoryFull() {
    //    return numberProducts == inventory.length;

	//}
	
	/**
	 * find product by name
	 * 
	 * @param product name
	 * @return
	 */
    public Product findProduct(String name) {
        for (Product product : inventory) {
            if (product != null && product.getName().equalsIgnoreCase(name)) {
                return product;
            }
        }
        return null;
    }
	
	

    // Método para cargar el inventario desde un archivo
    public void loadInventoryFromFile(String filename) {
        try {
            File file = new File("files/" + filename);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(";");
                String name = parts[0].split(":")[1];
                double wholesalerPrice = Double.parseDouble(parts[1].split(":")[1]);
                int stock = Integer.parseInt(parts[2].split(":")[1]);
                Product product = new Product(name, wholesalerPrice, true, stock);
                inventory.add(product);
             }
            scanner.close();
            System.out.println("Inventario cargado correctamente desde el archivo: " + filename);
        } catch (FileNotFoundException e) {
            System.out.println("Error al cargar el archivo: " + e.getMessage());
        }
    }
    
    
    
    public void addSale(Sale sale) {
        //for (int i = 0; i < sales.length; i++) {
        //    if (sales[i] == null) {
        //        sales[i] = sale;
        //        break;
        //    }
        //}
        sales.add(sale);
    }
    
    // Método para eliminar un producto del inventario
    public void removeProduct() {
		Scanner scanner = new Scanner(System.in);
        System.out.print("Seleccione el nombre del producto a eliminar: ");
        String name = scanner.next();
        Product product = findProduct(name);
        if (product != null) {
            inventory.remove(product);
            System.out.println("Producto eliminado del inventario: " + name);
        } else {
            System.out.println("Producto no encontrado en el inventario: " + name);
        }
    }

}