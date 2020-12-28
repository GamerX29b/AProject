package classes;

import java.io.Serializable;

public class Client implements Serializable {
    private static final long serialVersionUID = 45465446546546546L;
    private int id;
    private String clientName;
    private String clientAdres;

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

    public String getClientAdres() {
        return clientAdres;
    }

    public void setClientAdres(String clientAdres) {
        this.clientAdres = clientAdres;
    }
}
