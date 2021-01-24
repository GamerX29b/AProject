package Aproject.Aprojectsystem.jaxbComponent;

import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;

@Component
public class JAXBConverter implements JaxbConverterImpl {

//    public static void main(String[] args) {
//        XJC2Task xjc2Task = new XJC2Task();
//        Product pro = new ObjectFactory().createProduct();
//        BigInteger bi = BigInteger.valueOf(200L);
//        pro.setNameProduct("Двери");
//        pro.setQuantity(bi);
//        String xml = productToXml(pro);
//        System.out.println(xml);
//        Product product = xmlToProduct(xml);
//        System.out.println(product.getId() + " " + product.getNameProduct() + "_" + product.getQuantity());
//    }
    public JAXBConverter() {
    }

    /*
        PRODUCT
         */
    @Override
    public String productToXml(Product product) {
        String xmlProduct = "";
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Product.class);
            Marshaller jaxbNMarshaller = jaxbContext.createMarshaller();
            jaxbNMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            StringWriter sw = new StringWriter();
            jaxbNMarshaller.marshal(product, sw);
            xmlProduct = sw.toString();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return xmlProduct;
    }

    @Override
    public Product xmlToProduct(String textXml) {
        Product xmlToProduct = new Product();
        JAXBContext jaxbContext;
        try {
            jaxbContext = JAXBContext.newInstance(Product.class);
            StringReader readingStringProduct = new StringReader(textXml);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            xmlToProduct = (Product) jaxbUnmarshaller.unmarshal(readingStringProduct);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return xmlToProduct;
    }

    @Override
    public String orderToXml(Order order) {
        String xmlOrder = "";
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Order.class);
            Marshaller jaxbNMarshaller = jaxbContext.createMarshaller();
            jaxbNMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            StringWriter sw = new StringWriter();
            jaxbNMarshaller.marshal(order, sw);
            xmlOrder = sw.toString();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return xmlOrder;
    }

    @Override
    public Order xmlToOrder(String textXml) {
        Order xmlToProduct = new Order();
        JAXBContext jaxbContext;
        try {
            jaxbContext = JAXBContext.newInstance(Order.class);
            StringReader readingStringOrder = new StringReader(textXml);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            xmlToProduct = (Order) jaxbUnmarshaller.unmarshal(readingStringOrder);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return xmlToProduct;
    }

    @Override
    public String clientToXml(Client client) {
        String xmlClient = "";
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Client.class);
            Marshaller jaxbNMarshaller = jaxbContext.createMarshaller();
            jaxbNMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            StringWriter sw = new StringWriter();
            jaxbNMarshaller.marshal(client, sw);
            xmlClient = sw.toString();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return xmlClient;
    }

    @Override
    public Client xmlToClient(String textXml) {
        Client xmlToClient = new Client();
        JAXBContext jaxbContext;
        try {
            jaxbContext = JAXBContext.newInstance(Client.class);
            StringReader readingStringClient = new StringReader(textXml);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            xmlToClient = (Client) jaxbUnmarshaller.unmarshal(readingStringClient);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return xmlToClient;
    }
}
