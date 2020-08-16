package com.techelevator.employeeProfile;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import com.techelevator.profileDto.ProfileDTO;
import com.techelevator.user.User;

public interface EmployeeProfileDao {
	
	public void createUserProfile(EmployeeProfile newProfile, String email);
	
	public void makeUserInactive(Date endDate, int id);
	
	public EmployeeProfile getProfileByEmail(String email);
	
	public List<ProfileDTO> getAllProfiles();

}
