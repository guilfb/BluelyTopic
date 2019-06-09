package service;

import meserreurs.MonException;
import javax.persistence.*;

import metier.BorneEntity;
import metier.ClientEntity;
import metier.ReservationEntity;
import metier.VehiculeEntity;

import java.util.List;

public class ServiceReservation {

    private EntityManagerFactory factory;
    private  EntityManager entityManager;

    public  void insertionReservation(ReservationEntity uneR) throws Exception, MonException {

        factory = Persistence.createEntityManagerFactory("PInscription");
        entityManager  = factory.createEntityManager();

        System.out.println("TEST1");

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


    public BorneEntity getBorneEntityByVehicleId(int id){

        factory = Persistence.createEntityManagerFactory("PInscription");
        entityManager  = factory.createEntityManager();

        List<BorneEntity> borneList ;

        borneList = (List<BorneEntity> ) entityManager.createQuery("SELECT b FROM BorneEntity b WHERE b.vehicule="+id).getResultList();

        if(borneList.size() == 0 ) return null ;

        return borneList.get(0);
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

    public ClientEntity getClientById(int id){

        factory = Persistence.createEntityManagerFactory("PInscription");
        entityManager  = factory.createEntityManager();

        List<ClientEntity> list;

        list  = (List<ClientEntity>)  entityManager.createQuery("SELECT  v FROM ClientEntity  v WHERE v.id="+id).getResultList();

        if( list.size() == 0 ) return null;

        return list.get(0);

    }



}