package Aproject.Aprojectsystem.EjbClasses;

import Aproject.Aprojectsystem.BrokerClass.BrokerReceiver;
import Aproject.Aprojectsystem.BrokerClass.BrokerTransmitter;
import Aproject.Aprojectsystem.XSDSchema.JAXBConverter;
import Aproject.Aprojectsystem.database.GetFromBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.bind.JAXBContext;

@RestController
public class PunchBroker {

    @RequestMapping("/hello/{name}")
    String hello(@PathVariable String name) {

        return "Hi "+name+" !";

    }
    @RequestMapping("/toBase")
    public ResponseEntity punchBrokerTransmitter() {




        return ResponseEntity.ok().build();
    }

    @RequestMapping("/fromBase")
    public ResponseEntity punchBrokerReceiver() {
        GetFromBase getFromBase = new GetFromBase();
        BrokerTransmitter brokerTransmitter = new BrokerTransmitter();

        brokerTransmitter.clientSender(getFromBase.getClientById(2));

        return ResponseEntity.ok().build();
    }
}
