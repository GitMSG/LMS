package com.techelevator.user;

import java.util.List;

import com.techelevator.employeeProfile.EmployeeProfile;
import com.techelevator.profileDto.ProfileDTO;

public interface UserDao {

    public User saveUser(String email, String password, String permission);

    public void changePassword(User user, String newPassword);

    public User getValidUserWithPassword(String email, String password);
    
    public void createUserProfile(ProfileDTO aProfile,  String email);

    public List<User> getAllUsers();

    public User getUserByEmail(String email);
    
    public User getUserById(int id);
    
    public void changePermission(String email, String permission);
    
    public void deleteUser(int id);

}
