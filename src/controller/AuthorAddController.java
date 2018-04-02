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
import model.Author;

public class AuthorAddController {

	private ObservableList<String> genderList = FXCollections.observableArrayList("Male", "Female", "Unknown");
	private Author author;
	private Driver app;
	private DBGateway gateway;
	private Logger logger = LogManager.getLogger(AuthorAddController.class);
	@FXML
	private Label id;
	@FXML
	private TextField firstname;
	@FXML
	private TextField lastname;
	@FXML
	private TextField dateofbirth;
	@FXML
	private ChoiceBox<String> gender;
	@FXML
	private TextField website;

	@FXML
	private Button cancel;
	@FXML
	private Button save;

	public AuthorAddController(Driver app, DBGateway gateway) {
		this.app = app;
		author = new Author();
		this.gateway = gateway;
	}

	// Another way to handle an event on the base of an object instead of creating
	// an event handler
	@FXML
	private void handleButtonAction(ActionEvent event) throws Exception {
		if (event.getSource() == cancel) {
			logger.info("Author add selected.");
			// Set the center to blank
			app.getInstance().getRootPane().setCenter(new Pane());
		} else if (event.getSource() == save) {
			// Instance variable for author
			author.setFirstname(firstname.getText());
			author.setLastname(lastname.getText());
			author.setDateOfBirth(dateofbirth.getText());
			author.setGender(gender.getValue());
			author.setWebsite(website.getText());
			author.setGateway(gateway);
			// Function for saving author details
			author.saveAuthor();
			// Setting of the text in the view
			id.setText(String.valueOf(author.getId()));
			firstname.setText(author.getFirstname());
			lastname.setText(author.getLastname());
			dateofbirth.setText(author.getDateOfBirth());
			gender.setValue(author.getGender());
			website.setText(author.getWebsite());
		}
	}

	// To initialize the gender list
	@FXML
	public void initialize() {
		gender.setItems(genderList);
		gender.setValue("Unknown");
	}
}
