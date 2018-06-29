package usermanagement.service;

import java.io.File;


public interface EmailService {

	public void sendHTMLMail(String from, String to, String subject, String msg);

	void sendHTMLMailWithFile(String from, String to, String subject,String msg, File file);

}