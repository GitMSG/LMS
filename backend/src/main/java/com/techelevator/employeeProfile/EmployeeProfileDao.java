package com.techelevator.employeeProfile;

import java.time.LocalDate;
import java.util.List;
import com.techelevator.profileDto.ProfileDTO;
import com.techelevator.user.User;

public interface EmployeeProfileDao {
	
	public void createUserProfile(EmployeeProfile newProfile, String email);
	
	public EmployeeProfile getProfileById(String email);
	
	//public List<EmployeeProfile> getAllProfiles();
	
	public List<ProfileDTO> getAllProfiles();

}
