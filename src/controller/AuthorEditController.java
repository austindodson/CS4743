package controller;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Author;

public class AuthorEditController {

	private ObservableList<String> genderList = FXCollections.observableArrayList("Male", "Female", "Unknown");
	private Author author;
	private Stage stage;
	private Logger logger = LogManager.getLogger(AuthorEditController.class);
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

	public AuthorEditController(Author author, Stage stage) {
		this.author = author;
		this.stage = stage;
	}

	public void setFields() {
		id.setText(String.valueOf(author.getId()));
		firstname.setText(author.getFirstname());
		lastname.setText(author.getLastname());
		dateofbirth.setText(author.getDateOfBirth());
		gender.setItems(genderList);
		website.setText(author.getWebsite());

	}

	public void setButtonHandlers() {
		EventHandler<MouseEvent> cancelHandler = new EventHandler<MouseEvent>() {
			// EventHandler has a "handle" function that must be overridden
			@Override
			public void handle(MouseEvent mouseEvent) {
				// MouseButton.PRIMARY indicates the left mouse button (usually)
				if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {

					// We want to make a new scene
					try {
						// create a new pane layout to set the scene
						Pane pane = new Pane();
						// set the loader to the fxml file
						FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/AuthorDetail.fxml"));
						// set Controller
						loader.setController(new AuthorDetailController(author, stage));

						// load the fxml to the pane
						pane = (Pane) loader.load();

						// set the scene with the pane
						Scene scene = new Scene(pane, 800, 400);

						// set title
						stage.setTitle("Author Details");
						// put the scene on the stage
						stage.setScene(scene);
						// show the stage
						stage.show();

					} catch (IOException e) {
						logger.error("Error loading AuthorDetail.fxml" + e.getMessage());
					}
				}
			}
		};
		// after creating the double click event, set it to the List View object as a
		// mouse click event
		cancel.setOnMouseClicked(cancelHandler);

		EventHandler<MouseEvent> saveHandler = new EventHandler<MouseEvent>() {
			// EventHandler has a "handle" function that must be overridden
			@Override
			public void handle(MouseEvent mouseEvent) {
				// MouseButton.PRIMARY indicates the left mouse button (usually)
				if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
					author.setFirstname(firstname.getText());
					author.setLastname(lastname.getText());
					author.setDateOfBirth(dateofbirth.getText());
					author.setGender(gender.getValue());
					author.setWebsite(website.getText());

					author.updateAuthor();

					// We want to make a new scene
					try {
						// create a new pane layout to set the scene
						Pane pane = new Pane();
						// set the loader to the fxml file
						FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/AuthorDetail.fxml"));
						// set Controller
						loader.setController(new AuthorDetailController(author, stage));

						// load the fxml to the pane
						pane = (Pane) loader.load();

						// set the scene with the pane
						Scene scene = new Scene(pane, 800, 400);

						// set title
						stage.setTitle("Author Details");
						// put the scene on the stage
						stage.setScene(scene);
						// show the stage
						stage.show();

					} catch (IOException e) {
						logger.error("Error opening AuthorDetail.fxml" + e.getMessage());
					}
				}
			}
		};
		save.setOnMouseClicked(saveHandler);
	}

	@FXML
	public void initialize() {
		setFields();
		setButtonHandlers();
	}

}
