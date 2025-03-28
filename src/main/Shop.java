package main;

import model.Amount;
import model.Client;
import model.Employee;
import model.Product;
import model.Sale;
//import dao.DaoImplFile;
//import dao.DaoImplJaxb;
import dao.Dao;
//import dao.DaoImplJDBC;
//import dao.DaoImplHibernate;
import dao.DaoImplMongoDB;

import java.util.ArrayList;  // nuevo agregado
import java.util.List;
//import java.io.File;
//import java.io.FileNotFoundException;
import java.util.Scanner;

import org.hibernate.cfg.Configuration;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
    

/* añadir un campo mas a inputInventory.txt al final con valor disponible o noDisponible 
 * para asignar true o false al atributo producto.available   <- UF3_EXTRAORDINARIA
 * */              

public class Shop {
	public Amount cash = new Amount(100.00);
	//private Product[] inventory; modifado 
    //private ArrayList<Product> inventory;
    private List<Product> inventory;
	//private Sale[] sales; modificado
	private ArrayList<Sale> sales;
	//private DaoImplFile dao;
	
	//private Dao dao = new DaoImplJaxb();
//	private Dao dao = new DaoImplJDBC();  // p4
	//private Dao dao = new DaoImplHibernate(); 
	private Dao dao = new DaoImplMongoDB();
	

	final static double TAX_RATE = 1.04;
	final static double DOLLAR_A_EURO_RATE = 1.10; // convertidor aplicado a todos

	public Dao getDao() {
	    return this.dao;
	}

	public Shop() {
	    //inventory = new Product[10];
	    //sales = new Sale[10]; // Añade esta línea para inicializar el array sales
        //this.inventory = new ArrayList<>();  //LAST
		this.inventory = new ArrayList<>();
        this.sales = new ArrayList<>();
        //this.dao = new DaoImplJaxb();  // inicializar con DaiImplJaxb
        //this.dao = new DaoImplFile(); // inicializar objeto DaoImplFile
        //this.dao = new DaoImplJDBC();
        //this.dao = new DaoImplHibernate();
        this.dao = new DaoImplMongoDB();
	}

	
    public List<Product> getInventory() {
        return inventory;
    }
    
    public ArrayList<Sale> getSales() {
        return sales;
    }
   
    
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		Shop shop = new Shop();
		
		shop.dao.connect();
		
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
		
		Employee employee = new Employee(); // nombre de empleado fijo "test" temporal
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
            System.out.println("0) Exportar Inventario");  // Añadir nueva opcion
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
            case 0:
                exportInventory();
                break;
                
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
//	public void loadInventory() {
//		//addProduct(new Product("Manzana", 10.00, true, 10));
//		//addProduct(new Product("Pera", 20.00, true, 20));
//		//addProduct(new Product("Hamburguesa", 30.00, true, 30));
//		//addProduct(new Product("Fresa", 5.00, true, 20));
//		
//		//loadInventoryFromFile("inputInventory.txt");  
//		
//		// usar SaxReader para cargar txt formato XML     // nuevo 
//        System.out.println("Cargando inventario...");
//        this.inventory = dao.getInventory();
//        if (this.inventory != null && !this.inventory.isEmpty()) {
//            System.out.println("Inventario cargado correctamente desde el archivo XML.");
//        } else {
//            System.out.println("Error al cargar el inventario desde el archivo XML.");
//        }
//	}
	
	public void loadInventory() {
	    System.out.println("Cargando inventario desde base de datos...");
	    dao.connect(); 
	    this.inventory = dao.getInventory();

	    if (this.inventory != null && !this.inventory.isEmpty()) {
	        System.out.println("Inventario cargado correctamente desde base de datos.");
	    } else {
	        System.out.println("Error al cargar inventario o esta vacío.");
	    }
	}


	
    // Metodo para leer el inventario usando DaoImplFile
	public void readInventory() {
		dao.connect();
	    this.inventory = (ArrayList<Product>) dao.getInventory();  // Cargar productos desde el archivo
	    if (this.inventory != null && !this.inventory.isEmpty()) {
	        System.out.println("Inventario cargado correctamente.");
	    } else if (this.inventory.isEmpty()) {
	        System.out.println("El archivo de inventario está vacío.");
	    } else {
	        System.out.println("Error al cargar el inventario.");
	    }
	    dao.disconnect();
	}


    // Metodo para escribir el inventario usando DaoImplFile
    public boolean writeInventory() {
    	dao.connect();
        boolean result = dao.writeInventory(this.inventory);  // Guardar el inventario en el archivo
        if (result) {
            System.out.println("Inventario guardado correctamente.");
        } else {
            System.out.println("Error al guardar el inventario.");
        }
        dao.disconnect();
        return result;
    }
    
    
//    // Metodo para exportar el inventario
//    private void exportInventory() {
//        System.out.println("Exportando inventario...");
//        boolean result = dao.writeInventory(this.inventory);
//
//        if (result) {
//            System.out.println("Inventario exportado correctamente a: files/inventory_" + LocalDate.now() + ".xml");
//        } else {
//            System.out.println("Error al exportar el inventario.");
//        }
//    }
    
    
    public boolean exportInventory() {
    	dao.connect();
        System.out.println("Exportando inventario a la tabla historical_inventory...");
        boolean result = dao.exportInventoryToHistory(this.inventory);

        if (result) {
            System.out.println("Inventario exportado correctamente a la tabla historica.");
            loadInventory();
        } else {
            System.out.println("Error al exportar el inventario.");
        }
        dao.disconnect();
        return result;
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

	    System.out.print("Nombre del producto: ");
	    String name = scanner.nextLine();
	    System.out.print("Precio mayorista($): ");
	    double wholesalerPrice = scanner.nextDouble();
	    System.out.print("Stock: ");
	    int stock = scanner.nextInt();

	    wholesalerPrice = convertirDollarEuro(wholesalerPrice);

	    Product product = new Product(name, wholesalerPrice, true, stock);
	    
	    if (product != null) {
	        System.out.println("Producto añadido correctamente.");
	        loadInventory(); 
	    } else {
	        System.out.println("Error al añadir el producto.");
	    }
	}



	

	/**
	 * add stock for a specific product
	 */
	public void addStock() {
	    Scanner scanner = new Scanner(System.in);

	    System.out.print("Nombre del producto: ");
	    String name = scanner.next();
	    System.out.print("Cantidad a agregar: ");
	    int additionalStock = scanner.nextInt();

		Product product = findProduct(name);

	        if (product != null) {
	            product.setStock(product.getStock() + additionalStock);
	            System.out.println("Stock añadido correctamente.");
	        } else {
	            System.out.println("Producto no encontrado en la lista de inventario local.");
	        }
	    } 
	
	public void addStock(String name, int stock) {
		dao.connect();
		dao.addStock(name, stock);
		dao.disconnect();
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
	
    // Metodo para mostrar el inventario actual  NEW
    public void showInventory() {
        if (this.inventory != null && !this.inventory.isEmpty()) {
            for (Product product : this.inventory) {
                System.out.println(product);
            }
        } else {
            System.out.println("No hay productos en el inventario.");
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
		Amount totalAmount = new Amount (0.0);
		ArrayList<Product> productsSold = new ArrayList<>();
		String name = "";
		
		while (!name.equals("0")) {
			System.out.println("Introduce el nombre del producto, escribir 0 para terminar:");
			name = scanner.nextLine();

			if (name.equals("0")) {
				break;
			}
			
			Product product = findProduct(name);
			//boolean productAvailable = false;

			if (product != null && product.isAvailable()) {
				//productAvailable = true;
				totalAmount.setValue(product.getPublicPrice().getValue());
				product.setStock(product.getStock() - 1);
				// if no more stock, set as not available to sale
				if (product.getStock() == 0) {
					product.setAvailable(false);
				}
				productsSold.add(product);
				System.out.println("Producto añadido con exito");
			}else {
				System.out.println("Producto no encontrado o sin stock");
			}
			
			// si esta disponible o no; no del cantidad que queda  NEW

		}


		// show cost total
        totalAmount.setValue(totalAmount.getValue() * TAX_RATE);
        Amount totalAmountWithTax = new Amount(totalAmount.getValue());
        // Verificar si el cliente tiene saldo suficiente para realizar la compra
        if (client.getBalance().getValue() >= totalAmountWithTax.getValue()) {
            // Si el cliente tiene saldo suficiente, realizar el pago y actualizar el saldo de la tienda
            if (client.pay(totalAmountWithTax)) {
                cash = new Amount(cash.getValue() + totalAmountWithTax.getValue());
                System.out.println("Venta realizada con exito, total: " + totalAmountWithTax);
                //Saldo cliente despues de la compra
        		double amountSaldo = client.getBalance().getValue() - totalAmountWithTax.getValue();
                System.out.println("El saldo de la cuenta cliente: " + amountSaldo + "€");
            	}
        	} else {
        		// Si el cliente no tiene saldo suficiente, mostrar mensaje con la cantidad adeudada
        		double amountDue = client.getBalance().getValue() - totalAmountWithTax.getValue();
        		System.out.println("El cliente debe: " + amountDue + "€");
        	}
        
		        
        Sale sale = new Sale(client, productsSold, totalAmountWithTax, LocalDateTime.now());

	    // Agregar la fecha y hora dev la venta
	    sale.setDateTime(LocalDateTime.now()); // Agregar la fecha y hora actual

	    // Agregar la venta al registro de ventas
	    addSale(sale);
	    
	    //writeInventoryToFile();  //NEW : PARA GUARDAR EL INVENTARIO ACTUALIZADO
	}
	



	/**
	 * show all sales
	 */
	private void showSales() {
	    System.out.println("Lista de venta:");
	    
	    for (Sale sale : sales) {
	        if (sale != null) {
	            String clientUpperCase = sale.getClient().toUpperCase();
	            System.out.println(" - Cliente: " + clientUpperCase);
	            System.out.println(" - Productos: ");
	            for (Product product : sale.getProducts()) {
	            	System.out.print("  ; " + product.getName() + ", " + product.getPublicPrice() + "€ ");
	            }
	            System.out.println();
                System.out.println(" - Precio total: " + sale.getAmount());
                System.out.println(" - Fecha y hora: " + sale.getFormattedDateTime());
	        }
	    }
	    // Preguntar al usuario si desea exportar las ventas a un archivo
		Scanner scanner = new Scanner(System.in);
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
		            String clientUpperCase = sale.getClient().toUpperCase();

	                writer.write(saleNumber + "; Client=" + clientUpperCase + "; Date=" + sale.getFormattedDateTime() + "; \n");
	                writer.write(saleNumber + "; Products=");
	                for (Product products : sale.getProducts()) {
	                    writer.write(products.getName() + ", " + products.getPublicPrice() + "€; ");
	                }
	                writer.write("\n" + saleNumber + "; Amount=" + sale.getAmount() + "\n");
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
		inventory.add(product);
		dao.connect();
		dao.addProduct(product);
		dao.disconnect();
		}

	
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
	


    // Metodo para cargar el inventario desde un archivo
//    public void loadInventoryFromFile(String filename) {
//        try {
//            File file = new File("files/" + filename);
//            Scanner scanner = new Scanner(file);
//            while (scanner.hasNextLine()) {
//                String line = scanner.nextLine();
//                String[] parts = line.split(";");
//                
//                // Verificar que hay suficientes partes en la linea antes de continuar
//                if (parts.length < 3) {
//                    System.out.println("Línea de datos incompleta: " + line);
//                    continue; 
//                }
//
//                // extraer y procesar los datos de cada parte
//                String name = parts[0].split(":")[1];
//                double wholesalerPriceDollar = Double.parseDouble(parts[1].split(":")[1]);
//                int stock = Integer.parseInt(parts[2].split(":")[1]);
//                
//                // Convertir el valor de available de texto a booleano
//                //boolean available = Boolean(parts[3].split(":")[1]);
//                //boolean available = Boolean.parseBoolean(parts[3].split(":")[1]);
//                
//                // Ajustar para leer "disponible" o "no disponible" y convertir a booleano
//                // String availableText = parts[3].split(":")[1].trim();
//                // boolean available = availableText.equalsIgnoreCase("disponible");
//                
//                // Convertir el precio mayorista de dolares a euros
//                double wholesalerPriceEuro = convertirDollarEuro(wholesalerPriceDollar);
//                
//                Product product = new Product(name, wholesalerPriceEuro, true, stock);
//                inventory.add(product);
//             }
//            scanner.close();
//            System.out.println("Inventario cargado correctamente desde el archivo: " + filename);
//        } catch (FileNotFoundException e) {
//            System.out.println("Error al cargar el archivo: " + e.getMessage());
//        } catch (NumberFormatException e) {
//            System.out.println("Error al convertir valor numerico: " + e.getMessage());
//        } catch (ArrayIndexOutOfBoundsException e) {
//            System.out.println("Error en el formato de linea de inventario: " + e.getMessage());
//        }
//    }
    

	public double convertirDollarEuro(double euroAmount) {
    	return euroAmount * DOLLAR_A_EURO_RATE;
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
    
    // para eliminar un producto del inventario
    public void removeProduct() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Seleccione el nombre del producto a eliminar: ");
        String name = scanner.next();
        Product product = findProduct(name);

        if (product != null) {
            loadInventory();
            System.out.println("Producto eliminado correctamente.");
        } else {
            System.out.println("Error al eliminar el producto.");
        }
    }

	public void removeProduct(String name) {
		dao.connect();
		dao.deleteProduct(name);
		dao.disconnect();
		}

//        if (product != null) {
//            inventory.remove(product);
//            System.out.println("Producto eliminado del inventario: " + name);
//        } else {
//            System.out.println("Producto no encontrado en el inventario: " + name);
//        }
}