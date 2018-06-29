package usermanagement.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "request")
public class Request {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name="Send_Request_UserId", nullable=false)
	private String sendRequestUserId;
	
	@Column(name="Get_Request_UserId", nullable=false)
	private String getRequestUserId;

	public String getSendRequestUserId() {
		return sendRequestUserId;
	}

	public void setSendRequestUserId(String sendRequestUserId) {
		this.sendRequestUserId = sendRequestUserId;
	}

	public String getGetRequestUserId() {
		return getRequestUserId;
	}

	public void setGetRequestUserId(String getRequestUserId) {
		this.getRequestUserId = getRequestUserId;
	}

}
