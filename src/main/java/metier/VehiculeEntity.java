package metier;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "vehicule", schema = "autolib", catalog = "")
public class VehiculeEntity {
    private int idVehicule;
    private int rfid;
    private Integer etatBatterie;
    private String disponibilite;
    private BigDecimal latitude;
    private BigDecimal longitude;

    @Id
    @Column(name = "idVehicule")
    public int getIdVehicule() {
        return idVehicule;
    }

    public void setIdVehicule(int idVehicule) {
        this.idVehicule = idVehicule;
    }

    @Basic
    @Column(name = "RFID")
    public int getRfid() {
        return rfid;
    }

    public void setRfid(int rfid) {
        this.rfid = rfid;
    }

    @Basic
    @Column(name = "etatBatterie")
    public Integer getEtatBatterie() {
        return etatBatterie;
    }

    public void setEtatBatterie(Integer etatBatterie) {
        this.etatBatterie = etatBatterie;
    }

    @Basic
    @Column(name = "Disponibilite")
    public String getDisponibilite() {
        return disponibilite;
    }

    public void setDisponibilite(String disponibilite) {
        this.disponibilite = disponibilite;
    }

    @Basic
    @Column(name = "latitude")
    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    @Basic
    @Column(name = "longitude")
    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VehiculeEntity that = (VehiculeEntity) o;
        return idVehicule == that.idVehicule &&
                rfid == that.rfid &&
                Objects.equals(etatBatterie, that.etatBatterie) &&
                Objects.equals(disponibilite, that.disponibilite) &&
                Objects.equals(latitude, that.latitude) &&
                Objects.equals(longitude, that.longitude);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idVehicule, rfid, etatBatterie, disponibilite, latitude, longitude);
    }
}
