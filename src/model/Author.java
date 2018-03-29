package model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import gateway.DBGateway;

public class Author {

	private int id;
	private String firstname;
	private String lastname;
	private String DateOfBirth;
	private String gender;
	private String website;
	private DBGateway gateway;
	private LocalDateTime lastmodified;

	public Author() {
		id = 0;
		firstname = null;
		lastname = null;
		DateOfBirth = null;
		gender = null;
		website = null;
		gateway = null;
	}

	public Author(int id, String firstname, String lastname, String dateofbirth, String gender, String website,
			DBGateway gateway) {
		this.id = id;
		this.setFirstname(firstname);
		this.setLastname(lastname);
		this.setDateOfBirth(dateofbirth);
		this.setGender(gender);
		this.setWebsite(website);
		this.gateway = gateway;
		gateway.setAuthorTimestamp(this);;
	}

	public void updateAuthor() {
		gateway.updateAuthor(this);
	}

	public void saveAuthor() {
		if (id == 0) {
			gateway.saveAuthor(this);
		} else {
			updateAuthor();
		}

	}
	
	public void setTimestamp(LocalDateTime update) {
		this.lastmodified = update;
	}
	
	public LocalDateTime getTimestamp() {
		return lastmodified;
	}

	// Getters and setters

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getDateOfBirth() {
		return DateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		DateOfBirth = dateOfBirth;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public DBGateway getGateway() {
		return gateway;
	}

	public void setGateway(DBGateway gateway) {
		this.gateway = gateway;

	}

}
