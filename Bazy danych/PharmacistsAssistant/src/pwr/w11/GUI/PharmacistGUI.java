package pwr.w11.GUI;

import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import pwr.w11.users.UserManagement;
import pwr.w11.users.UserType;
import java.io.IOException;

/**
 * Created by Kacper on 2018-01-10.
 */
public class PharmacistGUI
{
	private BorderPane borderPane;
	private VBox vbox;
	private TableView table;
	private Stage stage;
	private Label actionLabel;
	private Configuration cfg;
	private SessionFactory sessionFactory;
	private UserType userType;
	private static final int WINDOW_WIDTH = 900;
	private static final int WINDOW_HEIGHT = 700;

	public PharmacistGUI(Stage stage, String login, String pass)
	{
		//TODO REMOVE AFTER ALPHA
		if(pass == null)
			pass = "";

		userType = new UserManagement(login).getUserType();

		cfg = new Configuration();
		cfg.configure("hibernate.cfg.xml");
		cfg.getProperties().setProperty("hibernate.connection.username", login);
		cfg.getProperties().setProperty("hibernate.connection.password", pass);
		sessionFactory = cfg.buildSessionFactory();

		pass = null;

		Parent root = null;
		try
		{
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
			root = (BorderPane) fxmlLoader.load();
			Controller controller = fxmlLoader.getController();
			controller.setLogin(login);
			controller.setUserType(userType);
			controller.setSessionFactory(sessionFactory);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		this.stage = stage;
		this.stage.setScene(new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT));
		this.stage.setResizable(true);
	}

	private void load()
	{
		GridPane gridPane = new GridPane();
		gridPane.setPrefHeight(65);
		gridPane.setStyle("-fx-background-color: #336699;");

		GridPane rightGridPane = new GridPane();
		rightGridPane.setPrefWidth(65);
		rightGridPane.setStyle("-fx-background-color: #336699;");

		borderPane = new BorderPane();

		actionLabel = new Label("Query 1");
		actionLabel.setFont(new Font("Arial", 20));

		table = new TableView();
		table.setBorder(new Border(new BorderStroke(Color.BLACK,
		BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));

		ChoiceBox<String> choiceAction = new ChoiceBox<>(FXCollections.observableArrayList("Select 1", "Select2", "Select3"));

		choiceAction.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) ->
		{
			vbox.getChildren().removeAll(actionLabel, table);
			vbox.setSpacing(5);
			vbox.setPadding(new Insets(10, 0, 0, 10));
			vbox.getChildren().addAll(actionLabel, table);
		});

		gridPane.getChildren().add(choiceAction);

		vbox = new VBox();
		borderPane.setTop(gridPane);
		borderPane.setCenter(vbox);
		borderPane.setLeft(rightGridPane);
	}
}
