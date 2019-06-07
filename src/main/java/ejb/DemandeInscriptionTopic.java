package ejb;

import meserreurs.MonException;
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
import service.EnregistreReservation;

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

                Reservation uneReservation = (Reservation) objectMessage.getObject();

                message = null;
                try {
                    ReservationEntity uneResaEntity = new ReservationEntity();

                    uneResaEntity.setDateEcheance(uneReservation.getDateEcheance());
                    uneResaEntity.setDateReservation(uneReservation.getDateReservation());
                    uneResaEntity.setClient(uneReservation.getClient().getIdClient());
                    uneResaEntity.setVehicule(uneReservation.getVehicule().getIdVehicule());

                    EnregistreReservation uneE = new EnregistreReservation();
                    uneE.insertionInscription(uneResaEntity);
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
