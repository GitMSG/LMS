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
   
	
	  @RequestMapping(path = "/addUserProfile", method = RequestMethod.POST) public
	  UserProfile addUserProfile(@RequestBody UserProfile aUserProfile, String
	  email) { UserProfile newProfile = new UserProfile(
	  aUserProfile.getFirstName(),
	  aUserProfile.getLastName(),aUserProfile.getRole(),
	  aUserProfile.getStartDate(),aUserProfile.getEndDate(),aUserProfile.
	  getProfilePic()); userProfileDao.createUserProfile(newProfile,email); return
	  aUserProfile;
	  
	  }
	 
    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String authorizedOnly() throws UnauthorizedException {
        if (!authProvider.userHasPermission(new String[] { "admin" })) {
            throw new UnauthorizedException();
        }
        return "Success";
    }
    
    @RequestMapping(path = "/users", method = RequestMethod.GET)
    public List<User> getAllUsers(){
    	List<User> allUsers = userDao.getAllUsers();
    	return allUsers;
    }
    
    @RequestMapping(path = "/changeUserPermission", method = RequestMethod.POST)
    public void changeUserPermission(@RequestBody User user) {
    	userDao.changePermission(user.getEmail(), user.getPermission());
    }
    
   
    
    
    
}







