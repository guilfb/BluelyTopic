package metier;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

public class UtiliseEntityPK implements Serializable {
    private int vehicule;
    private int client;
    private Timestamp date;

    @Column(name = "Vehicule")
    @Id
    public int getVehicule() {
        return vehicule;
    }

    public void setVehicule(int vehicule) {
        this.vehicule = vehicule;
    }

    @Column(name = "Client")
    @Id
    public int getClient() {
        return client;
    }

    public void setClient(int client) {
        this.client = client;
    }

    @Column(name = "date")
    @Id
    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UtiliseEntityPK that = (UtiliseEntityPK) o;
        return vehicule == that.vehicule &&
                client == that.client &&
                Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vehicule, client, date);
    }
}
