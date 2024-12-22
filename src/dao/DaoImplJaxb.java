//package dao;
//
//import java.util.List;
//
//import dao.jaxb.JaxbMarshaller;
//import dao.jaxb.JaxbUnMarshaller;
//import model.Employee;
//import model.Product;
//import model.ProductList;
//
//public class DaoImplJaxb implements Dao {
//	
//	// Implementar lógica metodo getInventory usando JaxbUnMarshaller 
//	// Implementar lógica metodo writeInventory usando JaxbMarshaller
//	// Resto de metodos interface sin implementación 
//	
//	private final JaxbUnMarshaller unmarshaller = new JaxbUnMarshaller();
//	private final JaxbMarshaller marshaller = new JaxbMarshaller();
//	
//	@Override
//	public Employee getEmployee(int employeeId, String password) {
//	    // Método no implementado ya que DaoImplJaxb no gestiona empleados.
//	    return null;
//	}
//	
//    @Override
//    public List<Product> getInventory() {
//        ProductList productList = unmarshaller.unmarshal("files/inputInventory.xml");
//        return productList != null ? productList.getProducts() : null;
//    }
//	
//    @Override
//    public boolean writeInventory(List<Product> inventory) {
//        ProductList productList = new ProductList(inventory);
//        return marshaller.marshal(productList, "files/inventory_" + java.time.LocalDate.now() + ".xml");
//        //return marshaller.marshal(productList, "file/inventory_" + java.time.LocalDate.now() + ".xml");   TEST PARA ERROR
//    }
//	
//	@Override
//	public void connect() {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void disconnect() {
//		// TODO Auto-generated method stub
//		
//	}
//
//
//}
