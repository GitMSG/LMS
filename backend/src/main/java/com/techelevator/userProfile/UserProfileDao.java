package com.techelevator.userProfile;

import java.time.LocalDate;

public interface UserProfileDao {
	
	public UserProfile createUserProfile(UserProfile newProfile, String email);
	
	

}
