package controller;

import java.util.ArrayList;

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
import model.Book;
import model.Publisher;

public class BookAddController {

	private ObservableList<String> publisherList;
	private Book book;
	private Driver app;
	private DBGateway gateway;
	private Logger logger = LogManager.getLogger(BookAddController.class);

	@FXML
	private TextField title;
	@FXML
	private TextField summary;
	@FXML
	private TextField yearPublished;
	@FXML
	private ChoiceBox<String> publisher;
	@FXML
	private TextField isbn;

	@FXML
	private Button cancel;
	@FXML
	private Button saveButton;

	public BookAddController(Driver app, DBGateway gateway) {
		this.app = app;
		book = new Book();
		this.gateway = gateway;
		ArrayList<Publisher> pubs = gateway.getPublishers();
		ArrayList<String> names = new ArrayList<String>();
		for(Publisher pub: pubs) {
			names.add(pub.getPublisherName());
		}
		this.publisherList = FXCollections.observableArrayList(names);
	}

	
	//Another way to handle an event on the base of an object instead of creating an event handler
	@FXML
	private void handleButton(ActionEvent event) throws Exception {
		if (event.getSource() == cancel) {
			//Set the center to blank
			app.getInstance().getRootPane().setCenter(new Pane());
		} else if (event.getSource() == saveButton) {
			logger.info("Book add selected.");
			//Instance variable for author
			book.setTitle(title.getText());
			book.setSummary(summary.getText());
			book.setPublisher(new Publisher(1,publisher.getValue()));
			book.setYearPublished(Integer.parseInt(yearPublished.getText()));
			book.setIsbn(isbn.getText());
			book.setGateway(gateway);
			//Function for saving author details
			book.saveBook();
			//Setting of the text in the view
			title.setText(String.valueOf(book.getTitle()));
			summary.setText(book.getSummary());
			publisher.setValue(book.getPublisher().getPublisherName());
			yearPublished.setText(Integer.toString(book.getYearPublished()));
			isbn.setText(book.getIsbn());
		}
	}
	//To initialize the gender list
	@FXML
	public void initialize() {
		publisher.setItems(publisherList);
		publisher.setValue("Unknown");
	}
}

