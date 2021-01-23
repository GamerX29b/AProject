package Aproject.Aprojectsystem.brokerClass;

import Aproject.Aprojectsystem.jaxbComponent.Client;
import Aproject.Aprojectsystem.jaxbComponent.JAXBConverter;
import Aproject.Aprojectsystem.jaxbComponent.Order;
import Aproject.Aprojectsystem.jaxbComponent.Product;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import javax.jms.*;

@Component
public class BrokerReceiver extends JmsConfig { //Reseiver это приёмщик!!! Почти как Ресивер!

    private static String subject = "BProject";    // Имя, кому принимать данные

    /**
     * Класс который будет получать класс продукта из брокера
     * @return
     */

    @JmsListener(destination = "BProjectProduct")
    @SendTo("AProjectProduct")
    public Product takeProduct(final Message productMessage) throws JMSException {
        Product product = new Product();
            if (productMessage instanceof TextMessage) {
                TextMessage textMessage  = (TextMessage) productMessage;  //Приведение к типу textMessage
                product = JAXBConverter.xmlToProduct(textMessage.getText());
            }
        return product;
    }
    /**
     * Класс который будет получать класс клиента из брокера
     * @return
     */
    @JmsListener(destination = "BProjectClient")
    @SendTo("AProjectClient")
    public Client takeClient(final Message clientMessage) throws JMSException {
        Client client = new Client();
            if (clientMessage instanceof TextMessage) {
                TextMessage textMessage  = (TextMessage) clientMessage;  //Приведение к типу textMessage
                client = JAXBConverter.xmlToClient(textMessage.getText());
            }
        return client;
    }
    /**
     * Класс который будет получать класс продукта из брокера
     * @return
     */
    public Order takeOrder(final Message orderMessage) throws JMSException {
        Order order = new Order();
            if (orderMessage instanceof TextMessage) {
                TextMessage textMessage  = (TextMessage) orderMessage;  //Приведение к типу textMessage
                order = JAXBConverter.xmlToOrder(textMessage.getText());
            }
        return order;
    }
}
