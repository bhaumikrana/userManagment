package usermanagement.service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import usermanagement.dto.RegistrationRequestDTO;
import usermanagement.dto.response.UserResponseDTO;
import usermanagement.entity.User;
import usermanagement.entity.UserAddress;
import usermanagement.repository.PagingRepository;
import usermanagement.repository.UserRepository;
import usermanagement.utils.GeneralUtils;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private EmailSenderService emailSenderService;
	
	@Autowired
	private PagingRepository pagingRepository;

//    public PersonServiceImpl(PersonRepository personRepository) {
//        this.personRepository = personRepository;
//    }

	@Override
	public Page<User> findAllPageable(Pageable pageable) {
		return pagingRepository.findAll(pageable);
	}
	
	@Autowired
	private UserRepository userRepository;
	
	public boolean validateAll(ModelMap modelmap,RegistrationRequestDTO registrationRequestDTO,String id,String httpServletRequest) {
		
//		if(registrationRequestDTO.getFirstName()==null || registrationRequestDTO.getFirstName().trim()=="") /same
//		if(registrationRequestDTO.getFirstName()==null || registrationRequestDTO.getFirstName().trim().length()==0)/same 
		if(registrationRequestDTO.getFirstName()==null || registrationRequestDTO.getFirstName().trim().length()==0) {
			String s = "FirstName is null";
			modelmap.addAttribute("user",registrationRequestDTO);
			modelmap.addAttribute("message",s);
			return true;
		}
		if(registrationRequestDTO.getLastName()==null || registrationRequestDTO.getLastName().trim().length()==0) {
			String s = "LastName is null";
			modelmap.addAttribute("user",registrationRequestDTO);
			modelmap.addAttribute("message",s);
			return true;
		}
		if(registrationRequestDTO.getEmail()==null || registrationRequestDTO.getEmail().trim().length()==0) {
			String s = "Email is null";
			modelmap.addAttribute("user",registrationRequestDTO);
			modelmap.addAttribute("message",s);
			return true;
		}
		if(registrationRequestDTO.getPassword()==null || registrationRequestDTO.getPassword().trim().length()==0) {
			String s = "Password is null";
			modelmap.addAttribute("user",registrationRequestDTO);
			modelmap.addAttribute("message",s);
			return true;
		}
		if(registrationRequestDTO.getConfirmPassword()==null || registrationRequestDTO.getConfirmPassword().trim().length()==0) {
			String s = "Confirm password is null";
			modelmap.addAttribute("user",registrationRequestDTO);
			modelmap.addAttribute("message",s);
			return true;
		}
		
		//Match confirm password and password
		if(!registrationRequestDTO.getPassword().equals(registrationRequestDTO.getConfirmPassword())) {
			String s = "Password and confirm password not match";
			modelmap.addAttribute("user",registrationRequestDTO);
			modelmap.addAttribute("message",s);
			return true;
		}
		
		//Check email already exists or not
		if(StringUtils.isEmpty(id)) {
			User oneUser = userRepository.emailVerification(registrationRequestDTO.getEmail());
			
			if(oneUser != null) {
				String s = "Entered email is already exists. Please enter another email";
				modelmap.addAttribute("user",registrationRequestDTO);
				modelmap.addAttribute("message",s);
				return true;
			}	
		}
		
		if(!StringUtils.isEmpty(id)) {
			User oneUser = userRepository.emailVerification(registrationRequestDTO.getEmail());
			
			if(oneUser==null) {
				System.out.println("bbb");
			}
			else {
				if(!oneUser.getId().equals(Long.parseLong(id))) {
					String s = "Edited email is already in use. Please enter another email";
					modelmap.addAttribute("user",registrationRequestDTO);
					modelmap.addAttribute("message",s);
					return true;
				}
			}
		}
		
		if(StringUtils.isEmpty(registrationRequestDTO.getGender())) {
			String s = "Gender is not selected";
			modelmap.addAttribute("user",registrationRequestDTO);
			modelmap.addAttribute("message",s);
			return true;
		}
		if(CollectionUtils.isEmpty(registrationRequestDTO.getHobby())) {
			String s = "Hobbies is null";
			modelmap.addAttribute("user",registrationRequestDTO);
			modelmap.addAttribute("message",s);
			return true;
		}
		if(registrationRequestDTO.getCity()==null || registrationRequestDTO.getCity().trim().length()==0) {
			String s = "City is null";
			modelmap.addAttribute("user",registrationRequestDTO);
			modelmap.addAttribute("message",s);
			return true;
		}
//		if(registrationRequestDTO.getAddress()==null || registrationRequestDTO.getAddress().trim().length()==0) {
//			String s = "Address is null";
//			modelmap.addAttribute("user",registrationRequestDTO);
//			modelmap.addAttribute("message",s);
//			return true;
//		}
		
		emailSenderService.sendRegistrationSuccessEmail(registrationRequestDTO.getFirstName()+registrationRequestDTO.getLastName(), registrationRequestDTO.getEmail(), " ", httpServletRequest);
		
		return false;
	}
	
/*	@Override
	public List<UserResponseDTO> searchUsers(String search) {
		
		String word = search.toLowerCase();
		
		//User table mathi query, get all user
		//List<User> users
		
		//Address table query, get all address
		//SELECT * FROM  `useraddress` WHERE address LIKE  '%a%' GROUP BY user_id
		//List<Address> addressess
		
		
		
		
		
		
		List<UserResponseDTO> users = userRepository.getAllUsers();
		List<UserResponseDTO> list = new ArrayList<UserResponseDTO>();
		
		for(UserResponseDTO user:users) {
			if(user.getFirstName().toLowerCase().contains(word) || user.getLastName().toLowerCase().contains(word) || user.getEmail().toLowerCase().contains(word) ||
					user.getPassword().toLowerCase().contains(word) || user.getConfirmPassword().toLowerCase().contains(word) || user.getGender().toLowerCase().contains(word) ||
					user.getCity().toLowerCase().contains(word) || user.getState().toLowerCase().contains(word) || user.getCountry().toLowerCase().contains(word) ) {
				
				list.add(user);
				continue;
			}
			int i = 0;
			for(UserAddress one:user.getUserAddresses()) {
				
				if(i>0) {
					break;
				}
				if(one.getAddress().toLowerCase().contains(word)) {
					list.add(user);
					i = 1;
				}
			}
		}
		System.out.println("bb");
		return list;
	}
*/	
	
	
	@Override
	public List<UserResponseDTO> searchUsers(String search) {
		
		String word = search.toLowerCase();
		
		//User table mathi query, get all user
		//List<User> users
		
		//Address table query, get all address
		//SELECT * FROM  `useraddress` WHERE address LIKE  '%a%' GROUP BY user_id
		//List<Address> addressess
		
//		List<User> users = userRepository.getSearchUsers(word);
		List<UserResponseDTO> listUsers = new ArrayList<UserResponseDTO>();
		
		List<UserAddress> addressess = userRepository.getAllAddress(word);
		List<UserAddress> listAddresses = new ArrayList<UserAddress>();
		
		for(UserAddress user:addressess) { 
//			userRepository.userSet(user.getUserId());
		}
//		List<UserResponseDTO> users1 = userRepository.getAllUsers();
//		List<UserResponseDTO> list = new ArrayList<UserResponseDTO>();
		
//		for(UserResponseDTO user:users) {
//			if(user.getFirstName().toLowerCase().contains(word) || user.getLastName().toLowerCase().contains(word) || user.getEmail().toLowerCase().contains(word) ||
//					user.getPassword().toLowerCase().contains(word) || user.getConfirmPassword().toLowerCase().contains(word) || user.getGender().toLowerCase().contains(word) ||
//					user.getCity().toLowerCase().contains(word) || user.getState().toLowerCase().contains(word) || user.getCountry().toLowerCase().contains(word) ) {
//				
//				list.add(user);
//				continue;
//			}
//			int i = 0;
//			for(UserAddress one:user.getUserAddresses()) {
//				
//				if(i>0) {
//					break;
//				}
//				if(one.getAddress().toLowerCase().contains(word)) {
//					list.add(user);
//					i = 1;
//				}
//			}
//		}
		System.out.println("bb");
		return null;
	}

	@Override
	public Boolean changePassValidate(
			UserResponseDTO userResponseDTO, String oldP,
			String newP, String confirmP, ModelMap modelmap) {
		
		if(!oldP.equals(userResponseDTO.getPassword())) {
			String s = "Entered Old Password is wrong.Please enter correct password";
			modelmap.addAttribute("message",s);
			modelmap.addAttribute("oldPassword",oldP);
			modelmap.addAttribute("newPassword",newP);
			modelmap.addAttribute("confirmNewPassword",confirmP);
			return true;
		}
		if(newP==null || newP.trim().length()==0) {
			String s = "New Password is null";
			modelmap.addAttribute("message",s);
			modelmap.addAttribute("oldPassword",oldP);
			modelmap.addAttribute("newPassword",newP);
			modelmap.addAttribute("confirmNewPassword",confirmP);
			return true;
		}
		if(confirmP==null || confirmP.trim().length()==0) {
			String s = "Confirm password is null";
			modelmap.addAttribute("message",s);
			modelmap.addAttribute("oldPassword",oldP);
			modelmap.addAttribute("newPassword",newP);
			modelmap.addAttribute("confirmNewPassword",confirmP);
			return true;
		}
		if(!newP.equals(confirmP)) {
			String s = "New password & confirm New password is not match";
			modelmap.addAttribute("message",s);
			modelmap.addAttribute("oldPassword",oldP);
			modelmap.addAttribute("newPassword",newP);
			modelmap.addAttribute("confirmNewPassword",confirmP);
			return true;
		}
		else {
			String id = userResponseDTO.getId();
			userRepository.updateUserChangePassword(newP,confirmP,id);
			return false;
		}
	}

	@Override
	public boolean validateSearch(String search,ModelMap modelmap) {
		
		if(search==null || search.trim().length()==0) {
			String s = "Search field is null";
			modelmap.addAttribute("message",s);
			return true;
		}
		return false;
	}

	@Override
	public void uploadImageName(MultipartFile file,RegistrationRequestDTO registrationRequestDTO) {

		try {

            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            String name = GeneralUtils.getRandomNumber(3) + file.getOriginalFilename();
            Path path = Paths.get("E:/bhaumik/profilePic/" + name);
            Files.write(path, bytes);
            registrationRequestDTO.setImageName(name);

//            redirectAttributes.addFlashAttribute("message",
//                    "You successfully uploaded '" + file.getOriginalFilename() + "'");

        } catch (Exception e) {
            e.printStackTrace();
        }
	}

	@Override
	public void deleteProfilePic(MultipartFile file, RegistrationRequestDTO registrationRequestDTO) {

		File file1=new File("E:/bhaumik/profilePic/" + registrationRequestDTO.getImageName());
		file1.delete();
//		if(file1.exists()){
//			if(file1.delete()){
//				System.out.println("File deleted successfully");
//			}else{
//				System.out.println("Fail to delete file");
//			}
//		}
	}

}
