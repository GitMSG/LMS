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
	    public List<ProfileDTO> getAllProfiles() {
		 ProfileDTO aProfile = new ProfileDTO();
	     List<ProfileDTO> profiles = new ArrayList<>();
	     String sqlBasic = "SELECT ep.emp_id,ep.role,ep.campus_short,ep.start_date,u.firstname, u.lastname, u.profile_pic "  
				    +"FROM users u "
			        +"JOIN employee_profile ep ON ep.user_id = u.id "
			    +"WHERE u.firstname NOT LIKE 'TE Firstname' AND ep.end_date is NULL" ;
	     SqlRowSet basicResult = myJdbcTemplate.queryForRowSet(sqlBasic);
			while(basicResult.next()) {								
				aProfile = mapRowToBasic(basicResult);
				int id = aProfile.getProfileId() ;
		            	aProfile = getProfileById(id);
		            	profiles.add(aProfile);   
			}
			return profiles;
	 }

	 @Override
		public ProfileDTO getProfileByEmail(String email) {
			ProfileDTO aProfile = new ProfileDTO();
			String sqlProfile =	"SELECT ep.emp_id,ep.role,ep.campus_short,ep.start_date,u.firstname, u.lastname, u.profile_pic, " 
											+ "sum(t.compliance_time)compliance_time,sum(t.elective_time)elective_time "
											+ "FROM users u "
											+ 		"JOIN employee_profile ep ON ep.user_id = u.id "
											+ 		"JOIN cert_period cp ON cp.emp_id = ep.emp_id "
											+ 		"JOIN training_cert_period tcp ON tcp.cert_period_id = cp.cert_id "
											+ 		"JOIN training t ON t.train_id = tcp.train_id "
											+	"WHERE u.email = ? "
											+ "GROUP BY ep.emp_id,ep.role, ep.campus_short,ep.start_date,u.firstname, u.lastname, u.profile_pic";
					SqlRowSet sqlResult = myJdbcTemplate.queryForRowSet(sqlProfile,email);
			
							while(sqlResult.next()) {
								aProfile = mapRowToProfileDto(sqlResult);
								}
							if(aProfile.getComplianceTime() == 0 && aProfile.getElectiveTime() == 0) {
								String sqlBasic = "SELECT ep.emp_id,ep.role,ep.campus_short,ep.start_date,u.firstname, u.lastname, u.profile_pic "  
									    +"FROM users u "
								        +"JOIN employee_profile ep ON ep.user_id = u.id "
								    +"WHERE u.email = ?" ;
								SqlRowSet basicResult = myJdbcTemplate.queryForRowSet(sqlBasic,email);
									while(basicResult.next()) {								
										aProfile = mapRowToBasic(basicResult);
									}
									return aProfile;
							}else {
								return aProfile;
							}
		}
	 @Override
		public ProfileDTO getProfileById(int id) {
			ProfileDTO aProfile = new ProfileDTO();
			String sqlProfile =	"SELECT ep.emp_id,ep.role,ep.campus_short,ep.start_date,u.firstname, u.lastname, u.profile_pic, " 
											+ "sum(t.compliance_time)compliance_time,sum(t.elective_time)elective_time "
											+ "FROM users u "
											+ 		"JOIN employee_profile ep ON ep.user_id = u.id "
											+ 		"JOIN cert_period cp ON cp.emp_id = ep.emp_id "
											+ 		"JOIN training_cert_period tcp ON tcp.cert_period_id = cp.cert_id "
											+ 		"JOIN training t ON t.train_id = tcp.train_id "
											+	"WHERE ep.emp_id = ? "
											+ "GROUP BY ep.emp_id,ep.role, ep.campus_short,ep.start_date,u.firstname, u.lastname, u.profile_pic";
					SqlRowSet sqlResult = myJdbcTemplate.queryForRowSet(sqlProfile,id);
			
							while(sqlResult.next()) {
								aProfile = mapRowToProfileDto(sqlResult);
								}
							if(aProfile.getComplianceTime() == 0 && aProfile.getElectiveTime() == 0) {
								String sqlBasic = "SELECT ep.emp_id,ep.role,ep.campus_short,ep.start_date,u.firstname, u.lastname, u.profile_pic "  
									    +"FROM users u "
								        +"JOIN employee_profile ep ON ep.user_id = u.id "
								    +"WHERE ep.emp_id = ?" ;
								SqlRowSet basicResult = myJdbcTemplate.queryForRowSet(sqlBasic,id);
									while(basicResult.next()) {								
										aProfile = mapRowToBasic(basicResult);
									}
									return aProfile;
							}else {
								return aProfile;
							}
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
	private ProfileDTO mapRowToBasic(SqlRowSet basicResult) {
		ProfileDTO aProfile = new ProfileDTO();
		aProfile.setProfileId(basicResult.getInt("emp_id"));
		aProfile.setFirstname(basicResult.getString("firstname"));
		aProfile.setLastname(basicResult.getString("lastname"));
		aProfile.setProfilePic(basicResult.getString("profile_pic"));
		aProfile.setRole(basicResult.getString("role"));
		aProfile.setStartDate(basicResult.getDate("start_date"));
		aProfile.setCampusShortCode(basicResult.getString("campus_short"));
		return aProfile;
	}
	
//	private EmployeeProfile mapRowToProfile(SqlRowSet result) {
//		EmployeeProfile newProfile = new EmployeeProfile();
//		newProfile.setProfileId(result.getInt("emp_id"));
//		newProfile.setUserId(result.getInt("user_id"));
//		newProfile.setRole(result.getString("role"));
//		newProfile.setStartDate(result.getDate("start_date"));
//		newProfile.setCampusShortCode(result.getString("campus_short"));
//		newProfile.setFirstName(result.getString("firstname"));
//		newProfile.setLastName(result.getString("lastname"));
//		newProfile.setProfilePic(result.getString("profile_pic"));
//		return newProfile;
//	}

}
