//ASSIGN 3 BY AUSTIN DODSON AND FAISAL KHURRAM
package libdemo;

import controller.MenuController;
import javafx.application.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.stage.*;

public class Driver extends Application {

	private static Driver instance = null;
	private Stage stage;
	private BorderPane rootPane;
	private MenuController menuControl;

	public Driver() {
		rootPane = new BorderPane();
		menuControl = new MenuController(this);
	}

	public static void main(String[] args) {

		launch(args);
	}

	public BorderPane getRootPane() {
		return rootPane;
	}

	public Stage getStage() {
		return stage;
	}

	// singleton pattern to create a single instance, if already exist, return exist
	public static Driver getInstance() {
		if (instance == null) {
			instance = new Driver();
		}

		return instance;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Driver app = getInstance();
		app.stage = primaryStage;

		FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/menu.fxml"));
		loader.setController(menuControl);
		MenuBar menu = loader.load();
		app.rootPane.setTop(menu);

		Scene scene = new Scene(app.rootPane, 640, 480);
		app.stage.setTitle("Assign3");
		app.stage.setScene(scene);
		app.stage.show();
	}
}
