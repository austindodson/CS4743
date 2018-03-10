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
import model.Book;
import model.Publisher;

public class BookEditController {

	private ObservableList<String> genderList = FXCollections.observableArrayList("Unknown");
	private Book book;
	private Stage stage;
	private Logger logger = LogManager.getLogger(AuthorEditController.class);
	@FXML
	private Label id;
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
	private Button save;

	public BookEditController(Book book, Stage stage) {
		this.book = book;
		this.stage = stage;
	}

	public void setFields() {
		id.setText(String.valueOf(book.getId()));
		title.setText(book.getTitle());
		summary.setText(book.getSummary());
		yearPublished.setText(book.getYearPublished()+"");
		publisher.setItems(genderList);
		isbn.setText(book.getIsbn());

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
						FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/BookDetail.fxml"));
						// set Controller
						loader.setController(new BookDetailController(book, stage));

						// load the fxml to the pane
						pane = (Pane) loader.load();

						// set the scene with the pane
						Scene scene = new Scene(pane, 800, 400);

						// set title
						stage.setTitle("Book Details");
						// put the scene on the stage
						stage.setScene(scene);
						// show the stage
						stage.show();

					} catch (IOException e) {
						logger.error("Error loading BookDetail.fxml" + e.getMessage());
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
					book.setTitle(title.getText());
					book.setSummary(summary.getText());
					book.setYearPublished(Integer.parseInt(yearPublished.getText()));
					book.setPublisher(new Publisher(1, publisher.getValue()));
					book.setIsbn(isbn.getText());

					book.updateBook();

					// We want to make a new scene
					try {
						// create a new pane layout to set the scene
						Pane pane = new Pane();
						// set the loader to the fxml file
						FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/BookDetail.fxml"));
						// set Controller
						loader.setController(new BookDetailController(book, stage));

						// load the fxml to the pane
						pane = (Pane) loader.load();

						// set the scene with the pane
						Scene scene = new Scene(pane, 800, 400);

						// set title
						stage.setTitle("Book Details");
						// put the scene on the stage
						stage.setScene(scene);
						// show the stage
						stage.show();

					} catch (IOException e) {
						logger.error("Error opening BookDetail.fxml" + e.getMessage());
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

