package com.techelevator.employeeProfile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.techelevator.profileDto.ProfileDTO;
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
		String insertSql = 	"UPDATE employee_profile "
               							+	"SET role = ?,start_date = ?, end_date = null, campus_short = ? "
               							+	"FROM users "  
               						+	"WHERE employee_profile.user_id = users.id AND users.email = '"+email+"';"
									+ "UPDATE users SET firstname = ?,lastname = ?,profile_pic = ? WHERE email = '" + email+ "'";
		myJdbcTemplate.update(insertSql,newProfile.getRole(),newProfile.getStartDate(),newProfile.getCampusShortCode(),newProfile.getFirstName(), newProfile.getLastName(), profilePic);
	}
	
	 @Override
	    public List<ProfileDTO> getAllProfiles() {
	        List<ProfileDTO> profiles = new ArrayList<>();
	        String sqlAllProfiles =	 "SELECT employee_profile.emp_id,employee_profile.role,employee_profile.campus_short,employee_profile.start_date, " 
	        											+ "users.firstname, users.lastname,sum(training.compliance_time)compliance_time,sum(training.elective_time)elective_time, users.profile_pic "
	        											+ "FROM training "
	        											+ 		"JOIN training_cert_period ON training.train_id = training_cert_period.train_id "
	        											+ 		"JOIN cert_period ON training_cert_period.cert_period_id = cert_period.cert_id "
	        											+ 		"JOIN employee_profile ON cert_period.emp_id = employee_profile.emp_id "
	        											+ 		"JOIN users ON employee_profile.user_id = users.id "
	        											+ "WHERE  users.firstname NOT LIKE 'TE Firstname' AND employee_profile.end_date is NULL "
	        											+ "GROUP BY employee_profile.emp_id,employee_profile.role, "
	        											+ 					"employee_profile.campus_short,employee_profile.start_date, "
	        											+ 					"users.firstname, users.lastname, users.profile_pic";
	        SqlRowSet results = myJdbcTemplate.queryForRowSet(sqlAllProfiles);
	        while (results.next()) {
	            ProfileDTO aProfile = mapRowToProfileDto(results);
	            profiles.add(aProfile);
	        }
	        return profiles;
	    }
	 
	@Override
	public EmployeeProfile getProfileByEmail(String email) {
		EmployeeProfile aProfile = new EmployeeProfile();
							String sqlProfile =	"SELECT employee_profile.*,users.email, users.firstname, users.lastname, users.profile_pic "
									 +  	"FROM employee_profile "
									 +		"JOIN users ON employee_profile.user_id = users.id "
									 +	"WHERE users.email = '"+ email +"'";
				SqlRowSet sqlResult = myJdbcTemplate.queryForRowSet(sqlProfile);
				while(sqlResult.next()) {
				aProfile = mapRowToProfile(sqlResult);
						}
					return aProfile;
	}
	@Override 
	public void makeUserInactive(Date endDate, int id) {
		myJdbcTemplate.update("UPDATE employee_profile SET end_date = ? WHERE emp_id = ?", endDate, id);
	}
	
	private ProfileDTO mapRowToProfileDto(SqlRowSet result) {
		ProfileDTO newProfile = new ProfileDTO();
		newProfile.setProfileId(result.getInt("emp_id"));
		newProfile.setFirstname(result.getString("firstname"));
		newProfile.setLastname(result.getString("lastname"));
		newProfile.setProfilePic(result.getString("profile_pic"));
		newProfile.setRole(result.getString("role"));
		newProfile.setStartDate(result.getDate("start_date"));
		newProfile.setCampusShortCode(result.getString("campus_short"));
		newProfile.setComplianceTime(result.getInt("compliance_time"));
		newProfile.setElectiveTime(result.getInt("elective_time"));
		return newProfile;
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
