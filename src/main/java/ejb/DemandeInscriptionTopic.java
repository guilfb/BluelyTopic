package ejb;

import meserreurs.MonException;
import metier.Client;
import metier.ClientEntity;
import metier.Reservation;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.naming.NamingException;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import metier.ReservationEntity;
import service.ServiceInscription;
import service.ServiceReservation;

@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "java:jboss/exported/topic/DemandeInscriptionJmsTopic"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic")}, mappedName = "DemandeInscriptionJmsTopic")
public class DemandeInscriptionTopic implements MessageListener {

    @Resource
    private MessageDrivenContext context;

    public DemandeInscriptionTopic() {
        // TODO Auto-generated constructor stub
    }

    public void onMessage(Message message) {
        // TODO Auto-generated method stub
        boolean ok = false;

        try {
            if (message != null) {
                ObjectMessage objectMessage = (ObjectMessage) message;

                System.out.println(objectMessage);
                System.out.println(objectMessage.getObject());
                System.out.println(objectMessage.getObject() instanceof Reservation);
                System.out.println(objectMessage.getObject() instanceof ReservationEntity);

               // Reservation uneReservation = (Reservation) objectMessage.getObject();
               // message = null;

                try {
                    if( objectMessage instanceof Reservation){
                        this.handleReservetation(objectMessage);
                    }
                    else if(objectMessage instanceof Client){
                        this.handleInscription(objectMessage);
                    }

                } catch (NamingException er) {
                    EcritureErreur(er.getMessage());
                } catch (MonException e) {
                    EcritureErreur(e.getMessage());
                } catch (Exception ex) {
                    EcritureErreur(ex.getMessage());
                }
            }
        } catch (JMSException jmse) {
            System.out.println(jmse.getMessage());
            EcritureErreur(jmse.getMessage());
            context.setRollbackOnly();
        }
    }

    public void EcritureErreur(String message) {
        BufferedWriter wr;
        String nomf = "erreurs.log";
        Date madate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy hh:mm");
        try {
            // On �crit � la fin du fichier
            wr = new BufferedWriter(new FileWriter(nomf, true));
            wr.newLine();
            wr.write(sdf.format(madate));
            wr.newLine();
            wr.write(message);
            wr.close();
        } catch (FileNotFoundException ef) {
            System.out.println(ef.getMessage());
        } catch (IOException eio) {
            System.out.println(eio.getMessage());
        }
    }


    /**
     *
     * @param objectMessage
     */
    public void handleReservetation(ObjectMessage objectMessage ) throws JMSException {

        // On transforme le message en demande de reservation
        System.out.println("DEMANDE RESERVATION TOPIC je suis la ");
        Reservation uneReservation = (Reservation) objectMessage.getObject();
        // On insere cette demande de reservation dans la base de donn�es
        // on s'assure que l'ecriture ne se fera qu'une fois.
        try {
            // on construit un objet Entity
            ReservationEntity uneReservationEntity = new ReservationEntity();

            // on tansfere les donnees recues dans l'objet Entity
            uneReservationEntity.setClient(uneReservation.getClient().getIdClient() );
            uneReservationEntity.setDateEcheance(uneReservation.getDateEcheance());
            uneReservationEntity.setDateReservation(uneReservation.getDateReservation());
            uneReservationEntity.setVehicule(uneReservation.getVehicule().getIdVehicule());

            ServiceReservation uneE = new ServiceReservation();
            uneE.insertionReservation(uneReservationEntity);
        } catch (NamingException er) {
            EcritureErreur(er.getMessage());
        } catch (MonException e) {
            EcritureErreur(e.getMessage());
        } catch (Exception ex) {
            EcritureErreur(ex.getMessage());
        }

    }


    public void handleInscription(ObjectMessage objectMessage ) throws Exception {
        // On transforme le message en demande d'une inscription
        System.out.println("**** DEMANDE d'inscription TOPIC je suis la **** ");
        Client data = (Client) objectMessage.getObject();
        ClientEntity unClient = new ClientEntity();

        // on tansfere les donnees recues dans l'objet Entity
        unClient.setNom( data.getNom());
        unClient.setIdClient(data.getIdClient());
        unClient.setDateNaissance(data.getDateNaissance());
        unClient.setPrenom(data.getPrenom());

        ServiceInscription uneE = new ServiceInscription();
        uneE.insertionInscription(unClient);
    }
}
