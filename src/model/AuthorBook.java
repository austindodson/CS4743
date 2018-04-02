package model;
import model.Author;
import model.Book;

public class AuthorBook {
	private int author_id;
	private int book_id;
	private int royalty;
	private boolean newRecord;
	
	public AuthorBook(int a_id, int b_id, int royalty, boolean newRec) {
		this.author_id = a_id;
		this.book_id = b_id;
		this.royalty = royalty;
		this.newRecord = newRec;
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