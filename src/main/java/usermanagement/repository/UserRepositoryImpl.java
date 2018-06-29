package usermanagement.repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.hibernate.SQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import usermanagement.dto.LoginRequestDTO;
import usermanagement.dto.RegistrationRequestDTO;
import usermanagement.dto.response.UserResponseDTO;
import usermanagement.entity.Friends;
import usermanagement.entity.Request;
import usermanagement.entity.User;
import usermanagement.entity.UserAddress;
import usermanagement.enums.Role;

@Repository
public class UserRepositoryImpl implements UserRepository {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	HbUserRepository HbUserRepository;
	
	@Autowired
	HbUserAddressRepository HbUserAddressRepository;
	
	@Autowired
	HbRequest hbRequest;
	
	@Autowired
	HbFriends hbFriends;
	
	@Override
	public void doRegister(RegistrationRequestDTO registrationRequestDTO) {
		
		User user = new User();
		user.setFirstName(registrationRequestDTO.getFirstName());
		user.setLastName(registrationRequestDTO.getLastName());
		user.setEmail(registrationRequestDTO.getEmail());
		user.setGender(registrationRequestDTO.getGender());
		user.setPassword(registrationRequestDTO.getPassword());
		user.setConfirmPassword(registrationRequestDTO.getConfirmPassword());
		
		String str = String.join(",", registrationRequestDTO.getHobby());
		
		user.setHobby(str);
		user.setCity(registrationRequestDTO.getCity());
		user.setCountry(registrationRequestDTO.getCountry());
		user.setState(registrationRequestDTO.getState());
		user.setImageName(registrationRequestDTO.getImageName());
		user.setRole(Role.ROLE_USER.toString());
		
		User saveUser = HbUserRepository.save(user);
//		User getUser = HbUserRepository.findByEmail(saveUser.getEmail());
		for(String add:registrationRequestDTO.getAddresses()) {
			UserAddress userAddress = new UserAddress();
			userAddress.setAddress(add);
			userAddress.setUser(saveUser);
			
			HbUserAddressRepository.save(userAddress);
		}
//		String sql = "INSERT INTO USERLIST VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
//		jdbcTemplate.update(sql,null,user.getFirstName(),user.getLastName(),
//				user.getEmail(),user.getPassword(),user.getConfirmPassword(),
//				user.getGender(),str,user.getCity(),
//				user.getState(),user.getCountry(),user.getImageName());

//		System.out.println("rc");
		
//		String query = "SELECT * FROM USERLIST WHERE EMAIL=?";
//		List<User> oneUser = jdbcTemplate.query(query,new Object[]{user.getEmail()},new BeanPropertyRowMapper<>(User.class));
//		System.out.println("bfcb");
//		User use = oneUser.get(0);
//		
//		for(String add:registrationRequestDTO.getAddresses()) {
//			String query1 = "INSERT INTO USERADDRESS VALUES(?,?,?)";
//			jdbcTemplate.update(query1,null,user1.getId(),add);
//		}
//		String query1 = "INSERT INTO USERADDRESS VALUES(?,?,?)";
//		jdbcTemplate.update(query1,null,user.getSrNo(),useraddress.getAddress(0));
//		
//		String query2 = "INSERT INTO USERADDRESS VALUES(?,?,?)";
//		jdbcTemplate.update(query2,null,user.getSrNo(),registrationRequestDTO.getAddress2());
//		
//		String query3 = "INSERT INTO USERADDRESS VALUES(?,?,?)";
//		jdbcTemplate.update(query3,null,user.getSrNo(),registrationRequestDTO.getAddress3());
	}

	@Override
	public User emailVerification(String mail) {
//		RegistrationRequestDTO registrationRequestDTO = new RegistrationRequestDTO();
//		String query = "SELECT * FROM USERLIST WHERE EMAIL=?";
//		List<RegistrationRequestDTO> oneUser = jdbcTemplate.query(query,new Object[]{mail},new BeanPropertyRowMapper<>(RegistrationRequestDTO.class));
//		System.out.println("bfcb");
		
		User getUser = HbUserRepository.findByEmail(mail);
//		User getUser1 = HbUserRepository.findByEmailQuery(mail);
		
		if(getUser==null) {
			return null;
		}
		return getUser;
	}

	@Override
	public Boolean checkUser(LoginRequestDTO loginRequestDTO) {
		
//		List<RegistrationRequestDTO> user = new ArrayList<RegistrationRequestDTO>();
		String sql = "SELECT * FROM USERLIST WHERE EMAIL=? AND PASSWORD=?";
		List<RegistrationRequestDTO> user  = jdbcTemplate.query(sql,new Object[]{loginRequestDTO.getUserName(),loginRequestDTO.getPassword()},new BeanPropertyRowMapper<>(RegistrationRequestDTO.class));
		if(!CollectionUtils.isEmpty(user)) {
			return true;
		} 
		return false;
	}

	@Override
	public List<UserResponseDTO> getAllUsers() {
//		String query = "SELECT * FROM USERADDRESS";
//		List<UserAddress> add = jdbcTemplate.query(query,new Object[]{},new BeanPropertyRowMapper<>(UserAddress.class));
		
// 		String sql = "SELECT * FROM USERLIST";
//		List<User> users = jdbcTemplate.query(sql,new Object[]{},new BeanPropertyRowMapper<>(User.class));
//		List<UserResponseDTO> userResponseDTOs = new ArrayList<>();
		
		List<User> users = HbUserRepository.findAll();
		List<UserResponseDTO> userResponseDTOs = new ArrayList<>();
		
		for(User user : users) {
			
			UserResponseDTO userResponseDTO = new UserResponseDTO();
			userResponseDTO.setUserAddress(user.getUserAddress());
			userResponseDTO.setFirstName(user.getFirstName());
			userResponseDTO.setLastName(user.getLastName());
			userResponseDTO.setEmail(user.getEmail());
			userResponseDTO.setPassword(user.getPassword());
			userResponseDTO.setConfirmPassword(user.getConfirmPassword());
			userResponseDTO.setGender(user.getGender());
			
			List<String> str = Arrays.asList(user.getHobby().split(","));
			userResponseDTO.setHobby(str);
			userResponseDTO.setCity(user.getCity());
			userResponseDTO.setState(user.getState());
			userResponseDTO.setCountry(user.getCountry());
			userResponseDTO.setImageName(user.getImageName());
			userResponseDTO.setId(Long.toString(user.getId()));
			
			
			userResponseDTOs.add(userResponseDTO);
		}
		return userResponseDTOs;
	}


	@Override
	public void deleteUser(String id) {
		
//		HbUserRepository.delete(arg0);
//		String sql = "DELETE FROM USERLIST WHERE SR_NO=?";
//		jdbcTemplate.update(sql,id);
//		
//		String query = "DELETE FROM USERADDRESS WHERE USER_ID=?";
//		jdbcTemplate.update(query,id);
	}

	@Override
	public UserResponseDTO editUser(String id) {
		
//		String sql = "SELECT * FROM USERLIST WHERE SR_NO=?";
//		List<UserResponseDTO> oneUser = jdbcTemplate.query(sql,new Object[]{id},new BeanPropertyRowMapper<>(UserResponseDTO.class));
//		return oneUser.get(0);
		
		Optional<User> users = HbUserRepository.findById(Long.parseLong(id));
		User user = users.get();
		UserResponseDTO userResponseDTO = new UserResponseDTO();
		userResponseDTO.setUserAddress(user.getUserAddress());
		userResponseDTO.setFirstName(user.getFirstName());
		userResponseDTO.setLastName(user.getLastName());
		userResponseDTO.setEmail(user.getEmail());
		userResponseDTO.setPassword(user.getPassword());
		userResponseDTO.setConfirmPassword(user.getConfirmPassword());
		userResponseDTO.setGender(user.getGender());
		
		List<String> str = Arrays.asList(user.getHobby().split(","));
		userResponseDTO.setHobby(str);
		userResponseDTO.setCity(user.getCity());
		userResponseDTO.setState(user.getState());
		userResponseDTO.setCountry(user.getCountry());
		userResponseDTO.setImageName(user.getImageName());
		userResponseDTO.setId(Long.toString(user.getId()));
		
		return userResponseDTO;
	}

	@Override
	public void updateUser(RegistrationRequestDTO registrationRequestDTO,
			String id) {
		
		
		User user = new User();
		user.setFirstName(registrationRequestDTO.getFirstName());
		user.setLastName(registrationRequestDTO.getLastName());
		user.setEmail(registrationRequestDTO.getEmail());
		user.setGender(registrationRequestDTO.getGender());
		user.setPassword(registrationRequestDTO.getPassword());
		user.setConfirmPassword(registrationRequestDTO.getConfirmPassword());
		
		String str = String.join(",", registrationRequestDTO.getHobby());
		
		user.setHobby(str);
		user.setCity(registrationRequestDTO.getCity());
		user.setCountry(registrationRequestDTO.getCountry());
		user.setState(registrationRequestDTO.getState());
		user.setImageName(registrationRequestDTO.getImageName());
		user.setId(Long.parseLong(id));
//		registrationRequestDTO.getAddresses().//
//		registrationRequestDTO.getAddresses().//
		HbUserRepository.save(user);
		List<UserAddress> userAddresses = HbUserAddressRepository.findByUser(user);
//		List<UserAddress> useList = new ArrayList<>();
		int i=0;
		for(UserAddress add:userAddresses) {
			UserAddress userAddress = new UserAddress();
			userAddress.setId(add.getId());
			userAddress.setUser(user);
			userAddress.setAddress(registrationRequestDTO.getAddresses().get(i));
//			useList.add(userAddress);
//			useList.add(userAddress);
			HbUserAddressRepository.save(userAddress);
			i++;
		}
//		List<UserAddress> addr = new ArrayList<>();
//		for(String add:registrationRequestDTO.getAddresses()) {
//			UserAddress userAddress = new UserAddress();
//			userAddress.setAddress(add);
//			
////			userAddress.setUser(saveUser);
//			addr.add(userAddress);
//			HbUserAddressRepository.save(userAddress);
//		}
//		user.setUserAddress(addr);
//		for(UserAddress add:user.getUserAddress()) {
//			add.setUser(user);
//		}
//		User saveUser1 = HbUserRepository.save(user);
//		System.out.println("bb");
//		User user = new User();
//		user.setFirstName(registrationRequestDTO.getFirstName());
//		user.setLastName(registrationRequestDTO.getLastName());
//		user.setEmail(registrationRequestDTO.getEmail());
//		user.setGender(registrationRequestDTO.getGender());
//		user.setPassword(registrationRequestDTO.getPassword());
//		user.setConfirmPassword(registrationRequestDTO.getConfirmPassword());
//		user.setHobby(registrationRequestDTO.getHobby());
//		user.setCity(registrationRequestDTO.getCity());
//		user.setCountry(registrationRequestDTO.getCountry());
//		user.setState(registrationRequestDTO.getState());
//		user.setImageName(registrationRequestDTO.getImageName());
//		
//		String str = String.join(",", user.getHobby());
//		String sql = "UPDATE USERLIST SET First_Name=?, Last_Name=?, Email=?, Password=?, Confirm_Password=?, Gender=?, Hobby=?, City=?, State=?, Country=?, Image_Name=? WHERE SR_NO=?";
//
//		jdbcTemplate.update(sql,user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(), user.getConfirmPassword(), user.getGender(), str, user.getCity(), user.getState(), user.getCountry(),user.getImageName(),user.getSrNo());
////		System.out.println("rc");
//		
//		String query = "SELECT * FROM USERLIST WHERE EMAIL=?";
//		List<User> oneUser = jdbcTemplate.query(query,new Object[]{user.getEmail()},new BeanPropertyRowMapper<>(User.class));
//		System.out.println("bfcb");
//		User user1 = oneUser.get(0);
//		
//		String string = "SELECT * FROM USERADDRESS WHERE USER_ID=?";
//		List<UserAddress> address = jdbcTemplate.query(string,new Object[]{user1.getSrNo()},new BeanPropertyRowMapper<>(UserAddress.class));
//		List<String> s = new ArrayList<>();
//		for(String add:registrationRequestDTO.getAddresses()) {
//			s.add(add);
//		}
//		List<String> i = new ArrayList<>();
//		for(UserAddress index:address) {
//			i.add(index.getSrNo());
//		}
//		String query1 = "UPDATE USERADDRESS SET ADDRESS=? WHERE sr_no=?";
//		jdbcTemplate.update(query1,s.get(0),i.get(0));
//		
//		String query2 = "UPDATE USERADDRESS SET ADDRESS=? WHERE sr_no=?";
//		jdbcTemplate.update(query2,s.get(1),i.get(1));
//		
//		String query3 = "UPDATE USERADDRESS SET ADDRESS=? WHERE sr_no=?";
//		jdbcTemplate.update(query3,s.get(2),i.get(2));
		
	}

	@Override
	public UserResponseDTO getUser(String userName) {
		
		User user = HbUserRepository.findByEmail(userName);
		
		UserResponseDTO userResponseDTO = new UserResponseDTO();
		userResponseDTO.setUserAddress(user.getUserAddress());
		userResponseDTO.setFirstName(user.getFirstName());
		userResponseDTO.setLastName(user.getLastName());
		userResponseDTO.setEmail(user.getEmail());
		userResponseDTO.setPassword(user.getPassword());
		userResponseDTO.setConfirmPassword(user.getConfirmPassword());
		userResponseDTO.setGender(user.getGender());
		List<String> str = Arrays.asList(user.getHobby().split(","));
		userResponseDTO.setHobby(str);
		userResponseDTO.setCity(user.getCity());
		userResponseDTO.setState(user.getState());
		userResponseDTO.setCountry(user.getCountry());
		userResponseDTO.setImageName(user.getImageName());
		userResponseDTO.setId( String.valueOf(user.getId()));
		userResponseDTO.setRole(user.getRole());
		return userResponseDTO;
	}

	@Override
	public void updateUserChangePassword(String newP,
			String confirmP, String id) {
		
//		String sql = "UPDATE USERLIST SET Password=?, Confirm_Password=? WHERE SR_NO=?";
//		jdbcTemplate.update(sql,newP,confirmP,id);
//		System.out.println("cxc");
		
		HbUserRepository.upadtePassword(newP,confirmP,Long.parseLong(id));
	}

	@Override
	public List<UserAddress> getAllAddress(String word) {
		
		String sql = "SELECT * FROM USERADDRESS WHERE address LIKE  '%" +word+ "%' GROUP BY user_id";
		List<UserAddress> users = jdbcTemplate.query(sql,new Object[]{},new BeanPropertyRowMapper<>(UserAddress.class));
		return users;
	}

	@Override
	public List<UserResponseDTO> getSearchUsers(String word) {
		
//		String sql = "SELECT * FROM USERLIST where First_Name like '%"+word+"%' or Last_Name like '%"+word+"%' or Last_Name like '%"+word+"% or Email like '%"+word+"%"
//				+ "or City like '%"+word+"% or State like '%"+word+"% or Country like '%"+word+"% ";
//		List<User> users = jdbcTemplate.query(sql,new Object[]{},new BeanPropertyRowMapper<>(User.class));
//		List<User> user = HbUserRepository.findByFirstNameOrLastNameOrEmailOrCityLike(word,word,word,word);
		List<User> list = HbUserRepository.findByQuery(word,word,word,word);
		List<UserResponseDTO> userResponseDTOs = new ArrayList<>();
		
		List<UserAddress> addresses = HbUserAddressRepository.findAdd(word);
		
		for(UserAddress id:addresses) {
			list.add(id.getUser());
		}
		Set<User> users = new HashSet<>();
		users.addAll(list);
		users.remove(null);
		
		for(User user : users ) {
			
			UserResponseDTO userResponseDTO = new UserResponseDTO();
			userResponseDTO.setUserAddress(user.getUserAddress());	
			userResponseDTO.setFirstName(user.getFirstName());
			userResponseDTO.setLastName(user.getLastName());
			userResponseDTO.setEmail(user.getEmail());
			userResponseDTO.setPassword(user.getPassword());
			userResponseDTO.setConfirmPassword(user.getConfirmPassword());
			userResponseDTO.setGender(user.getGender());
			
			List<String> str = Arrays.asList(user.getHobby().split(","));
			userResponseDTO.setHobby(str);
			userResponseDTO.setCity(user.getCity());
			userResponseDTO.setState(user.getState());
			userResponseDTO.setCountry(user.getCountry());
			userResponseDTO.setImageName(user.getImageName());
			
			userResponseDTOs.add(userResponseDTO);
		}
		return userResponseDTOs;
	}

	@Override
	public void userSet(String userId) {

		
	}

	@Override
	public void registerFriendRequest(String fuserId, String userId) {
		
		Request request = new Request(); 
		request.setSendRequestUserId(userId);
		request.setGetRequestUserId(fuserId);
		hbRequest.save(request);
	}

	@Override
	public void registerFriends(String fuserId, String userId) {

		Friends friends = new Friends();
		friends.setLoginUserId(fuserId);
		friends.setFriendUserId(userId);
		hbFriends.save(friends);
	}

	@Override
	public void deleteRowInRequestTable(String fuserId, String userId) {

		Long id = hbRequest.getId(fuserId,userId); 
		Request request = new Request(); 
		request.setSendRequestUserId(userId);
		request.setGetRequestUserId(fuserId);
		request.setId(id);
		hbRequest.delete(request);
	}

	@Override
	public void deleteRowInFriendsTable(String fuserId, String userId) {

		Friends friend = hbFriends.findByBothId(fuserId,userId); 
		Friends friends = new Friends();
		friends.setLoginUserId(fuserId);
		friends.setFriendUserId(userId);
		friends.setId(friend.getId());
		hbFriends.delete(friends);
	}
}
