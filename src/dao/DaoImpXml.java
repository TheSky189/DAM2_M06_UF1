//package dao;
//
//import dao.xml.SaxReader;
//import dao.xml.DomWriter;
//import model.Employee;
//import model.Product;
//import java.util.List;
//
//public class DaoImpXml implements Dao {
//    private final SaxReader saxReader = new SaxReader();
//    private final DomWriter domWriter = new DomWriter();
//
//    // usar SaxReader para cargar inventario
//    @Override
//    public List<Product> getInventory() {
//        saxReader.parseInventoryFile("files/inputInventory.xml"); // leer documento XML
//        // saxReader.parseInventoryFile("user/inputInventory.xml"); // para test de error
//        return saxReader.getInventory(); // devolver lista
//    }
//
//    // usaar DonWriter para añqdir datos al XML
//    @Override
//    public boolean writeInventory(List<Product> inventory) {
//        try {
//            domWriter.writeInventoryToFile(inventory); // exportar fichero
//            return true;
//        } catch (Exception e) {
//            System.out.println("Error al exportar el inventario: " + e.getMessage());
//            return false;
//        }
//    }
//
//	@Override
//	public void connect() {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public Employee getEmployee(int employeeId, String password) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public void disconnect() {
//		// TODO Auto-generated method stub
//		
//	}
//
//}
