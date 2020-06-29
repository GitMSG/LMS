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
	
	public UserProfile createUserProfile(UserProfile newProfile, String email) {
		return null;
		
	}

}
