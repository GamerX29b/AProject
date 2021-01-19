package Aproject.Aprojectsystem.database.classes;

import org.springframework.data.annotation.Id;

import javax.xml.bind.annotation.XmlElement;
import java.math.BigInteger;

public class ProductDb {

    @Id
    protected int id;
    protected String nameProduct;
    protected int quantity;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
