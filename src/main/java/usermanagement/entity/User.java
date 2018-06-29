package usermanagement.entity;


import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity; 
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
/**
 * userlist table entity
 * @author Bhaumik
 *
 */
@Entity
@Table(name = "userlist")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@Column(name ="ID")
	private Long id;
	
	@Column(name="First_Name", nullable=false)
	private String firstName;
	
	@Column(name="Last_Name", nullable=false)
	private String lastName;
	
	@Column(name="Email", nullable=false, unique=true)
	private String email;
	
	@Column(name="Password", nullable=false)
	private String password;
	
	@Column(name="Confirm_Password", nullable=false)
	private String confirmPassword;
	
	@Column(name="Gender", nullable=false)
	private String gender;
	
//	@ElementCollection
////	@CollectionTable(name="Hobby")
//	@Column(name="Hobby", nullable=true)
//	private List<String> hobby = new ArrayList<String>();
	@Column(name="Hobby", nullable=true)
	private String hobby;
	
	@Column(name="City", nullable=false)
	private String city;
	
	@Column(name="State", nullable=false)
	private String state;
	
	@Column(name="Country", nullable=false)
	private String country;
	
	@Column(name="Image_Name", nullable=false)
	private String imageName;

	@OneToMany(mappedBy = "user")
	private List<UserAddress> userAddress;
	
	@Column(name="Role", nullable=false)
	private String role;
//	
//	public List<UserAddress> getUserAddress() {
//		return userAddress;
//	}
//
//	public void setUserAddress(List<UserAddress> userAddress) {
//		this.userAddress = userAddress;
//	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Long getId() {
		return id;
	}

	public List<UserAddress> getUserAddress() {
		return userAddress;
	}

	public void setUserAddress(List<UserAddress> userAddress) {
		this.userAddress = userAddress;
	}

	public void setId(Long id) {
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getHobby() {
		return hobby;
	}

	public void setHobby(String hobby) {
		this.hobby = hobby;
	}

//	public List<String> getHobby() {
//		return hobby;
//	}
//
//	public void setHobby(List<String> hobby) {
//		this.hobby = hobby;
//	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	
}
