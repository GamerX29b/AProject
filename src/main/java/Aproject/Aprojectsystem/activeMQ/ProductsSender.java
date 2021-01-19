package Aproject.Aprojectsystem.activeMQ;

import javax.jms.*;
import javax.xml.datatype.DatatypeConfigurationException;

import Aproject.Aprojectsystem.Utils.UtilXml;
import Aproject.Aprojectsystem.XSDSchema.Client;
import Aproject.Aprojectsystem.XSDSchema.JAXBConverter;

import Aproject.Aprojectsystem.XSDSchema.Order;
import Aproject.Aprojectsystem.XSDSchema.Product;
import Aproject.Aprojectsystem.database.AddToBase;
import Aproject.Aprojectsystem.database.GetFromBase;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import java.math.BigInteger;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

public class ProductsSender {

    static Logger LOGGER;

    //URL of the JMS server. DEFAULT_BROKER_URL will just mean that JMS server is on localhost
    private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;

    // default broker URL is : tcp://localhost:61616"
    private static String subject = "BProject"; // Queue Name.You can create any/many queue names as per your requirement.

    public static void main(String[] args) throws JMSException, DatatypeConfigurationException {
        //sender();
        Client clientAdd = new Client();
        UtilXml utilXml = new UtilXml();
        List<Order> orderList = new LinkedList<>();
        List<Product> productList = new LinkedList<>();

        clientAdd.setClientName("Денис Поликарпович");
        clientAdd.setClientAddress("Грозная улица");

        Product product = new Product();

        product.setId(BigInteger.valueOf(1));
        product.setQuantity(BigInteger.valueOf(8));

        productList.add(product);

        product.setId(BigInteger.valueOf(2));
        product.setQuantity(BigInteger.valueOf(4));

        productList.add(product);


        Order order = new Order();
        order.setDate(utilXml.dataToCalendar(new Date()));
        order.setOrderGroupId("50");
        order.setProduct(productList);
        orderList.add(order);

        clientAdd.setOrder(orderList);
        GetFromBase getFromBase = new GetFromBase();
        AddToBase addToBase = new AddToBase();

        //Client client = getFromBase.getClientFromAllOrders();
        System.out.println(addToBase.createClientGetId(clientAdd));

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
