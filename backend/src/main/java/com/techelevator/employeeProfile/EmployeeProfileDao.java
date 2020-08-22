package com.techelevator.employeeProfile;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import com.techelevator.profileDto.ProfileDTO;
import com.techelevator.user.User;

public interface EmployeeProfileDao {
	
	public void makeUserInactive(Date endDate, int id);
	
	public ProfileDTO getProfileByEmail(String email);
	
	public List<ProfileDTO> getAllProfiles();
	
	public ProfileDTO getProfileById(int id);

}
