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
import model.AuditTrail;
import model.Author;
import model.Book;

public class bookauditlistController {

	private ArrayList<AuditTrail> auditList;
	private Logger logger = LogManager.getLogger(bookauditlistController.class);
	private DBGateway gateway;
	private Book book;

	@FXML
	private ListView<String> list = new ListView<String>();
	@FXML
	private Button delete;
	@FXML
	private Label title;

	public bookauditlistController(Book book, DBGateway gateway) {
		this.gateway = gateway;
		this.book = book;
	}

	public void setListView() {

		title.setText("Audit Table for " + book.getTitle());

		auditList = gateway.getAudits(book.getId());

		ObservableList<String> auditItems = FXCollections.observableArrayList();
		// fill the ObservableList with the author names
		for (int i = 0; i < auditList.size(); i++) {
			auditItems.add(auditList.get(i).getMessage());
		}
		// set the list with the ObservableList
		list.setItems(auditItems);
	}

	// Activate fxml to set List View.
	@FXML
	public void initialize() {
		setListView();
	}

}
