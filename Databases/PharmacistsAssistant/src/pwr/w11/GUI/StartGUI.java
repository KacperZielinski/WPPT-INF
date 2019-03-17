package pwr.w11.GUI;

/**
 * Created by Kacper on 2018-01-05.
 */

import javafx.application.Application;
import javafx.stage.Stage;

public class StartGUI extends Application
{

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage)
	{
		primaryStage.setTitle("PharmacistAssistant");

		new LoginScreen(primaryStage);

		primaryStage.show();
	}
}
