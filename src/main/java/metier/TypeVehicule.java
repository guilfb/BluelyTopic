package metier;

import java.io.Serializable;

public class TypeVehicule implements Serializable {
    private int idTypeVehicule;
    private String categorie;
    private String typeVehicule;

    public int getIdTypeVehicule() {
        return idTypeVehicule;
    }
    public void setIdTypeVehicule(int idTypeVehicule) {
        this.idTypeVehicule = idTypeVehicule;
    }

    public String getCategorie() {
        return categorie;
    }
    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getTypeVehicule() {
        return typeVehicule;
    }
    public void setTypeVehicule(String typeVehicule) {
        this.typeVehicule = typeVehicule;
    }

}
