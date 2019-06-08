package service;

import meserreurs.MonException;
import metier.BorneEntity;
import metier.StationEntity;
import metier.UtiliseEntity;
import metier.VehiculeEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import java.util.List;

public class ServiceUtilise {

    // on declare un EntityManager
    private EntityManagerFactory factory;
    private EntityManager entityManager;

    public void insertUtilise( UtiliseEntity utiliseEntity){

        System.out.println("***** Dans methode InsertUtilise *******\n");
        System.out.println("Utilise Entity recu = ");
        System.out.println("Utilise vehicule id= "+utiliseEntity.getVehicule());
        System.out.println("Utilise client id= "+utiliseEntity.getClient());
        System.out.println("Utilise Date ="+utiliseEntity.getDate());


        // On instancie l'entity Manager
        factory = Persistence.createEntityManagerFactory("PInscription");
        entityManager  = factory.createEntityManager();


        try{

            ServiceReservation unServiceReservation = new ServiceReservation();
            VehiculeEntity vehicule = unServiceReservation.getVehiculeById( utiliseEntity.getVehicule() );
            BorneEntity borne = unServiceReservation.getBorneEntityByVehicleId(utiliseEntity.getVehicule());
            StationEntity stationArrive = this.getStationById(borne.getStation());
            entityManager.getTransaction().begin();

            // Update de la borne d'arrivée
            byte etat=1;
            borne.setEtatBorne(etat);
            borne.setVehicule(utiliseEntity.getVehicule());

            // Update du vehicule utilisée
            vehicule.setDisponibilite("LIBRE");
            vehicule.setLongitude(stationArrive.getLongitude());
            vehicule.setLatitude(stationArrive.getLatitude());

            System.out.println("Les info a update:");
            System.out.println("Borne d'arivee:"+borne.getIdBorne() );
            System.out.println("Station d'arrivee:"+stationArrive.getIdStation() );

            // on insere Utilise Entity
            entityManager.persist(utiliseEntity);
            entityManager.flush();
            entityManager.getTransaction().commit(); // Necessaire ? ou un seul commit a la fin ?


            // ON update les tables
            entityManager.merge(borne);
            entityManager.flush();
            entityManager.getTransaction().commit();
            entityManager.merge(vehicule);
            entityManager.flush();
            entityManager.getTransaction().commit();

            entityManager.close();

        }
        catch (EntityNotFoundException h) {
            new MonException("Erreur d'insertion", h.getMessage());
        } catch (Exception e) {
            new MonException("Erreur d'insertion", e.getMessage());
        }

    }

    public StationEntity getStationById( int id){

        List<StationEntity> list ;
        list = (List<StationEntity>) entityManager.createQuery("SELECT s FROM StationEntity  s WHERE s.id="+id).getResultList();

        if( list.size() == 0 ) return null;

        return list.get(0);
    }


}
