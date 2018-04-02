package controller;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.converter.DefaultStringConverter;
import model.Author;
import model.AuthorBook;
import model.Book;

public class BookDetailController {

	private Book book;
	private Stage stage;
	private Logger logger = LogManager.getLogger(BookDetailController.class);
	private ObservableList<Author> authorList;
	@FXML
	private Label id;
	@FXML
	private Label title;
	@FXML
	private Label summary;
	@FXML
	private Label yearPublished;
	@FXML
	private Label publisher;
	@FXML
	private Label isbn;
	@FXML
	private Label dateAdded;
	@FXML
	private Button edit;
	@FXML
	private Button audit;
	@FXML
	private TableView<AuthorBook> authorTable;
	@FXML
	private TableColumn<AuthorBook, String> authorName;
	@FXML
	private TableColumn<AuthorBook, String> authorRoyalty;

	private ObservableList<AuthorBook> authorBookList;
	public BookDetailController(Book book, Stage stage) {
		this.book = book;
		this.stage = stage;
		ArrayList<Author> authors = book.getGateway().getAuthorsForBook(book);
		this.authorList = FXCollections.observableArrayList(authors);
		authorTable = new TableView<>();
		authorTable.setEditable(true);
	}

	// Set the label texts for author's fields.
	public void setLabels() {
		id.setText(String.valueOf(book.getId()));
		title.setText(book.getTitle());
		summary.setText(book.getSummary());
		yearPublished.setText(book.getYearPublished() + "");
		publisher.setText(book.getPublisher().getPublisherName());
		isbn.setText(book.getIsbn());
		dateAdded.setText(book.getDateAdded());
		if (authorList != null) {
			ArrayList<AuthorBook> authbook = new ArrayList<AuthorBook>();
			for (Author x: authorList) {
				AuthorBook temp = new AuthorBook(x.getId(), book.getId(), book.getGateway().getRoyalty(x, book), false, x.getFirstname(), x.getLastname());
				authbook.add(temp);
			}
			authorBookList = FXCollections.observableArrayList(authbook);
			authorTable.getItems().addAll(authorBookList);
			authorName.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getfirst()+ " " +c.getValue().getlast()));
			authorRoyalty.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getRoyalty() + "%"));
			authorName.setEditable(true);

		}
	}

	public void setButtonHandler() {
		EventHandler<MouseEvent> editHandler = new EventHandler<MouseEvent>() {

			//Overridden function for EventHandler
			@Override
			public void handle(MouseEvent mouseEvent) {
				// Mouse button usually the left one
				if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
					// Creating of another scene
					try {
						// New pane layout to set the scene
						Pane pane = new Pane();
						// Loader for the fxml file
						FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/BookDetailEdit.fxml"));
						// Controller setting for the author editing
						loader.setController(new BookEditController(book, stage));
						// Loading of fxml file to the pane
						pane = (Pane) loader.load();
						// Setting of the scene
						Scene scene = new Scene(pane, 500, 400);
						// The title setting
						stage.setTitle("Edit Book Details");
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
		// Setting mouse click event to the List View
		edit.setOnMouseClicked(editHandler);
		EventHandler<MouseEvent> auditHandler = new EventHandler<MouseEvent>() {
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
						FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/bookaudittrail.fxml"));
						// Controller setting for the author editing
						loader.setController(new bookauditlistController(book, book.getGateway()));
						// Loading of fxml file to the pane
						pane = (Pane) loader.load();
						// Setting of the scene
						Scene scene = new Scene(pane, 500, 400);
						// The title setting
						stage.setTitle("Book Audit");
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
		// Setting mouse click event to the List View
		audit.setOnMouseClicked(auditHandler);
	}

	// Initialize to run stLabels in the fxml as part og the controller
	@FXML
	public void initialize() {
		setLabels();
		setButtonHandler();
	}
}
