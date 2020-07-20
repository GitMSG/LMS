package com.techelevator.training;

import java.util.HashMap;
import java.util.List;

public interface TrainingDao {
	
	public List<Training> getAUsersTraining(int id);
	
	public void createTraining(Training myTraining, int id, String permission);
	
	public List<Training>getUnApproved();

}
