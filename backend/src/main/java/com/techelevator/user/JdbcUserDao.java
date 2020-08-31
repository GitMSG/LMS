package com.techelevator.user;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.bouncycastle.util.encoders.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.techelevator.authentication.PasswordHasher;
import com.techelevator.employeeProfile.EmployeeProfile;
import com.techelevator.profileDto.ProfileDTO;

@Component
public class JdbcUserDao implements UserDao {

	private JdbcTemplate jdbcTemplate;
	private PasswordHasher passwordHasher;

	@Autowired
	public JdbcUserDao(DataSource dataSource, PasswordHasher passwordHasher) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.passwordHasher = passwordHasher;
	}

	@Override
	public User saveUser(String email, String password, String permission) { 			// Only creates a login for the user and
		byte[] salt = passwordHasher.generateRandomSalt();										// adds defaults into not null columns
		String hashedPassword = passwordHasher.computeHash(password, salt);
		String saltString = new String(Base64.encode(salt));
		String defaultFirst = "TE Firstname";
		String defaultLast = "TE Lastname";
		String defaultRole = "TE Instructor";
		LocalDate defaultDate = LocalDate.of(2016, Month.JANUARY, 1);
		String defaultCampus = "CLE";
		String defaultPic = "https://res.cloudinary.com/goshorn/image/upload/v1596286167/lms_test/TE_bur_z3zvc4.png";

		long newId = jdbcTemplate.queryForObject(
				"INSERT INTO users(email,firstname,lastname,profile_pic, password, salt, permission) VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING id",
				Long.class, email, defaultFirst, defaultLast, defaultPic, hashedPassword, saltString, permission);
		jdbcTemplate.update("INSERT INTO employee_profile(user_id,role,start_date,end_date,campus_short)VALUES ('"+newId+"','"+defaultRole+"','"+defaultDate+"',null,'"+defaultCampus+"')");
		User newUser = new User();
		newUser.setId(newId);
		newUser.setEmail(email);
		newUser.setPermission(permission);
		return newUser;
	}

	@Override
	public void createUserProfile(ProfileDTO aProfile, String email) {
		String profilePic ;
		if (aProfile.getProfilePic() == null || aProfile.getProfilePic() == "") {
			profilePic = "https://res.cloudinary.com/goshorn/image/upload/v1596286167/lms_test/TE_bur_z3zvc4.png";
		}else {
			profilePic = aProfile.getProfilePic();
		}
		String insertSql = 	"UPDATE employee_profile "
               							+	"SET role = ?,start_date = ?, end_date = null, campus_short = ? "
               							+	"FROM users "  
               						+	"WHERE employee_profile.user_id = users.id AND users.email = '"+email+"';"
									+ "UPDATE users SET firstname = ?,lastname = ?,profile_pic = ? WHERE email = '" + email+ "'";
		jdbcTemplate.update(insertSql,aProfile.getRole(),aProfile.getStartDate(),aProfile.getCampusShortCode(),aProfile.getFirstname(), aProfile.getLastname(), profilePic);
	}

	@Override
	public void changePassword(int id, String newPassword) {
		byte[] salt = passwordHasher.generateRandomSalt();
		String hashedPassword = passwordHasher.computeHash(newPassword, salt);
		String saltString = new String(Base64.encode(salt));
		jdbcTemplate.update("UPDATE users SET password=?, salt=? WHERE id=?", hashedPassword, saltString, id);
	}

	@Override
	public User getValidUserWithPassword(String email, String password) {
		String sqlSearchForUser = "SELECT * FROM users WHERE UPPER(email) = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSearchForUser, email.toUpperCase());
		if (results.next()) {
			String storedSalt = results.getString("salt");
			String storedPassword = results.getString("password");
			String hashedPassword = passwordHasher.computeHash(password, Base64.decode(storedSalt));
			if (storedPassword.equals(hashedPassword)) {
				return mapResultToUser(results);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	@Override
	public List<User> getAllUsers() {
		List<User> users = new ArrayList<User>();
		String sqlSelectAllUsers = "SELECT id, email, permission FROM users";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSelectAllUsers);
		while (results.next()) {
			User user = mapResultToUser(results);
			users.add(user);
		}

		return users;
	}

	@Override
	public User getUserByEmail(String email) {
		String sqlSelectUserByEmail = "SELECT id, email, permission FROM users WHERE email = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSelectUserByEmail, email);
		if (results.next()) {
			User aUser = new User();
			aUser.setId(results.getLong("id"));
			aUser.setEmail(results.getString("email"));
			aUser.setPermission(results.getString("permission"));
			return aUser;
		} else {
			return null;
		}
	}
	
	@Override
	public User getUserById(int id) {
		String sqlSelectUserByEmail = "SELECT id, email, permission " 
														+  "FROM users u "
														+  		"JOIN employee_profile ep ON ep.user_id = u.id "
														+  "WHERE ep.emp_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSelectUserByEmail, id);
		if (results.next()) {
			User aUser = new User();
			aUser.setId(results.getLong("id"));
			aUser.setEmail(results.getString("email"));
			aUser.setPermission(results.getString("permission"));
			return aUser;
		} else {
			return null;
		}
	}

	@Override
	public void deleteUser(int id) {
		String sql = "DELETE  FROM training t "
						+		"WHERE t.train_id IN  ( SELECT tcp.train_id " 
                 		+    	    "FROM training_cert_period tcp "
                        +			"JOIN cert_period cp ON cp.cert_id = tcp.cert_period_id "
                        + 		"WHERE cp.emp_id IN (SELECT ep.emp_id " 
                        + 			"FROM employee_profile ep "
                        +			"JOIN users u ON ep.user_id = u.id  AND u.id = ? )); "
                        +	"DELETE FROM employee_profile WHERE user_id =? ; " 
                      	+	"DELETE FROM users WHERE id = ?";
		jdbcTemplate.update(sql, id, id,id);
	}

	@Override
	public void changePermission(String email, String permission) {
		// TODO Auto-generated method stub
		jdbcTemplate.update("UPDATE users SET permission = ? WHERE email = ?", permission, email);
	}

	private User mapResultToUser(SqlRowSet results) {
		User user = new User();
		user.setId(results.getLong("id"));
		user.setEmail(results.getString("email"));
		user.setPermission(results.getString("permission"));
		user.setFirstName(results.getString("firstname"));
		user.setLastName(results.getString("lastname"));
		user.setProfilePic(results.getString("profile_pic"));
		return user;
	}

}
