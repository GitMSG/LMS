package com.techelevator.campus;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class JdbcCampusDao implements CampusDao {
	
private JdbcTemplate myJdbcTemplate;
	
	@Autowired
	public JdbcCampusDao(DataSource myDataSource) {
		this.myJdbcTemplate = new JdbcTemplate(myDataSource);
	}
	
	@Override
	public void setCampusPeriod(String campusShortCode, Campus c) {
		myJdbcTemplate.update("UPDATE campus SET  cert_length = ?, current_period = ? WHERE short_code = '"+campusShortCode+"'", c.getCertLength(),c.getCurrentPeriod());
	}
	
	@Override
	public Campus getCertPeriod(String shortCode) {
		Campus curPeriod = new Campus();
		String sql = "SELECT cert_length, current_period FROM campus WHERE short_code = '"+shortCode+"' ";
		SqlRowSet results = myJdbcTemplate.queryForRowSet(sql);
		while(results.next()) {
			curPeriod.setCertLength(results.getInt("cert_length"));
			curPeriod.setCurrentPeriod(results.getDate("current_period"));
		}
		return curPeriod;
	}
	
	@Override
	public String getShortCode(int id) {
		String code = myJdbcTemplate.queryForObject("SELECT short_code "
																						+"FROM campus c "
																						+"JOIN employee_profile ep ON ep.campus_short = c.short_code "
																						+ "WHERE ep.emp_id = '"+id+"' ",String.class); 
			
		return code;
	}

}
