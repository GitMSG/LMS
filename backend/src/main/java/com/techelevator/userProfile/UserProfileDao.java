package com.techelevator.userProfile;

import java.time.LocalDate;
import java.util.List;

public interface UserProfileDao {
	
	public void createUserProfile(UserProfile newProfile, String email);
	
	public UserProfile getProfileById(int id);
	
	public List<UserProfile> getAllProfiles();

}
