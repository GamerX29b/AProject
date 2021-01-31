package Aproject.Aprojectsystem.brokerClass;

import Aproject.Aprojectsystem.jaxbComponent.Client;
import Aproject.Aprojectsystem.jaxbComponent.JaxbConverterImpl;
import Aproject.Aprojectsystem.jaxbComponent.Order;
import Aproject.Aprojectsystem.jaxbComponent.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

/*
Клиент для отправки сообщений на AProject
 */
@Component
public class BrokerTransmitter {

    @Autowired
    JmsTemplate jmsTemplate;
    JaxbConverterImpl jaxbConverter;

    public BrokerTransmitter(JaxbConverterImpl jaxbConverter, JmsTemplate jmsTemplate) {
        this.jaxbConverter = jaxbConverter;
        this.jmsTemplate = jmsTemplate;
    }

    public boolean clientSender(Client client) {
        if (client == null) return false;
        jmsTemplate.send("BProjectClient", new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(jaxbConverter.clientToXml(client));
            }
        });
        return true;
    }

    public boolean productSender(Product product) {
        if (product == null) return false;
        jmsTemplate.send("BProjectProduct", new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                TextMessage message = session.createTextMessage(jaxbConverter.productToXml(product));
                return message;
            }
        });
        return true;
    }

    public boolean orderSender(Order order) {
        if (order == null) return false;
        jmsTemplate.send("BProjectOrder", new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                String orderMesage = jaxbConverter.orderToXml(order);
                TextMessage message = session.createTextMessage(orderMesage);
                return message;
            }
        });
        return true;
    }
}
