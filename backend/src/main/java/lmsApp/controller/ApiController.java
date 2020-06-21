package lmsApp.controller;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lmsApp.authentication.AuthProvider;
import lmsApp.authentication.UnauthorizedException;
import lmsApp.user.User;
import lmsApp.user.UserDao;


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
   
   
    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String authorizedOnly() throws UnauthorizedException {
        /*
        You can lock down which roles are allowed by checking
        if the current user has a role.
        
        In this example, if the user does not have the admin role
        we send back an unauthorized error.
        */
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







