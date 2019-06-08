package metier;

import java.io.Serializable;
import java.sql.Timestamp;

public class Utilise implements Serializable {
    private int vehicule;
    private int client;
    private Timestamp date;
    private int borneDepart;
    private int borneArrivee;

    public int getVehicule() {
        return vehicule;
    }
    public void setVehicule(int vehicule) {
        this.vehicule = vehicule;
    }

    public int getClient() {
        return client;
    }
    public void setClient(int client) {
        this.client = client;
    }

    public Timestamp getDate() {
        return date;
    }
    public void setDate(Timestamp date) {
        this.date = date;
    }

    public int getBorneDepart() {
        return borneDepart;
    }
    public void setBorneDepart(int borneDepart) {
        this.borneDepart = borneDepart;
    }

    public int getBorneArrivee() {
        return borneArrivee;
    }
    public void setBorneArrivee(int borneArrivee) {
        this.borneArrivee = borneArrivee;
    }
}
