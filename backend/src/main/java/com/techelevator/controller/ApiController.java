package com.techelevator.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.techelevator.authentication.AuthProvider;
import com.techelevator.authentication.UnauthorizedException;
import com.techelevator.campus.Campus;
import com.techelevator.campus.CampusDao;
import com.techelevator.certPeriod.CertPeriod;
import com.techelevator.employeeProfile.EmployeeProfile;
import com.techelevator.employeeProfile.EmployeeProfileDao;
import com.techelevator.profileDto.ProfileDTO;
import com.techelevator.training.Training;
import com.techelevator.training.TrainingDao;
import com.techelevator.user.User;
import com.techelevator.user.UserDao;

/**
 * ApiController
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiController {

	@Autowired
	private AuthProvider authProvider;
	@Autowired
	private UserDao userDao;
	@Autowired
	private CampusDao campusDao;
	@Autowired
	private EmployeeProfileDao employeeProfileDao;
	@Autowired
	private TrainingDao trainingDao;

	@RequestMapping(path = "/createProfile", method = RequestMethod.POST)
	public void addUserProfile(@RequestBody ProfileDTO aProfile) {
		String email = authProvider.getCurrentUser().getEmail();
		 userDao.createUserProfile( aProfile, email);
	}

	@RequestMapping(path = "/profile", method = RequestMethod.GET)
	public ProfileDTO getProfile()throws UnauthorizedException {
		ProfileDTO aProfile = new ProfileDTO();
		String email =  authProvider.getCurrentUser().getEmail();
				aProfile = employeeProfileDao.getProfileByEmail(email);
				return aProfile;
	}

	@RequestMapping(path = "/", method = RequestMethod.GET)
	public String authorizedOnly() throws UnauthorizedException {
		if (!authProvider.userHasPermission(new String[] { "admin" })) {
			throw new UnauthorizedException();
		}
		return "Success";
	}

	@RequestMapping(path = "/allProfiles", method = RequestMethod.GET)
	public List<ProfileDTO> getProfiles() {
		List<ProfileDTO> allProfiles = employeeProfileDao.getAllProfiles();
		return allProfiles;
	}

	@RequestMapping(path = "/addTraining/{id}", method = RequestMethod.POST)
	public void addTraining(@RequestBody Training newTraining, @PathVariable int id) {
		String permission = authProvider.getCurrentUser().getPermission();
		String code = campusDao.getShortCode(id);
		trainingDao.createTraining(newTraining, id, permission,code);
	}

	@RequestMapping(path = "/training/{id}", method = RequestMethod.GET)
	public List<Training> getTraining( @PathVariable  int id ) {
		List<Training> usersTraining = trainingDao.getAUsersTraining(id);
		return usersTraining;
	}
	
	@RequestMapping(path = "/deleteTraining/{id}", method = RequestMethod.DELETE)
	public void removeTraining(@PathVariable int id) {
		trainingDao.deleteTraining(id);
	}
	
	@RequestMapping(path = "/searchTraining", method = RequestMethod.GET)														
	public  Map<String,Training> getFiltered( @RequestParam String fromDate,@RequestParam String toDate) {
		LocalDate from = LocalDate.parse(fromDate);
		LocalDate to = LocalDate.parse(toDate);		
		 Map<String,Training> filteredTrainings = trainingDao.searchTrainingFiltered(from, to);;
		return filteredTrainings;
	}
	
	@RequestMapping(path = "/certperiod/{shortCode}", method = RequestMethod.GET)
	public Campus getCP( @PathVariable  String shortCode ) {
		Campus curCertPeriod = campusDao.getCertPeriod(shortCode);
		return curCertPeriod;
	}
	
	@RequestMapping(path = "/period/{campusShortCode}", method = RequestMethod.POST)
	public void setCertLength(@PathVariable String campusShortCode, @RequestBody Campus c) throws UnauthorizedException {
		String permission = authProvider.getCurrentUser().getPermission();
		if(permission.equals("admin") ) {
			campusDao.setCampusPeriod(campusShortCode, c);
		}
		else {
			throw new UnauthorizedException();
		}
		
	}

	@RequestMapping(path = "/needApproval", method = RequestMethod.GET)
	public Map<String,Training> getUnApproved() throws UnauthorizedException {
		String permission = authProvider.getCurrentUser().getPermission();
		if (permission.equals("admin")) {

			Map<String,Training> unApprovedList = trainingDao.getUnApproved();
			return unApprovedList;
		} else {
			throw new UnauthorizedException();
		}
	}
	
	@RequestMapping(path = "/updateApproval/{id}", method = RequestMethod.PUT)
	public void updateTrainingApproval(@PathVariable int id) {
		trainingDao.updateApproval(id);
	}
	
	@RequestMapping(path = "/deactivateUser/{id}", method = RequestMethod.PUT)
	public void deactivateUser(@RequestBody Date endDate, @PathVariable int id) {
		 employeeProfileDao.makeUserInactive(endDate,id);	
	}

	@RequestMapping(path = "/user/{id}", method = RequestMethod.GET)
	public User getUser(@PathVariable int id) {
		User user = userDao.getUserById(id);
		return user;
	}
	
	@RequestMapping(path = "/users", method = RequestMethod.GET)
	public List<User> getUsers() {
		List<User> allUsers = userDao.getAllUsers();
		return allUsers;
	}

	@RequestMapping(path = "/changeUserPermission", method = RequestMethod.POST)
	public void changeUserPermission(@RequestBody User user) {
		userDao.changePermission(user.getEmail(), user.getPermission());
	}
	
	@RequestMapping(path = "/updatePassword", method = RequestMethod.PUT)
	public void alterPassword(@RequestBody User user) {
		int id = (int)authProvider.getCurrentUser().getId();
		String password = user.getPassword();
		System.out.println(password);
		 userDao.changePassword(id, password);	
	}

	@RequestMapping(path = "/deleteUser/{id}", method = RequestMethod.DELETE)
	public void deleteUser(@PathVariable int id) {
		userDao.deleteUser(id);
	}

}
