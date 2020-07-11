package com.techelevator.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
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
import org.springframework.web.bind.annotation.RestController;
import com.techelevator.authentication.AuthProvider;
import com.techelevator.authentication.UnauthorizedException;
import com.techelevator.training.Training;
import com.techelevator.training.TrainingDao;
import com.techelevator.user.User;
import com.techelevator.user.UserDao;
import com.techelevator.userProfile.UserProfile;
import com.techelevator.userProfile.UserProfileDao;


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
    private UserProfileDao userProfileDao;
    @Autowired
    private TrainingDao trainingDao;
   
	
      @RequestMapping(path = "/createProfile", method = RequestMethod.POST) 
      public void addUserProfile(@RequestBody UserProfile aUserProfile) {
    	  String email = authProvider.getCurrentUser().getEmail();
			UserProfile newProfile = new UserProfile( 	aUserProfile.getFirstName(),aUserProfile.getLastName()
																				   ,aUserProfile.getRole(),aUserProfile.getStartDate()
																				   ,aUserProfile.getEndDate(),aUserProfile.getProfilePic()
	        		   															); 
        userProfileDao.createUserProfile( newProfile , email );   
	  }
      
      @RequestMapping(path = "/profile", method = RequestMethod.GET)
      public UserProfile getProfileById() {
    	  int id = (int) authProvider.getCurrentUser().getId();
    	  UserProfile aProfile = userProfileDao.getProfileById(id);
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
    public List<UserProfile> getProfiles(){
    	List<UserProfile> allProfiles = userProfileDao.getAllProfiles();
    	return allProfiles;
    }
    
    @RequestMapping(path = "/addTraining", method = RequestMethod.POST) 
    public void addTraining(@RequestBody Training newTraining) {
  	  int id = (int) authProvider.getCurrentUser().getId();
			Training aTraining = new Training( 	newTraining.getName(),newTraining.getProvider(),newTraining.getTopic()
							,newTraining.getDate(),newTraining.getIsCompliance(),newTraining.getProof(),newTraining.getMinutes() ); 
			trainingDao.createTraining( newTraining , id );   
	  }
    
    @RequestMapping(path = "/training", method = RequestMethod.GET)
    public List<Training> getTraining(){
    	 int id = (int) authProvider.getCurrentUser().getId();
    	List<Training> usersTraining = trainingDao.getAUsersTraining(id);
    	return usersTraining;
    }
    
    @RequestMapping(path = "/users", method = RequestMethod.GET)
    public List<User> getUsers(){
    	List<User> allUsers = userDao.getAllUsers();
    	return allUsers;
    }
    
    @RequestMapping(path = "/changeUserPermission", method = RequestMethod.POST)
    public void changeUserPermission(@RequestBody User user) {
    	userDao.changePermission(user.getEmail(), user.getPermission());
    }
    
    @RequestMapping(path = "/deleteUser/{id}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable int id) {
    	userDao.deleteUser(id);
    }
    
   
    
    
    
}







