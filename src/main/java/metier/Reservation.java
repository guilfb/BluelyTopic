package metier;

import java.io.Serializable;
import java.sql.Timestamp;

public class Reservation implements Serializable {
    private Vehicule vehicule;
    private Client client;
    private Timestamp dateReservation;
    private Timestamp dateEcheance;

    public Vehicule getVehicule() {
        return vehicule;
    }
    public void setVehicule(Vehicule vehicule) {
        this.vehicule = vehicule;
    }

    public Client getClient() {
        return client;
    }
    public void setClient(Client client) {
        this.client = client;
    }

    public Timestamp getDateReservation() {
        return dateReservation;
    }
    public void setDateReservation(Timestamp dateReservation) {
        this.dateReservation = dateReservation;
    }

    public Timestamp getDateEcheance() {
        return dateEcheance;
    }
    public void setDateEcheance(Timestamp dateEcheance) {
        this.dateEcheance = dateEcheance;
    }
}
