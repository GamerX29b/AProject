package Aproject.Aprojectsystem.ejbClasses;

import Aproject.Aprojectsystem.brokerClass.BrokerTransmitter;
import Aproject.Aprojectsystem.database.AddToBase;
import Aproject.Aprojectsystem.database.classes.ClientDb;
import Aproject.Aprojectsystem.database.dao.ClientDao;
import Aproject.Aprojectsystem.jaxbComponent.Client;
import Aproject.Aprojectsystem.utils.MapperClass;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ejb.Stateless;
import javax.xml.datatype.DatatypeConfigurationException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless(name = "PunchBroker")
public class PunchBroker implements PunchBrokerAnswer{

    static Logger LOGGER = Logger.getLogger(PunchBroker.class.getName());
    private AddToBase addToBase;


    @Autowired
    ClientDao clientDao;
    BrokerTransmitter brokerTransmitter;

//    @Autowired
//    public PunchBroker(BrokerReceiver brokerReceiver,
//                       AddToBase addToBase,
//                       OrderDao orderDao,
//                       JaxbConverterImpl jaxbConverter,
//                       BrokerTransmitter brokerTransmitter,
//                       ProductDao productDao) {
//        this.brokerReceiver = brokerReceiver;
//        this.addToBase = addToBase;
//        this.orderDao = orderDao;
//        this.productDao = productDao;
//        this.jaxbConverter = jaxbConverter;
//        this.brokerTransmitter = brokerTransmitter;
//    }

    @Override
    public void getClientById(int idClient) {

        ClientDb clientDb = clientDao.getClientById(3);

      MapperClass mapperClass = new MapperClass();
       Client client = null;
       try {
           client = mapperClass.clientDbToClient(clientDb);
       } catch (DatatypeConfigurationException e) {
           LOGGER.log(Level.SEVERE, e.getMessage());
       }

         brokerTransmitter.clientSender(client);

    }

    /**
     * После передачи данных в брокер нужно дёрнуть этот запрос чтобы АПроект забрал его из очереди брокера
     * По коду ошибки можно понять засунул ли он его в базу или нет.
     * @return
     */

}
