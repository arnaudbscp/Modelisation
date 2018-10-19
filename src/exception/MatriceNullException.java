package exception;

/**
 * Exception soulevée si une matrice est NULL.
 * @author Valentin
 *
 */
@SuppressWarnings("serial")
public class MatriceNullException extends Exception{
	
	/**
	 * Message d'erreur.
	 */
	private String message;
	
	/**
	 * Constructeur créant le message.
	 */
	public MatriceNullException() {
		message="Une des deux matrices est NULL.";
	}
	
	/**
	 * Retourne le message d'erreur.
	 */
	public String toString() {
		return message;
	}
}
