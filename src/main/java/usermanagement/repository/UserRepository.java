package usermanagement.repository;

import java.util.List;
import java.util.Optional;

import usermanagement.dto.LoginRequestDTO;
import usermanagement.dto.RegistrationRequestDTO;
import usermanagement.dto.response.UserResponseDTO;
import usermanagement.entity.User;
import usermanagement.entity.UserAddress;

public interface UserRepository {

	public void doRegister(RegistrationRequestDTO registrationRequestDTO);

	public User emailVerification(String mail);

	public Boolean checkUser(LoginRequestDTO loginRequestDTO);

	public List<UserResponseDTO> getAllUsers();

	public void deleteUser(String id);

	public UserResponseDTO editUser(String id);

	public void updateUser(RegistrationRequestDTO registrationRequestDTO, String id);

	public UserResponseDTO getUser(String userName);

	public void updateUserChangePassword(String newP,
			String confirmP, String id);

	public List<UserAddress> getAllAddress(String word);

	public List<UserResponseDTO> getSearchUsers(String word);

	public void userSet(String userId);

	public void registerFriendRequest(String fuserId, String userId);

	public void registerFriends(String fuserId, String userId);

	public void deleteRowInRequestTable(String fuserId, String userId);

	public void deleteRowInFriendsTable(String fuserId, String userId);
}
