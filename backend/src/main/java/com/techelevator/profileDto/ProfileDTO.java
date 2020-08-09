package com.techelevator.profileDto;

public class ProfileDTO {

	private int profileId;
	private String firstname;
	private String lastname;
	private String profilePic;
	private String role;
	private String campusShortCode;
	private boolean trainComp;
	private int trainMinutes;
	
	
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
	public String getCampusShortCode() {
		return campusShortCode;
	}
	public void setCampusShortCode(String campusShortCode) {
		this.campusShortCode = campusShortCode;
	}
	public boolean getTrainComp() {
		return trainComp;
	}
	public void setTrainComp(boolean trainComp) {
		this.trainComp = trainComp;
	}
	public int getTrainMinutes() {
		return trainMinutes;
	}
	public void setTrainMinutes(int trainMinutes) {
		this.trainMinutes = trainMinutes;
	}
	
	
	
	
}
