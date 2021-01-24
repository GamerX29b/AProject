package Aproject.Aprojectsystem.brokerClass;

import Aproject.Aprojectsystem.jaxbComponent.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import javax.jms.*;

@Component
public class BrokerReceiver{ //Reseiver это приёмщик!!! Почти как Ресивер!

    private static String subject = "BProject";    // Имя, кому принимать данные

    @Autowired
    JmsTemplate jmsTemplate;
    JaxbConverterImpl jaxbConverter;

    public BrokerReceiver(JaxbConverterImpl jaxbConverter, JmsTemplate jmsTemplate) {
        this.jaxbConverter = jaxbConverter;
        this.jmsTemplate = jmsTemplate;
    }

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
                product = jaxbConverter.xmlToProduct(textMessage.getText());
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
                client = jaxbConverter.xmlToClient(textMessage.getText());
            }
        return client;
    }
    /**
     * Класс который будет получать класс продукта из брокера
     * @return
     */
    @JmsListener(destination = "BProjectOrder")
    @SendTo("AProjectOrder")
    public Order takeOrder(final Message orderMessage) throws JMSException {
        Order order = new Order();
            if (orderMessage instanceof TextMessage) {
                TextMessage textMessage  = (TextMessage) orderMessage;  //Приведение к типу textMessage
                order = jaxbConverter.xmlToOrder(textMessage.getText());
            }
        return order;
    }
}
