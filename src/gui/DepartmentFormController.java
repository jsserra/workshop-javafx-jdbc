package gui;

import java.net.URL;
import java.util.ResourceBundle;

import gui.util.Constraints;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Department;

public class DepartmentFormController implements Initializable {

	private Department entity; //Agora nosso controlado tem uma instância do departamento e mais abaixo criei método setDepartment para inserir
	
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
	private void onBtSaveAction() {
		System.out.println("onBtSaveAction");			
	}
	
	public void setDepartment(Department entity) {
		this.entity = entity;		
	}
	
	@FXML
	private void onBtCancelAction() {
		System.out.println("onBtCancelAction");
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
}
