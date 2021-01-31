package Aproject.Aprojectsystem;

import Aproject.Aprojectsystem.jaxbComponent.Client;
import Aproject.Aprojectsystem.jaxbComponent.JaxbConverterImpl;
import Aproject.Aprojectsystem.jaxbComponent.Order;
import Aproject.Aprojectsystem.jaxbComponent.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Самый обычный спринговый тест.
 */

@SpringBootTest(properties = "Ivan=Ivan")
@ActiveProfiles("test")
class jaxbConverterTest {
        @Autowired
       JaxbConverterImpl jaxbConverter;

        @Autowired
        jaxbConverterTest(JaxbConverterImpl jaxbConverter){
            this.jaxbConverter = jaxbConverter;
        }

    /**
     * Проверяем работу джаксби конвертера. Туда и обратно. Он должен создать XML с именем клиента Ivan
     * Потом превратить его обратно в объект Client получить из него имя и там тоже должно быть имя Ivan
     * Это означает что не сбилась кодировка, данные не просрались, при желании такими тестами можно накрыть все
     * методы.
     */
        @Test
        void testJaxbConverter(){
            Client client = new Client();
            client.setClientName("Ivan");
            client.setClientAddress("Орджиникидзе");
            Order order = new Order();
            Product product = new Product();
            product.setNameProduct("Пылесос");
            List<Product> productList = new LinkedList<>();
            productList.add(product);
            order.setProduct(productList);
            List<Order> orderList = new LinkedList<>();
            orderList.add(order);
            client.setOrder(orderList);
            String clientXml = jaxbConverter.clientToXml(client);
            Client xmltoClient = jaxbConverter.xmlToClient(clientXml);
            assertEquals(xmltoClient.getClientName(), ("Ivan")); //Специальный метод который сравнивает данные.
            assertEquals(xmltoClient.getClientAddress(), ("Орджиникидзе"));
            assertEquals(xmltoClient.getOrder().get(0).getProduct().get(0).getNameProduct(), ("Пылесос"));
    }
}
