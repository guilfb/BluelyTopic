package service;

import meserreurs.MonException;
import metier.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;

public class ServiceUtilise {

    private EntityManagerFactory factory;
    private EntityManager entityManager;

    public void insertUtilise( Utilise utilise){

        factory = Persistence.createEntityManagerFactory("PInscription");
        entityManager  = factory.createEntityManager();

        try {
            ServiceReservation unServiceReservation = new ServiceReservation();

            UtiliseEntity utiliseEntity = new UtiliseEntity();
            utiliseEntity.setClient(utilise.getClient());
            utiliseEntity.setDate(utilise.getDate());
            utiliseEntity.setVehicule(utilise.getVehicule());
            utiliseEntity.setBorneDepart(utilise.getBorneDepart());

            entityManager.getTransaction().begin();
            entityManager.persist(utiliseEntity);
            entityManager.flush();
            entityManager.getTransaction().commit();

            VehiculeEntity vehicule = unServiceReservation.getVehiculeById(utiliseEntity.getVehicule());

            vehicule.setDisponibilite("UTILISE");

            entityManager.getTransaction().begin();
            entityManager.merge(vehicule);
            entityManager.flush();
            entityManager.getTransaction().commit();

            BorneEntity borne = unServiceReservation.getBorneEntityById(utiliseEntity.getBorneDepart());

            borne.setEtatBorne((byte)1);
            borne.setVehicule(null);

            entityManager.getTransaction().begin();
            entityManager.merge(borne);
            entityManager.flush();
            entityManager.getTransaction().commit();
            entityManager.close();

        }
        catch (EntityNotFoundException h) {
            new MonException("Erreur d'insertion", h.getMessage());
            System.out.println("Erreur d'insertion " + h.getMessage());
        } catch (Exception e) {
            new MonException("Erreur d'insertion", e.getMessage());
            System.out.println(e.getCause());
            e.printStackTrace();
            System.out.println("Erreur d'insertion2 " + e.getMessage());
        }

    }

    public void updateUtilise(Utilise utilise){

        factory = Persistence.createEntityManagerFactory("PInscription");
        entityManager  = factory.createEntityManager();

        try{
            ServiceReservation unServiceReservation = new ServiceReservation();

            UtiliseEntity utiliseEntity = new UtiliseEntity();
            utiliseEntity.setClient(utilise.getClient());
            utiliseEntity.setDate(utilise.getDate());
            utiliseEntity.setBorneArrivee(utilise.getBorneArrivee());
            utiliseEntity.setVehicule(utilise.getVehicule());
            utiliseEntity.setBorneDepart(utilise.getBorneDepart());

            VehiculeEntity vehicule = unServiceReservation.getVehiculeById(utiliseEntity.getVehicule());
            BorneEntity borneArrivee = unServiceReservation.getBorneEntityById(utiliseEntity.getBorneArrivee());

            borneArrivee.setEtatBorne((byte)0);
            borneArrivee.setVehicule(vehicule);

            vehicule.setDisponibilite("LIBRE");
            vehicule.setLongitude(borneArrivee.getStation().getLongitude());
            vehicule.setLatitude(borneArrivee.getStation().getLatitude());

            entityManager.getTransaction().begin();
            entityManager.merge(utiliseEntity);
            entityManager.flush();
            entityManager.getTransaction().commit();

            entityManager.getTransaction().begin();
            entityManager.merge(vehicule);
            entityManager.flush();
            entityManager.getTransaction().commit();

            entityManager.getTransaction().begin();
            entityManager.merge(borneArrivee);
            entityManager.flush();
            entityManager.getTransaction().commit();
            entityManager.close();

        }
        catch (EntityNotFoundException h) {
            new MonException("Erreur d'insertion", h.getMessage());
            System.out.println(h.getMessage());
            System.out.println(h.getCause());
            h.printStackTrace();
        } catch (Exception e) {
            new MonException("Erreur d'insertion", e.getMessage());
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            e.printStackTrace();
        }

    }


}