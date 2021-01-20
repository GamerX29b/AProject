package Aproject.Aprojectsystem.database.classes;

import org.springframework.data.annotation.Id;

import javax.persistence.OneToMany;
import java.util.List;

public class ClientDb {

        @Id
        protected int id;
        protected String clientName;
        protected String clientAddress;

        @OneToMany
        protected List<OrderDb> order;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientAddress() {
        return clientAddress;
    }

    public void setClientAddress(String clientAddress) {
        this.clientAddress = clientAddress;
    }

    public List<OrderDb> getOrder() {
        return order;
    }

    public void setOrder(List<OrderDb> order) {
        this.order = order;
    }
}
