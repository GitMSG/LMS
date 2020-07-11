package com.techelevator.training;

import java.time.LocalDate;
import java.util.Date;

public class Training {
	private int trainingId;
	private String name;
	private String provider;
	private String topic;
	private Date date;
	private boolean isCompliance;
	private String proof;
	private int minutes;
	
	public Training() {
		
	}
	
	public Training(String name, String provider,String topic, Date date, boolean isCompliance,String proof, int minutes){
		super();
		this.name=name;
		this.provider=provider;
		this.topic=topic;
		this.date=date;
		this.isCompliance=isCompliance;
		this.proof=proof;
		this.minutes=minutes;
	}
	

	@Override
	public String toString() {
		return "Training [trainingId=" + trainingId + ", name=" + name + ", provider=" + provider + ", topic=" + topic
				+ ", date=" + date + ", isCompliance=" + isCompliance + ", proof=" + proof + ", minutes=" + minutes
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
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public boolean getIsCompliance() {
		return isCompliance;
	}

	public void setCompliance(boolean isCompliance) {
		this.isCompliance = isCompliance;
	}

	public String getProof() {
		return proof;
	}

	public void setProof(String proof) {
		this.proof = proof;
	}

	public int getMinutes() {
		return minutes;
	}

	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}
	
	
}
