package service;

import meserreurs.MonException;
import metier.InscriptionEntity;
import metier.ReservationEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;

public class EnregistreReservation {

  // on décvlare un EntityManager
    private EntityManagerFactory factory;
    private EntityManager entityManager;

    public  void insertionInscription(ReservationEntity uneReservation) throws Exception, MonException {

       // On instancie l'entity Manager
        factory = Persistence.createEntityManagerFactory("PInscription");
        entityManager  = factory.createEntityManager();

        try {

            if (!entityManager.contains(uneReservation))
            {
                // On démarre une transaction
                entityManager.getTransaction().begin();
                entityManager.persist(uneReservation);
                entityManager.flush();
                // on valide la transacition
                entityManager.getTransaction().commit();
            }
            entityManager.close();

        } catch (EntityNotFoundException h) {
            new MonException("Erreur d'insertion", h.getMessage());
        } catch (Exception e) {
            new MonException("Erreur d'insertion", e.getMessage());
        }
    }
}

