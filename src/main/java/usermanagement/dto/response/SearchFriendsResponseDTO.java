package usermanagement.dto.response;

public class SearchFriendsResponseDTO {

	private String firstName;

	private String lastName;
	
	private String imageName;
	
	private String id;
	
	private Boolean isFriend;
	
	private boolean alreadySent;
	
	public boolean isAlreadySent() {
		return alreadySent;
	}

	public void setAlreadySent(boolean alreadySent) {
		this.alreadySent = alreadySent;
	}

	public Boolean getIsFriend() {
		return isFriend;
	}

	public void setIsFriend(Boolean isFriend) {
		this.isFriend = isFriend;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
}
