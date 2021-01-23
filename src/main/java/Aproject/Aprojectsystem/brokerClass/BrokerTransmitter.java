package Aproject.Aprojectsystem.brokerClass;

import Aproject.Aprojectsystem.jaxbComponent.Client;
import Aproject.Aprojectsystem.jaxbComponent.JAXBConverter;
import Aproject.Aprojectsystem.jaxbComponent.Order;
import Aproject.Aprojectsystem.jaxbComponent.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.*;

/*
Клиент для отправки сообщений на AProject
 */
@Component
public class BrokerTransmitter extends JmsConfig {

    @Autowired
    JmsTemplate jmsTemplate;
    //Todo тоже переписать под JMSTemplate

    public boolean clientSender(Client client) {
        if (client == null) return false;
        try {
            Connection connection = getConnection();
            connection.start();
            Session session = connection.createSession(false,
                    Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue(subjectClient);
            MessageProducer producer = session.createProducer(destination); // Создаётся некий "режиссёр" сообщения
            TextMessage message = session.createTextMessage(JAXBConverter.clientToXml(client)); // Тут то, что мы шлём
            session.createObjectMessage();
            // Here we are sending our message!
            producer.send(message);
            connection.close();
            return true;
        } catch (JMSException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean productSender(Product product) {
        if (product == null) return false;
        try {
            Connection connection = getConnection();
            connection.start();
            Session session = connection.createSession(false,
                    Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue(subjectProduct);
            MessageProducer producer = session.createProducer(destination); // Создаётся некий "режиссёр" сообщения
            TextMessage message = session.createTextMessage(JAXBConverter.productToXml(product)); // Тут то, что мы шлём
            session.createObjectMessage();
            producer.send(message);
            connection.close();
            return true;
        } catch (JMSException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean orderSender(Order order) {
        if (order == null) return false;
        try {
            Connection connection = getConnection();
            connection.start();
            Session session = connection.createSession(false,
                    Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue(subjectOrder);
            MessageProducer producer = session.createProducer(destination); // Создаётся некий "режиссёр" сообщения
            TextMessage message = session.createTextMessage(JAXBConverter.OrderToXml(order)); // Тут то, что мы шлём
            session.createObjectMessage();
            producer.send(message);
            connection.close();
            return true;
        } catch (JMSException e) {
            e.printStackTrace();
            return false;
        }
    }
}
