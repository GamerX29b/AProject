package Aproject.Aprojectsystem.EjbClasses;

import Aproject.Aprojectsystem.BrokerClass.BrokerReceiver;
import Aproject.Aprojectsystem.BrokerClass.BrokerTransmitter;
import Aproject.Aprojectsystem.XSDSchema.JAXBConverter;
import Aproject.Aprojectsystem.XSDSchema.Order;
import Aproject.Aprojectsystem.database.AddToBase;
import Aproject.Aprojectsystem.database.GetFromBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.bind.JAXBContext;
import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;

@RestController
public class PunchBroker {

    private BrokerReceiver brokerReceiver;
    private AddToBase addToBase;

    @Autowired
    public void setPerson (BrokerReceiver brokerReceiver, AddToBase addToBase) {
        this.brokerReceiver = brokerReceiver;
        this.addToBase = addToBase;
    }

    @RequestMapping("/hello/{name}")
    String hello(@PathVariable String name) {

        return "Hi "+name+" !";

    }

    /**
     * После передачи данных в брокер нужно дёрнуть этот запрос чтобы АПроект забрал его из очереди брокера
     * По коду ошибки можно понять засунул ли он его в базу или нет.
     * @return
     */
    @RequestMapping("/clientToBase")
    public ResponseEntity punchBrokerTransmitterTakeClient() {
        int id = addToBase.createClientGetId(brokerReceiver.takeClient());
        if (id == 0){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok().build();
    }

    @RequestMapping("/orderToBase")
    public ResponseEntity punchBrokerTransmitterTakeOrder(@PathVariable int idClient) {
        LinkedList<Order> order = new LinkedList<>();
        order.add(brokerReceiver.takeOrder());
        boolean id = addToBase.createOrders(order, BigInteger.valueOf(idClient));
        if (id){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok().build();
    }

    @RequestMapping("/fromBase")
    public ResponseEntity punchBrokerReceiver(@PathVariable int id) {
        GetFromBase getFromBase = new GetFromBase();
        BrokerTransmitter brokerTransmitter = new BrokerTransmitter();
        brokerTransmitter.clientSender(getFromBase.getClientById(id));
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
