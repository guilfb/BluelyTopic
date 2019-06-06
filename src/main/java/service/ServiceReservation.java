package service;

import meserreurs.MonException;
import javax.persistence.*;

import metier.BorneEntity;
import metier.ReservationEntity;
import metier.VehiculeEntity;

import java.util.List;

public class ServiceReservation {

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
                List<ReservationEntity> reservList;


                reservList = (List<ReservationEntity>)entityManager.createQuery(String.format("SELECT r FROM ReservationEntity r  WHERE r.vehicule=%d AND r.client= %d AND r.dateReservation=%s", uneR.getVehicule(), uneR.getClient(), uneR.getDateReservation())
                ).getResultList();

                BorneEntity borne = this.getBorneEntityByVehicleId(uneR.getVehicule());
                VehiculeEntity vehicule = this.getVehiculeById(uneR.getVehicule());

                if( borne != null ){
                    byte etat = 1;
                    borne.setEtatBorne(etat);
                    borne.setVehicule(null)  ;
                }
                if( vehicule != null ){
                    vehicule.setDisponibilite("RESERVE");
                    vehicule.setLatitude(null);
                    vehicule.setLongitude(null);
                }

                if( reservList.size() == 0 ){
                    entityManager.persist(uneR);
                    entityManager.merge(borne) ;
                    entityManager.merge(vehicule);
                }
                else{ // COrrespond à une fin de reservation , Quand on clique sur bouton Utiliser voiture ???
                    entityManager.merge(uneR);
                }

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


    public BorneEntity getBorneEntityByVehicleId(int id){

        List<BorneEntity> borneList ;

        borneList = (List<BorneEntity> ) entityManager.createQuery("SELECT b FROM BorneEntity b WHERE b.vehicule="+id).getResultList();

        if(borneList.size() == 0 ) return null ;

        return borneList.get(0);
    }

    public VehiculeEntity getVehiculeById(int id){

        List<VehiculeEntity> list;

        list  = (List<VehiculeEntity>)  entityManager.createQuery("SELECT  v FROM VehiculeEntity  v WHERE v.id="+id).getResultList();

        if( list.size() == 0 ) return null;

        return list.get(0);

    }



}

