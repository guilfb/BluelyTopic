package metier;

import java.io.Serializable;

public class Borne implements Serializable {
    private int idBorne;
    private byte etatBorne;
    private Station station;
    private Vehicule vehicule;

    public int getIdBorne() {
        return idBorne;
    }
    public void setIdBorne(int idBorne) {
        this.idBorne = idBorne;
    }

    public byte getEtatBorne() {
        return etatBorne;
    }
    public void setEtatBorne(byte etatBorne) {
        this.etatBorne = etatBorne;
    }

    public Station getStation() {
        return station;
    }
    public void setStation(Station station) {
        this.station = station;
    }

    public Vehicule getVehicule() {
        return vehicule;
    }
    public void setVehicule(Vehicule vehicule) {
        this.vehicule = vehicule;
    }

}
