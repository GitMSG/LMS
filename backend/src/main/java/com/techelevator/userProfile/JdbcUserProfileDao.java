package com.techelevator.userProfile;

import java.time.LocalDate;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class JdbcUserProfileDao implements UserProfileDao {
	
	private JdbcTemplate myJdbcTemplate;
	
	@Autowired
	public JdbcUserProfileDao(DataSource myDataSource) {
		this.myJdbcTemplate = new JdbcTemplate(myDataSource);
	}
	
	public void createUserProfile(UserProfile newProfile, String email) {
		LocalDate start = newProfile.makeDate(newProfile.getStartDate());
		String insertSql = "INSERT INTO user_profile "
						 + "(profile_id, firstname, lastname, role, start_date,end_date, profile_pic) "
						 + "VALUES ((SELECT id from users where email = '"+ email +"'),?,?,?,?,null,?) ";
		
		myJdbcTemplate.update(insertSql,newProfile.getFirstName(),newProfile.getLastName(),
				   newProfile.getRole(),start,newProfile.getProfilePic());
	}
	
	public UserProfile getProfileById(int id) {
		UserProfile myProfile = new UserProfile();
		String sqlProfile = "SELECT * FROM user_profile WHERE profile_id = '" + id + "' ";
		SqlRowSet sqlResult = myJdbcTemplate.queryForRowSet(sqlProfile);
		while(sqlResult.next()) {
			myProfile = mapRowToProfile(sqlResult);
		}
		return myProfile;
		
	}
	private UserProfile mapRowToProfile(SqlRowSet result) {
		UserProfile newProfile = new UserProfile();
		newProfile.setProfileId(result.getInt("profile_id"));
		newProfile.setFirstName(result.getString("firstname"));
		newProfile.setLastName(result.getString("lastname"));
		newProfile.setRole(result.getString("role"));
		newProfile.setStartDate(result.getString("start_date"));
		newProfile.setProfilePic(result.getString("profile_pic"));
		return newProfile;
	}

}
