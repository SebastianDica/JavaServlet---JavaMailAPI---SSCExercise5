package ssc;

import java.util.ArrayList;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class Model {
	//private IMAPFolder folder = null;
	private Store store = null;
	private Transport transport = null; private Session session;
	private String smtphost = "smtp.gmail.com";
	public Model()
	{
		
	}
	public boolean login(String username, String password)
	{
		try 
		{
			Properties props = System.getProperties();
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", smtphost);
			props.put("mail.smtp.port", "587");
			props.setProperty("mail.store.protocol", "imaps");
			props.setProperty("mail.user", username);
			props.setProperty("mail.password", password);
			session = Session.getDefaultInstance(props);
			store = session.getStore("imaps");
			store.connect("imap.googlemail.com",username, password);
			transport = session.getTransport("smtp");
			transport.connect(smtphost, username, password);
			return true;
		} 
		catch (Exception e) 
		{
		} 
		return false;
	}
	public boolean sendMessage(String from, String to, ArrayList<String> cc, String subject, String content)
	{
		MimeMessage message = new MimeMessage(session);
		try
		{
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
			/*for(int i = 0 ; i < currentFileAttachment.size(); i++)
			{
				MimeBodyPart messageBodyPartAttach =  new MimeBodyPart();
				DataSource source = new FileDataSource(currentFileAttachment.get(i));
				messageBodyPartAttach.setDataHandler(new DataHandler(source));
				messageBodyPartAttach.setFileName(currentFileAttachment.get(i).getName());
				multipart.addBodyPart(messageBodyPartAttach);
			}*/
			message.setContent(multipart);
			message.saveChanges();
		}
		catch(Exception e)
		{
			return false;
		}
		try 
		{
			transport.sendMessage(message, message.getAllRecipients());
			return true;
		} 
		catch (Exception e) 
		{
			
		}
		return false;
	}
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
