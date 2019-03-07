package gui.util;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

public class Utils {
	
	/* Criando m�dodo currentStage "palco atual" para retornar um stage (palco),
	 * esse m�todo vai receber como argumento ActionEvent que � evento que o bot�o recebeu,
	 * Para eu acessar o stage onde meu controler que recebeu o evento est�. Ex.: Se eu clico no bot�o
	 * eu vou pegar o stage daquele bot�o
	 */
	public static Stage currentStage(ActionEvent event) {
		//1 retornar event.getSource() por�m o getSource � um objeto muito gen�rico
		//2 Ent�o vou transformalo em Node fazendo um Downcast e coloco tudo entre par�nteses
		//3 A partir desse node vou chamar a fun��o getScene() para pegar a cena
		//4 depois chamo a fun��o getWindow() para chamar a tela fazendo um downcast para Stage
		return (Stage)((Node)event.getSource()).getScene().getWindow();
	}
	
	//XI. M�todo criado para n�o precisar criar excess�o toda vez que for transforma String p/ Inteiro 
	public static Integer tryParseToInt(String str) {
		try {
		return Integer.parseInt(str);
		}catch(NumberFormatException e) {
			return null;
		}
	}

}
