package com.techelevator.userProfile;

import java.time.LocalDate;

public interface UserProfileDao {
	
	public void createUserProfile(UserProfile newProfile, String email);
	
	public UserProfile getProfileById(int id);

}
