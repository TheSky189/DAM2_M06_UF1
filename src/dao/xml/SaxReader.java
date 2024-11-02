package dao.xml;

import model.Product;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SaxReader extends DefaultHandler {
    private List<Product> inventory = new ArrayList<>(); // Lista para almacenar productos del inventario
    private String currentElement; // Etiqueta XML actual
    private Product currentProduct; // Producto actual en proceso

    public void parseInventoryFile(String filePath) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            saxParser.parse(new File(filePath), this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        currentElement = qName;
        if ("product".equals(qName)) {
            // Usar el constructor de Product con valores predeterminados para disponible y precio mayorista
            String name = attributes.getValue("name");
        	currentProduct = new Product(name, 0.0, true, 0);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String value = new String(ch, start, length).trim();
        if ("wholesalerPrice".equals(currentElement)) {
            // Asignar el precio mayorista y precio publico basado en el precio mayorista
            double wholesalerPrice = Double.parseDouble(value);
            currentProduct.setWholesalerPrice(wholesalerPrice);
            currentProduct.setPublicPrice(wholesalerPrice * 2);  // Calcular precio publico basado del precio mayorista
        } else if ("stock".equals(currentElement)) {
            // Asignar el stock
            currentProduct.setStock(Integer.parseInt(value));
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if ("product".equals(qName)) {
            inventory.add(currentProduct); // Añadir el producto actual a la lista de inventario
        }
        currentElement = null;
    }

    public ArrayList<Product> getInventory() {
        return new ArrayList<>(inventory);
    }
}