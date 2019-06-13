package service;

import meserreurs.MonException;
import javax.persistence.*;

import metier.*;

import java.util.List;

public class ServiceReservation {

    private EntityManagerFactory factory;
    private  EntityManager entityManager;

    public  void insertionReservation(ReservationEntity uneR) throws Exception, MonException {

        factory = Persistence.createEntityManagerFactory("PInscription");
        entityManager  = factory.createEntityManager();

        try {
            if (!entityManager.contains(uneR)) {
                entityManager.getTransaction().begin();
                entityManager.persist(uneR);
                entityManager.flush();
                entityManager.getTransaction().commit();
            }
            entityManager.close();

        } catch (EntityNotFoundException h) {
            new MonException("Erreur d'insertion", h.getMessage());
        } catch (Exception e) {
            new MonException("Erreur d'insertion", e.getMessage());
        }

    }

    public  void updateReservation(ReservationEntity uneR) throws Exception, MonException {

        factory = Persistence.createEntityManagerFactory("PInscription");
        entityManager  = factory.createEntityManager();

        try {
            entityManager.getTransaction().begin();
            entityManager.merge(uneR);
            entityManager.flush();
            entityManager.getTransaction().commit();
            entityManager.close();

        } catch (EntityNotFoundException h) {
            new MonException("Erreur d'insertion", h.getMessage());
        } catch (Exception e) {
            new MonException("Erreur d'insertion", e.getMessage());
        }

    }

    public List<ReservationEntity> consulterReservations() throws MonException {

        factory = Persistence.createEntityManagerFactory("PInscription");
        entityManager = factory.createEntityManager();

        List<ReservationEntity> mesRes = null;
        mesRes = (List<ReservationEntity>)
                entityManager.createQuery(
                        "SELECT a FROM ReservationEntity a ").getResultList();
        entityManager.close();

        return mesRes;
    }

    public BorneEntity getBorneEntityById(int id){

        factory = Persistence.createEntityManagerFactory("PInscription");
        entityManager  = factory.createEntityManager();

        List<BorneEntity> borneList ;

        borneList = (List<BorneEntity> ) entityManager.createQuery("SELECT b FROM BorneEntity b WHERE b.idBorne="+id).getResultList();

        if(borneList.size() == 0 ) return null ;

        return borneList.get(0);
    }

    public VehiculeEntity getVehiculeById(int id){

        factory = Persistence.createEntityManagerFactory("PInscription");
        entityManager  = factory.createEntityManager();

        List<VehiculeEntity> list;

        list  = (List<VehiculeEntity>)  entityManager.createQuery("SELECT  v FROM VehiculeEntity  v WHERE v.id="+id).getResultList();

        if( list.size() == 0 ) return null;

        return list.get(0);
    }
}