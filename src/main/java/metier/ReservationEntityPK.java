package metier;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

public class ReservationEntityPK implements Serializable {
    private int vehicule;
    private int client;
    private Timestamp dateReservation;

    @Column(name = "vehicule")
    @Id
    public int getVehicule() {
        return vehicule;
    }

    public void setVehicule(int vehicule) {
        this.vehicule = vehicule;
    }

    @Column(name = "client")
    @Id
    public int getClient() {
        return client;
    }

    public void setClient(int client) {
        this.client = client;
    }

    @Column(name = "date_reservation")
    @Id
    public Timestamp getDateReservation() {
        return dateReservation;
    }

    public void setDateReservation(Timestamp dateReservation) {
        this.dateReservation = dateReservation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReservationEntityPK that = (ReservationEntityPK) o;
        return vehicule == that.vehicule &&
                client == that.client &&
                Objects.equals(dateReservation, that.dateReservation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vehicule, client, dateReservation);
    }
}
