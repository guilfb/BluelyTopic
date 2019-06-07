package metier;

import java.io.Serializable;
import java.math.BigDecimal;

public class Vehicule implements Serializable {
    private int idVehicule;
    private int rfid;
    private Integer etatBatterie;
    private String disponibilite;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private TypeVehicule typeVehicule;

    public int getIdVehicule() {
        return idVehicule;
    }
    public void setIdVehicule(int idVehicule) {
        this.idVehicule = idVehicule;
    }

    public int getRfid() {
        return rfid;
    }
    public void setRfid(int rfid) {
        this.rfid = rfid;
    }

    public Integer getEtatBatterie() {
        return etatBatterie;
    }
    public void setEtatBatterie(Integer etatBatterie) {
        this.etatBatterie = etatBatterie;
    }

    public String getDisponibilite() {
        return disponibilite;
    }
    public void setDisponibilite(String disponibilite) {
        this.disponibilite = disponibilite;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }
    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }
    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public TypeVehicule getTypeVehicule() {
        return typeVehicule;
    }
    public void setTypeVehicule(TypeVehicule typeVehicule) {
        this.typeVehicule = typeVehicule;
    }

}
