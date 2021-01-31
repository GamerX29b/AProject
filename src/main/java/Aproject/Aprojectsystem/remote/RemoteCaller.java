package Aproject.Aprojectsystem.remote;

import Aproject.Aprojectsystem.brokerClass.BrokerTransmitter;
import Aproject.Aprojectsystem.database.classes.ClientDb;
import Aproject.Aprojectsystem.database.classes.OrderDb;
import Aproject.Aprojectsystem.database.dao.ClientDao;
import Aproject.Aprojectsystem.database.dao.OrderDao;
import Aproject.Aprojectsystem.ejbClasses.PunchBroker;
import Aproject.Aprojectsystem.jaxbComponent.Client;
import Aproject.Aprojectsystem.jaxbComponent.JaxbConverterImpl;
import Aproject.Aprojectsystem.jaxbComponent.Order;
import Aproject.Aprojectsystem.utils.MapperClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.xml.datatype.DatatypeConfigurationException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
public class RemoteCaller {
    static Logger LOGGER = Logger.getLogger(PunchBroker.class.getName());

    @Autowired
    ClientDao clientDao;
    OrderDao orderDao;
    BrokerTransmitter brokerTransmitter;
    JaxbConverterImpl jaxbConverter;

    @Autowired
    public RemoteCaller(OrderDao orderDao, BrokerTransmitter brokerTransmitter, JaxbConverterImpl jaxbConverter) {
        this.orderDao = orderDao;
        this.brokerTransmitter = brokerTransmitter;
        this.jaxbConverter = jaxbConverter;
    }

    @GetMapping(value = "/getClientDataFromId")
    public String boxer(@RequestParam(name = "id") String id){
            ClientDb clientDb = clientDao.getClientById(Integer.valueOf(id.replaceAll("[^0-9]","")));
            MapperClass mapperClass = new MapperClass();
            Client client = null;
            try {
                client = mapperClass.clientDbToClient(clientDb);
            } catch (DatatypeConfigurationException e) {
                LOGGER.log(Level.SEVERE, e.getMessage());
            }
            brokerTransmitter.clientSender(client);
            return HttpStatus.OK.toString();
    }
    @GetMapping(value = "/getProductDataFromClientId")
    public String productPuncher(@RequestParam (name = "id")int id){
        OrderDb orderDb = orderDao.getOrderFromProductByGroupId(id);
        MapperClass mapperClass = new MapperClass();
        Order order = null;
        try {
            order = mapperClass.orderDbToOrder(orderDb);
        } catch (DatatypeConfigurationException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
        brokerTransmitter.orderSender(order);
        return HttpStatus.OK.toString();
    }
}
