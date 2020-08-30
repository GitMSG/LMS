package com.techelevator.campus;

public interface CampusDao {

	public void setCampusPeriod(String campusShortCode,Campus c);
	
	public Campus getCertPeriod(String shortCode);
}
