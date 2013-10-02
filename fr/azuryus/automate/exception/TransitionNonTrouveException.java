package fr.azuryus.automate.exception;

public class TransitionNonTrouveException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4765651448682090924L;
	private String message;

	
	public TransitionNonTrouveException() {
		this.message = "Transition non trouvée";
	}
	
	public TransitionNonTrouveException(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return this.message;
	}
}
