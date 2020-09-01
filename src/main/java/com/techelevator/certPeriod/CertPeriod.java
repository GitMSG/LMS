package com.techelevator.certPeriod;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class CertPeriod {
	
	private int certId;
	private int profileId;
	private LocalDate certStart;
	
	public CertPeriod() {
	}
	
	public CertPeriod(int profileId, LocalDate certStart) {
		super();
		this.profileId= profileId;
		this.certStart= certStart;
	}
	
	
	@Override
	public String toString() {
		return "CertPeriod [certId=" + certId + ", profileId=" + profileId + ", certStart=" + certStart + "]";
	}

	public int getCertId() {
		return certId;
	}

	public void setCertId(int certId) {
		this.certId = certId;
	}

	public int getProfileId() {
		return profileId;
	}

	public void setProfileId(int profileId) {
		this.profileId = profileId;
	}

	public LocalDate getCertStart() {
		LocalDate certStart = LocalDate.parse("2018-10-01");
		return certStart;
	}

	public void setCertStart(LocalDate certStart) {
		this.certStart = certStart;
	}
	
	
}
