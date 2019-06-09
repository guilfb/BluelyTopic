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
import service.ServiceUtilise;

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

        if (message != null) {
            ObjectMessage objectMessage = (ObjectMessage) message;
            try {
                Client client = new Client();
                Reservation reservation = new Reservation();
                Utilise utilise = new Utilise();

                System.out.println("TESSSSSSSSSSSSST");
                System.out.println(objectMessage);
                System.out.println("object " + objectMessage.getObject());
                System.out.println("object1 " + objectMessage.getObject().getClass());
                System.out.println("object2 " + objectMessage.getObject().getClass().isInstance(utilise));

                if(objectMessage.getObject().getClass().isInstance(reservation)){
                    this.handleReservetation(objectMessage);
                }
                else if(objectMessage.getObject().getClass().isInstance(client)){
                    this.handleInscription(objectMessage);
                }
                else if(objectMessage.getObject().getClass().isInstance(utilise)) {
                    this.handleUtilise(objectMessage);
                }
            } catch (NamingException er) {
                EcritureErreur(er.getMessage());
                System.out.println(er.getMessage());
            } catch (MonException e) {
                EcritureErreur(e.getMessage());
                System.out.println(e.getMessage());
            } catch (Exception ex) {
                EcritureErreur(ex.getMessage());
                System.out.println(ex.getMessage());
            }
        }
    }

    public void handleReservetation(ObjectMessage objectMessage ) throws JMSException {

        System.out.println("**** Demande de reservation ****");
        Reservation uneReservation = (Reservation) objectMessage.getObject();

        try {
            ReservationEntity uneReservationEntity = new ReservationEntity();
            uneReservationEntity.setClient(uneReservation.getClient().getIdClient());
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

        System.out.println("**** Demande d'inscription ****");

        Client data = (Client) objectMessage.getObject();
        ClientEntity unClient = new ClientEntity();

        unClient.setIdClient(data.getIdClient());
        unClient.setDateNaissance(data.getDateNaissance());
        unClient.setPrenom(data.getPrenom());
        unClient.setNom(data.getNom());
        unClient.setLogin(data.getLogin());
        unClient.setMotdepasse(data.getMotdepasse());

        ServiceInscription uneE = new ServiceInscription();
        uneE.insertionInscription(unClient);
    }

    private void handleUtilise(ObjectMessage objectMessage) throws Exception {
        System.out.println("**** Utilisation ****");

        Utilise utilise = (Utilise)objectMessage.getObject();

        ServiceUtilise service = new ServiceUtilise();

        if(utilise.getBorneArrivee() == null) {
            service.insertUtilise(utilise);
        }else{
            service.updateUtilise(utilise);
        }
    }

    public void EcritureErreur(String message) {
        BufferedWriter wr;
        String nomf = "erreurs.log";
        Date madate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy hh:mm");
        try {
            // On écrit à la fin du fichier
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
}
