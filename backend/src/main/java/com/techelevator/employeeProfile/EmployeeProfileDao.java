package com.techelevator.employeeProfile;

import java.time.LocalDate;
import java.util.List;

import com.techelevator.user.User;

public interface EmployeeProfileDao {
	
	public void createUserProfile(EmployeeProfile newProfile, String email);
	
	public EmployeeProfile getProfileById(int id);
	
	public List<EmployeeProfile> getAllProfiles();

}
