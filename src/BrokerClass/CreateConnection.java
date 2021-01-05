package BrokerClass;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.JMSException;

public class CreateConnection {

    // Адрес JMS сервера, пока можно оставить дефолтный ибо пофиг tcp://localhost:61616
    private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;

    Connection getConnection () throws JMSException {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);

        return connectionFactory.createConnection();
    }
}
