package controller;

import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import libdemo.Driver;
import gateway.DBGateway;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.WindowEvent;

public class MenuController {

	@FXML
	private MenuBar menuBar;
	@FXML
	private MenuItem quit;
	@FXML
	private MenuItem authorList;
	@FXML
	private MenuItem authorAdd;
	private Logger logger = LogManager.getLogger(MenuController.class);
	private DBGateway gateway;

	private Driver driver;

	public MenuController(Driver driver) {
		this.driver = driver;
		gateway = new DBGateway();
	}

	//Action handler for menu items
	@FXML
	private void handleMenuAction(ActionEvent event) throws Exception {
		if (event.getSource() == authorList) {
			logger.info("Author list selected.");
			changeToAuthorScene();
		} else if (event.getSource() == authorAdd) {
			logger.info("Add author selected.");
			changeToAuthorAddScene();
		} else if (event.getSource() == quit) {
			logger.info("Quit selected.");
			System.exit(0);
		}
	}

	//Border Layout center settings
	private void changeToAuthorScene() throws IOException {
		Driver app = driver.getInstance();
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/authorList.fxml"));
			loader.setController(new AuthorListController(gateway));
			Pane pane = (Pane) loader.load();
			app.getRootPane().setCenter(pane);
		} catch (IOException e) {
			logger.error("Error opening authorList.fxml " + e.getMessage());
			throw new IOException(e);
		}
	}

	private void changeToAuthorAddScene() throws IOException {
		Driver app = driver.getInstance();
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/addAuthor.fxml"));
			loader.setController(new AuthorAddController(driver, gateway));
			Pane pane = (Pane) loader.load();
			// replace current content with the new content pane
			app.getRootPane().setCenter(pane);
		} catch (IOException e) {
			logger.error("Error opening authorlist.fxml " + e.getMessage());
			throw new IOException(e);
		}
	}

	@FXML
	public void initialize() {
		//Database Gateway connection closed as stage get close
		driver.getInstance().getStage().setOnCloseRequest(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent we) {
				gateway.close();
				logger.info("Application closed.");
			}
		});
	}
}