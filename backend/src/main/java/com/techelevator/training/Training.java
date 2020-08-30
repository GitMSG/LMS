package com.techelevator.training;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Training {
	private int trainingId;
	private String name;
	private String provider;
	private String topic;
	private Date date;
	private int complianceTime;
	private String proof;
	private int electiveTime;
	private boolean approved;
	
	public Training() {
		
	}
	
	public Training(String name, String provider,String topic, Date date, int complianceTime,String proof, int electiveTime, boolean approved){
		super();
		this.name=name;
		this.provider=provider;
		this.topic=topic;
		this.date=date;
		this.complianceTime=complianceTime;
		this.proof=proof;
		this.electiveTime=electiveTime;
		this.approved=approved;
	}
	

	@Override
	public String toString() {
		return "Training [trainingId=" + trainingId + ", name=" + name + ", provider=" + provider + ", topic=" + topic
				+ ", date=" + date + ", complianceTime=" + complianceTime + ", proof=" + proof + ", electiveTime=" + electiveTime
				+ "]";
	}

	public int getTrainingId() {
		return trainingId;
	}

	public void setTrainingId(int trainingId) {
		this.trainingId = trainingId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public Date getDate() {
//		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd-yyyy");
//		dtf.format(date);
		return date;
	}

	public void setDate(Date date) {
//		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd-yyyy");
//		dtf.format(date);
		this.date = date;
	}

	public int getComplianceTime() {
		return complianceTime;
	}

	public void setComplianceTime(int complianceTime) {
		this.complianceTime = complianceTime;
	}

	public String getProof() {
		return proof;
	}

	public void setProof(String proof) {
		this.proof = proof;
	}

	public int getElectiveTime() {
		return electiveTime;
	}

	public void setElectiveTime(int electiveTime) {
		this.electiveTime = electiveTime;
	}

	public boolean getIsApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}
	
	
}
