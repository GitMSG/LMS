package com.techelevator.user;

import javax.validation.constraints.AssertTrue;

import org.hibernate.validator.constraints.NotBlank;

/**
 * User
 */
public class User {
	@NotBlank(message = "Email is required")
	private String email;
	private String permission;
	private long id;
	private String firstName;
	private String lastName;
	private String profilePic;

	@NotBlank(message = "Password is required")
	private String password;
	private String confirmPassword;

	@AssertTrue(message = "Passwords must match")
	public boolean isPasswordMatching() {
		if (password != null) {
			return password.equals(confirmPassword);
		}
		return true;
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

	public String getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}

	public String getPassword() {
		return password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	/**
	 * @return the username
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the role
	 */
	public String getPermission() {
		return permission;
	}

	/**
	 * @param permission the role to set
	 */
	public void setPermission(String permission) {
		this.permission = permission;
	}

	/**
	 * @param username the username to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
}