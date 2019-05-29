package metier;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "borne", schema = "autolib", catalog = "")
public class BorneEntity {
    private int idBorne;
    private byte etatBorne;

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
