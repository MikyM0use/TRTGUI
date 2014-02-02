package TRTEmail;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author michele
 */
public class TRTEmail {

    String trtsubject;
    String trtmsg;
    String trtdest;
    String trtsmtp;

    public TRTEmail(String subject, String dest, String smtp) {
        this.trtsubject = subject;
        this.trtdest = dest;
        this.trtsmtp = smtp;
        this.trtmsg = "";
    }

    public TRTEmail(String subject, String msg, String dest, String smtp) {
        this.trtsubject = subject;
        this.trtdest = dest;
        this.trtsmtp = smtp;
        this.trtmsg = msg;
    }

    public void send() {
        if (trtdest.isEmpty()  ) //TODO validate email address (regex?)
        {
            
            return; //TODO add error window
        }
        Properties properties = new Properties();
        properties.setProperty("mail.smtp.host", trtsmtp);
        Session session = Session.getDefaultInstance(properties, null);

        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("admin@TRTGUI.com"));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(trtdest));
            msg.setSubject(trtsubject);
            msg.setText(trtmsg);
            Transport.send(msg);
            //System.out.println("sending to "+trtdest+" smtp "+trtsmtp);

        } catch (AddressException e) {
        } catch (MessagingException e) {
        }
    }
}
