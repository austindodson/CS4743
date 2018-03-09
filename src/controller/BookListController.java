package controller;

import model.Book;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import gateway.DBGateway;
import javafx.collections.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.layout.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.stage.*;
import model.Author;

public class BookListController {

	private ArrayList<Book> booksList;
	private Logger logger = LogManager.getLogger(BookListController.class);
	private DBGateway gateway;

	@FXML
	private ListView<String> list = new ListView<String>();
	@FXML
	private Button delete;

	public BookListController(DBGateway gateway) {
		this.gateway = gateway;
	}

	public void setListView() {

		booksList = gateway.getBooks();

		ObservableList<String> bookItems = FXCollections.observableArrayList();
		// fill the ObservableList with the author names
		for (int i = 0; i < booksList.size(); i++) {
			bookItems.add(booksList.get(i).getTitle());
		}
		// set the list with the ObservableList
		list.setItems(bookItems);
		EventHandler<MouseEvent> doubleClick = new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent mouseEvent) {
				if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
					if (mouseEvent.getClickCount() == 2) {

						Book selected = findBook(list.getSelectionModel().getSelectedItem());
						//To make a new window
						try {
							Stage stage = new Stage();
							Pane pane = new Pane();
							FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/BookDetail.fxml"));
							loader.setController(new BookDetailController(selected, stage));
							pane = (Pane) loader.load();
							// set the scene with the pane
							Scene scene = new Scene(pane, 800, 400);
							// set title
							stage.setTitle("Book Details");
							// put the scene on the stage
							stage.setScene(scene);
							// show time
							stage.show();
						} catch (IOException e) {
							System.out.println(e.getMessage());
							logger.error("Error opening bookdetail.fxml: " + e.getMessage());
						}

						logger.info("Book " + selected.getTitle() + " selected.");
					}
				}
			}
		};

		list.setOnMouseClicked(doubleClick);
	}

	public void setButtonHandler() {
		EventHandler<MouseEvent> deleteHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				// MouseButton usually the left key
				if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
					Book selected = findBook(list.getSelectionModel().getSelectedItem());
					// delete author in the database
					gateway.deleteBook(selected);
					// delete author from the authorsList
					booksList.remove(selected);
					setListView();
				}
			}
		};
		delete.setOnMouseClicked(deleteHandler);
	}

	// Activate fxml to set List View.
	@FXML
	public void initialize() {
		setListView();
		setButtonHandler();
	}
	
	// On the base of author's ID find author list.
	public Book findBook(String name) {
		for (int i = 0; i < booksList.size(); i++) {
			if (booksList.get(i).getTitle().equalsIgnoreCase(name)) {
				return booksList.get(i);
			}
		}
		return null;
	}

}
