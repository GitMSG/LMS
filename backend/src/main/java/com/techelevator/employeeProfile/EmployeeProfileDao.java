package com.techelevator.employeeProfile;

import java.time.LocalDate;
import java.util.List;
import com.techelevator.profileDto.ProfileDTO;
import com.techelevator.user.User;

public interface EmployeeProfileDao {
	
	public void createUserProfile(EmployeeProfile newProfile, String email);
	
	
	public EmployeeProfile getProfileByEmail(String email);
	
	
	public List<ProfileDTO> getAllProfiles();

}
