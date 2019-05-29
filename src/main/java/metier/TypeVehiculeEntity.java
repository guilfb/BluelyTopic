package metier;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "type_vehicule", schema = "autolib", catalog = "")
public class TypeVehiculeEntity {
    private int idTypeVehicule;
    private String categorie;
    private String typeVehicule;

    @Id
    @Column(name = "idType_vehicule")
    public int getIdTypeVehicule() {
        return idTypeVehicule;
    }

    public void setIdTypeVehicule(int idTypeVehicule) {
        this.idTypeVehicule = idTypeVehicule;
    }

    @Basic
    @Column(name = "categorie")
    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    @Basic
    @Column(name = "type_vehicule")
    public String getTypeVehicule() {
        return typeVehicule;
    }

    public void setTypeVehicule(String typeVehicule) {
        this.typeVehicule = typeVehicule;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TypeVehiculeEntity that = (TypeVehiculeEntity) o;
        return idTypeVehicule == that.idTypeVehicule &&
                Objects.equals(categorie, that.categorie) &&
                Objects.equals(typeVehicule, that.typeVehicule);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTypeVehicule, categorie, typeVehicule);
    }
}
