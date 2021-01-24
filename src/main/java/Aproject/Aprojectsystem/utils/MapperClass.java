package Aproject.Aprojectsystem.utils;

import Aproject.Aprojectsystem.database.classes.ClientDb;
import Aproject.Aprojectsystem.database.classes.OrderDb;
import Aproject.Aprojectsystem.database.classes.ProductDb;
import Aproject.Aprojectsystem.jaxbComponent.Client;
import Aproject.Aprojectsystem.jaxbComponent.Order;
import Aproject.Aprojectsystem.jaxbComponent.Product;
import org.springframework.stereotype.Component;

import javax.xml.datatype.DatatypeConfigurationException;
import java.math.BigInteger;

@Component
public class MapperClass {

    public MapperClass() {
    }

    public Client clientDbToClient(ClientDb clientDb) throws DatatypeConfigurationException {
        Client client = new Client();
        client.setId(BigInteger.valueOf(clientDb.getId()));
        client.setClientName(clientDb.getClientName());
        client.setClientAddress(clientDb.getClientAddress());
        if (clientDb.getOrder() != null) {
            for (OrderDb orderDb : clientDb.getOrder()) {
                client.getOrder().add(orderDbToOrder(orderDb));
            }
        }
        return client;
    }

    public Order orderDbToOrder(OrderDb orderDb) throws DatatypeConfigurationException {
        Order order = new Order();
        order.setId(BigInteger.valueOf(orderDb.getId()));
        order.setOrderGroupId(String.valueOf(orderDb.getOrderGroupId()));
        order.setQuantity(BigInteger.valueOf(orderDb.getQuantity()));
        order.setDate(new UtilXml().dataToCalendar(orderDb.getDate()));
        if (orderDb.getProduct() != null) {
            for (ProductDb productDb : orderDb.getProduct()) {
                order.getProduct().add(productDbToProduct(productDb));
            }
        }
        return order;
    }

    public Product productDbToProduct(ProductDb productDb) {
        Product product = new Product();
        product.setId(BigInteger.valueOf(productDb.getId()));
        product.setNameProduct(productDb.getNameProduct());
        product.setQuantity(BigInteger.valueOf(productDb.getQuantity()));
        return product;
    }
}
