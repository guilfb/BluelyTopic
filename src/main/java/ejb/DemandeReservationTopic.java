package ejb;

import meserreurs.MonException;
import metier.*;

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

import service.ServiceInscription;
import service.ServiceReservation;

/**
 * Message-Driven Bean implementation class for: DemandeReservationTopic
 */
// On se connecte � la file d'attente InscriptionTopic
@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "java:jboss/exported/topic/DemandeInscriptionJmsTopic"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic")}, mappedName = "DemandeInscriptionJmsTopic")
public class DemandeReservationTopic implements MessageListener {

    @Resource
    private MessageDrivenContext context;

    /*
     * Default constructor.
     */
    public DemandeReservationTopic() {
        // TODO Auto-generated constructor stub
    }
    /**
     * @see MessageListener#onMessage(Message)
     */
    public void onMessage(Message message) {
        // TODO Auto-generated method stub
        boolean ok = false;
        // On g�re le message r�cup�r� dans le topic

        if( message == null ) return ;

        ObjectMessage objectMessage = (ObjectMessage) message;

        try{
            if( objectMessage instanceof ReservationEntity){
                this.handleReservetation(objectMessage);
            }
            else if(objectMessage instanceof ClientEntity){
                this.handleInscription(objectMessage);
            }

        }
        catch (JMSException jmse) {
            System.out.println(jmse.getMessage());
            EcritureErreur(jmse.getMessage());
            context.setRollbackOnly();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    /**
     *
     * @param objectMessage
     */
    public void handleReservetation(ObjectMessage objectMessage ) throws JMSException {

        // On transforme le message en demande de reservation
        System.out.println("DEMANDE RESERVATION TOPIC je suis la ");
        ReservationEntity uneReservation = (ReservationEntity) objectMessage.getObject();
        // On insere cette demande de reservation dans la base de donn�es
        // on s'assure que l'ecriture ne se fera qu'une fois.
        try {
            // on construit un objet Entity
            ReservationEntity uneReservationEntity = new ReservationEntity();

            // on tansfere les donnees recues dans l'objet Entity
            uneReservationEntity.setClient(uneReservation.getClient());
            uneReservationEntity.setDateEcheance(uneReservation.getDateEcheance());
            uneReservationEntity.setDateReservation(uneReservation.getDateReservation());
            uneReservationEntity.setVehicule(uneReservation.getVehicule());

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
        ClientEntity data = (ClientEntity) objectMessage.getObject();
        ClientEntity unClient = new ClientEntity();

        // on tansfere les donnees recues dans l'objet Entity
        unClient.setNom( data.getNom());
        unClient.setIdClient(data.getIdClient());
        unClient.setDateNaissance(data.getDateNaissance());
        unClient.setPrenom(data.getPrenom());

        ServiceInscription uneE = new ServiceInscription();
        uneE.insertionInscription(unClient);
    }

    /**
     * Permet d'enregistrer une erreur dans un fichier log
     *
     * @param message Le message d'erreur
     */
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
            ;
        } catch (IOException eio) {
            ;
        }
    }
}
