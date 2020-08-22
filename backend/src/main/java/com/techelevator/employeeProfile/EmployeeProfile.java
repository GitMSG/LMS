package com.techelevator.employeeProfile;

import java.time.LocalDate;
import java.util.Date;

public class EmployeeProfile {

	private int profileId;
	private int userId;
	private String role;
	private Date startDate;
	private Date endDate;
	private String campusShortCode;

	public EmployeeProfile() {

	}

	public EmployeeProfile(String role, Date startDate, String campusShortCode) {
		super();
		this.role = role;
		this.startDate = startDate;
		this.campusShortCode = campusShortCode;
	}
	

	public long getProfileId() {
		return profileId;
	}

	public void setProfileId(int profileId) {
		this.profileId = profileId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
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

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getCampusShortCode() {
		return campusShortCode;
	}

	public void setCampusShortCode(String campusShortCode) {
		this.campusShortCode = campusShortCode;
	}

}
