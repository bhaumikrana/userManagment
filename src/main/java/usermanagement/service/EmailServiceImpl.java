package usermanagement.service;

import java.io.File;

import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	private JavaMailSender mailSender;
	
	final static Logger logger = Logger.getLogger(EmailServiceImpl.class);

	@Override
	public void sendHTMLMail(String from, String to, String subject, String msg)  {
		this.sendHTMLMail(from, to, null, null, subject, msg,null);
	}

	public void sendHTMLMail(String from, String to, String cc, String bcc, String subject, String messageText,File file) {
		
		try{
			// Create a default MimeMessage object.
			MimeMessage mimemessage = mailSender.createMimeMessage();// MimeMessage(session);
			MimeMessageHelper message = new MimeMessageHelper(mimemessage, true);
			message.setFrom(from);
			message.setTo(to);
			message.setBcc(from);
			
			if(file != null)
				message.addAttachment(file.getName(), file);
			
			message.setSubject(subject);
			message.setText(messageText, true);
			mailSender.send(mimemessage);
		} catch(Exception e) {
			logger.error("Error occured while send email : " + e);
		}
	}
	
	@Override
	public void sendHTMLMailWithFile(String from, String to, String subject, String msg,File file)  {
		this.sendHTMLMail(from, to, null, null, subject, msg,file);
	}
	
}
