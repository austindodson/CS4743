package controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import libdemo.Driver;
import gateway.DBGateway;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Author;
import model.AuthorBook;
import model.Book;

public class authorbookaddcontroller {

	private AuthorBook ab;
	private Stage stage;
	private Book book;
	private Logger logger = LogManager.getLogger(AuthorAddController.class);
	@FXML
	private TextField authorid;
	@FXML
	private TextField royalty;
	@FXML
	private Button cancel;
	@FXML
	private Button save;

	public authorbookaddcontroller(Stage stage,Book book) {
		this.stage = stage;
		this.book = book;
		ab = new AuthorBook();
	}

	// Another way to handle an event on the base of an object instead of creating
	// an event handler
	@FXML
	private void handleButtonAction(ActionEvent event) throws Exception {
		if (event.getSource() == cancel) {
			logger.info("Cancel selected.");
			// Set the center to blank
		} else if (event.getSource() == save) {
			// Instance variable for author
			ab.setAuthor_id(Integer.parseInt(authorid.getText()));
			ab.setBook_id(book.getId());
			ab.setRoyalty(Integer.parseInt(royalty.getText()));
			// Function for saving author details
			book.getGateway().saveAuthorBook(ab, book);
			// Setting of the text in the view
			authorid.setText(String.valueOf(ab.getAuthor_id()));
			royalty.setText(String.valueOf(ab.getRoyalty()));
		}
	}

	// To initialize the gender list
	@FXML
	public void initialize() {
		
	}
}
