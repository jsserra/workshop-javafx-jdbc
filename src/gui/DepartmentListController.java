package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Department;
import model.services.DepartmentService;

public class DepartmentListController implements Initializable{

	private DepartmentService service;
	
	@FXML
	private TableView<Department> tableViewDepartment;
	
	@FXML
	private TableColumn<Department, Integer> tableColumnId;
	
	@FXML
	private TableColumn<Department, String> tableColumnName;
	
	@FXML
	private Button btNew;
	
	private ObservableList<Department> obsList; //Vou carregar meus departamentos nessa ObservableList
	
	@FXML
	public void onBtNewAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		Department obj = new Department();
		createDialogForm(obj, "/gui/DepartmentForm.fxml", parentStage);
	}
	
	public void setDepartmentService(DepartmentService service) {
		this.service = service;
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rd) {
		initializeNodes();
		
	}

	//método responsável por: 
	// 1. acessar o serviço, 2. carregar os departamentos, 3. Jogas esses departamentos na observableList 
	//Depois associa a observableLista com a TableView e os departamentos vão aparecer na tela
	public void updateTableView() {
		if (service == null) {
			throw new IllegalStateException("Service was null");
		}
		List<Department> list = service.findAll();
		obsList = FXCollections.observableArrayList(list);//Carregando a lista dentro de observableList
		tableViewDepartment.setItems(obsList); //carregar os itens na tableView e mostrar na tela
	}
	
	//padrão do javaFx para iniciar o comportamento das colunas
	private void initializeNodes() {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
		
		//Macete para TableView acompanhar altura da janela, Instancio stage recebendo janela da cena da minha classe Main
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewDepartment.prefHeightProperty().bind(stage.heightProperty());
	}
	
	/* Nossa função pra carregar a janela do formulário para preencher novo departamento
	 *Esse método vai receber como parâmetro Stage pq quando agente cria um tela de diálogo
	 agente tem que informar que é o Stage (pai) que criou essa janela de diálogo*/
	public void createDialogForm(Department obj, String absoluteName, Stage parentStage) {
		try {
			//vamos criar aqui nossa lógica para abrir a tela de formulário
			// pra carregar uma tela precisamos do objeto da classe FXMLLoader
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));/* O getCalss é padrão da classe
			FXMLLoader. Qual recurso que vamos pegar? É o que vem como parâmetro */
			Pane pane = loader.load();
			//Agora, que vai entrar o stage, quando vou carregar uma janela de diálogo modal
			//na frente de uma janela existente eu tenho que instanciar um novo stage, logo vai ser 
			//um palco na frente do outro
			
			DepartmentFormController controller = loader.getController();
			controller.setDepartment(obj);
			controller.setDepartmentService(new DepartmentService());
			controller.updateFormData();
			
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Enter Department Data");
			dialogStage.setScene(new Scene(pane)); //Quem vai ser o elemento raiz dessa cena? R: Pane
			dialogStage.setResizable(false); //Essa função é para dizer se a janela pode ou não ser redimensionada
			dialogStage.initOwner(parentStage); //Quem que é o Stage pai dessa janela? R: parentStage
			dialogStage.initModality(Modality.WINDOW_MODAL);//Esse método que vai dizer se a janela vai ser modal ou se vai ter outro comportamento
			//nesse caso vou travar ele para não poder mexer na janela anterior até que essa seja fechada
			dialogStage.showAndWait();
		}catch(IOException e)  {
			Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
		}
		
	}

}
