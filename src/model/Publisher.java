package model;

import javafx.beans.property.SimpleStringProperty;

public class Publisher {

	@Override
	public String toString() {
		return publisherName;
	}

	private int id;
	private String publisherName;

	public Publisher(int id, String publisherName) {
		super();
		this.id = id;
		this.publisherName = publisherName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPublisherName() {
		return publisherName;
	}

	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}

}
