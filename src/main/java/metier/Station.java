package metier;

import java.io.Serializable;
import java.math.BigDecimal;

public class Station implements Serializable {
    private int idStation;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String adresse;
    private Integer numero;
    private String ville;
    private Integer codePostal;

    public int getIdStation() {
        return idStation;
    }
    public void setIdStation(int idStation) {
        this.idStation = idStation;
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

    public String getAdresse() {
        return adresse;
    }
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Integer getNumero() {
        return numero;
    }
    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getVille() {
        return ville;
    }
    public void setVille(String ville) {
        this.ville = ville;
    }

    public Integer getCodePostal() {
        return codePostal;
    }
    public void setCodePostal(Integer codePostal) {
        this.codePostal = codePostal;
    }
}
