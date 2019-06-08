package metier;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "client", schema = "autolib", catalog = "")
@NamedQuery(name = "ClientEntity.rechercheNom", query = "select ct  from ClientEntity  ct where ct.login = :login")
public class ClientEntity implements Serializable {
    private int idClient;
    private String login;
    private String motdepasse;
    private String nom;
    private String prenom;
    private Date dateNaissance;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "idClient")
    public int getIdClient() {
        return idClient;
    }
    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }


    @Basic
    @Column(name = "login")
    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }

    @Basic
    @Column(name = "motdepasse")
    public String getMotdepasse() {
        return motdepasse;
    }

    public void setMotdepasse(String motdepasse) {
        this.motdepasse = motdepasse;
    }

    @Basic
    @Column(name = "nom")
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Basic
    @Column(name = "prenom")
    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    @Basic
    @Column(name = "date_naissance")
    public Date getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientEntity that = (ClientEntity) o;
        return idClient == that.idClient &&
                Objects.equals(nom, that.nom) &&
                Objects.equals(prenom, that.prenom) &&
                Objects.equals(dateNaissance, that.dateNaissance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idClient, nom, prenom, dateNaissance);
    }
}