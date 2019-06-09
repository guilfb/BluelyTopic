package service;

import meserreurs.MonException;
import javax.persistence.*;
import javax.ws.rs.client.Client;

import metier.ClientEntity;

import java.util.List;


public class ServiceInscription {

    // on declare un EntityManager
    private EntityManagerFactory factory;
    private  EntityManager entityManager;

    public  void insertionInscription(ClientEntity unClient) throws Exception, MonException {

        // On instancie l'entity Manager
        factory = Persistence.createEntityManagerFactory("PInscription");
        entityManager  = factory.createEntityManager();

        try {

            if (!entityManager.contains(unClient))
            {
                entityManager.getTransaction().begin();
                entityManager.persist(unClient);
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

}

