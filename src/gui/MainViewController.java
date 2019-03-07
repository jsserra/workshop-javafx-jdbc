package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class MainViewController implements Initializable {

	// vamos colocar alguns itens de tela para corresponder com os itens do menu da
	// nossa tela
	@FXML
	private MenuItem menuItemSeller;

	@FXML
	private MenuItem menuItemDepartment;

	@FXML
	private MenuItem menuItemAbout;

	// Definindo o métodos para tratar cada os itens de menu
	@FXML
	public void onMenuItemSellerAction() { // nomeclatura padrão "event hendler" on + nome do controle + evento
		System.out.println("onMenuItemSeller");
	}

	@FXML
	public void onMenuItemDepartmentAction() {
		loadView("/gui/DepartmentList.fxml");
	}

	@FXML
	public void onMenuItemAboutAction() {
		loadView("/gui/About.fxml");
	}

	@Override
	public void initialize(URL uri, ResourceBundle rb) {
		// TODO Auto-generated method stub

	}

	// load = carga --> método para carregar um nova tela, vou passar como parâmetro o caminho
	// completo gui\About.fxml
	//synchronized --> como aplicações gráficas são multithred essa declaração não vai deixar que interrompa o processamento desse código durante o multithred
	private synchronized void loadView(String absoluteName) {
		try {
			// pra carregar uma tela precisamos do objeto da classe FXMLLoader
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));/* O getCalss é padrão da classe
			FXMLLoader. Qual recurso que vamos pegar? É o que vem como parâmetro */
			VBox newVBox = loader.load(); // Esse método exige try catch
			
			/* Agora precisamos colocar essa view dentro da janela principal:
			 * 1° Vou precisar pegar uma referênica da cena */
			Scene mainScene = Main.getMainScene(); //colocamos no Main metodo e classe static
			VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();
			//acima, Para eu colocar tudo no vbox (tela principal) tenho que pegar o 1° elemento, scrollPane (só ver .fxml) Peguei minha cena principal
			Node mainMenu = mainVBox.getChildren().get(0);
			mainVBox.getChildren().clear();
			mainVBox.getChildren().add(mainMenu);
			mainVBox.getChildren().addAll(newVBox.getChildren());
			
		} catch (IOException e) {
			Alerts.showAlert("IOException", "Error loading view", e.getMessage(), AlertType.ERROR);
		}
	}

}
