package Aproject.Aprojectsystem.remote;

import Aproject.Aprojectsystem.brokerClass.BrokerTransmitter;
import Aproject.Aprojectsystem.database.classes.ClientDb;
import Aproject.Aprojectsystem.database.dao.ClientDao;
import Aproject.Aprojectsystem.ejbClasses.PunchBroker;
import Aproject.Aprojectsystem.jaxbComponent.Client;
import Aproject.Aprojectsystem.utils.MapperClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.xml.datatype.DatatypeConfigurationException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
public class RemoteCaller {
    static Logger LOGGER = Logger.getLogger(PunchBroker.class.getName());

    @Autowired
    ClientDao clientDao;
    BrokerTransmitter brokerTransmitter;

    @RequestMapping("/punchBroker")
    public void boxer(@RequestParam int id){
            ClientDb clientDb = clientDao.getClientById(id);
            MapperClass mapperClass = new MapperClass();
            Client client = null;
            try {
                client = mapperClass.clientDbToClient(clientDb);
            } catch (DatatypeConfigurationException e) {
                LOGGER.log(Level.SEVERE, e.getMessage());
            }
            brokerTransmitter.clientSender(client);

    }

}
