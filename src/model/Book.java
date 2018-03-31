package model;

import java.time.LocalDate;
import java.util.ArrayList;

import gateway.DBGateway;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import model.Author;

public class Book {
	private int id;
	private String title;
	private String summary;
	private int yearPublished;
	private Publisher publisher;
	private String isbn;
	private String dateAdded;
	private DBGateway gateway;
	
	public Book(int id, String title, String summary, int yearPublished,
			Publisher publisher, String isbn,
			String localDate, DBGateway gateway) {
		super();
		this.id = id;
		this.setTitle(title);
		this.setSummary(summary);
		this.setYearPublished(yearPublished);
		this.setPublisher(publisher);
		this.setIsbn(isbn);
		this.setDateAdded(localDate);
		this.gateway = gateway;
	}
	
	public Book() {
		this.id= 0;
		this.title = "";
		this.summary= "";
		this.yearPublished = 0;
		this.isbn = "";
		this.dateAdded = "";
		
	}
	
	@Override
	public String toString() {
		return "Book [id=" + id + ", title=" + title + ", summary=" + summary + ", yearPublished=" + yearPublished
				+ ", publisher=" + publisher + ", isbn=" + isbn + ", dateAdded=" + dateAdded + "]";
	}

	public void updateBook() {
		gateway.updateBook(this);
	}
	
	public ArrayList<Author> getAuthors() {
		ArrayList<Author> authorlist = gateway.getAuthorsForBook(this);
		return authorlist;
	}
	
	public void saveBook() {
		System.out.println("saving book");
		gateway.saveBook(this);
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

	public String getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(String dateAdded) {
		this.dateAdded = dateAdded;
	}
	
	public void setGateway(DBGateway gateway) {
		this.gateway = gateway;
	}
	
	public DBGateway getGateway() {
		return gateway;
	}

}
