package gui;

import java.net.URL;
import java.util.ResourceBundle;

import gui.util.Constraints;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class DepartmentFormController implements Initializable {

	//vamos ter que colocar a declara��o dos componentes da tela do DepartmentForm.fxml que nesse caso s�o os 2x TextField, 1x label e 2x bot�es
	
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
	
	@FXML
	private void onBtCancelAction() {
		System.out.println("onBtCancelAction");
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

}
