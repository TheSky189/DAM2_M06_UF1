package dao.jaxb;

import model.ProductList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;

public class JaxbMarshaller {

    public boolean marshal(ProductList productList, String filePath) {
        try {
            JAXBContext context = JAXBContext.newInstance(ProductList.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(productList, new File(filePath));
            System.out.println("Archivo XML generado correctamente: " + filePath);
            return true;
        } catch (JAXBException e) {
            System.out.println("Error al generar el archivo XML: " + e.getMessage());
            return false;
        }
    }
}
