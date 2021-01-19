package Aproject.Aprojectsystem.database.classes;

import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.List;

public class OrderDb {

    @Id
    protected int id;
    protected String orderGroupId;
    protected int quantity;
    protected Date date;
    protected List<ProductDb> product;


}
