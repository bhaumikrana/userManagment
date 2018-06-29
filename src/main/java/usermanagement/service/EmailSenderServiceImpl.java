package usermanagement.service;

import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import usermanagement.config.MailPropertyConfig;
import usermanagement.utils.EncryptDecryptUtils;
import usermanagement.utils.Utils;

//import com.estack.config.MailPropertyConfig;
//import com.estack.utils.EncryptDecryptUtils;
//import com.estack.utils.Utils;

/**
 * Send event specific emails
 * 
 * @author admin
 *
 */
@Service
public class EmailSenderServiceImpl implements EmailSenderService ,Runnable {

	@Autowired
	private EmailService emailService;
	
	@Autowired
	private TemplateEngine templateEngine;
	
	private String email;
	private String htmlContent;
	private String subject;
	
	public EmailSenderServiceImpl(){ }
	
	public EmailSenderServiceImpl(String email,String htmlContent,String subject) {
		
		this.email = email;
		this.htmlContent = htmlContent;
		this.subject = subject;
	}
	
	@Override
	public void run() {
		
		emailService.sendHTMLMail(MailPropertyConfig.FROMEMAIL,email, subject, htmlContent);
	}
	
	/**
	 * start executor 
	 */
	private void startExecutorService() {
		
		ExecutorService executor = Executors.newSingleThreadExecutor();
		executor.submit(this);
		executor.shutdown();
	}
	
	/**
	 * send forgot password
	 */
	@Override
	public void sendForgotPasswordEmail(String name, String userEmail, String forgotPasswordLink, String serverUrl) {
		
		final Context ctx = new Context(Locale.US);
		ctx.setVariable("fullname", name);
		ctx.setVariable("forgotPasswordLink", forgotPasswordLink);
		ctx.setVariable("serverUrl", serverUrl);
		final String htmlContent = templateEngine.process("forgot_password_email", ctx);
		
		this.email = userEmail;
		this.htmlContent = htmlContent;
		this.subject = MailPropertyConfig.SUBJECT_FORGOT_PASSWORD;
		startExecutorService();
	}

	@Override
	public void sendRegistrationSuccessEmail(String name, String email, String verifyEmailLink, String serverUrl) {
		
		final Context ctx = new Context(Locale.US);
		ctx.setVariable("fullname", name);
		String encryptDate = EncryptDecryptUtils.encrypt(Utils.currentStringDate());
		ctx.setVariable("verifyEmailLink", serverUrl + verifyEmailLink+"&d="+encryptDate);
		ctx.setVariable("serverUrl", serverUrl);
		final String htmlContent = templateEngine.process("test", ctx);
		
		this.email = email;
		this.htmlContent = htmlContent;
		this.subject = MailPropertyConfig.SUBJECT_REGISTRATION;
		startExecutorService();
	}
	
	/**
	 * Send credential mail 
	 */
	@Override
	public void sendRegistrationSuccessEmail(String name, String email,
			String username, String password, String serverUrl) {
		
		final Context ctx = new Context(Locale.US);
		ctx.setVariable("fullname", name);
		ctx.setVariable("username", username);
		ctx.setVariable("password", password);
		ctx.setVariable("serverUrl", serverUrl);
		final String htmlContent = templateEngine.process("admin_send_credential_mail", ctx);
		
		this.email = email; 
		this.htmlContent = htmlContent;
		this.subject = "Your EmployeeStack account has been created.";
		startExecutorService();
	}

}
