package gui;

import java.net.URL;
import java.util.ResourceBundle;

import db.DbException;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import model.entities.Department;
import model.services.DepartmentService;

public class DepartmentFormController implements Initializable {

	private Department entity; //Agora nosso controlado tem uma inst�ncia do departamento e mais abaixo criei m�todo setDepartment para inserir
	
	private DepartmentService service; //XI.criar Depend�ncia DepartmentService e implantar metodo set
	
	//IX. vamos ter que colocar a declara��o dos componentes da tela do DepartmentForm.fxml que nesse caso s�o os 2x TextField, 1x label e 2x bot�es
	
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
			entity = getFormData(); //vou fazer o objeto entity receber esse m�todo getFormData � o respons�vel por pegar os dados na caixinha (textFild) e converter em objeto Department			
			service.saveOrUpdate(entity);
			Utils.currentStage(event).close();
		}catch (DbException e) {
			Alerts.showAlert("Erros saving object", null, e.getMessage(), AlertType.ERROR);
		}
	}
	
	private Department getFormData() {
		Department obj = new Department();
		obj.setId(Utils.tryParseToInt(txtId.getText()));
		obj.setName(txtName.getText());
		return obj;
	}

	public void setDepartment(Department entity) {
		this.entity = entity;		
	}
	
	public void setDepartmentService(DepartmentService service) {
		this.service = service;
	}
	
	@FXML
	private void onBtCancelAction(ActionEvent event) {
		Utils.currentStage(event).close();
	}
	
	//Configurar um Controlador b�sico
	//Aqui na inicializa��o posso colocar algumas restri��es como o Id s� inteiros e no name um limite de caracteres
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO Auto-generated method stub
		initializeNodes();		
	}
	
	private void initializeNodes() {
		Constraints.setTextFieldInteger(txtId);
		Constraints.setTextFieldMaxLength(txtName, 30);
	}

	//Esse m�todo vai copular as caixas de texto txtId e txtName com os dados do objeto Department
	public void updateFormData() {
		if (entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		txtId.setText(String.valueOf(entity.getId()));
		txtName.setText(entity.getName());
	}
}
