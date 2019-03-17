package pwr.w11.GUI;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * LoginScreen for user
 * It allows to login to database
 */
public class LoginScreen
{
	private static final int WINDOW_WIDTH = 300;
	private static final int WINDOW_HEIGHT = 200;

	private VBox vBox;
	private Stage stage;
	private TextField userTextField;
	private PasswordField passField;

	/**
	 * Creates LoginScreen GUI
	 * @param primaryStage javaFX Stage
	 */
	public LoginScreen(Stage primaryStage)
	{
		this.stage = primaryStage;

		load();

		Scene scene = new Scene(vBox, WINDOW_WIDTH, WINDOW_HEIGHT);
		stage.setScene(scene);
		stage.setMinHeight(WINDOW_HEIGHT);
		stage.setMinWidth(WINDOW_WIDTH);
		stage.setResizable(false);

		stage.setOnCloseRequest(e ->
		{
			Platform.exit();
			System.exit(0);
		});
	}

	/**
	 * This method creates a content: container, buttons, textfield
	 */
	private void load()
	{
		vBox = new VBox();
		vBox.setAlignment(Pos.CENTER);
		vBox.setSpacing(10);
		vBox.setPadding(new Insets(5, 5, 5, 5));

		Text welcomeMessage = new Text("Welcome!");
		welcomeMessage.setFont(Font.font("Tahoma", FontWeight.BOLD, 18));
		welcomeMessage.setFill(Color.WHITE);

		Text pleaseLoginMessage = new Text("Please login!");
		pleaseLoginMessage.setFont(Font.font("Tahoma", FontWeight.EXTRA_BOLD, 14));

		userTextField = new TextField();
		userTextField.setPromptText("login");

		passField = new PasswordField();
		passField.setPromptText("password");

		userTextField.setOnKeyPressed(event ->
		{
			if(event.getCode() == KeyCode.ENTER)
				login();
		});

		passField.setOnKeyPressed(event ->
		{
			if(event.getCode() == KeyCode.ENTER)
				login();
		});

		Button btn = new Button("Login");
		btn.setOnAction(event -> login());

		VBox inputs = new VBox();
		inputs.getChildren().addAll(userTextField, passField);
		inputs.setPadding(new Insets(10, 2, 10, 2));

		vBox.getChildren().addAll(welcomeMessage, pleaseLoginMessage, inputs, btn);
		vBox.setBackground(new Background(new BackgroundFill(Color.valueOf("#336699"), CornerRadii.EMPTY, Insets.EMPTY)));
	}

	/**
	 * Tries log to application GUI, updates connection label and validates player name.
	 */
	private void login()
	{
		String name = userTextField.getText();
		String pass = passField.getText();

		if (name.matches("[\\w]+"))
			new PharmacistGUI(stage, name, pass);

		else
		{
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Error");
			alert.setHeaderText(null);
			alert.setContentText("Your name is invalid. Use only alphanumeric characters");

			alert.showAndWait();
		}
	}
}
