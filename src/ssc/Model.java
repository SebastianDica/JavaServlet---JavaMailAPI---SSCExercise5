package ssc;

import java.util.ArrayList;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
/**
 * Model class for this system.
 * It manages logging in and sending messages
 * using the JavaMailAPI.
 * @author Sebastian
 *
 */
public class Model {
	//private IMAPFolder folder = null;
	private Store store = null;
	private Transport transport = null; private Session session;
	private String smtphost = "smtp.gmail.com";
	public Model()
	{
		
	}
	/**
	 * This method attempts to login a user.
	 * @param username The username of the user.
	 * @param password The password of the user.
	 * @return Whether it has been successful or not.
	 */
	public boolean login(String username, String password)
	{
		try 
		{
			//Setting a few properties for the connection
			Properties props = System.getProperties();
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", smtphost);
			props.put("mail.smtp.port", "587");
			props.setProperty("mail.store.protocol", "imaps");
			props.setProperty("mail.user", username);
			props.setProperty("mail.password", password);
			//Creating a session for the connection with the properties.
			session = Session.getDefaultInstance(props);
			store = session.getStore("imaps");
			//Connecting to the store.
			store.connect("imap.googlemail.com",username, password);
			//Connecting to the transport method/ for sending e-mails.
			transport = session.getTransport("smtp");
			transport.connect(smtphost, username, password);
			return true;
		} 
		catch (Exception e) 
		{
		} 
		return false;
	}
	/**
	 * Sending a message via the specified user, using
	 * the transport created from their credentials
	 * @param from From who will this mail be sent
	 * @param to To whom will this mail be sent
	 * @param cc To whom will this mail be send
	 * @param subject What subject should the mail have
	 * @param content What content should the mail have
	 * @return Whether it has been succesfull or not.
	 */
	public boolean sendMessage(String from, String to, ArrayList<String> cc, String subject, String content)
	{
		//Creating a mime message using the current session.
		MimeMessage message = new MimeMessage(session);
		try
		{
			//Setting the details of the message.
			message.setFrom(new InternetAddress(from));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(to));
			InternetAddress[] ccAddresses = new InternetAddress[cc.size()];
			for(int i = 0 ; i < cc.size() ; i++)
			{
				ccAddresses[i] = new InternetAddress(cc.get(i));
			}
			message.setRecipients(Message.RecipientType.CC, ccAddresses);
			message.setSubject(subject);
			MimeBodyPart messageBodyPart =  new MimeBodyPart();
			messageBodyPart.setText(content);
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);
			message.setContent(multipart);
			message.saveChanges();
			//Attempting to send the message.
			transport.sendMessage(message, message.getAllRecipients());
			return true;
		} 
		catch (Exception e) 
		{
			
		}
		return false;
	}
	/**
	 * This method closes the connection of the store.
	 */
	public void close()
	{
		if (store != null) 
		{ 
			try 
			{
				store.close();
			} 
			catch (MessagingException e) 
			{
				e.printStackTrace();
			} 
		}
	}
}
