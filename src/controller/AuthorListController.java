package controller;

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

public class AuthorListController {

	private ArrayList<Author> authorsList;
	private Logger logger = LogManager.getLogger(AuthorListController.class);
	private DBGateway gateway;

	@FXML
	private ListView<String> list = new ListView<String>();
	@FXML
	private Button delete;

	public AuthorListController(DBGateway gateway) {
		this.gateway = gateway;
	}

	public void setListView() {

		authorsList = gateway.getAuthors();

		ObservableList<String> dogItems = FXCollections.observableArrayList();
		// fill the ObservableList with the author names
		for (int i = 0; i < authorsList.size(); i++) {
			dogItems.add(authorsList.get(i).getFirstname());
		}
		// set the list with the ObservableList
		list.setItems(dogItems);
		EventHandler<MouseEvent> doubleClick = new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent mouseEvent) {
				if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
					if (mouseEvent.getClickCount() == 2) {

						Author selected = findAuthor(list.getSelectionModel().getSelectedItem());
						//To make a new window
						try {
							Stage stage = new Stage();
							Pane pane = new Pane();
							FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/AuthorDetail.fxml"));
							loader.setController(new AuthorDetailController(selected, stage));
							pane = (Pane) loader.load();
							// set the scene with the pane
							Scene scene = new Scene(pane, 800, 400);
							// set title
							stage.setTitle("Author Details");
							// put the scene on the stage
							stage.setScene(scene);
							// show time
							stage.show();
						} catch (IOException e) {
							System.out.println(e.getMessage());
							logger.error("Error opening authordetail.fxml: " + e.getMessage());
						}

						logger.info("Author " + selected.getFirstname() + " selected.");
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
					Author selected = findAuthor(list.getSelectionModel().getSelectedItem());
					// delete author in the database
					gateway.deleteAuthor(selected);
					// delete author from the authorsList
					authorsList.remove(selected);
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
	public Author findAuthor(String name) {
		for (int i = 0; i < authorsList.size(); i++) {
			if (authorsList.get(i).getFirstname().equalsIgnoreCase(name)) {
				return authorsList.get(i);
			}
		}
		return null;
	}

}
