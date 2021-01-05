package BrokerClass;

import XSDSchema.Client;
import XSDSchema.JAXBConverter;
import XSDSchema.Order;
import XSDSchema.Product;

import javax.jms.*;

public class BrokerReceiver extends CreateConnection{ //Reseiver это приёмщик!!! Почти как Ресивер!


    private static String subject = "BProject";    // Имя, кому принимать данные

    /**
     * Класс который будет получать класс продукта из брокера
     * @return
     */
    public Product takeProduct() {
        Product product = new Product();
        try {
        Connection connection = getConnection();
        connection.start();
        //Создание сессии кому отправить сообщение
        Session session = connection.createSession(false,
                Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createQueue("BProjectProduct"); // отправляем сообщение в 'ClientRes'
        MessageConsumer consumer = session.createConsumer(destination); // MessageConsumer для получения сообщений
        Message message = consumer.receive();         // Тут получаем сообщение
        if (message instanceof TextMessage) {
            TextMessage textMessage  = (TextMessage) message;  //Приведение к типу textMessage
            product = JAXBConverter.xmlToProduct(textMessage.getText());
        }
        connection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }
        return product;
    }
    /**
     * Класс который будет получать класс клиента из брокера
     * @return
     */
    public Client takeClient() {
        Client client = new Client();
        try {
            Connection connection = getConnection();;
            connection.start();
            //Создание сессии кому отправить сообщение
            Session session = connection.createSession(false,
                    Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue("BProjectClient"); // отправляем сообщение в 'ClientRes'
            MessageConsumer consumer = session.createConsumer(destination); // MessageConsumer для получения сообщений
            Message message = consumer.receive();         // Тут получаем сообщение
            if (message instanceof TextMessage) {
                TextMessage textMessage  = (TextMessage) message;  //Приведение к типу textMessage
                client = JAXBConverter.xmlToClient(textMessage.toString());
            }
            connection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }
        return client;
    }
    /**
     * Класс который будет получать класс продукта из брокера
     * @return
     */
    public Order takeOrder() {
        Order order = new Order();
        try {
            Connection connection = getConnection();;
            connection.start();
            //Создание сессии кому отправить сообщение
            Session session = connection.createSession(false,
                    Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue("BProjectOrder"); // отправляем сообщение в 'ClientRes'
            MessageConsumer consumer = session.createConsumer(destination); // MessageConsumer для получения сообщений
            Message message = consumer.receive();         // Тут получаем сообщение
            if (message instanceof TextMessage) {
                TextMessage textMessage  = (TextMessage) message;  //Приведение к типу textMessage
                order = JAXBConverter.xmlToOrder(textMessage.getText());
            }
            connection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }
        return order;
    }
}
