package ActiveMQ;

import javax.jms.*;

import classes.Client;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import java.util.ArrayList;
import java.util.Arrays;

public class ProductsSender {

    //URL of the JMS server. DEFAULT_BROKER_URL will just mean that JMS server is on localhost
    private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;

    // default broker URL is : tcp://localhost:61616"
    private static String subject = "JCG_QUEUE"; // Queue Name.You can create any/many queue names as per your requirement.

    public static void main(String[] args) throws JMSException {
        // Getting JMS connection from the server and starting it
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        connectionFactory.setTrustAllPackages(true); //в доке написано что это не безопасно
        Connection connection = connectionFactory.createConnection();
        connection.start();


        //Creating a non transactional session to send/receive JMS message.
        Session session = connection.createSession(false,
                Session.AUTO_ACKNOWLEDGE);

        //Destination represents here our queue 'JCG_QUEUE' on the JMS server.
        //The queue will be created automatically on the server.
        Destination destination = session.createQueue(subject);

        // MessageProducer is used for sending messages to the queue.
        MessageProducer producer = session.createProducer(destination);

        // We will send a small text message saying 'Hello World!!!'

        Client client = new Client();
        client.setClientName("Utan");
        client.setClientAdres("Karbishewa");


        ObjectMessage message = session.createObjectMessage(client);

        session.createObjectMessage();
        // Here we are sending our message!
        producer.send(message);

        connection.close();
    }
}
