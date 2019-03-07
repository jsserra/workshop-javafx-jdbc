package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

public class Main extends Application {
	
	private static Scene mainScene;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/MainView.fxml")); //loader = carregador
			ScrollPane scrollPane = loader.load(); //substitui Parente pelo ScrollPane
			
			scrollPane.setFitToHeight(true); //ajustando na vertical o scrollPane na janela
			scrollPane.setFitToWidth(true); //ajustando na horizontal o scrollPane na janela
			
			mainScene = new Scene(scrollPane);
			primaryStage.setScene(mainScene); //primaryStage é o Palco
			primaryStage.setTitle("Sample JavaFX application");
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Scene getMainScene() {
		return mainScene;
	}
}
