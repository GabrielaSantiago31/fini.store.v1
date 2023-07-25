package mail;

import java.util.Properties;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.Authenticator;

public class EmailSender {
	
	String to;
	
	String name;
	
	int orderId;
	
    String from = "px47069@gmail.com";
    
    final String username = "px47069@gmail.com";
 
    final String password = "boogqmyewtfjgxem";
    
    
    public EmailSender(String to, String name, int orderId) {
		super();
		this.to = to;
		this.name = name;
		this.orderId = orderId;
	}

	public void sendAnEmail() {
    	Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.starttls.enable", "true");
	    props.put("mail.smtp.host", "smtp.gmail.com");
	    props.put("mail.smtp.port", "587");
	    
	    Session session = Session.getInstance(props, new Authenticator() {
	    	
	    	@Override
	    	protected PasswordAuthentication getPasswordAuthentication() {
	    		return new PasswordAuthentication(username, password);
	    	}
	    });
	    
	    try {
	    	
	    	Message message = new MimeMessage(session);
	    	
	    	message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
	    	message.setFrom(new InternetAddress(from));
	    	
	    	message.setSubject("Order confirmed! " + orderId);
	    	
	    	message.setText("Hello, " + name + "!\n" + "Thank you so much for your business! We will get started on your "
	        		+ "order right away. We will send an auto-generated notification email when your order is ready. The "
	        		+ "transit time will vary based on the method of shipping you chose.\n" + "In the meantime, if any questions "
	        				+ "come up, please do not hesitate to message us. Any of our customer service agents will always be happy "
	        				+ "to help.\n" + "Cheers!");
	    	
	    	Transport.send(message);
	    	
	    }catch(Exception e) {
	    	e.printStackTrace();
	    }
	}
    
}
   

		