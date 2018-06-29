package usermanagement.controller;

import java.util.ArrayList;
import java.util.List;

public class Test {

	public static void main(String[] args) {
		
		User user = new User();
		user.setEmail("test");
		user.setId(1L);
	
		
		Address address1 = new Address();
		address1.setId(1L);
		address1.setAddress("bilimora");
		
		Address address2 = new Address();
		address2.setId(1L);
		address2.setAddress("bilimora");
		
		List<Address> addresses = new ArrayList<>();
		addresses.add(address1);
		addresses.add(address2);
		
		user.setAddresses(addresses);
		
		System.out.println(user.getEmail());
		
		for(Address address : user.getAddresses()) {
			
			System.out.println(address.getAddress());
		}
	}
}

class User {
	
	private Long id;
	
	private String email;

	private List<Address> addresses = new ArrayList<Address>();
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}
	
}


class Address {
	
	private Long id;
	
	private Long userId;
	
	private String address;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
}