package com.techelevator.user;

import java.util.List;

public interface UserDao {

    public User saveUser(String email, String password, String permission);

    public void changePassword(User user, String newPassword);

    public User getValidUserWithPassword(String email, String password);

    public List<User> getAllUsers();

    public User getUserByEmail(String email);
    
    public void changePermission(String email, String permission);
    
    public void deleteUser(int id);

}
