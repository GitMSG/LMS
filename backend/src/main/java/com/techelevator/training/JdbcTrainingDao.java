package com.techelevator.training;

import java.time.LocalDate;
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
	public void createTraining(Training newTraining, int id, String permission) {
		LocalDate certStartDate = new CertPeriod().getCertStart();
		boolean preApproved = permission.equals("admin");
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
			String email = results.getString("email");
			Training aTraining = mapRowToTraining(results);
			unApproved.put(email,aTraining);
		}
		return unApproved;
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

	private Training mapRowToTraining(SqlRowSet result) {
		Training aTraining = new Training();
		aTraining.setTrainingId(result.getInt("train_id"));
		aTraining.setName(result.getString("train_name"));
		aTraining.setProvider(result.getString("train_provider"));
		aTraining.setTopic(result.getString("train_topic"));
		aTraining.setDate(result.getDate("train_date"));
		aTraining.setComplianceTime(result.getInt("compliance_time"));
		aTraining.setProof(result.getString("train_proof"));
		aTraining.setElectiveTime(result.getInt("elective_time"));
		aTraining.setApproved(result.getBoolean("approved"));

		return aTraining;
	}

}
