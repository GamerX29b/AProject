package Aproject.Aprojectsystem.ActiveMQ;

import javax.jms.*;
import javax.xml.bind.JAXBContext;

import Aproject.Aprojectsystem.XSDSchema.Client;
import Aproject.Aprojectsystem.XSDSchema.JAXBConverter;

import Aproject.Aprojectsystem.database.GetFromBase;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class ProductsSender {

    //URL of the JMS server. DEFAULT_BROKER_URL will just mean that JMS server is on localhost
    private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;

    // default broker URL is : tcp://localhost:61616"
    private static String subject = "BProject"; // Queue Name.You can create any/many queue names as per your requirement.

    public static void main(String[] args) throws JMSException {
        //sender();
        GetFromBase getFromBase = new GetFromBase();
        Client client = getFromBase.getClientFromAllOrders(1);
        System.out.println(JAXBConverter.clientToXml(client));

        //Для тестов
    }
        public static void sender() throws JMSException {
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
            connectionFactory.setTrustAllPackages(true); //в доке написано что это не безопасно
            Connection connection = connectionFactory.createConnection();
            connection.start();
            Session session = connection.createSession(false,
                    Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue(subject);
            MessageProducer producer = session.createProducer(destination);
            Client client = new Client();
            GetFromBase getFromBase = new GetFromBase();
            TextMessage message = session.createTextMessage(JAXBConverter.clientToXml(getFromBase.getClientById(2)));
            session.createObjectMessage();
            producer.send(message);
            connection.close();
    }
}
