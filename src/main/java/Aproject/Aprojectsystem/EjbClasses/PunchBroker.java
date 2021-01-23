package Aproject.Aprojectsystem.EjbClasses;

import Aproject.Aprojectsystem.BrokerClass.BrokerReceiver;
import Aproject.Aprojectsystem.BrokerClass.BrokerTransmitter;
import Aproject.Aprojectsystem.XSDSchema.Order;
import Aproject.Aprojectsystem.database.AddToBase;
import Aproject.Aprojectsystem.database.GetFromBase;
import Aproject.Aprojectsystem.database.classes.OrderDb;
import Aproject.Aprojectsystem.database.dao.ClientDao;
import Aproject.Aprojectsystem.database.dao.OrderDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@RestController
public class PunchBroker {

    private final BrokerReceiver brokerReceiver;
    private AddToBase addToBase;

    @Autowired
    ClientDao clientDao;
    OrderDao orderDao;

    @Autowired
    public PunchBroker(BrokerReceiver brokerReceiver,
                       AddToBase addToBase,
                       OrderDao orderDao) {
        this.brokerReceiver = brokerReceiver;
        this.addToBase = addToBase;
        this.orderDao = orderDao;
    }

    //Тест
    @RequestMapping("/hello/{name}")
    String hello(@PathVariable String name) {

        OrderDb orderDb = new OrderDb();
        List<OrderDb> orderDbList = new LinkedList<>();

        orderDb.setOrderGroupId(43);
        orderDb.setQuantity(15);
        orderDb.setProductId(1);
        orderDb.setUserId(3);
        orderDb.setDate(new Date());
        orderDbList.add(orderDb);

        OrderDb orderDb2 = new OrderDb();
        orderDb2.setOrderGroupId(43);
        orderDb2.setQuantity(2);
        orderDb2.setProductId(2);
        orderDb2.setUserId(3);
        orderDb2.setDate(new Date());
        orderDbList.add(orderDb2);

        orderDao.addNewOrder(orderDbList, 3);

        return "Hi "+ name + " !";

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
