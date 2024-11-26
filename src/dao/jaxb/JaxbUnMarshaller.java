package dao.jaxb;

import model.ProductList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class JaxbUnMarshaller {

    public ProductList unmarshal(String filePath) {
        try {
            JAXBContext context = JAXBContext.newInstance(ProductList.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            ProductList productList = (ProductList) unmarshaller.unmarshal(new File(filePath));
            System.out.println("Archivo XML cargado correctamente desde: " + filePath);
            return productList;
        } catch (JAXBException e) {
            System.out.println("Error al cargar el archivo XML: " + e.getMessage());
            return null;
        }
    }
}
