package dao.xml;

import model.Product;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.time.LocalDate;
import java.util.List;

public class DomWriter {

    public boolean writeInventoryToFile(List<Product> inventory) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            // Crear elemento <products>
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("products");
            rootElement.setAttribute("total", String.valueOf(inventory.size()));
            doc.appendChild(rootElement);

            // Añadir cada producto al documento XML
            int id = 1;
            for (Product product : inventory) {
                Element productElement = doc.createElement("product");
                productElement.setAttribute("id", String.valueOf(id++));

                Element name = doc.createElement("name");
                name.appendChild(doc.createTextNode(product.getName()));
                productElement.appendChild(name);

                Element price = doc.createElement("price");
                price.setAttribute("currency", "Euro");
                price.appendChild(doc.createTextNode(String.valueOf(product.getWholesalerPrice())));
                productElement.appendChild(price);

                Element stock = doc.createElement("stock");
                stock.appendChild(doc.createTextNode(String.valueOf(product.getStock())));
                productElement.appendChild(stock);

                rootElement.appendChild(productElement);
            }

            // Configurar el transformador para escribir el archivo XML
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);

            // Crear el archivo de salida con el formato de fecha
            String fileName = "files/inventory_" + LocalDate.now() + ".xml";
            
            StreamResult result = new StreamResult(new File(fileName));
            transformer.transform(source, result);
            return true;

	        } catch (Exception e) {
	            System.out.println("Error al exportar el inventario: " + e.getMessage());
	            return false;
	        }

    }
}
