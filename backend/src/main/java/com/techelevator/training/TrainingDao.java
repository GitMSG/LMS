package com.techelevator.training;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface TrainingDao {
	
	public List<Training> getAUsersTraining(int id);
	
	public void createTraining(Training myTraining, int id, String permission,String code);
	
	public Map<String,Training>getUnApproved();

	public void updateApproval(int id);
	
	public  Map<String,Training> searchTrainingFiltered(LocalDate from, LocalDate to);
	
	public void deleteTraining(int id);
	
}
