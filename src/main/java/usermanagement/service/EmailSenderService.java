package usermanagement.service;

public interface EmailSenderService {
	
	/**
	 * To send email with welcome message and hyper link to activate the account
	 * 
	 * @param name
	 * @param userEmail
	 * @param forgotPasswordLink
	 */
	public void sendForgotPasswordEmail(String name, String userEmail, String forgotPasswordLink, String serverUrl) ;

	/**
	 * Send Registration success email and verify email
	 * 
	 * @param name
	 * @param email
	 * @param verifyEmailLink
	 */
	public void sendRegistrationSuccessEmail(String name, String email, String verifyEmailLink, String serverUrl);
	
	
	/**
	 * Send email for suer success
	 */
	public void sendRegistrationSuccessEmail(String name, String email, String username , String password, String serverUrl);
	
}
