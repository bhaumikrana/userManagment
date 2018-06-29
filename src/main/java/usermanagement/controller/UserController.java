package usermanagement.controller;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import usermanagement.domain.Pager;
import usermanagement.dto.LoginRequestDTO;
import usermanagement.dto.RegistrationRequestDTO;
import usermanagement.dto.response.SearchFriendsResponseDTO;
import usermanagement.dto.response.SearchResponseDTO;
import usermanagement.dto.response.UserResponseDTO;
import usermanagement.entity.Friends;
import usermanagement.entity.Request;
import usermanagement.entity.User;
import usermanagement.entity.UserAddress;
import usermanagement.enums.Role;
import usermanagement.repository.HbFriends;
import usermanagement.repository.HbRequest;
import usermanagement.repository.HbUserRepository;
import usermanagement.repository.UserRepository;
import usermanagement.service.UserService;
import usermanagement.utils.GeneralUtils;
@Controller
public class UserController {
	
	private static final String ERROR = "ERROR";
	private static final String SUCCESS = "SUCCESS";
	
	@Autowired
	private UserService service;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private HbRequest hbRequest;
	
	class ResponseData {
		
		private String status;
		
		private String message;
		
		private Object data;

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public Object getData() {
			return data;
		}

		public void setData(Object data) {
			this.data = data;
		}
		
	}
	
	@RequestMapping(value = "/testV", method = RequestMethod.POST)
	@ResponseBody
    public Object test(@RequestParam("name") String name) {
		
		System.out.println("This is ajax");
		
		//Delete user
		//Success
		ResponseData responseData = new ResponseData();
		
		if(name.equals("SUCCESS")) {
			
			responseData.setStatus(SUCCESS);
			responseData.setMessage("Successfully user deleted");
			
			UserResponseDTO userResponseDTO = new UserResponseDTO();
			userResponseDTO.setEmail("test@test.com");
			
			responseData.setData(userResponseDTO);
			
		} else {
			
			responseData.setStatus(ERROR);
			responseData.setMessage("Some problem occurred when try to delete user");
		}
		
		return responseData;
	}
	
	/*@PostMapping("/upload") // //new annotation since 4.3
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String singleFileUpload(@RequestParam("image") MultipartFile file,ModelMap modelmap) {

//        if (file.isEmpty()) {
//            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
//            return "redirect:uploadStatus";
//        }

        try {

            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            String name = GeneralUtils.getRandomNumber(3) + file.getOriginalFilename();
            Path path = Paths.get("E:/bhaumik/" + name);
            Files.write(path, bytes);
//            practice.setImageName(name);

//            redirectAttributes.addFlashAttribute("message",
//                    "You successfully uploaded '" + file.getOriginalFilename() + "'");

        } catch (Exception e) {
            e.printStackTrace();
        }
        modelmap.addAttribute("user", new RegistrationRequestDTO());
		return "registration";
    }*/
	
	@RequestMapping(value="/getImage", method=RequestMethod.GET)
	public void getImage(HttpServletResponse response,@RequestParam("name") String name ) throws Exception {
		
		response.setContentType("image/jpeg");

//		String pathToWeb = getServletContext().getRealPath(File.separator);
		File f = new File("E:/bhaumik/profilePic/" + name);
		BufferedImage bi = ImageIO.read(f);
		OutputStream out = response.getOutputStream();
		ImageIO.write(bi, "jpg", out);
		out.close();
	}
	
	@Autowired
	HbUserRepository hbUserRepository;
	
	@RequestMapping(value="/user/registration", method=RequestMethod.GET)
	public String registrationGET(ModelMap modelmap,HttpSession httpSession) throws Exception {
//		
//		//For cache
//		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
//		response.setHeader("Pragma", "no-cache"); // HTTP 1.0
//		response.setDateHeader("Expires", 0); // Proxies
//		//END For cache
//		String userName = (String) httpSession.getAttribute("userName");
//		if(userName==null) {
//			return "redirect:/user/login";
//		}
		
		Long userId = 2L;
		Optional<User> userOptional = hbUserRepository.findById(userId);
		User user = userOptional.get();
		
		for(UserAddress userAddress : user.getUserAddress()) {
			
			System.out.println(userAddress.getAddress());
		}
		
		modelmap.addAttribute("user", new RegistrationRequestDTO());
		return "registration";
	}
	
	@RequestMapping(value="/user/registration", method=RequestMethod.POST)
	public String registrationPOST(HttpServletRequest request,HttpSession httpSession,ModelMap modelmap,@ModelAttribute RegistrationRequestDTO registrationRequestDTO,@RequestParam("id") String id,@RequestParam("image") MultipartFile file) {
		
		String httpServletRequest= request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
		if(!StringUtils.isEmpty(id)) {
			boolean isError = service.validateAll(modelmap,registrationRequestDTO,id,httpServletRequest);
//			registrationRequestDTO.setId(id);
			if(isError) {
				modelmap.addAttribute("user", registrationRequestDTO);
				return "updateUser";
			} else {
//				id = 
//				registrationRequestDTO.setSrNo(null);
				if(!file.isEmpty()) {
					service.deleteProfilePic(file,registrationRequestDTO);
					service.uploadImageName(file,registrationRequestDTO);
				}
//				if(file.isEmpty()) {
//					registrationRequestDTO.setImageName(imageName);
//				}
				
				userRepository.updateUser(registrationRequestDTO,id);
//				modelmap.addAttribute("user", registrationRequestDTO);
//				String s = "Edited Successful";
//				modelmap.addAttribute("message",s);
				return "redirect:/user/dashboard";
			}
		}
		boolean isError = service.validateAll(modelmap,registrationRequestDTO,id,httpServletRequest);
		
		if(isError) {
			
			return "registration";
			
		} else {
			service.uploadImageName(file,registrationRequestDTO);
			userRepository.doRegister(registrationRequestDTO);
			modelmap.addAttribute("user", registrationRequestDTO);
			String s = "Registration Successful";
			modelmap.addAttribute("message",s);
			return "redirect:/user/dashboard";
		}
	}
	
	@RequestMapping(value = "/user/login", method = RequestMethod.GET)
	public String displayLoginPage(ModelMap modelmap, HttpServletResponse response) {
		
		//For cache
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
        response.setHeader("Pragma", "no-cache"); // HTTP 1.0
        response.setDateHeader("Expires", 0); // Proxies
		//END For cache 
		
		modelmap.addAttribute("user", new LoginRequestDTO());
		return "login";
	}
	
	@RequestMapping(value = "/user/login", method = RequestMethod.POST)
	public String submitLoginPage(HttpSession httpSession,ModelMap modelmap,@ModelAttribute LoginRequestDTO loginRequestDTO) {
		
		Boolean user = userRepository.checkUser(loginRequestDTO);
		if(!user) {
			modelmap.addAttribute("user", loginRequestDTO);
			String message = "Username or Password is invalid";
			modelmap.addAttribute("message", message);
			return "login";
		}
		httpSession.setAttribute("userName", loginRequestDTO.getUserName());
		httpSession.setAttribute("password", loginRequestDTO.getPassword());
		return "redirect:/user/dashboard";
	}
	
	@RequestMapping(value = "/user/dashboard")
	public String dashBoardPage(HttpSession httpSession,ModelMap modelmap, HttpServletResponse response, @RequestParam(value = "searchKeyword", required = false) String searchKeyword, Optional<Integer> pageSize,@RequestParam("page") Optional<Integer> page) {
		
		//For cache
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0
		response.setDateHeader("Expires", 0); // Proxies
		//END For cache 
		
//		HttpSession session = request.getSession(false);
		String userName = (String) httpSession.getAttribute("userName");
//		RegistrationRequestDTO registrationRequestDTO = userRepository.getUser(userName);
		
//		String password = (String) httpSession.getAttribute("password");
//		HttpSession session = request.getSession(false);
		if(userName==null) {
			return "redirect:/user/login";
		}
		UserResponseDTO userResponseDTO = userRepository.getUser(userName);
		if(userResponseDTO.getRole().equals(Role.ROLE_USER.toString())) {
			return "redirect:/user/profile";
		}
//		List<UserResponseDTO> allusers = userRepository.getAllUsers();
//		 ModelAndView modelAndView = new ModelAndView("dashboard");

	        // Evaluate page size. If requested parameter is null, return initial
	        // page size
	        int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
	        // Evaluate page. If requested parameter is null or less than 0 (to
	        // prevent exception), return initial size. Otherwise, return value of
	        // param. decreased by 1.
	        int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;

	        Page<User> persons = service.findAllPageable(PageRequest.of(evalPage, evalPageSize));
	        Pager pager = new Pager(persons.getTotalPages(), persons.getNumber(), BUTTONS_TO_SHOW);
	        
	        String pagination = "on";
//	        modelAndView.addObject("persons", persons);
//	        modelAndView.addObject("selectedPageSize", evalPageSize);
//	        modelAndView.addObject("pageSizes", PAGE_SIZES);
//	        modelAndView.addObject("pager", pager);
//	        return modelAndView;
	        modelmap.addAttribute("persons", persons);
	        modelmap.addAttribute("selectedPageSize", evalPageSize);
	        modelmap.addAttribute("pageSizes", PAGE_SIZES);
	        modelmap.addAttribute("pager", pager);
	        modelmap.addAttribute("pagination", pagination);
	        return "dashboard";
	}
	
//	@RequestMapping(value = "/user/dashboard")
//	public String dashBoardPage(HttpSession httpSession,ModelMap modelmap, HttpServletResponse response) {
//		
//		//For cache
//		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
//        response.setHeader("Pragma", "no-cache"); // HTTP 1.0
//        response.setDateHeader("Expires", 0); // Proxies
//		//END For cache 
//		        
////		HttpSession session = request.getSession(false);
//		String userName = (String) httpSession.getAttribute("userName");
////		RegistrationRequestDTO registrationRequestDTO = userRepository.getUser(userName);
//
////		String password = (String) httpSession.getAttribute("password");
////		HttpSession session = request.getSession(false);
//		if(userName==null) {
//			return "redirect:/user/login";
//		}
//		List<UserResponseDTO> allusers = userRepository.getAllUsers();
////		List<UserAddress> addresses = new ArrayList<>();
////		for(RegistrationRequestDTO users:allusers) {
////			addresses.addAll(users.getAddresses());
////			for(UserAddress user:users.getAddresses()) {
////				System.out.println(user.getAddress());
////				addresses.addAll(user.getAddress());
////			}
////		}
////		modelmap.addAttribute("addresses", addresses);
//		modelmap.addAttribute("allusers", allusers);
//		return "Copy of dashboard";
//	}
	
	@RequestMapping(value = "/user/search", method = RequestMethod.GET)
	public String searchMethod(@RequestParam("search")String search,HttpSession httpSession,HttpServletRequest httpServletRequest,ModelMap modelmap,HttpServletResponse response) {
		
		//For cache
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
        response.setHeader("Pragma", "no-cache"); // HTTP 1.0
		response.setDateHeader("Expires", 0); // Proxies
		//END For cache
		String userName = (String) httpSession.getAttribute("userName");
		if(userName==null) {
			return "redirect:/user/login";
		}
//		String search = httpServletRequest.getParameter("search");
		boolean isError = service.validateSearch(search,modelmap);
		if(isError) {
			List<UserResponseDTO> users = userRepository.getSearchUsers(search);
			modelmap.addAttribute("persons", users);
			return "dashboard";
		}
		List<UserResponseDTO> users = userRepository.getSearchUsers(search);
		String pagination = "off";
		modelmap.addAttribute("pagination", pagination);
		if(users.size()==0) {
			String s = "No user found";
			modelmap.addAttribute("message",s);
			modelmap.addAttribute("persons", users);
			return "dashboard";
		}
		modelmap.addAttribute("persons", users);
		return "dashboard";
	}
	
	@RequestMapping(value = "/user/delete", method=RequestMethod.GET)
	public String deleteMethod(@RequestParam("id") String id) {
		
		userRepository.deleteUser(id);
		return "redirect:/user/dashboard";
	}
	
	@RequestMapping(value = "/user/edit", method=RequestMethod.GET)
	public String editMethod(ModelMap modelmap,@RequestParam("id") String id,HttpSession httpSession,HttpServletResponse response) {
		
		//For cache
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0
		response.setDateHeader("Expires", 0); // Proxies
        //END For cache
		String userName = (String) httpSession.getAttribute("userName");
		if(userName==null) {
			return "redirect:/user/login";
		}
		UserResponseDTO userResponseDTO = userRepository.editUser(id);
		if(!userResponseDTO.getEmail().equals(userName)) {
			String message = "You have to login first to edit that user information";
			modelmap.addAttribute("message", message);
			List<UserResponseDTO> allusers = userRepository.getAllUsers();
			modelmap.addAttribute("allusers", allusers);
			return "dashboard";
		}
//		modelmap.addAttribute("id", userResponseDTO.getId());
		modelmap.addAttribute("user", userResponseDTO);
		return "updateUser";
	}
	
	@RequestMapping(value = "/user/profile")
	public String profileMethod(ModelMap modelmap,HttpSession httpSession,HttpServletResponse response) {
		
		//For cache
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0
		response.setDateHeader("Expires", 0); // Proxies
		//END For cache
		String userName = (String) httpSession.getAttribute("userName");
//		String password = (String) httpSession.getAttribute("password");
		if(userName==null) {
			return "redirect:/user/login";
		}
		UserResponseDTO userResponseDTO = userRepository.getUser(userName);
		String str =  String.join(",", userResponseDTO.getHobby());
		modelmap.addAttribute("str", str);
		modelmap.addAttribute("user", userResponseDTO);
		
		return "profile";
	}
	
	@RequestMapping(value = "/user/logout")
	public String logoutMethod(ModelMap modelmap,HttpSession httpSession) {
		
//		@SuppressWarnings("unused")
//		String userName = (String) httpSession.getAttribute("userName");
		httpSession.removeAttribute("userName");
		httpSession.removeAttribute("password");
		httpSession.invalidate(); 
//		modelmap.addAttribute("user", new RegistrationRequestDTO());
		return "redirect:/user/login";
	}
	
	@RequestMapping(value = "/user/changePassword", method=RequestMethod.GET)
	public String changepMethod(ModelMap modelmap,HttpSession httpSession,HttpServletResponse response) {
		
		//For cache
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0
		response.setDateHeader("Expires", 0); // Proxies
		//END For cache
		String userName = (String) httpSession.getAttribute("userName");
		if(userName==null) {
			return "redirect:/user/login";
		}
		return "changePassword";
	}
	
	@RequestMapping(value = "/user/changePassword", method=RequestMethod.POST)
	public String changePost(ModelMap modelmap,HttpSession httpSession,HttpServletResponse response,HttpServletRequest httpServletRequest) {
		
		//For cache
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0
		response.setDateHeader("Expires", 0); // Proxies
		//END For cache
		String userName = (String) httpSession.getAttribute("userName");
		if(userName==null) {
			return "redirect:/user/login";
		}
		UserResponseDTO userResponseDTO = userRepository.getUser(userName);
		String oldP = httpServletRequest.getParameter("oldPassword");
		String newP = httpServletRequest.getParameter("newPassword");
		String confirmP = httpServletRequest.getParameter("confirmNewPassword");
		Boolean page = service.changePassValidate(userResponseDTO,oldP,newP,confirmP, modelmap);
		if(page) {
			return "changePassword";
		}
		else {
			return "redirect:/user/dashboard";
		}
	}
	
//	private static final int BUTTONS_TO_SHOW = 5;
//	private static final int INITIAL_PAGE = 0;
//	private static final int INITIAL_PAGE_SIZE = 5;
//	private static final int[] PAGE_SIZES = {5, 10, 20};
	private static final int BUTTONS_TO_SHOW = 5;
	private static final int INITIAL_PAGE = 0;
	private static final int INITIAL_PAGE_SIZE = 2;
	private static final int[] PAGE_SIZES = {2, 4, 8};
	    
//	@GetMapping("/")
//    public ModelAndView showPersonsPage(@RequestParam("pageSize") Optional<Integer> pageSize,
//                                        @RequestParam("page") Optional<Integer> page) {
//        ModelAndView modelAndView = new ModelAndView("dashboard");
//
//        // Evaluate page size. If requested parameter is null, return initial
//        // page size
//        int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
//        // Evaluate page. If requested parameter is null or less than 0 (to
//        // prevent exception), return initial size. Otherwise, return value of
//        // param. decreased by 1.
//        int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;
//
//        Page<User> persons = service.findAllPageable(PageRequest.of(evalPage, evalPageSize));
//        Pager pager = new Pager(persons.getTotalPages(), persons.getNumber(), BUTTONS_TO_SHOW);
//        
//        modelAndView.addObject("persons", persons);
//        modelAndView.addObject("selectedPageSize", evalPageSize);
//        modelAndView.addObject("pageSizes", PAGE_SIZES);
//        modelAndView.addObject("pager", pager);
//        return modelAndView;
//    }
	
	@RequestMapping(value = "/user/uploadExcelFile", method = RequestMethod.POST)
	public void uploadInDatabase(@RequestParam("file") MultipartFile multipartFile) {
		
		try {

            // Get the file and save it somewhere
//            byte[] bytes = multipartFile.getBytes();
//            String name = GeneralUtils.getRandomNumber(3) + multipartFile.getOriginalFilename();
//            Path path = Paths.get("E:/bhaumik/profilePic/" + name);
//            Files.write(path, bytes);
//            registrationRequestDTO.setImageName(name);

//            redirectAttributes.addFlashAttribute("message",
//                    "You successfully uploaded '" + file.getOriginalFilename() + "'");

        
//		File f = new File("E:/bhaumik/profilePic/" + name);
//		InputStream stream = ((ServletRequest) f).getInputStream();
		InputStream stream = multipartFile.getInputStream();
//		FileInputStream file = new FileInputStream(new File("E:/bhaumik/profilePic/" + name));
		
//		Workbook workbook = WorkbookFactory.create(f);
		XSSFWorkbook workbook = new XSSFWorkbook(stream);
		XSSFSheet sheet = workbook.getSheetAt(0);  /// this will read 1st workbook of ExcelSheet
		
//		HSSFWorkbook workbook = new HSSFWorkbook(stream);
//		HSSFSheet sheet = workbook.getSheetAt(0);  /// this will read 1st workbook of ExcelSheet
		Iterator<Row> rowIterator = sheet.iterator();    
		while (rowIterator.hasNext()) {
			// Skip read heading 
//			sheet.getRow()
			Row row = rowIterator.next();
			Row row1 = rowIterator.next();
			User user = new User();
			user.setCity(row1.getCell(0).toString());
			
//			if (row.getRowNum() == 0) {
//				continue;
//            }
//			Cell s = row.getCell(0);
		}
		} catch (Exception e) {
            e.printStackTrace();
        }
		
	}
	
	@RequestMapping(value = "user/download")
	public void excelDownload(HttpServletResponse response) {
		
		String[] columns = {"Id", "FirstName", "LastName", "Email", "Gender", "Hobby", "City", "State", "Country", "Address1",
				"Address2", "Address3"};
		List<UserResponseDTO> users = userRepository.getAllUsers();
		
		
		// Create a Workbook
        Workbook workbook = new XSSFWorkbook(); // new HSSFWorkbook() for generating `.xls` file

        /* CreationHelper helps us create instances of various things like DataFormat, 
           Hyperlink, RichTextString etc, in a format (HSSF, XSSF) independent way */
        CreationHelper createHelper = workbook.getCreationHelper();

        // Create a Sheet
        Sheet sheet = workbook.createSheet("Employee");

        // Create a Font for styling header cells
        Font headerFont = workbook.createFont();
        headerFont.setBoldweight((short) 10);
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setColor(IndexedColors.RED.getIndex());

        // Create a CellStyle with the font
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);

        // Create a Row
        Row headerRow = sheet.createRow(0);

        // Create cells
        for(int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerCellStyle);
        }
		
        // Create Other rows and cells with employees data
        int rowNum = 1;
        for(UserResponseDTO user: users) {
            Row row = sheet.createRow(rowNum++);

            row.createCell(0)
            .setCellValue(user.getId());
            
            row.createCell(1)
            .setCellValue(user.getFirstName());
            
            row.createCell(2)
            .setCellValue(user.getLastName());
            
            row.createCell(3)
            .setCellValue(user.getEmail());
            
            row.createCell(4)
            .setCellValue(user.getGender());
            
            row.createCell(5)
            .setCellValue(String.join(",", user.getHobby()));
            
            row.createCell(6)
            .setCellValue(user.getCity());
            
            row.createCell(7)
            .setCellValue(user.getState());
            
            row.createCell(8)
            .setCellValue(user.getCountry());

            row.createCell(9)
            .setCellValue(user.getUserAddress().get(0).getAddress());
            
            row.createCell(10)
            .setCellValue(user.getUserAddress().get(1).getAddress());
            
            row.createCell(11)
            .setCellValue(user.getUserAddress().get(2).getAddress());
        }
        
        // Resize all columns to fit the content size
        for(int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Write the output to a file
        try {
        	String temp = System.getProperty("java.io.tmpdir");
        	FileOutputStream fileOut = new FileOutputStream(temp + "poi-generated-file.xlsx");
        	System.out.println(fileOut);
			workbook.write(fileOut);
			fileOut.close();
		
		File file = new File(temp + "poi-generated-file.xlsx");
//        byte[] bytes = fileOut.getBytes;
////        String name = GeneralUtils.getRandomNumber(3) + multipartFile.getOriginalFilename();
//        String temp = System.getProperty("java.io.tmpdir");
//        Path path = Paths.get(temp);
//        Files.write(path, workbook);
        
        
        response.setContentType("application/xlsx");
        response.setHeader("Content-Disposition", "attachment;filename=" + file.getName());
        BufferedInputStream inStrem = new BufferedInputStream(new FileInputStream(file));
        BufferedOutputStream outStream = new BufferedOutputStream(response.getOutputStream());
        
        byte[] buffer = new byte[1024];
        int bytesRead = 0;
        while ((bytesRead = inStrem.read(buffer)) != -1) {
          outStream.write(buffer, 0, bytesRead);
        }
        outStream.flush();
        inStrem.close();
        // Closing the workbook
//        workbook.close();
        } catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This is for search user in auto complete
	 * @param searchstr
	 * @return
	 */
	@RequestMapping(value = "/suggestion", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Object autocompleteSuggestions(@RequestParam("searchstr") String searchstr) {
		
		System.out.println("searchstr: " + searchstr);
		
		List<User> users = hbUserRepository.findByName(searchstr,searchstr);
		
		List<SearchResponseDTO> searchResponseDTOs = new ArrayList<>();
		
		for(User user : users) {
			
			SearchResponseDTO searchResponseDTO = new SearchResponseDTO();
			searchResponseDTO.setFirstName(user.getFirstName());
			searchResponseDTO.setLastName(user.getLastName());
			
			searchResponseDTOs.add(searchResponseDTO);
		}
		
		return searchResponseDTOs;
	}
	
	@RequestMapping(value = "/suggestFriend", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Object autocompleteFriendSuggestions(@RequestParam("searchstr") String searchstr) {
		
		System.out.println("searchstr: " + searchstr);

		List<User> users = hbUserRepository.findByFirstNameLastName(searchstr,searchstr);
		
		List<SearchResponseDTO> searchResponseDTOs = new ArrayList<>();
		
		for(User user : users) {
			
			SearchResponseDTO searchResponseDTO = new SearchResponseDTO();
			searchResponseDTO.setFirstName(user.getFirstName());
			searchResponseDTO.setLastName(user.getLastName());
			
			searchResponseDTOs.add(searchResponseDTO);
		}
		
		return searchResponseDTOs;
	  }
	
//	@RequestMapping(value = "/user/searchFriend")
//	public String searchFriend(@RequestParam("search")String search,ModelMap modelMap,HttpSession httpSession,HttpServletResponse response) {
//		
//		//For cache
//		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
//		response.setHeader("Pragma", "no-cache"); // HTTP 1.0
//		response.setDateHeader("Expires", 0); // Proxies
//		//END For cache
//		String userName = (String) httpSession.getAttribute("userName");
////		String password = (String) httpSession.getAttribute("password");
//		
//		UserResponseDTO userResponseDTO = userRepository.getUser(userName);
//		modelMap.addAttribute("user", userResponseDTO);
//		
//		List<String> words=Arrays.asList(search.split("\\s"));
//		List<User> friendList = hbUserRepository.findByFirstName(words.get(0));
//		modelMap.addAttribute("friendList",friendList);
//		modelMap.addAttribute("search",search);
//		
//		return "friends";
//	}
	
	@Autowired
	HbFriends hbFriends;
	
	@RequestMapping(value = "/user/myfriends")
	public String myfriends(ModelMap modelMap,HttpSession httpSession,HttpServletResponse response) {
		
		//For cache
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0
		response.setDateHeader("Expires", 0); // Proxies
		//END For cache
		String userName = (String) httpSession.getAttribute("userName");
//		String password = (String) httpSession.getAttribute("password");
		
		UserResponseDTO userResponseDTO = userRepository.getUser(userName);
		modelMap.addAttribute("user", userResponseDTO);
		
		List<Friends> friends = hbFriends.findByUserId(userResponseDTO.getId());
		List<User> users = new ArrayList<User>();
		for(Friends friend:friends) {
			Optional<User> one = hbUserRepository.findById(Long.parseLong(friend.getFriendUserId()));
			User oneuser = one.get();
			users.add(oneuser);
		}
		modelMap.addAttribute("users", users);
		
		return "myfriends";
	}
	
	@RequestMapping(value = "/user/searchFriend", method=RequestMethod.POST)
	public String searchFriend(@Param("search")String search,ModelMap modelMap,HttpSession httpSession,HttpServletResponse response) {
		
		//For cache
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0
		response.setDateHeader("Expires", 0); // Proxies
		//END For cache
		String userName = (String) httpSession.getAttribute("userName");
//		String password = (String) httpSession.getAttribute("password");
				
		UserResponseDTO userResponseDTO = userRepository.getUser(userName);
		modelMap.addAttribute("user", userResponseDTO);
		
		List<String> words=Arrays.asList(search.split("\\s"));
		List<User> list = hbUserRepository.findByFirstName(words.get(0));
//		List<User> templist = new ArrayList<>();
//		templist.addAll(list);
		
		List<SearchFriendsResponseDTO> friendList = new ArrayList<SearchFriendsResponseDTO>();
		for(User userList : list) {

			if(userList.getId()==Long.parseLong(userResponseDTO.getId())) {
				continue;
			}
			List<Request> request = hbRequest.findBySendRequestUserId(userResponseDTO.getId());
			SearchFriendsResponseDTO sDto = new SearchFriendsResponseDTO();
			if(request!=null) {
				for(Request reUser:request) {
					if(Long.parseLong(reUser.getGetRequestUserId())==userList.getId()) {
						sDto.setAlreadySent(true);
					}
				}
			}
			Friends friend = hbFriends.findByBothId(userResponseDTO.getId(),userList.getId().toString());
			if(friend==null) {
				
				sDto.setId(userList.getId().toString());
				sDto.setFirstName(userList.getFirstName());
				sDto.setLastName(userList.getLastName());
				sDto.setImageName(userList.getImageName());
				sDto.setIsFriend(false);
				friendList.add(sDto);
			}
			if(friend!=null) {
				sDto.setId(userList.getId().toString());
				sDto.setFirstName(userList.getFirstName());
				sDto.setLastName(userList.getLastName());
				sDto.setImageName(userList.getImageName());
				sDto.setIsFriend(true);
				friendList.add(sDto);
			}
			
		}
		
		modelMap.addAttribute("friendList",friendList);
		modelMap.addAttribute("search",search);
		
		return "searchfriends";
	}
	
	@RequestMapping(value = "/sendFriendRequest",method= RequestMethod.POST)
	@ResponseBody
	public ResponseData handleFriendRequest(@Param("fuserId")String fuserId,@Param("userId")String userId) {
		
		System.out.println("b");
		
		userRepository.registerFriendRequest(fuserId,userId);
		ResponseData responseData = new ResponseData();
		responseData.setData("data");
		responseData.setStatus("SUCCESS");
		return responseData;
	}
	
	@RequestMapping(value = "/user/friendRequest", method=RequestMethod.GET)
	public String friends(ModelMap modelmap,HttpSession httpSession,HttpServletResponse response) {
		
		//For cache
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0
		response.setDateHeader("Expires", 0); // Proxies
		//END For cache
		String userName = (String) httpSession.getAttribute("userName");
		UserResponseDTO userResponseDTO = userRepository.getUser(userName);
		modelmap.addAttribute("fuser", userResponseDTO);
		
		List<Request> request = hbRequest.findByGetRequestUserId(userResponseDTO.getId());
		List<User> requestList = new ArrayList<>();
		for(Request id:request) {
			Optional<User> users = hbUserRepository.findById(Long.parseLong(id.getSendRequestUserId()));
			User user = users.get();
			requestList.add(user);
		}
		modelmap.addAttribute("requestList", requestList);
		
		return "friends";
	}
	
	@RequestMapping(value = "/acceptFriendRequest",method= RequestMethod.POST)
	@ResponseBody
	public ResponseData acceptButtonRequest(@Param("fuserId")String fuserId,@Param("userId")String userId) {
		
		System.out.println("b");
		
		userRepository.registerFriends(fuserId,userId);
		userRepository.deleteRowInRequestTable(fuserId,userId);
		ResponseData responseData = new ResponseData();
		responseData.setData("data");
		responseData.setStatus("SUCCESS");
		return responseData;
	}
	
	@RequestMapping(value = "/rejectFriendRequest",method= RequestMethod.POST)
	@ResponseBody
	public ResponseData rejectButtonRequest(@Param("fuserId")String fuserId,@Param("userId")String userId) {
		
		System.out.println("b");
		
		userRepository.deleteRowInRequestTable(fuserId,userId);
		ResponseData responseData = new ResponseData();
		responseData.setData("data");
		responseData.setStatus("SUCCESS");
		return responseData;
	}
	
	@RequestMapping(value = "/unFriendRequest",method= RequestMethod.POST)
	@ResponseBody
	public ResponseData unfriendButtonRequest(@Param("fuserId")String fuserId,@Param("userId")String userId) {
		
		System.out.println("b");
		
		userRepository.deleteRowInFriendsTable(fuserId,userId);
		ResponseData responseData = new ResponseData();
		responseData.setData("data");
		responseData.setStatus("SUCCESS");
		return responseData;
	}
	
//	@Test
//	public void testFindAllCustom() throws Exception {
//	    Page<Foo> allCustom = fooRepo.findAllCustom( pageable );
//
//	    assertThat( allCustom.getSize(), is( 2 ) );
//
//	    Page<Foo> sortByBazAsc = fooRepo.findAllCustom( new PageRequest( 0, 2, Sort.Direction.ASC, "bar.baz" ) );
//
//	    assertThat( sortByBazAsc.iterator().next().getBar().getBaz(), is( "2baz2bfoo" ) );
//
//	    Page<Foo> complexSort = fooRepo.findAllCustom( new PageRequest( 0, 2, new Sort(
//	            new Sort.Order( Sort.Direction.DESC, "bar.baz" ),
//	            new Sort.Order( Sort.Direction.ASC, "id" )
//	    ) ) );
//
//	    assertThat( complexSort.iterator().next().getBar().getBaz(), is( "baz1" ) );
//	}
//	@RequestMapping(value="/user/practice", method=RequestMethod.GET)
//	public String practice(ModelMap modelmap,HttpSession httpSession) {
//		
//		modelmap.addAttribute("user", new RegistrationRequestDTO());
//		return "practice";
//	}
	
//	@Autowired
//	JdbcTemplate jdbcTemplate;
//	
//	@RequestMapping(value="/user/practice", method=RequestMethod.POST)
//	public String practicePOST(HttpSession httpSession,ModelMap modelmap,RegistrationRequestDTO registrationRequestDTO,@RequestParam("id") String id) {
//		
////		boolean isError = service.validateAll(modelmap,registrationRequestDTO,id);
////
////		if(isError) {
////			
////			return "registration";
////			
////		} else {
////			
////			userRepository.doRegister(registrationRequestDTO);
////			modelmap.addAttribute("user", registrationRequestDTO);
////			String s = "Registration Successful";
////			modelmap.addAttribute("message",s);
////			return "redirect:/user/dashboard";
//		
//		String str = String.join(",", registrationRequestDTO.getHobby());
//		String sql = "INSERT INTO ITEM VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
////		jdbcTemplate.update(sql,null,);
////		}
//	}
}
