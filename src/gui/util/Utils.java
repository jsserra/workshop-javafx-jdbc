package gui.util;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

public class Utils {
	
	/* Criando médodo currentStage "palco atual" para retornar um stage (palco),
	 * esse método vai receber como argumento ActionEvent que é evento que o botão recebeu,
	 * Para eu acessar o stage onde meu controler que recebeu o evento está. Ex.: Se eu clico no botão
	 * eu vou pegar o stage daquele botão
	 */
	public static Stage currentStage(ActionEvent event) {
		//1 retornar event.getSource() porém o getSource é um objeto muito genérico
		//2 Então vou transformalo em Node fazendo um Downcast e coloco tudo entre parênteses
		//3 A partir desse node vou chamar a função getScene() para pegar a cena
		//4 depois chamo a função getWindow() para chamar a tela fazendo um downcast para Stage
		return (Stage)((Node)event.getSource()).getScene().getWindow();
	}
	
	//XI. Método criado para não precisar criar excessão toda vez que for transforma String p/ Inteiro 
	public static Integer tryParseToInt(String str) {
		try {
		return Integer.parseInt(str);
		}catch(NumberFormatException e) {
			return null;
		}
	}

}
