package com.techelevator.userProfile;

import java.time.LocalDate;

	public class UserProfile {
	
	private long profileId;
	private String firstName;
	private String lastName;
	private String role;
	private LocalDate startDate;
	private LocalDate endDate;
	private String profilePic;
	
	public UserProfile( String firstName, String lastName, String role, LocalDate startDate, LocalDate endDate, String profilePic) {
		super();
	
		this.firstName = firstName;
		this.lastName = lastName;
		this.role = role;
		this.startDate = startDate;
		this.endDate = endDate;
		this.profilePic = profilePic;
	}

	public long getProfileId() {
		return profileId;
	}

	public void setProfileId(long profileId) {
		this.profileId = profileId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public String getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}

	
}
