package Aproject.Aprojectsystem.EjbClasses;

import Aproject.Aprojectsystem.brokerClass.BrokerReceiver;
import Aproject.Aprojectsystem.brokerClass.BrokerTransmitter;
import Aproject.Aprojectsystem.database.AddToBase;
import Aproject.Aprojectsystem.database.classes.ProductDb;
import Aproject.Aprojectsystem.database.dao.ClientDao;
import Aproject.Aprojectsystem.database.dao.OrderDao;
import Aproject.Aprojectsystem.database.dao.ProductDao;
import Aproject.Aprojectsystem.jaxbComponent.JaxbConverterImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
public class PunchBroker {

    static Logger LOGGER = Logger.getLogger(PunchBroker.class.getName());
    private AddToBase addToBase;
    private final BrokerReceiver brokerReceiver;

    @Autowired
    ClientDao clientDao;
    OrderDao orderDao;
    JaxbConverterImpl jaxbConverter;
    BrokerTransmitter brokerTransmitter;
    ProductDao productDao;

    @Autowired
    public PunchBroker(BrokerReceiver brokerReceiver,
                       AddToBase addToBase,
                       OrderDao orderDao,
                       JaxbConverterImpl jaxbConverter,
                       BrokerTransmitter brokerTransmitter,
                       ProductDao productDao) {
        this.brokerReceiver = brokerReceiver;
        this.addToBase = addToBase;
        this.orderDao = orderDao;
        this.productDao = productDao;
        this.jaxbConverter = jaxbConverter;
        this.brokerTransmitter = brokerTransmitter;
    }

    //Тесты
    @RequestMapping("/hello/{name}")
    String hello(@PathVariable String name) {

//        ClientDb clientDb = clientDao.getClientById(3);
//
//        MapperClass mapperClass = new MapperClass();
//        Client client = null;
//        try {
//            client = mapperClass.clientDbToClient(clientDb);
//        } catch (DatatypeConfigurationException e) {
//            LOGGER.log(Level.SEVERE, e.getMessage());
//        }
//        String str = jaxbConverter.clientToXml(client);
//        //brokerTransmitter.clientSender(client);
//        LOGGER.log(Level.WARNING, str);
//       // boolean bobo = ;
//        // LOGGER.log(Level.WARNING, String.valueOf(bobo));
//        brokerTransmitter.clientSender(str);

        ProductDb productDb = new ProductDb();
        productDb.setNameProduct(name);
        productDb.setQuantity(300);
        productDao.setNewProduct(productDb);


        return name;

    }

    /**
     * После передачи данных в брокер нужно дёрнуть этот запрос чтобы АПроект забрал его из очереди брокера
     * По коду ошибки можно понять засунул ли он его в базу или нет.
     * @return
     */

//    @RequestMapping("/orderToBase")
//    public ResponseEntity punchBrokerTransmitterTakeOrder(@PathVariable int idClient) {
//        LinkedList<Order> order = new LinkedList<>();
//        order.add(brokerReceiver.takeOrder());
//        boolean id = addToBase.createOrders(order, BigInteger.valueOf(idClient));
//        if (id){
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//        }
//        return ResponseEntity.ok().build();
//    }
//
//    @RequestMapping("/fromBase")
//    public ResponseEntity punchBrokerReceiver(@PathVariable int id) {
//        GetFromBase getFromBase = new GetFromBase();
//        BrokerTransmitter brokerTransmitter = new BrokerTransmitter();
//        brokerTransmitter.clientSender(getFromBase.getClientById(id));
//        return ResponseEntity.status(HttpStatus.OK).build();
//    }
}
