package gateway;

import java.io.*;
import java.util.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import model.Author;
import model.Book;
import model.Publisher;
import java.time.LocalDate;
import java.time.*;

public class DBGateway {

	private Connection conn;
	private Logger logger = LogManager.getLogger(DBGateway.class);

	public DBGateway() {
		try {
			// create new connection object
			makeConnection();
		} catch (Exception e) {
			logger.error("An error occured in creating a connection to the database: " + e.getMessage());
		}
	}

	// reads and creates objects of all the author objects currently in the table
	public ArrayList<Author> getAuthors() {

		ArrayList<Author> authors = new ArrayList<Author>();
		PreparedStatement st = null;
		ResultSet rs = null;
		try {

			String query = "select * " + " from author ";
			st = conn.prepareStatement(query);

			// used to run select statements
			rs = st.executeQuery();

			// create a author object for each row in the dog table
			while (rs.next()) {
				authors.add(new Author(rs.getInt("id"), rs.getString("firstname"), rs.getString("lastname"),
						rs.getString("dob"), rs.getString("gender"), rs.getString("website"), this));
			}

		} catch (SQLException e) {
			logger.error("Error reading from author table in database: " + e.getMessage());
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (st != null)
					st.close();
			} catch (SQLException e) {
				logger.error("Set close Statement or Result error: " + e.getMessage());
			}
		}

		return authors;
	}

	public ArrayList<Book> getBooks() {

		ArrayList<Book> books = new ArrayList<Book>();
		PreparedStatement st = null;
		ResultSet rs = null;
		try {

			String query = "select * " + " from book ";
			st = conn.prepareStatement(query);

			// used to run select statements
			rs = st.executeQuery();

			// create a book object for each row in the book table
			while (rs.next()) {
				books.add(new Book(rs.getInt("id"), rs.getString("title"), rs.getString("summary"),
						rs.getInt("year_published"), new Publisher(rs.getInt("publisher_id"), "unknown"), rs.getString("isbn"),
						rs.getString("date_added"), this));
			}

		} catch (SQLException e) {
			logger.error("Error reading from book table in database: " + e.getMessage());
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (st != null)
					st.close();
			} catch (SQLException e) {
				logger.error("Set close Statement or Result error: " + e.getMessage());
			}
		}

		return books;
	}
	
	// modify an existing author
	public void updateAuthor(Author author) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {

			String query = "update author set firstname = ?, lastname = ?, dob = ?, gender = ?, website = ? "
					+ "where id = ? ";
			st = conn.prepareStatement(query);
			st.setString(1, author.getFirstname());
			st.setString(2, author.getLastname());
			st.setString(3, author.getDateOfBirth());
			st.setString(4, author.getGender());
			st.setString(5, author.getWebsite());
			st.setInt(6, author.getId());

			// executeUpdate is used to run insert, update, and delete statements
			st.executeUpdate();

			logger.info("Updated author");
		} catch (SQLException e) {
			logger.error("Error updating author table in database: " + e.getMessage());
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (st != null)
					st.close();
			} catch (SQLException e) {
				logger.error("Set close Statement or Result error: " + e.getMessage());
			}
		}
	}
	
	public void updateBook(Book book) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {

			String query = "update book set title = ?, summary = ?, year_published = ?, publisher_id = ?, isbn = ?"
					+ "where id = ? ";
			st = conn.prepareStatement(query);
			System.out.print("hello!!!!!!!!"+book.toString());
			st.setString(1, book.getTitle());
			st.setString(2, book.getSummary());
			st.setInt(3, book.getYearPublished());
			st.setInt(4, book.getPublisher().getId());
			st.setString(5, book.getIsbn());
			st.setInt(6, book.getId());

			// executeUpdate is used to run insert, update, and delete statements
			st.executeUpdate();

			logger.info("Updated book");
		} catch (SQLException e) {
			logger.error("Error updating book table in database: " + e.getMessage());
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (st != null)
					st.close();
			} catch (SQLException e) {
				logger.error("Set close Statement or Result error: " + e.getMessage());
			}
		}
	}

	// create a new author
	public void saveAuthor(Author author) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {

			String query = "insert into author (firstname, lastname, dob, gender, website) "
					+ "values (?, ?, ?, ?, ?)";
			st = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			st.setString(1, author.getFirstname());
			st.setString(2, author.getLastname());
			st.setString(3, author.getDateOfBirth());
			st.setString(4, author.getGender());
			st.setString(5, author.getWebsite());
			// executeUpdate is used to run insert, update, and delete statements
			st.executeUpdate();

			rs = st.getGeneratedKeys();
			if (rs.first()) {
				author.setId(rs.getInt(1));
			} else {
				logger.error("Didn't get the new key.");
			}
			logger.info("New author created. Id = " + author.getId());
		} catch (SQLException e) {
			logger.error("Error inserting into Author table in database: " + e.getMessage());
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (st != null)
					st.close();
			} catch (SQLException e) {
				logger.error("Set close Statement or Result error: " + e.getMessage());
			}
		}
	}

	//To delete the author
	public void deleteAuthor(Author author) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			String query = "Delete from author " + "where id = ?";
			st = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			st.setInt(1, author.getId());
			// executeUpdate is used to run insert, update, and delete statements
			st.executeUpdate();
			logger.info("Author with id = " + author.getId() + " deleted from database.");
		} catch (SQLException e) {
			logger.error("Error: deleting from author's table in Databse: " + e.getMessage());
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (st != null)
					st.close();
			} catch (SQLException e) {
				logger.error("Set close Statement or Result error" + e.getMessage());
			}
		}
	}
	
	//To delete the author
		public void deleteBook(Book book) {
			PreparedStatement st = null;
			ResultSet rs = null;
			try {
				String query = "Delete from book " + "where id = ?";
				st = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
				st.setInt(1, book.getId());
				// executeUpdate is used to run insert, update, and delete statements
				st.executeUpdate();
				logger.info("Book with id = " + book.getId() + " deleted from database.");
			} catch (SQLException e) {
				logger.error("Error: deleting from book table in Database: " + e.getMessage());
			} finally {
				try {
					if (rs != null)
						rs.close();
					if (st != null)
						st.close();
				} catch (SQLException e) {
					logger.error("Set close Statement or Result error" + e.getMessage());
				}
			}
		}

	public void close() {
		try {
			if (conn != null) {
				// close the connection
				conn.close();
				logger.info("Database connection closed.");
			}
		} catch (SQLException e) {
			logger.error("Error while closing connection: " + e.getMessage());
		}
	}

	private void makeConnection() throws IOException, SQLException {
		Properties props = new Properties();
		FileInputStream fis = null;
		//Change this file to access your database table
		fis = new FileInputStream("db.properties");
		props.load(fis);
		fis.close();
		//Database creation by using login and password 
		MysqlDataSource ds = new MysqlDataSource();
		ds.setURL(props.getProperty("MYSQL_DB_URL"));
		ds.setUser(props.getProperty("MYSQL_DB_USERNAME"));
		ds.setPassword(props.getProperty("MYSQL_DB_PASSWORD"));
		//Connection Creation
		conn = ds.getConnection();
	}
}
