package model;

import java.time.LocalDate;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class Book {
	private int id;
	private String title;
	private String summary;
	private int yearPublished;
	private Publisher publisher;
	private String isbn;
	private LocalDate dateAdded;
	
	public Book(int id, String title, String summary, int yearPublished,
			Publisher publisher, String isbn,
			LocalDate localDate) {
		super();
		this.id = id;
		this.setTitle(title);
		this.setSummary(summary);
		this.setYearPublished(yearPublished);
		this.setPublisher(publisher);
		this.setIsbn(isbn);
		this.setDateAdded(localDate);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title2) {
		this.title = title2;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public int getYearPublished() {
		return yearPublished;
	}

	public void setYearPublished(int yearPublished) {
		this.yearPublished = yearPublished;
	}

	public Publisher getPublisher() {
		return publisher;
	}

	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public LocalDate getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(LocalDate dateAdded) {
		this.dateAdded = dateAdded;
	}
	
	

}
