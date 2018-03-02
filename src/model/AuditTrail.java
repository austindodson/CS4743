package model;

import java.sql.Timestamp;


public class AuditTrail {

	private String recordDescriptor;
	private Timestamp dateAdded;
	private String message;
	
	public AuditTrail(){
		recordDescriptor = null;
		dateAdded = null;
		message = null;
	}
	
	public AuditTrail(String recordDescriptor, Timestamp dateAdded, String message){
		this.recordDescriptor = recordDescriptor;
		this.dateAdded = dateAdded;
		this.message = message;
	}

	public String getRecordDescriptor() {
		return recordDescriptor;
	}

	public void setRecordDescriptor(String recordDescriptor) {
		this.recordDescriptor = recordDescriptor;
	}

	public Timestamp getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(Timestamp dateAdded) {
		this.dateAdded = dateAdded;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
