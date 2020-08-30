package com.techelevator.campus;

import java.time.LocalDate;
import java.util.Date;

public class Campus {
	
	
	private String shortName;
	private String city;
	private String state;
	private int certLength;
	private Date currentPeriod;
	
	public Campus() {
		
	}
	public Campus( int certLength, Date currentPeriod) {
		this.certLength=certLength;
		this.currentPeriod=currentPeriod;
	}
	
	
	public Date getCurrentPeriod() {
		return currentPeriod;
	}


	public void setCurrentPeriod(Date currentPeriod) {
		this.currentPeriod = currentPeriod;
	}


	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public int getCertLength() {
		return certLength;
	}
	public void setCertLength(int certLength) {
		this.certLength = certLength;
	}


	@Override
	public String toString() {
		return "Campus [shortName=" + shortName + ", city=" + city + ", state=" + state + ", certLength=" + certLength
				+ ", currentPeriod=" + currentPeriod + "]";
	}
	
	

}
