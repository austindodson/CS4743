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
	private String matchingString = "";

	@FXML
	private ListView<String> list = new ListView<String>();
	@FXML
	private Button delete;
	@FXML
	private Button goSearch;
	@FXML
	private TextField search;
	@FXML
	private Button first;
	@FXML
	private Button prev;
	@FXML
	private Button next;
	@FXML
	private Button last;
	@FXML 
	private Label page;
	
	private ObservableList<String> bookItems;
	
	private int listStart;
	
	public BookListController(DBGateway gateway) {
		this.gateway = gateway;
		this.listStart = 0;
	}

	@FXML
	public void setListView() {
		

		if (!matchingString.equals("")) {
			booksList = gateway.getBooksLike(matchingString);
			if (booksList.size() < 50) {
				int start = 0;
				if (booksList.size() != 0) {
					start = 1;
				}
				page.setText("Fetched records "+ start +" to "+ booksList.size()+" out of "+ booksList.size());
			}else {
				int start = 0;
				if (booksList.size() != 0) {
					start = listStart+1;
				}
				int temp = start+50;
				if(temp >= booksList.size()) {
					temp = booksList.size();
				}
				page.setText("Fetched records "+ start +" to "+ temp+" out of "+ booksList.size());
			}
		
		} else {
			booksList = gateway.getBooks();
			page.setText("Fetched records 1 to "+ booksList.size()+" out of "+ booksList.size());
		}

		bookItems = FXCollections.observableArrayList();
		// fill the ObservableList with the author names
		if ((booksList.size()-listStart) >= 50) {
			for (int i = listStart; i < listStart+50; i++) {
				bookItems.add(booksList.get(i).getTitle());
			}
		}
		else {
			for (int i = listStart; i < listStart+(booksList.size()-listStart); i++) {
				bookItems.add(booksList.get(i).getTitle());
			}
		}

		// set the list with the ObservableList
		list.setItems(bookItems);
		//page.setText("Fetched records 1 to "+ bookItems.size()+" out of "+ booksList.size());
		EventHandler<MouseEvent> doubleClick = new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent mouseEvent) {
				if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
					if (mouseEvent.getClickCount() == 2) {

						Book selected = findBook(list.getSelectionModel().getSelectedItem());
						// To make a new window
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
		matchingString = "";
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

		EventHandler<MouseEvent> searchHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
					matchingString = search.getText();
					listStart = 0;
					page.setText("Fetched records 1 to "+ bookItems.size()+" out of "+ booksList.size());
					setListView();
				}
			}
		};
		goSearch.setOnMouseClicked(searchHandler);
		
		EventHandler<MouseEvent> firstHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
					if (listStart != 0 && booksList.size() > 50) {
						if (search.getText() != null || search.getText() != "") {
							matchingString = search.getText();
						}
						page.setText("Fetched records 1 to "+ bookItems.size()+" out of "+ booksList.size());
						listStart=0;
						setListView();
					}
				}
			}
		};
		first.setOnMouseClicked(firstHandler);
		
		EventHandler<MouseEvent> prevHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
					if ((listStart-50) >= 0) {	
						if (search.getText() != null || search.getText() != "") {
							matchingString = search.getText();
						}
						listStart=listStart-50;
						int start = listStart+1;
						int listtemp = start+(booksList.size()-listStart);
						page.setText("Fetched records "+ start +" to "+ listtemp +" out of "+ booksList.size());
						setListView();
					}
				}
			}
		};
		prev.setOnMouseClicked(prevHandler);
		
		EventHandler<MouseEvent> nextHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
					if((listStart+50) < booksList.size()) {	
						if (search.getText() != null || search.getText() != "") {
							matchingString = search.getText();
						}
						listStart=listStart+50;
						int start = listStart+1;
						int listtemp = start+(booksList.size()-listStart);
						page.setText("Fetched records "+ start +" to "+ listtemp +" out of "+ booksList.size());
						setListView();
					}
				}
			}
		};
		next.setOnMouseClicked(nextHandler);
		
		EventHandler<MouseEvent> lastHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
					if (search.getText() != null || search.getText() != "") {
						matchingString = search.getText();
					}
					if (listStart <= (booksList.size()-50) && booksList.size() > 50) {
						listStart = booksList.size()-50;
						page.setText("Fetched records "+ listStart +" to "+ booksList.size() +" out of "+ booksList.size());
						setListView();
					}
				}
			}
		};
		last.setOnMouseClicked(lastHandler);
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
