package com.techelevator.employeeProfile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.techelevator.user.User;

@Component
public class JdbcEmployeeProfileDao implements EmployeeProfileDao {
	
	private JdbcTemplate myJdbcTemplate;
	
	@Autowired
	public JdbcEmployeeProfileDao(DataSource myDataSource) {
		this.myJdbcTemplate = new JdbcTemplate(myDataSource);
	}
	
	@Override
	public void createUserProfile(EmployeeProfile newProfile, String email) {
		String profilePic ;
		if (newProfile.getProfilePic() == null || newProfile.getProfilePic() == "") {
			profilePic = "https://res.cloudinary.com/goshorn/image/upload/v1596286167/lms_test/TE_bur_z3zvc4.png";
		}else {
			profilePic = newProfile.getProfilePic();
		}
		String insertSql = "INSERT INTO employee_profile "
						 + "(user_id, role, start_date,end_date, campus_short) "
						 + "VALUES ((SELECT id from users where email = '"+ email +"'),?,?,null,?) ;" + "UPDATE users SET firstname = ?,lastname = ?,profile_pic = ? WHERE email = '" + email+ "'";
		myJdbcTemplate.update(insertSql,newProfile.getRole(),newProfile.getStartDate(),newProfile.getCampusShortCode(),newProfile.getFirstName(), newProfile.getLastName(), profilePic);
	}
//	public void createUserProfile(EmployeeProfile newProfile, String email) {
//		String insertSql = "INSERT INTO user_profile "
//						 + "(profile_id, firstname, lastname, role, start_date,end_date, profile_pic, campus_short_code) "
//						 + "VALUES ((SELECT id from users where email = '"+ email +"'),?,?,?,?,null,?,?) ";
//		myJdbcTemplate.update(insertSql,newProfile.getFirstName(),newProfile.getLastName(),
//		newProfile.getRole(),newProfile.getStartDate(),newProfile.getProfilePic(),newProfile.getCampusShortCode());
//	}
	
	 @Override
	    public List<EmployeeProfile> getAllProfiles() {
	        List<EmployeeProfile> profiles = new ArrayList<EmployeeProfile>();
	        String sqlAllProfiles =	"SELECT employee_profile.*,users.email, users.firstname, users.lastname, users.profile_pic "
					 							+  		"FROM employee_profile "
					 							+		"JOIN users ON employee_profile.user_id = users.id ";
	        SqlRowSet results = myJdbcTemplate.queryForRowSet(sqlAllProfiles);
	        while (results.next()) {
	            EmployeeProfile aProfile = mapRowToProfile(results);
	            profiles.add(aProfile);
	        }
	        return profiles;
	    }
	 
	@Override
	public EmployeeProfile getProfileById(int id) {
		EmployeeProfile aProfile = new EmployeeProfile();
		String sqlProfile =	"SELECT employee_profile.*,users.email, users.firstname, users.lastname, users.profile_pic "
									 +  	"FROM employee_profile "
									 +		"JOIN users ON employee_profile.user_id = users.id "
		        					 +	"WHERE users.id = '"+ id +"'";
		SqlRowSet sqlResult = myJdbcTemplate.queryForRowSet(sqlProfile);
		while(sqlResult.next()) {
			aProfile = mapRowToProfile(sqlResult);
		}
			
					return aProfile;
	}
	
	private EmployeeProfile mapRowToProfile(SqlRowSet result) {
		EmployeeProfile newProfile = new EmployeeProfile();
		newProfile.setProfileId(result.getInt("emp_id"));
		newProfile.setUserId(result.getInt("user_id"));
		newProfile.setRole(result.getString("role"));
		newProfile.setStartDate(result.getDate("start_date"));
		newProfile.setCampusShortCode(result.getString("campus_short"));
		newProfile.setFirstName(result.getString("firstname"));
		newProfile.setLastName(result.getString("lastname"));
		newProfile.setProfilePic(result.getString("profile_pic"));
		return newProfile;
	}

}
