package controller;

import java.io.IOException;
import java.util.ArrayList;

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
import model.Book;

public class AuthorDetailController {

	private Logger logger = LogManager.getLogger(AuthorDetailController.class);
	private ObservableList<Book> bookList;
	private Author author;
	private Stage stage;
	@FXML
	private Label id;
	@FXML
	private Label firstname;
	@FXML
	private Label lastname;
	@FXML
	private Label dateofbirth;
	@FXML
	private Label gender;
	@FXML
	private Label website;
	@FXML
	private Button edit;
	@FXML
	private Button audit;
	
	public AuthorDetailController(Author author, Stage stage) {
		this.author = author;
		this.stage = stage;
		ArrayList<Book> books = author.getGateway().getBooksForAuthor(author);
		this.bookList = FXCollections.observableArrayList(books);
		System.out.println(bookList.toString());
	}

	// Set the label texts for author's fields.
	public void setLabels() {
		id.setText(String.valueOf(author.getId()));
		firstname.setText(author.getFirstname());
		lastname.setText(author.getLastname());
		dateofbirth.setText(author.getDateOfBirth());
		gender.setText(author.getGender());
		website.setText(author.getWebsite());
	}

	public void setButtonHandler() {
		EventHandler<MouseEvent> editHandler = new EventHandler<MouseEvent>() {
			// Overriden function for EventHandler
			@Override
			public void handle(MouseEvent mouseEvent) {
				// Mouse button usually the left one
				if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
					// Creating of another scene
					try {
						// New pane layout to set the scene
						Pane pane = new Pane();
						// Loader for the fxml file
						FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/AuthorDetailEdit.fxml"));
						// Controller setting for the author editing
						loader.setController(new AuthorEditController(author, stage));
						// Loading of fxml file to the pane
						pane = (Pane) loader.load();
						// Setting of the scene
						Scene scene = new Scene(pane, 500, 400);
						// The title setting
						stage.setTitle("Edit Author Details");
						// Setting scene on the stage
						stage.setScene(scene);
						// Show time
						stage.show();
					} catch (IOException e) {
						System.out.println(e.getMessage());
					}
				}
			}
		};
		// Setting mouse click event to the List View
		edit.setOnMouseClicked(editHandler);
		EventHandler<MouseEvent> auditHandler = new EventHandler<MouseEvent>() {
			//Overriden function for EventHandler
		
	@Override
	public void handle(MouseEvent mouseEvent) {
		// Mouse button usually the left one
		if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
			// Creating of another scene
			try {
				// New pane layout to set the scene
				Pane pane = new Pane();
				// Loader for the fxml file
				FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/AuthorAuditTrail.fxml"));
				// Controller setting for the author editing
				loader.setController(new AuthorAuditListController(author, author.getGateway()));
				// Loading of fxml file to the pane
				pane = (Pane) loader.load();
				// Setting of the scene
				Scene scene = new Scene(pane, 500, 400);
				// The title setting
				stage.setTitle("Author Audit");
				// Setting scene on the stage
				stage.setScene(scene);
				// Show time
				stage.show();
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		}
	
	}
};
//Setting mouse click event to the List View
		audit.setOnMouseClicked(auditHandler);
	}
	//Initialize to run stLabels in the fxml as part og the controller
	@FXML
	public void initialize() {
		setLabels();
		setButtonHandler();
	}
}