package controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import authdemo.auth.RBACPolicyAuth;
import authdemo.auth.Authenticator;
import authdemo.auth.AuthenticatorLocal;
import libdemo.Driver;
import misc.CryptoStuff;
import model.Author;
import model.AuthorBook;
import model.Book;
import model.Publisher;
import gateway.DBGateway;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Pair;

public class MenuController {

	@FXML
	private MenuBar menuBar;
	@FXML
	private MenuItem quit;
	@FXML
	private MenuItem login;
	@FXML
	private MenuItem logout;
	@FXML
	private MenuItem authorList;
	@FXML
	private MenuItem authorAdd;
	@FXML
	private MenuItem bookList;
	@FXML
	private MenuItem bookAdd;
	@FXML
	private MenuItem pubGen;
	@FXML
	private MenuItem ten;
	private Logger logger = LogManager.getLogger(MenuController.class);
	private DBGateway gateway;

	private Driver driver;

	//MenuController is the app-level controller for this program
	//so it will have an authenticator to use for logging in
	private Authenticator auth;
	
	//user's local session id
	//session id should not normally be an int (too easy to guess other session ids) 
	int sessionId;

	public MenuController(Driver driver) {
		this.driver = driver;
		gateway = new DBGateway();
		//create an authenticator
		auth = new AuthenticatorLocal();
		//default to no session
		sessionId = Authenticator.INVALID_SESSION;
	}

	// Action handler for menu items
	/**
	 * @param event
	 * @throws Exception
	 */
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
		} else if (event.getSource() == bookList) {
			logger.info("Book List selected.");
			changeToBookScene();
		} else if (event.getSource() == bookAdd) {
			logger.info("Add book selected.");
			changeToBookAddScene();
		}
		else if (event.getSource() == login) {
			logger.info("Login selected.");
			doLogin();
		}
		else if (event.getSource() == logout) {
			logger.info("Logout selected.");
		}
		else if (event.getSource() == pubGen) {
			logger.info("pubGen selected");
			publisherReports();
		}
	/*else if (event.getSource() == ten){
			generateTenThousand();
		}*/
	}
	
	/*private void generateTenThousand() {
		ArrayList<Author> authors = gateway.getAuthors();
		ArrayList<Publisher> pubs = gateway.getPublishers();
		ArrayList<Book> books = gateway.getBooks();
		Random random = new Random();
		
		for(Book book: books) {
			System.out.println(book);
			Author auth = authors.get(random.nextInt(authors.size()));
			System.out.println(auth);
			AuthorBook ab = new AuthorBook(auth.getId(), book.getId(), random.nextInt(100), true);
			System.out.println(ab);
			gateway.saveAuthorBook(ab, book);
		}
		System.out.println("10000!!!!!!!!!!!!!!!1");
	}*/

	private void publisherReports() throws IOException{
		Driver app = driver.getInstance();
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/pubReports.fxml"));
			loader.setController(new pubReportsController(gateway));
			Pane pane = (Pane) loader.load();
			app.getRootPane().setCenter(pane);
		} catch (IOException e) {
			logger.error("Error opening pubReports"
					+ "pubReports.fxml " + e.getMessage());
			throw new IOException(e);
		}
	}
	
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

	// Border Layout center settings
	private void changeToBookScene() throws IOException {
		Driver app = driver.getInstance();
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/BooksList.fxml"));
			loader.setController(new BookListController(gateway));
			Pane pane = (Pane) loader.load();
			app.getRootPane().setCenter(pane);
		} catch (IOException e) {
			logger.error("Error opening BooksList.fxml " + e.getMessage());
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
			logger.error("Error opening addAuthor.fxml " + e.getMessage());
			throw new IOException(e);
		}
	}

	private void changeToBookAddScene() throws IOException {
		Driver app = driver.getInstance();
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/addBook.fxml"));
			loader.setController(new BookAddController(driver, gateway));
			Pane pane = (Pane) loader.load();
			// replace current content with the new content pane
			app.getRootPane().setCenter(pane);
		} catch (IOException e) {
			logger.error("Error opening addBook.fxml " + e.getMessage());
			throw new IOException(e);
		}
	}

	@FXML
	public void initialize() {
		// Database Gateway connection closed as stage get close
		driver.getInstance().getStage().setOnCloseRequest(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent we) {
				gateway.close();
				logger.info("Application closed.");
			}
		});
	}
	
	private void doLogin() {
		//display login modal dialog. get login (username) and password
		//key is login, value is pw
		Pair<String, String> creds = LoginDialog.showLoginDialog();
		if(creds == null) //canceled
			return;
		
		String userName = creds.getKey();
		String pw = creds.getValue();
		
		//hash password
		String pwHash = CryptoStuff.sha256(pw);
		
		//send login and hashed pw to authenticator
		try {
			//if get session id back, then replace current session
			sessionId = auth.loginSha256(userName, pwHash);
			logger.info("session id is " + sessionId);
			
		} catch (LoginException e) {
			//else display login failure
			Alert alert = new Alert(AlertType.WARNING);
			alert.getButtonTypes().clear();
			ButtonType buttonTypeOne = new ButtonType("OK");
			alert.getButtonTypes().setAll(buttonTypeOne);
			alert.setTitle("Login Failed");
			alert.setHeaderText("The user name and password you provided do not match stored credentials.");
			alert.showAndWait();

			return;
		}
		
		//restrict access to GUI controls based on current login session
		updateGUIAccess();
	}
	
	private void updateGUIAccess() {
		//if logged in, login should be disabled
		if(sessionId == Authenticator.INVALID_SESSION)
			login.setDisable(false);
		else
			login.setDisable(true);
		
		//if not logged in, logout should be disabled
		if(sessionId == Authenticator.INVALID_SESSION)
			logout.setDisable(true);
		else
			logout.setDisable(false);
		
		//update menu info based on current user's access privileges
//		if(auth.hasAccess(sessionId, ABACPolicyAuthDemo.CAN_ACCESS_CHOICE_1))
//			choice1.setDisable(false);
//		else 
//			choice1.setDisable(true);
//		if(auth.hasAccess(sessionId, ABACPolicyAuthDemo.CAN_ACCESS_CHOICE_2))
//			choice2.setDisable(false);
//		else 
//			choice2.setDisable(true);
//		if(auth.hasAccess(sessionId, ABACPolicyAuthDemo.CAN_ACCESS_CHOICE_3))
//			choice3.setDisable(false);
//		else 
//			choice3.setDisable(true);
	}
}
