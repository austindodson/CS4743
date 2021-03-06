package model;

import model.Author;
import model.Book;

public class AuthorBook {
	@Override
	public String toString() {
		return "AuthorBook [author_id=" + author_id + ", book_id=" + book_id + ", author_firstname=" + author_firstname
				+ ", author_lastname=" + author_lastname + ", royalty=" + royalty + ", newRecord=" + newRecord + "]";
	}

	private int author_id;
	private int book_id;
	private String author_firstname;
	private String author_lastname;
	private int royalty;
	private boolean newRecord;

	public AuthorBook(int a_id, int b_id, int royalty, boolean newRec, String a, String b) {
		this.author_id = a_id;
		this.book_id = b_id;
		this.royalty = royalty;
		this.newRecord = newRec;
		this.author_firstname = a;
		this.author_lastname = b;
	}
	
	public AuthorBook(int a_id, int b_id, int royalty, boolean newRec) {
		this.author_id = a_id;
		this.book_id = b_id;
		this.royalty = royalty;
		this.newRecord = newRec;
	}
	
	public AuthorBook() {
		this.author_id = 0;
		this.book_id = 0;
		this.royalty = 0;
		this.newRecord = true;
	}
	
	public String getfirst() {
		return author_firstname;
	}
	
	public String getlast() {
		return author_lastname;
	}

	public int getAuthor_id() {
		return author_id;
	}

	public void setAuthor_id(int author_id) {
		this.author_id = author_id;
	}

	public int getBook_id() {
		return book_id;
	}

	public void setBook_id(int book_id) {
		this.book_id = book_id;
	}

	public int getRoyalty() {
		return royalty;
	}

	public void setRoyalty(int royalty) {
		this.royalty = royalty;
	}

	public boolean isNewRecord() {
		return newRecord;
	}

	public void setNewRecord(boolean newRecord) {
		this.newRecord = newRecord;
	}
}
