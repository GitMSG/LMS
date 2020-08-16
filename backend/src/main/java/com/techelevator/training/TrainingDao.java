package com.techelevator.training;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface TrainingDao {
	
	public List<Training> getAUsersTraining(int id);
	
	public void createTraining(Training myTraining, int id, String permission);
	
//	public List<Training>getUnApproved();
	public Map<String,Training>getUnApproved();

	public void updateApproval(int id);
}
