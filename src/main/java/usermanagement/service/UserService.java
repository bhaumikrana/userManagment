package usermanagement.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;

import usermanagement.dto.RegistrationRequestDTO;
import usermanagement.dto.response.UserResponseDTO;
import usermanagement.entity.User;
import usermanagement.entity.UserAddress;

public interface UserService {

	Page<User> findAllPageable(Pageable pageable);
	
	public boolean validateAll(ModelMap modelmap, RegistrationRequestDTO registrationRequestDTO,String id, String httpServletRequest);

	public List<UserResponseDTO> searchUsers(String search);

	public Boolean changePassValidate(
			UserResponseDTO userResponseDTO, String oldP,
			String newP, String confirmP, ModelMap modelmap);

	public boolean validateSearch(String search, ModelMap modelmap);

	public void uploadImageName(MultipartFile file, RegistrationRequestDTO registrationRequestDTO);

	public void deleteProfilePic(MultipartFile file, RegistrationRequestDTO registrationRequestDTO);

//	Page<UserResponseDTO> findAllPageable1(PageRequest of);
}
