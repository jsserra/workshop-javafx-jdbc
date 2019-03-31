package model.exceptions;

import java.util.HashMap;
import java.util.Map;

public class ValidationException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	/* Como esse tipo de validação é para cada campo de uma tela, vou precisar criar um objeto MAP 
	 * qué um composto de chave e valor, onde a chave é o campo (ex nome) e valor é o erro para aquele campo.
	 * */
	private Map<String, String> errors = new HashMap<>();
	
	public ValidationException(String msg) {
		super(msg);
		// TODO Auto-generated constructor stub
	}
	
	public Map<String, String> getErrors(){
		return errors;
	}
	
	public void addErrors(String fieldName, String errorMessage) {
		errors.put(fieldName, errorMessage);
	}
}
