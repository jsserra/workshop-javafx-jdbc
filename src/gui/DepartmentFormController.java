package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import db.DbException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Department;
import model.exceptions.ValidationException;
import model.services.DepartmentService;

public class DepartmentFormController implements Initializable {

	private Department entity; //Agora nosso controlado tem uma instância do departamento e mais abaixo criei método setDepartment para inserir
	
	private DepartmentService service; //XI.criar Dependência DepartmentService e implantar metodo set
	
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>(); //XII. Receber a lista que vai ser atualizada, que vai permitir outros objetos se inscreverem nessa lista e receberem um evento, para permitir que outros objetos se increvam tenho que criar método para isso
	
	//IX. vamos ter que colocar a declaração dos componentes da tela do DepartmentForm.fxml que nesse caso são os 2x TextField, 1x label e 2x botões
	
	@FXML
	private TextField txtId;
	
	@FXML
	private TextField txtName;
	
	@FXML 
	private Label labelErrorName;
	
	@FXML
	private Button btSave;
	
	@FXML
	private Button btCancel;
	
	@FXML
	private void onBtSaveAction(ActionEvent event) {
		if (entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		if (service == null) {
			throw new IllegalStateException("Service was null");	
		}
		try {
			entity = getFormData(); //vou fazer o objeto entity receber esse método getFormData é o responsável por pegar os dados na caixinha (textFild) e converter em objeto Department			
			service.saveOrUpdate(entity);
			notifyDataChangeListeners();//XII. método que vai fazer a notificação
			Utils.currentStage(event).close();
		}catch(ValidationException e) {
			setErrorMessages(e.getErrors());
		}
		
		catch (DbException e) {
			Alerts.showAlert("Erros saving object", null, e.getMessage(), AlertType.ERROR);
		}
	}
	
	private void notifyDataChangeListeners() {
		for(DataChangeListener listener : dataChangeListeners) {
			listener.onDataChanged();
		}
		
	}

	private Department getFormData() {
		Department obj = new Department();
		//XIII Vamos instanciar a exceção personalizada ValidationException e para agilizar vamos só não permitir que campo name fique vazio
		ValidationException exception = new ValidationException("Validation error");
		
		obj.setId(Utils.tryParseToInt(txtId.getText()));
		
		if(txtName.getText()==null || txtName.getText().trim().equals("")) { //trim é para eliminar qualquer conteúdo vazio
			exception.addErrors("name", "Field can't be empty");
		}
		obj.setName(txtName.getText());
		
		//Aqui eu estou testando se na minha coleção de erro tem pelo menos um erro
		if (exception.getErrors().size() > 0) {
			throw exception;
		}
		return obj;
		
		
	}

	public void setDepartment(Department entity) {
		this.entity = entity;		
	}
	
	public void setDepartmentService(DepartmentService service) {
		this.service = service;
	}
	
	//XII. esse método vai inscrever esse listener na minha lista, inscrever aqui quer dizer criar
	public void subscribeDataChangeListener(DataChangeListener listener) {
		dataChangeListeners.add(listener);
		
	}
	
	@FXML
	private void onBtCancelAction(ActionEvent event) {
		Utils.currentStage(event).close();
	}
	
	//Configurar um Controlador básico
	//Aqui na inicialização posso colocar algumas restrições como o Id só inteiros e no name um limite de caracteres
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO Auto-generated method stub
		initializeNodes();		
	}
	
	private void initializeNodes() {
		Constraints.setTextFieldInteger(txtId);
		Constraints.setTextFieldMaxLength(txtName, 30);
	}

	//Esse método vai copular as caixas de texto txtId e txtName com os dados do objeto Department
	public void updateFormData() {
		if (entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		txtId.setText(String.valueOf(entity.getId()));
		txtName.setText(entity.getName());
	}
	
	//Esse método vai carregar os erros percorrendo minha coleção map, preenchendo minha caixa de texto (Label)
	private void setErrorMessages(Map<String, String> errors) {
		Set<String> fields = errors.keySet();
		if(fields.contains("name")) {
			labelErrorName.setText(errors.get("name"));
		}
		
	}
}
