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
import model.Author;

public class AuthorAuditListController {

	private ArrayList<AuditTrail> auditList;
	private Logger logger = LogManager.getLogger(AuthorAuditListController.class);
	private DBGateway gateway;
	private Author author;

	@FXML
	private ListView<String> list = new ListView<String>();
	@FXML
	private Button delete;
	@FXML
	private Label title;

	public AuthorAuditListController(Author author, DBGateway gateway) {
		this.gateway = gateway;
		this.author = author;
	}

	public void setListView() {

		title.setText("Audit Table for " + author.getFirstname());

		auditList = gateway.getAuthorAudits(author.getId());

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
