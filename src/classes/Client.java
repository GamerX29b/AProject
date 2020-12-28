package classes;

import java.io.Serializable;

public class Client implements Serializable {
    private static final long serialVersionUID = 45465446546546546L;
    private int id;
    private String clientName;
    private String clientAddress;

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
}
