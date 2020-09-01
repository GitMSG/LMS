package com.techelevator.training;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.techelevator.campus.Campus;
import com.techelevator.certPeriod.CertPeriod;
import com.techelevator.employeeProfile.EmployeeProfile;

@Component
public class JdbcTrainingDao implements TrainingDao {
	private JdbcTemplate myJdbcTemplate;

	@Autowired
	public JdbcTrainingDao(DataSource myDataSource) {
		this.myJdbcTemplate = new JdbcTemplate(myDataSource);
	}
	
	@Override
	public void createTraining(Training newTraining, int id, String permission,String code) {
		LocalDate certStartDate = new CertPeriod().getCertStart();
		LocalDate trainDate = newTraining.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		boolean preApproved = permission.equals("admin");
		boolean inRange = withinRange(trainDate,code);
			if(inRange) {
						try {
							LocalDate myCertDate = (LocalDate) myJdbcTemplate.queryForObject(
									"SELECT cert_start_date FROM cert_period WHERE emp_id = '" + id + "'", LocalDate.class);
							if (myCertDate != null && myCertDate != certStartDate) {
								int certId = (int) myJdbcTemplate
										.queryForObject("SELECT cert_id FROM cert_period WHERE emp_id = '" + id + "'", int.class);
								int sqlTrain = myJdbcTemplate.queryForObject("INSERT INTO training "
										+ "(train_name, train_provider, train_topic, train_date, compliance_time,  elective_time,train_proof, approved) "
										+ "VALUES(?,?,?,?,?,?,?,'" + preApproved + "') RETURNING train_id", int.class,
										newTraining.getName(), newTraining.getProvider(), newTraining.getTopic(), newTraining.getDate(),
										newTraining.getComplianceTime(), newTraining.getElectiveTime(), newTraining.getProof());
								String sql = "INSERT INTO training_cert_period (train_id, cert_period_id) VALUES ('" + sqlTrain + "','"
										+ certId + "')";
								myJdbcTemplate.update(sql);
							}
						} catch (EmptyResultDataAccessException e) {
							int certId = myJdbcTemplate.queryForObject("INSERT INTO cert_period (emp_id, cert_start_date) VALUES('"
									+ id + "', '" + certStartDate + "' ) RETURNING cert_id", int.class);
							int sqlTrain = myJdbcTemplate.queryForObject("INSERT INTO training "
									+ "(train_name, train_provider, train_topic, train_date, compliance_time, elective_time,train_proof,  approved) "
									+ "VALUES(?,?,?,?,?,?,?,'" + preApproved + "') RETURNING train_id", int.class,
									newTraining.getName(), newTraining.getProvider(), newTraining.getTopic(), newTraining.getDate(),
									newTraining.getComplianceTime() , newTraining.getElectiveTime(),newTraining.getProof());
							String sql = "INSERT INTO training_cert_period (train_id, cert_period_id) VALUES ('" + sqlTrain + "','"
									+ certId + "')";
							myJdbcTemplate.update(sql);
						}
			}else {
				System.out.println("ERROR!!!! Training date is out of Range. See JdbcTrainingDao ");  // Need to create and throw exception for now.  But in the future could add
			}																																				//  logic to update 'current training period' automatically 
	}
	@Override
	public Map<String,Training> getUnApproved() {
		Map<String,Training> unApproved = new TreeMap<>();
		String sql = "SELECT u.email, t.* " + 
							"FROM training t " + 
								"JOIN training_cert_period tcp ON t.train_id = tcp.train_id " + 
								"JOIN cert_period cp ON tcp.cert_period_id = cp.cert_id " + 
								"JOIN employee_profile ep ON cp.emp_id = ep.emp_id " + 
								"JOIN users u ON ep.user_id = u.id " + 
							"WHERE t.approved = false AND ep.end_date is NULL";
		SqlRowSet results = myJdbcTemplate.queryForRowSet(sql);
		while (results.next()) {
			String trainId = Integer.toString(results.getInt("train_id") );
			String email = results.getString("email");
			String uniqueTrainingCode = email+" -Train Id "+trainId;
			Training aTraining = mapRowToTraining(results);
			unApproved.put(uniqueTrainingCode,aTraining);
		}
		return unApproved;
	}
	@Override
	public  Map<String,Training> searchTrainingFiltered(LocalDate from, LocalDate to) {
		 Map<String,Training> filteredList = new TreeMap<>();
		 String sql = "SELECT u.email, t.* " + 
					"FROM training t " + 
						"JOIN training_cert_period tcp ON t.train_id = tcp.train_id " + 
						"JOIN cert_period cp ON tcp.cert_period_id = cp.cert_id " + 
						"JOIN employee_profile ep ON cp.emp_id = ep.emp_id " + 
						"JOIN users u ON ep.user_id = u.id " + 
					"WHERE t.train_date >= '"+from+"' AND t.train_date <= '"+to+"'" ;
		 SqlRowSet results = myJdbcTemplate.queryForRowSet(sql);
			while (results.next()) {
				String trainId = Integer.toString(results.getInt("train_id") );
				String email = results.getString("email");
				String uniqueTrainingCode = email+" -Train Id "+trainId;
				Training aTraining = mapRowToTraining(results);
				filteredList.put(uniqueTrainingCode,aTraining);
			}
		return filteredList;
	}
	
	@Override
	public void updateApproval(int id) {
		String sql = "UPDATE training SET approved = true WHERE train_id = ?";
		myJdbcTemplate.update(sql, id);
	}
	
	@Override
	public List<Training> getAUsersTraining(int id) {
		List<Training> trainings = new ArrayList<>();
		String sql = "SELECT * FROM training "
						 + "JOIN training_cert_period ON training.train_id = training_cert_period.train_id "
						 + "JOIN cert_period ON training_cert_period.cert_period_id = cert_period.cert_id "
						 + "WHERE cert_period.emp_id = ?";
		SqlRowSet results = myJdbcTemplate.queryForRowSet(sql, id);

		while (results.next()) {
			Training aTraining = mapRowToTraining(results);
			trainings.add(aTraining);
		}
		return trainings;
	}
	
	@Override
	public void deleteTraining(int id) {
		String sql = "DELETE FROM training t WHERE t.train_id = '"+id+"'; DELETE FROM training_cert_period tcp WHERE tcp.train_id = '"+id+"'";
		myJdbcTemplate.update(sql);
	}
	
	private boolean withinRange(LocalDate td, String c) {																								// Helper method to see if training falls within current cert period
		Campus camp = new Campus();
		String campusSql = "SELECT cert_length,current_period FROM campus WHERE short_code = '" + c + "'";
		SqlRowSet results = myJdbcTemplate.queryForRowSet(campusSql);
		while(results.next()) {
			camp.setCertLength(results.getInt("cert_length"));
			camp.setCurrentPeriod(results.getDate("current_period"));
		}
		 LocalDate[] dates = createDates(camp);
		if(td.isAfter(dates[0]) && td.isBefore(dates[1])) {
			return true;
		}else {
			return false;
		}
	}
	
	private LocalDate[] createDates(Campus camp) {															// Helper method to create an array that contains start and end date of current period
		LocalDate[] dates = new LocalDate[2];
		LocalDate start = new java.sql.Date(camp.getCurrentPeriod().getTime()).toLocalDate() ;														//.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()			
		LocalDate end = start.plusMonths(camp.getCertLength());
		dates[0] = start;
		dates[1] = end;
		return dates;
	}
	
	private Training mapRowToTraining(SqlRowSet result) {
		Training aTraining = new Training();
		aTraining.setTrainingId(result.getInt("train_id"));
		aTraining.setName(result.getString("train_name"));
		aTraining.setProvider(result.getString("train_provider"));
		aTraining.setTopic(result.getString("train_topic"));
		aTraining.setDate(result.getDate("train_date")) ;
		aTraining.setComplianceTime(result.getInt("compliance_time"));
		aTraining.setProof(result.getString("train_proof"));
		aTraining.setElectiveTime(result.getInt("elective_time"));
		aTraining.setApproved(result.getBoolean("approved"));

		return aTraining;
	}

}
