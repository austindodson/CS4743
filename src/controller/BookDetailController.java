package controller;

import java.io.IOException;

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

public class BookDetailController {

	private Book book;
	private Stage stage;
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

	public BookDetailController(Book book, Stage stage) {
		this.book = book;
		this.stage = stage;
	}

	//Set the label texts for author's fields. 
	public void setLabels() {
		id.setText(String.valueOf(book.getId()));
		title.setText(book.getTitle());
		//summary.setText(book.getSummary());
		//yearPublished.setText(book.getYearPublished()+"");
		//publisher.setText(book.getPublisher().getPublisherName());
		//isbn.setText(book.getIsbn());
		//dateAdded.setText(book.getDateAdded().toString());
	}

	public void setButtonHandler() {
		EventHandler<MouseEvent> editHandler = new EventHandler<MouseEvent>() {
			//Overriden function for EventHandler
			@Override
			public void handle(MouseEvent mouseEvent) {
				//Mouse button usually the left one
				if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
					//Creating of another scene
					try {
						//New pane layout to set the scene
						Pane pane = new Pane();
						//Loader for the fxml file
						FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/AuthorDetailEdit.fxml"));
						//Controller setting for the author editing
						loader.setController(new BookEditController(book, stage));
						//Loading of fxml file to the pane
						pane = (Pane) loader.load();
						//Setting of the scene
						Scene scene = new Scene(pane, 500, 400);
						//The title setting
						stage.setTitle("Edit Book Details");
						//Setting scene on the stage
						stage.setScene(scene);
						//Show time
						stage.show();
					} catch (IOException e) {
						System.out.println(e.getMessage());
					}
				}
			}
		};
		// Setting mouse click event to the List View
		edit.setOnMouseClicked(editHandler);
	}
	//Initialize to run stLabels in the fxml as part og the controller
	@FXML
	public void initialize() {
		setLabels();
		setButtonHandler();
	}
}
