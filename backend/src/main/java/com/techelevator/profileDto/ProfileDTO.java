package com.techelevator.profileDto;

import java.sql.Date;
import java.time.LocalDate;

public class ProfileDTO {

	private int profileId;
	private String firstname;
	private String lastname;
	private String profilePic;
	private String role;
	private String campusShortCode;
	private Date startDate;
	private int complianceTime;
	private int electiveTime;
	
	
	public int getProfileId() {
		return profileId;
	}
	public void setProfileId(int profileId) {
		this.profileId = profileId;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getProfilePic() {
		return profilePic;
	}
	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date date) {
		this.startDate = date;
	}
	public String getCampusShortCode() {
		return campusShortCode;
	}
	public void setCampusShortCode(String campusShortCode) {
		this.campusShortCode = campusShortCode;
	}
	public int getComplianceTime() {
		return complianceTime;
	}
	public void setComplianceTime(int compliance) {
		this.complianceTime = compliance;
	}
	public int getElectiveTime() {
		return electiveTime;
	}
	public void setElectiveTime(int elective) {
		this.electiveTime = elective;
	}
	
	
	
	
}
