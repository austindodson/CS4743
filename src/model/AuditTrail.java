package model;

import java.sql.Timestamp;

public class AuditTrail {

	private int id;
	private String dateAdded;
	private String message;

	public AuditTrail() {
		id = 0;
		dateAdded = null;
		message = null;
	}

	public AuditTrail(int id, String dateAdded, String message) {
		this.id = id;
		this.dateAdded = dateAdded;
		this.message = message;
	}

	public int getRecordDescriptor() {
		return id;
	}

	public void setRecordDescriptor(int id) {
		this.id = id;
	}

	public String getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(String dateAdded) {
		this.dateAdded = dateAdded;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
