package dao;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class DaoImplFileTest {

    @Test
    public void testGetInventory() {
        DaoImplFile dao = new DaoImplFile();
        List<Product> inventory = dao.getInventory();

        // verificar que la lista no sea nula ni vacia
        assertNotNull(inventory, "La lista de inventario no deberia ser nula.");
        assertFalse(inventory.isEmpty(), "La lista de inventario no deberia estar vacia.");

        // imprimir cada producto para verificacion visual
        for (Product product : inventory) {
            System.out.println("Producto: " + product.getName() + ", Stock: " + product.getStock());
        }
    }
}
