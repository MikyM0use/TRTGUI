package TRTEmail;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
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

    private String smtpServer;
    private String destination;
    private String user;
    private String pass;
    private boolean requireAuth;

    public TRTEmail(String smtpServer, String destination, boolean requireAuth, String user, String pass) {
        this.smtpServer = smtpServer;
        this.destination = destination;
        this.requireAuth = requireAuth;
        this.user = user;
        this.pass = pass;
    }

    public TRTEmail(String smtpServer, String destination, boolean requireAuth) {
        this.smtpServer = smtpServer;
        this.destination = destination;
        this.requireAuth = requireAuth;

        if (requireAuth) //TODO window for error
        {
            System.out.println("ERROR: request auth without user and pass");
        }
    }

    public TRTEmail(String smtpServer, String destination) {
        this.smtpServer = smtpServer;
        this.destination = destination;
        this.requireAuth = false;
    }

    public void send(String subject, String text) {
        if (destination.isEmpty()) //TODO validate email address (regex?)
        {

            return; //TODO add error window
        }
        Session session;
        Properties properties = new Properties();
        if (this.isRequiredAuth()) {
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.starttls.enable", "true");
            properties.setProperty("mail.smtp.host", this.getSmtpServer());
            properties.put("mail.smtp.port", "587");

            session = Session.getInstance(properties,
                    new javax.mail.Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(user,pass);
                }
            });
        } else {
            properties.setProperty("mail.smtp.host", this.getSmtpServer());
            session = Session.getDefaultInstance(properties, null);
        }

        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("admin@TRTGUI.com"));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(this.getDestination()));
            msg.setSubject(subject);
            msg.setText(text);
            Transport.send(msg);

        } catch (AddressException e) {
        } catch (MessagingException e) {
        }
    }

    /**
     * @return the smtpServer
     */
    public String getSmtpServer() {
        return smtpServer;
    }

    /**
     * @param smtpServer the smtpServer to set
     */
    public void setSmtpServer(String smtpServer) {
        this.smtpServer = smtpServer;
    }

    /**
     * @return the destination
     */
    public String getDestination() {
        return destination;
    }

    /**
     * @param destination the destination to set
     */
    public void setDestination(String destination) {
        this.destination = destination;
    }

    /**
     * @return the requireAuth
     */
    public boolean isRequiredAuth() {
        return requireAuth;
    }

    /**
     * @param requireAuth the requireAuth to set
     */
    public void setRequiredAuth(boolean requireAuth) {
        this.requireAuth = requireAuth;
    }

    /**
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * @return the pass
     */
    public String getPass() {
        return pass;
    }

    /**
     * @param pass the pass to set
     */
    public void setPass(String pass) {
        this.pass = pass;
    }
}
