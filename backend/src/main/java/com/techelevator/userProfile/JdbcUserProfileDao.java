package com.techelevator.userProfile;

import java.time.LocalDate;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class JdbcUserProfileDao implements UserProfileDao {
	
	private JdbcTemplate myJdbcTemplate;
	
	@Autowired
	public JdbcUserProfileDao(DataSource myDataSource) {
		this.myJdbcTemplate = new JdbcTemplate(myDataSource);
	}
	
	public void createUserProfile(UserProfile newProfile, String email) {
		String insertSql = "INSERT INTO user_profile "
						 + "(profile_id, firstname, lastname, role, start_date, end_date, profile_pic) "
						 + "VALUES ((SELECT id from users where email = '"+ email +"'),?,?,?,?,?,?) ";
		myJdbcTemplate.update(insertSql,newProfile.getProfileId(),newProfile.getFirstName(),newProfile.getLastName(),
				   newProfile.getRole(),newProfile.getStartDate(),newProfile.getEndDate(),newProfile.getProfilePic());
		
		
	}

}
