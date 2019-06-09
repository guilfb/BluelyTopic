package metier;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "borne", schema = "autolib", catalog = "")
public class BorneEntity implements Serializable {
    private int idBorne;
    private byte etatBorne;
    private StationEntity station;
    private VehiculeEntity vehicule;

    @Id
    @Column(name = "idBorne")
    public int getIdBorne() {
        return idBorne;
    }
    public void setIdBorne(int idBorne) {
        this.idBorne = idBorne;
    }

    @Basic
    @Column(name = "etatBorne")
    public byte getEtatBorne() {
        return etatBorne;
    }
    public void setEtatBorne(byte etatBorne) {
        this.etatBorne = etatBorne;
    }

    @ManyToOne
    @JoinColumn(name = "station", referencedColumnName = "idStation")
    public StationEntity getStation() {
        return station;
    }
    public void setStation(StationEntity station) {
        this.station = station;
    }

    @ManyToOne
    @JoinColumn(name = "idVehicule", referencedColumnName = "idVehicule")
    public VehiculeEntity getVehicule() {
        return vehicule;
    }
    public void setVehicule(VehiculeEntity vehicule) {
        this.vehicule = vehicule;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BorneEntity that = (BorneEntity) o;
        return idBorne == that.idBorne &&
                etatBorne == that.etatBorne;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idBorne, etatBorne);
    }
}
