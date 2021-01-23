package Aproject.Aprojectsystem.database.classes;

import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.List;

public class OrderDb {

    @Id
    protected int id;
    protected int userId;
    protected int orderGroupId;
    protected int quantity;
    protected Date date;
    protected int productId;
    protected List<ProductDb> product;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getOrderGroupId() {
        return orderGroupId;
    }

    public void setOrderGroupId(int orderGroupId) {
        this.orderGroupId = orderGroupId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getDate() {
        return date;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<ProductDb> getProduct() {
        return product;
    }

    public void setProduct(List<ProductDb> product) {
        this.product = product;
    }
}
