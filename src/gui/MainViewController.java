package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

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
import model.services.DepartmentService;

public class MainViewController implements Initializable {

	// vamos colocar alguns itens de tela para corresponder com os itens do menu da
	// nossa tela
	@FXML
	private MenuItem menuItemSeller;

	@FXML
	private MenuItem menuItemDepartment;

	@FXML
	private MenuItem menuItemAbout;

	// Definindo o m�todos para tratar cada os itens de menu
	@FXML
	public void onMenuItemSellerAction() { // nomeclatura padr�o "event hendler" on + nome do controle + evento
		System.out.println("onMenuItemSeller");
	}

	@FXML //� isso que aqui que vai abrir na tela, ou seja departmentList
	public void onMenuItemDepartmentAction() {
		loadView("/gui/DepartmentList.fxml", (DepartmentListController controller) -> {
			controller.setDepartmentService(new DepartmentService());
			controller.updateTableView();
		}	);
	}

	@FXML
	public void onMenuItemAboutAction() {
		loadView("/gui/About.fxml", x -> {}); 
		//como tenho q passar inicializa��o e About n�o vai precisar carregar nada
		// criei express�o que x leva a nada 
	}

	@Override
	public void initialize(URL uri, ResourceBundle rb) {
		// TODO Auto-generated method stub

	}

	// load = carga --> m�todo para carregar um nova tela, vou passar como par�metro o caminho
	// completo gui\About.fxml
	//synchronized --> como aplica��es gr�ficas s�o multithred essa declara��o n�o vai deixar que interrompa o processamento desse c�digo durante o multithred
	private synchronized <T> void loadView(String absoluteName, Consumer<T> initializingAction) {
		try {
			// pra carregar uma tela precisamos do objeto da classe FXMLLoader
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));/* O getCalss � padr�o da classe
			FXMLLoader. Qual recurso que vamos pegar? � o que vem como par�metro */
			VBox newVBox = loader.load(); // Esse m�todo exige try catch
			
			/* Agora precisamos colocar essa view dentro da janela principal:
			 * 1� Vou precisar pegar uma refer�nica da cena */
			Scene mainScene = Main.getMainScene(); //colocamos no Main metodo e classe static
			VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();
			//acima, Para eu colocar tudo no vbox (tela principal) tenho que pegar o 1� elemento, scrollPane (s� ver .fxml) Peguei minha cena principal
			Node mainMenu = mainVBox.getChildren().get(0);
			mainVBox.getChildren().clear();
			mainVBox.getChildren().add(mainMenu);
			mainVBox.getChildren().addAll(newVBox.getChildren());
			
			//Depois de carregar a janela, vou abaixo informar comando para ativar a fun��o initializingAction,
			//e as duas linhas abaixo v�o executar a fun��o passada como argumento
			T controller = loader.getController(); //criei vari�vel do tipo T que vai retornar qualquer tipo controller, ex.: DepartmentListController
			initializingAction.accept(controller); //Executando a a��o initializingAction, para isso tenho que chamar a fun��o accept do meu Consumer
			
		} catch (IOException e) {
			Alerts.showAlert("IOException", "Error loading view", e.getMessage(), AlertType.ERROR);
		}
	}

}
