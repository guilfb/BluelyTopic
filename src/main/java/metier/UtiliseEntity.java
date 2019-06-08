package metier;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "utilise", schema = "autolib", catalog = "")
@IdClass(UtiliseEntityPK.class)
public class UtiliseEntity {
    private int vehicule;
    private int client;
    private Timestamp date;
    private int borneDepart;
    private int borneArrivee;

    @Id
    @Column(name = "Vehicule")
    public int getVehicule() {
        return vehicule;
    }

    public void setVehicule(int vehicule) {
        this.vehicule = vehicule;
    }

    @Id
    @Column(name = "Client")
    public int getClient() {
        return client;
    }

    public void setClient(int client) {
        this.client = client;
    }

    @Id
    @Column(name = "date")
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
        UtiliseEntity that = (UtiliseEntity) o;
        return vehicule == that.vehicule &&
                client == that.client &&
                Objects.equals(date, that.date);
    }

    @Basic
    @Column(name = "borne_depart")
    public int getBorneDepart() {
        return borneDepart;
    }
    public void setBorneDepart(int id) {
        this.borneDepart = id;
    }

    @Basic
    @Column(name = "borne_arrivee")
    public int getBorneArrivee() {
        return this.borneArrivee;
    }
    public void setBorneArrivee(int id) {
        this.borneArrivee = id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(vehicule, client, date);
    }
}
