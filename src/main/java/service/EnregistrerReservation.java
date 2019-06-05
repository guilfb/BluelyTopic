package service;

import meserreurs.MonException;
import javax.persistence.*;
import metier.InscriptionEntity;
import metier.ReservationEntity;

import java.util.ArrayList;
import java.util.List;

public class EnregistrerReservation {

  // on décvlare un EntityManager
    private EntityManagerFactory factory;
    private  EntityManager entityManager;

    public  void insertionReservation(ReservationEntity uneR) throws Exception, MonException {

       // On instancie l'entity Manager
        factory = Persistence.createEntityManagerFactory("PInscription");
        entityManager  = factory.createEntityManager();

        try {

            if (!entityManager.contains(uneR))
            {
                // On démarre une transaction
                entityManager.getTransaction().begin();
                // On recherche si la reservation existe  existe deja
                //ReservationEntity reserv = entityManager.find(ReservationEntity.class,uneR.getVehicule());
                ReservationEntity reserv = new ReservationEntity();
                List<ReservationEntity> reservList = new ArrayList<ReservationEntity>();

                reservList = (List<ReservationEntity>)entityManager.createQuery(String.format("SELECT r FROM ReservationEntity r  WHERE r.vehicule=%d AND r.client= %d AND r.dateReservation=%s", uneR.getVehicule(), uneR.getClient(), uneR.getDateReservation())
                ).getResultList();


                if( reservList.size() == 0 )
                    entityManager.persist(uneR);
                else
                    entityManager.merge(uneR);

                /*
                if( reserv==null ){
                    entityManager.persist(uneR);
                }
                else
                    entityManager.merge(uneR);

                */

                entityManager.persist(uneR);
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

