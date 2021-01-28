package Aproject.Aprojectsystem.ejbClasses;

import javax.ejb.Remote;

    @Remote
    public interface PunchBrokerAnswer {

        void getClientById(int idClient);
        
}
